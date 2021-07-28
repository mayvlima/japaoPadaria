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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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

    @GetMapping("/listarTodos")
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

    @GetMapping("buscar/{id}")
    public ResponseEntity<List<ItensCompra>> getItensCompraByIdCompra(@PathVariable("id") long id) {

        try {
            Optional<Compra> compra = compraRepository.findById(id);

            if (compra.isPresent()) {
                List<ItensCompra> itensCompras = new ArrayList<>();

                itensCompraRepository.findAllByCompra(compra.get()).forEach(itensCompras::add);

                if (itensCompras.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<>(itensCompras, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ItensCompra> updateItensCompra(@PathVariable("id") long id, @RequestBody ItensCompra itensCompra) {
        Optional<ItensCompra> itensCompraDesejado = itensCompraRepository.findById(id);

        if (itensCompraDesejado.isPresent()) {
            ItensCompra atualizacao = itensCompraDesejado.get();
            atualizacao.setQuantidade(itensCompra.getQuantidade());
            atualizacao.setValorDeCusto(itensCompra.getValorDeCusto());
            atualizacao.setCompra(itensCompra.getCompra());
            atualizacao.setProduto(itensCompra.getProduto());

            return new ResponseEntity<>(itensCompraRepository.save(atualizacao), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
