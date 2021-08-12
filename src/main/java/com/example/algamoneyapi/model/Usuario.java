package com.example.algamoneyapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario {

    @Id 
    private Long codigo;

    private String nome;

    private String email;

    private String senha ;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_permissao", joinColumns = @JoinColumn(name = "codigo_usuario")
            , inverseJoinColumns = @JoinColumn(name = "codigo_permissao"))
    private List<Permissao> permissoes;
}
