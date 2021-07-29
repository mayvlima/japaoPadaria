package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.Fornecedor;
import com.techbank.japaoPadaria.model.Produto;
import com.techbank.japaoPadaria.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired
    FornecedorRepository fornecedorRepository;

    @GetMapping
    public ResponseEntity<List<Fornecedor>> getAllFornecedores() {
        try {

            List<Fornecedor> fornecedores = fornecedorRepository.findAll();

            if (fornecedores.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(fornecedores, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> getFornecedorById(@PathVariable("id") long id) {
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);

        if (fornecedor.isPresent()) {
            return new ResponseEntity<>(fornecedor.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Fornecedor> createFornecedor(@RequestBody Fornecedor fornecedor) {
        try {
            Fornecedor novoFornecedor = fornecedorRepository
                    .save(fornecedor);
            return new ResponseEntity<>(novoFornecedor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> updateFornecedor(@PathVariable("id") long id, @RequestBody Fornecedor fornecedor) {
        Optional<Fornecedor> fornecedorDesejado = fornecedorRepository.findById(id);

        if (fornecedorDesejado.isPresent()) {
            Fornecedor atualizacao = fornecedorDesejado.get();
            atualizacao.setNome(fornecedor.getNome());
            atualizacao.setCnpj(fornecedor.getCnpj());

            return new ResponseEntity<>(fornecedorRepository.save(atualizacao), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
