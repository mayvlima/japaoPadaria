package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.Produto;
import com.techbank.japaoPadaria.model.Receita;
import com.techbank.japaoPadaria.repository.ProdutoRepository;
import com.techbank.japaoPadaria.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/receita")
public class ReceitaController {

    @Autowired
    ReceitaRepository receitaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<List<Receita>> listarReceitas() {
        try{
            List<Receita> receita = new ArrayList<Receita>();

            receitaRepository.findAll().forEach(receita::add);
            if (receita.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(receita, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Receita> getReceitaById(@PathVariable("id") long id) {
        Optional<Receita> receita = receitaRepository.findById(id);

        if (receita.isPresent()) {
            return new ResponseEntity<>(receita.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<Receita> criarReceita(@RequestBody Produto produto) {
        try {
            Optional<Produto> receitaDoProduto = produtoRepository.findById(produto.getId());

            if (receitaDoProduto.isPresent()) {
                Receita novaReceita = new Receita();
                novaReceita.setIdProduto(receitaDoProduto.get());
                novaReceita.setDescricao(receitaDoProduto.get().getDescricao());

                return new ResponseEntity<>(receitaRepository.save(novaReceita), HttpStatus.CREATED);

            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


}
