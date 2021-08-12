package com.example.algamoneyapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "permissao")
public class Permissao {
    @Id
    private Long codigo;

    private String descricao;

}
