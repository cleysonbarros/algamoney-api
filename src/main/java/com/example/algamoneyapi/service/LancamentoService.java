package com.example.algamoneyapi.service;

import com.example.algamoneyapi.model.Lancamento;
import com.example.algamoneyapi.model.Pessoa;
import com.example.algamoneyapi.repository.LancamentoRepository;
import com.example.algamoneyapi.repository.PessoaRepository;
import com.example.algamoneyapi.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Lancamento salvar(Lancamento lancamento){
        Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
        if(pessoa == null ||pessoa.isPresent()){
            throw new PessoaInexistenteOuInativaException();

        }
        return lancamentoRepository.save(lancamento);


    }
}
