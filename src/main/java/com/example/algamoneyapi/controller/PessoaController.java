package com.example.algamoneyapi.controller;

import com.example.algamoneyapi.event.RecursoCriadoEvent;
import com.example.algamoneyapi.model.Pessoa;
import com.example.algamoneyapi.repository.PessoaRepository;
import com.example.algamoneyapi.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> buscaPeloCodigo(@PathVariable Long codigo){
        Optional<Pessoa> pessoa= pessoaRepository.findById(codigo);
        if(pessoa.isPresent()){
            return ResponseEntity.ok(pessoa.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Pessoa> salvar(@RequestBody Pessoa pessoa , HttpServletResponse response){
        Pessoa pessoasalva = pessoaRepository.save(pessoa);
        publisher.publishEvent(new RecursoCriadoEvent(this,response, pessoasalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoasalva);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo){
        pessoaRepository.deleteById(codigo);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Pessoa>atualiza(@PathVariable Long codigo, @RequestBody Pessoa pessoa){
        Pessoa pessoaSalva = pessoaService.Atualizar(codigo,pessoa);
        return ResponseEntity.ok(pessoaSalva);
    }
    @PutMapping("/{codigo}/ativo")
    public void atualizarPropriedadeAtivo(@PathVariable Long codigo,@RequestBody Boolean ativo){
        pessoaService.atualizarPropriedadeAtivo(codigo,ativo);
    }

}
