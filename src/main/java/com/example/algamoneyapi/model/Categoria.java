package com.example.algamoneyapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    private String nome;

}
