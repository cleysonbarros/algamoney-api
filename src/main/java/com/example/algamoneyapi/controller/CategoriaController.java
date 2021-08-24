package com.example.algamoneyapi.controller;

import com.example.algamoneyapi.event.RecursoCriadoEvent;
import com.example.algamoneyapi.model.Categoria;
import com.example.algamoneyapi.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ApplicationEventPublisher publisher;


    @GetMapping
    //@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')and #oauth2.hasScope('read')")
    public List<Categoria> listar(){

        return categoriaRepository.findAll();
    }
    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')and #oauth2.hasScope('read')")
    public ResponseEntity<Categoria> buscarId (@PathVariable Long codigo){
        Optional<Categoria> categoria = categoriaRepository.findById(codigo);
        if(categoria.isPresent()){
            return ResponseEntity.ok(categoria.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')and #oauth2.hasScope('write')")
    public ResponseEntity<Categoria> salva(@RequestBody Categoria categoria,HttpServletResponse response){
        Categoria categoriaSalva = categoriaRepository.save(categoria);
        publisher.publishEvent(new RecursoCriadoEvent(this,response,categoriaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @PutMapping
    public Categoria atualiza(@RequestBody Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    @DeleteMapping
    public void remover(@PathVariable Long id){
        categoriaRepository.deleteById(id);
   }


}
