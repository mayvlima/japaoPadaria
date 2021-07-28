package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.ItensProducao;
import com.techbank.japaoPadaria.repository.ItensProducaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itensproducao")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItensProducaoController {

    @Autowired
    private ItensProducaoRepository repository;

    @GetMapping
    public ResponseEntity<List<ItensProducao>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItensProducao> getById(@PathVariable long id) {
        return repository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItensProducao> post(@RequestBody ItensProducao itensProducao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(itensProducao));
    }

    @PutMapping
    public ResponseEntity<ItensProducao> put(@RequestBody ItensProducao itensProducao) {
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(itensProducao));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        repository.deleteById(id);
    }

}
