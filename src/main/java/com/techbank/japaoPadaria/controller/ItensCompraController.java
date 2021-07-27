package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.Compra;
import com.techbank.japaoPadaria.model.Estoque;
import com.techbank.japaoPadaria.model.Fornecedor;
import com.techbank.japaoPadaria.model.ItensCompra;
import com.techbank.japaoPadaria.model.Produto;
import com.techbank.japaoPadaria.repository.CompraRepository;
import com.techbank.japaoPadaria.repository.EstoqueRepository;
import com.techbank.japaoPadaria.repository.FornecedorRepository;
import com.techbank.japaoPadaria.repository.ItensCompraRepository;
import com.techbank.japaoPadaria.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itenscompra")
public class ItensCompraController {

    @Autowired
    ItensCompraRepository itensCompraRepository;

    @Autowired
    CompraRepository compraRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    EstoqueRepository estoqueRepository;

    @GetMapping()
    public ResponseEntity<List<ItensCompra>> getAllItensCompra() {
        try {

            List<ItensCompra> itensCompras = new ArrayList<ItensCompra>();

            itensCompraRepository.findAll().forEach(itensCompras::add);

            if (itensCompras.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(itensCompras, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItensCompra> getItensCompraById(@PathVariable("id") long id) {
        Optional<ItensCompra> itensCompra = itensCompraRepository.findById(id);

        if (itensCompra.isPresent()) {
            return new ResponseEntity<>(itensCompra.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<ItensCompra> createItensCompra(@RequestBody ItensCompra itensCompra) {
        try {
            Optional<Compra> compra = compraRepository.findById(itensCompra.getCompra().getId());
            Optional<Produto> produto = produtoRepository.findById(itensCompra.getProduto().getId());


            if (produto.isPresent() && compra.isPresent()) {

                ItensCompra novoItensCompra = new ItensCompra(itensCompra.getQuantidade(),
                        itensCompra.getValorDeCusto(),
                        produto.get(),
                        compra.get());

                Estoque novaMovimentacao = new Estoque(itensCompra.getQuantidade(),
                        LocalDateTime.now(),
                        "compra",
                        produto.get());

                estoqueRepository.save(novaMovimentacao);

                return new ResponseEntity<>(itensCompraRepository.save(novoItensCompra), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
