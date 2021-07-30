package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.Compra;
import com.techbank.japaoPadaria.model.Estoque;
import com.techbank.japaoPadaria.model.Fornecedor;
import com.techbank.japaoPadaria.model.ItemCompra;
import com.techbank.japaoPadaria.model.Produto;
import com.techbank.japaoPadaria.repository.CompraRepository;
import com.techbank.japaoPadaria.repository.EstoqueRepository;
import com.techbank.japaoPadaria.repository.FornecedorRepository;
import com.techbank.japaoPadaria.repository.ItemCompraRepository;
import com.techbank.japaoPadaria.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    CompraRepository compraRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ItemCompraRepository itemCompraRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    EstoqueRepository estoqueRepository;

    @GetMapping
    public ResponseEntity<List<Compra>> getAllCompras() {
        try {

            List<Compra> compras = new ArrayList<>(compraRepository.findAll());

            if (compras.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(compras, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> getCompraById(@PathVariable("id") long id) {
        Optional<Compra> compra = compraRepository.findById(id);

        if (compra.isPresent()) {
            return new ResponseEntity<>(compra.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Compra> createCompra(@RequestBody Fornecedor fornecedor) {
        try {
            Optional<Fornecedor> fornecedorCompra = fornecedorRepository.findById(fornecedor.getId());


            if (fornecedorCompra.isPresent()) {

                Compra novaCompra = new Compra();
                novaCompra.setDataDaCompra(LocalDateTime.now());
                novaCompra.setFornecedor(fornecedorCompra.get());
                novaCompra.setFinalizada(false);

                compraRepository.save(novaCompra);
                return new ResponseEntity<>(novaCompra, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/adicionarproduto/{id}")
    public ResponseEntity<ItemCompra> createItensCompra(@PathVariable("id") long id, @RequestBody ItemCompra itemCompra) {
        try {
            Optional<Compra> compra = compraRepository.findById(id);
            Optional<Produto> produto = produtoRepository.findById(itemCompra.getProduto().getId());


            if (produto.isPresent() && compra.isPresent() && !compra.get().isFinalizada()) {

                ItemCompra novoItemCompra = new ItemCompra(itemCompra.getQuantidade(),
                        itemCompra.getValorDeCompra(),
                        produto.get(),
                        compra.get());

                return new ResponseEntity<>(itemCompraRepository.save(novoItemCompra), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity finalizarCompra(@PathVariable("id") long id) {
        try {
            Optional<Compra> compraDesejada = compraRepository.findById(id);

            if (compraDesejada.isPresent() && !compraDesejada.get().isFinalizada()) {

                List<ItemCompra> itemCompras = itemCompraRepository.findAllByCompra(compraDesejada.get());

                if (!itemCompras.isEmpty()) {
                    for (ItemCompra item : itemCompras) {
                        Estoque novaMovimentacao = new Estoque(item.getQuantidade(),
                                LocalDateTime.now(),
                                "compra",
                                item.getProduto());
                        estoqueRepository.save(novaMovimentacao);
                    }
                }

                Compra atualizacao = compraDesejada.get();
                BigDecimal valorTotal = itemCompraRepository.valorTotalCompra(id);
                atualizacao.setValorTotal(valorTotal);
                atualizacao.setFornecedor(compraDesejada.get().getFornecedor());
                atualizacao.setDataDaCompra(compraDesejada.get().getDataDaCompra());
                atualizacao.setFinalizada(true);

                return new ResponseEntity<>(compraRepository.save(atualizacao), HttpStatus.OK);
            }

            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity cancelarCompra(@PathVariable("id") long id) {
        try {
            Optional<Compra> compraDesejada = compraRepository.findById(id);

            if (compraDesejada.isPresent() && !compraDesejada.get().isFinalizada()) {

                List<ItemCompra> itemCompras = itemCompraRepository.findAllByCompra(compraDesejada.get());

                if (!itemCompras.isEmpty()) {
                    for (ItemCompra item : itemCompras) {
                        itemCompraRepository.deleteById(item.getId());
                    }
                }

                compraRepository.deleteById(compraDesejada.get().getId());

                return ResponseEntity.status(HttpStatus.OK).body("Compra cancelada");
            }

            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("");

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
