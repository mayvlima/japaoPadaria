package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.Producao;
import com.techbank.japaoPadaria.repository.ProducaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producao")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProducaoController {

    @Autowired
    private ProducaoRepository repository;

    @GetMapping
    public ResponseEntity<List<Producao>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producao> getById(@PathVariable long id) {
        return repository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producao> post(@RequestBody Producao producao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(producao));
    }

    @PutMapping
    public ResponseEntity<Producao> put(@RequestBody Producao producao) {
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(producao));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        repository.deleteById(id);
    }

}
