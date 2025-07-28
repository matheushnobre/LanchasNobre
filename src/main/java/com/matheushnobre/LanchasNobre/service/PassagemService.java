package com.matheushnobre.LanchasNobre.service;

import com.matheushnobre.LanchasNobre.entity.Passagem;
import com.matheushnobre.LanchasNobre.entity.StatusPagamento;
import com.matheushnobre.LanchasNobre.exception.RegistroNaoEncontradoException;
import com.matheushnobre.LanchasNobre.repository.PassagemRepository;
import com.matheushnobre.LanchasNobre.validator.PassagemValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassagemService {

    @Autowired
    private PassagemRepository passagemRepository;

    @Autowired
    private PassagemValidator passagemValidator;

    @Transactional
    public Passagem add(Passagem passagem) {
        // Caso o status do pagamento não tenha sido preenchido, será preenchido com "PROCESSAMENTO" por padrão
        passagem = passagemValidator.atualizarChavesEstrangeiras(passagem);

        passagem.setStatusPagamento(StatusPagamento.PROCESSAMENTO);
        passagemValidator.validarInsercao(passagem);
        return passagemRepository.save(passagem);
    }

    public Passagem getById(Long id) {
        // verifica se existe passagem com id solicitado e, se existir, retorna
        return passagemRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Passagem com o id " + id + " não encontrada.")
        );
    }

}
