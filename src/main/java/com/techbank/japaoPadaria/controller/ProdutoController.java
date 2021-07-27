package com.techbank.japaoPadaria.controller;


import com.techbank.japaoPadaria.model.Produto;
import com.techbank.japaoPadaria.repository.ProdutoRepository;
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
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping()
    public ResponseEntity<List<Produto>> getAllProdutos() {
        try {

            List<Produto> produtos = new ArrayList<Produto>(produtoRepository.findAll());

            if (produtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/descricao")
    public ResponseEntity<List<Produto>> getAllProdutosDescricao(@RequestBody Produto produto) {
        try {
            List<Produto> produtos = new ArrayList<Produto>(produtoRepository.findAllByDescricao(produto.getDescricao()));

            if (produtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/codigo")
    public ResponseEntity<Produto> getAllProdutosCodigo(@RequestBody Produto produto) {
        Optional<Produto> produtoDesejado = produtoRepository.findByCodigoDeBarras(produto.getCodigoDeBarras());

        if (produtoDesejado.isPresent()) {
            return new ResponseEntity<>(produtoDesejado.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Produto>> getAllProdutoAtivos() {
        try {
            List<Produto> produtos = new ArrayList<Produto>(produtoRepository.findAllByStatus(true));


            produtoRepository.findAllByStatus(true);

            if (produtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/inativos")
    public ResponseEntity<List<Produto>> getAllProdutoDesativados() {
        try {
            List<Produto> produtos = new ArrayList<Produto>(produtoRepository.findAllByStatus(false));

            if (produtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable("id") long id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isPresent()) {
            return new ResponseEntity<>(produto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        try {
            Produto novoProduto = produtoRepository.save(produto);
            return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable("id") long id, @RequestBody Produto produto) {
        Optional<Produto> produtoDesejado = produtoRepository.findById(id);

        if (produtoDesejado.isPresent()) {
            Produto atualizacao = produtoDesejado.get();
            atualizacao.setDescricao(produto.getDescricao());
            atualizacao.setValorDeCusto(produto.getValorDeCusto());
            atualizacao.setValorDeVenda(produto.getValorDeVenda());
            atualizacao.setCodigoDeBarras(produto.getCodigoDeBarras());
            atualizacao.setQuantidaDeMedida(produto.getQuantidaDeMedida());
            atualizacao.setUnidadeDeMedida(produto.getUnidadeDeMedida());
            atualizacao.setStatus(produto.getStatus());

            return new ResponseEntity<>(produtoRepository.save(atualizacao), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/alterarStatus/{id}")
    public ResponseEntity<Produto> updateStatus(@PathVariable("id") long id, @RequestBody Produto produto) {
        Optional<Produto> produtoDesejado = produtoRepository.findById(id);

        if (produtoDesejado.isPresent()) {
            Produto atualizacao = produtoDesejado.get();
            atualizacao.setStatus(produto.getStatus());

            return new ResponseEntity<>(produtoRepository.save(atualizacao), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
