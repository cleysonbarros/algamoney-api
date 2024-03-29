package com.example.algamoneyapi.repository;

import com.example.algamoneyapi.model.Lancamento;
import com.example.algamoneyapi.repository.lancamento.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento,Long>, LancamentoRepositoryQuery {

}
