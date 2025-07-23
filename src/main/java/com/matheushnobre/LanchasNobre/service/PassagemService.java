package com.matheushnobre.LanchasNobre.service;

import com.matheushnobre.LanchasNobre.entity.Passagem;
import com.matheushnobre.LanchasNobre.entity.StatusPagamento;
import com.matheushnobre.LanchasNobre.exception.AssentoOcupadoException;
import com.matheushnobre.LanchasNobre.exception.RegistroNaoEncontradoException;
import com.matheushnobre.LanchasNobre.repository.PassagemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassagemService {

    @Autowired
    private PassagemRepository passagemRepository;

    @Transactional
    public Passagem salvar(Passagem passagem) {
        // Caso o status do pagamento não tenha sido preenchido, será preenchido com "PROCESSAMENTO" por padrão
        if(passagem.getStatusPagamento() == null)
            passagem.setStatusPagamento(StatusPagamento.PROCESSAMENTO);

        validarCadastro(passagem);
        return passagemRepository.save(passagem);
    }

    public List<Passagem> listarTodas(){
        return passagemRepository.findAll();
    }

    public Passagem selecionarPorId(Long id) {
        // verifica se existe passagem com id solicitado e, se existir, retorna
        return passagemRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Não existe passagem com o id solicitado")
        );
    }

    private boolean validarCadastro(Passagem passagem){
        // O assento desejado não pode já estar ocupado (status "PROCESSAMENTO" ou "CONFIRMADO")
        // Caso esteja com status "CANCELADO" não há problema em realizar a venda

        // verificar se existe passagem já cadastrada com id_viagem e id_assentos iguais a este
        // se existir, recuperar a passagem e verificar status
        // se não existir ou status = CANCELADA, realizar venda
        Passagem p = passagemRepository.findByAssentoAndViagem(passagem.getAssento(), passagem.getViagem());
        if(p != null && p.getStatusPagamento() != StatusPagamento.CANCELADA) {
            throw new AssentoOcupadoException("Assento já ocupado");
        }

        return true;
    }
}
