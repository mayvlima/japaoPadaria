package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.Compra;
import com.techbank.japaoPadaria.model.ItemCompra;
import com.techbank.japaoPadaria.repository.CompraRepository;
import com.techbank.japaoPadaria.repository.EstoqueRepository;
import com.techbank.japaoPadaria.repository.ItemCompraRepository;
import com.techbank.japaoPadaria.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itemcompra")
public class ItemCompraController {

    @Autowired
    ItemCompraRepository itemCompraRepository;

    @Autowired
    CompraRepository compraRepository;


    @GetMapping
    public ResponseEntity<List<ItemCompra>> getAllItensCompra() {
        try {

            List<ItemCompra> itemCompras = itemCompraRepository.findAll();

            if (itemCompras.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(itemCompras, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ItemCompra>> getItensCompraByIdCompra(@PathVariable("id") long id) {

        try {
            Optional<Compra> compra = compraRepository.findById(id);

            if (compra.isPresent()) {
                List<ItemCompra> itemCompras = itemCompraRepository.findAllByCompra(compra.get());

                if (itemCompras.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<>(itemCompras, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemCompra> updateItensCompra(@PathVariable("id") long id, @RequestBody ItemCompra itemCompra) {
        try {
            Optional<ItemCompra> itemCompraDesejado = itemCompraRepository.findById(id);

            if (itemCompraDesejado.isPresent()) {
                ItemCompra atualizacao = itemCompraDesejado.get();
                atualizacao.setQuantidade(itemCompra.getQuantidade());
                atualizacao.setValorDeCompra(itemCompra.getValorDeCompra());
                atualizacao.setCompra(itemCompraDesejado.get().getCompra());
                atualizacao.setProduto(itemCompraDesejado.get().getProduto());

                return new ResponseEntity<>(itemCompraRepository.save(atualizacao), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<ItemCompra> deleteItemCompra(@PathVariable("id") long id) {
        try{
            itemCompraRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
