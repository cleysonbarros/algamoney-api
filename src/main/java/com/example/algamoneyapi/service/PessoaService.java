package com.example.algamoneyapi.service;

import com.example.algamoneyapi.model.Pessoa;
import com.example.algamoneyapi.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa Atualizar(Long codigo,Pessoa pessoa){
        Optional<Pessoa> pessoaSalva = buscarPessoaPeloCodigo(codigo);
        BeanUtils.copyProperties(pessoa,pessoaSalva,"codigo");
        return pessoaRepository.save(pessoa);

    }

    public ResponseEntity<?> atualizarPropriedadeAtivo(Long codigo, Boolean ativo){
        Optional<Pessoa> pessoaSalva = buscarPessoaPeloCodigo(codigo);
        if(pessoaSalva.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).build();

        }

        return ResponseEntity.noContent().build();
    }


    private Optional<Pessoa> buscarPessoaPeloCodigo(Long codigo) {
        Optional<Pessoa> pessoaSalva = pessoaRepository.findById(codigo);
        if(pessoaSalva == null){
             throw  new EmptyResultDataAccessException(1);
        }
        return pessoaSalva;
    }
}
