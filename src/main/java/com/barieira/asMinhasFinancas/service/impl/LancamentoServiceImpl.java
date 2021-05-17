package com.barieira.asMinhasFinancas.service.impl;

import com.barieira.asMinhasFinancas.exception.RegraNegocioException;
import com.barieira.asMinhasFinancas.model.entity.Lancamento;
import com.barieira.asMinhasFinancas.model.enums.StatusLancamento;
import com.barieira.asMinhasFinancas.model.repository.LancamentoRepository;
import com.barieira.asMinhasFinancas.service.LancamentoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class LancamentoServiceImpl implements LancamentoService {



    private LancamentoRepository repository;

    public LancamentoServiceImpl(LancamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Lancamento guardar(Lancamento lancamento) {
        validar(lancamento);
        lancamento.setStatus(StatusLancamento.PENDENTE);
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        validar(lancamento);
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public void remover(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        repository.delete(lancamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamento> get(Lancamento lancamentoFiltro) {
        Example example = Example.of( lancamentoFiltro,
                ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) );
        return repository.findAll(example);
    }

    @Override
    public void atualizaStatus(Lancamento lancamento, StatusLancamento status) {
        lancamento.setStatus(status);
        atualizar(lancamento);
    }

    @Override
    public void validar(Lancamento lancamento){

        if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Digite uma descrição válida!");
        }

        if(lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12) {
            throw new RegraNegocioException("Digite um mês válido!");
        }

        if(lancamento.getAno() == null || lancamento.getAno().toString().length() != 4) {
            throw new RegraNegocioException("Digite um ano válido!");
        }

        if(lancamento.getUser() == null || lancamento.getUser().getId() == null) {
            throw new RegraNegocioException("Digite um User!");
        }

        if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1 ){
            throw new RegraNegocioException("Digite um valor válido!");
        }

        if(lancamento.getTipo() == null) {
            throw new RegraNegocioException("Digite um tipo de Lançamento!");
        }
    }
}
