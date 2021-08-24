package com.example.algamoneyapi.repository.projection;

import com.example.algamoneyapi.model.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ResumoLancamento {
    
    private Long codigo ;

    private String descricao;

    private LocalDate dataVencimento;

    private LocalDate dataPagamento;

    private BigDecimal valor;

    private TipoLancamento tipo ;

    private String categoria;

    private String pessoa;



}
