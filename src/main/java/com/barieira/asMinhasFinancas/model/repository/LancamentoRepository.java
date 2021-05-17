package com.barieira.asMinhasFinancas.model.repository;

import com.barieira.asMinhasFinancas.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
