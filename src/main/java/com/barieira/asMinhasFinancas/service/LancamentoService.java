package com.barieira.asMinhasFinancas.service;

import com.barieira.asMinhasFinancas.model.entity.Lancamento;
import com.barieira.asMinhasFinancas.model.enums.StatusLancamento;

import java.util.List;

public interface LancamentoService {

    Lancamento guardar(Lancamento lancamento);

    Lancamento atualizar(Lancamento lancamento);

    void remover(Lancamento lancamento);

    List<Lancamento> get(Lancamento lancamentoFiltro);

    void atualizaStatus(Lancamento lancamento, StatusLancamento status);

    void validar(Lancamento lancamento);

}
