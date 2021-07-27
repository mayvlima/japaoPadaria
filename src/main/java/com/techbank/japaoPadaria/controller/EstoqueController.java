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

    @GetMapping()
    public ResponseEntity<List<Estoque>> getAllEstoque() {
        try {

            List<Estoque> estoque = new ArrayList<Estoque>();

            estoqueRepository.findAll().forEach(estoque::add);

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
                List<Estoque> estoque = new ArrayList<Estoque>();

                estoqueRepository.findAllByProduto(produto.get()).forEach(estoque::add);

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
    public ResponseEntity<List<Estoque>> getAllEstoqueByData(@RequestParam String dataInicial,
                                                             @RequestParam String dataFinal) {
        try {
            List<Estoque> estoque = new ArrayList<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            LocalDateTime fDataInical = LocalDateTime.parse(dataInicial, formatter);
            LocalDateTime fDataFinal = LocalDateTime.parse(dataFinal, formatter);

            estoqueRepository.findAllByDataDeMovimentacaoBetween(fDataInical, fDataFinal).forEach(estoque::add);

            if (estoque.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(estoque, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
