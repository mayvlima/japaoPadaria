package com.techbank.japaoPadaria.controller;


import com.techbank.japaoPadaria.model.Estoque;
import com.techbank.japaoPadaria.model.Produto;
import com.techbank.japaoPadaria.repository.EstoqueRepository;
import com.techbank.japaoPadaria.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {


    @Autowired
    EstoqueRepository estoqueRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<List<Estoque>> getAllEstoque() {
        try {

            List<Estoque> estoque = estoqueRepository.findAll();

            if (estoque.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(estoque, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Estoque>> getAllEstoqueByProduto(@PathVariable("id") long id) {
        try {
            Optional<Produto> produto = produtoRepository.findById(id);

            if (produto.isPresent()) {
                List<Estoque> estoque = estoqueRepository.findAllByProduto(produto.get());

                if (estoque.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<>(estoque, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Estoque>> getAllEstoqueByData(@RequestParam String datainicial,
                                                             @RequestParam String datafinal) {
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            datainicial = datainicial + " 00:00:00";
            datafinal = datafinal + " 23:59:59";


            LocalDateTime fDataInical = LocalDateTime.parse(datainicial, formatter);
            LocalDateTime fDataFinal = LocalDateTime.parse(datafinal, formatter);

            List<Estoque> estoque = estoqueRepository.findAllByDataDeMovimentacaoBetween(fDataInical, fDataFinal);

            if (estoque.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(estoque, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
