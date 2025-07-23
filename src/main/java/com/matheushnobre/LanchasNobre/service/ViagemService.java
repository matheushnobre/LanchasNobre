package com.matheushnobre.LanchasNobre.service;

import com.matheushnobre.LanchasNobre.entity.Viagem;
import com.matheushnobre.LanchasNobre.exception.CidadeInvalidaException;
import com.matheushnobre.LanchasNobre.exception.DataInvalidaException;
import com.matheushnobre.LanchasNobre.exception.RegistroNaoEncontradoException;
import com.matheushnobre.LanchasNobre.repository.ViagemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViagemService {

    @Autowired
    private ViagemRepository viagemRepository;

    @Transactional
    public Viagem salvar(Viagem viagem){
        // Uma viagem, para ser salva, deve seguir as seguintes regras de negócio definidas em validarViagem()
        validarViagem(viagem);
        return viagemRepository.save(viagem);
    }

    public List<Viagem> listarTodas(){
        return viagemRepository.findAll();
    }

    @Transactional
    public Viagem atualizar(Long id, Viagem viagem){
        // Para atualizar, seguiremos a mesma regra da criação, validando a viagem com o método validarViagem

        // Procura por viagem com ID solicitado
        Viagem viagemAtualizada = viagemRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Viagem com id " + id + "nao encontrada."));

        // Atualiza campos desejados
        if(viagem.getCidadeOrigem() != null) viagemAtualizada.setCidadeOrigem(viagem.getCidadeOrigem());
        if(viagem.getCidadeDestino() != null) viagemAtualizada.setCidadeDestino(viagem.getCidadeDestino());
        if(viagem.getDataPartida() != null) viagemAtualizada.setDataPartida(viagem.getDataPartida());
        if(viagem.getDataChegada() != null) viagemAtualizada.setDataChegada(viagem.getDataChegada());
        if(viagem.getLancha() != null) viagemAtualizada.setLancha(viagem.getLancha());
        if(viagem.getPreco() != null) viagemAtualizada.setPreco(viagem.getPreco());

        // Valida e persiste a atualização
        validarViagem(viagemAtualizada);
        return viagemRepository.save(viagemAtualizada);
    }

    public boolean deletarPorId(Long id){
        if(!viagemRepository.existsById(id)) return false;
        viagemRepository.deleteById(id);
        return true;
    }

    private boolean validarViagem(Viagem viagem){
        // 1. Cidade de origem diferente da cidade de destino
        // 2. Horário de chegada superior ao horário de partida
        // 3. Horário de partida superior ao horário vigente pois não faria sentido cadastrar uma viagem que já passou (tratado com BeanValidation)
        // 4. Preço positivo (tratado com bean validation)

        System.out.println(viagem.getCidadeOrigem());
        System.out.println(viagem.getCidadeDestino());

        if(viagem.getCidadeOrigem().getId().equals(viagem.getCidadeDestino().getId())){
            throw new CidadeInvalidaException("Cidade de destino deve ser diferente da cidade de origem");
        }

        if(!viagem.getDataPartida().isBefore(viagem.getDataChegada())){
            throw new DataInvalidaException("Horario de chegada deve ser superior ao de partida.");
        }

        return true;
    }
}
