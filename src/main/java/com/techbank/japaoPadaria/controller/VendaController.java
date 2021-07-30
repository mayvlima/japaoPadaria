package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.*;
import com.techbank.japaoPadaria.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.techbank.japaoPadaria.repository.ItemVendaRepository;


@RestController
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    VendaRepository vendaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ItemVendaRepository itemVendaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    EstoqueRepository estoqueRepository;


    @GetMapping
    public ResponseEntity<List<Venda>> getAllVendas() {
        try {

            List<Venda> venda = new ArrayList<Venda>(vendaRepository.findAll());


            if (venda.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(venda, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> getVendaById(@PathVariable("id") long id) {
        Optional<Venda> venda = vendaRepository.findById(id);

        if (venda.isPresent()) {
            return new ResponseEntity<>(venda.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //TODO TESTAR
    @GetMapping("/buscarVenda")
    public ResponseEntity<List<Venda>> getAllVendaByData(@RequestParam String dataInicial, @RequestParam String dataFinal) {
        try {
            List<Venda> venda = new ArrayList<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


            LocalDateTime fDataInical = LocalDateTime.parse(dataInicial, formatter);
            LocalDateTime fDataFinal = LocalDateTime.parse(dataFinal, formatter);

            vendaRepository.findAllByDataDeMovimentacaoBetween(fDataInical, fDataFinal).forEach(venda::add);

            if (venda.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(venda, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<Venda> createVenda(@RequestBody Cliente cliente) {
        try {
            Optional<Cliente> clienteVenda = clienteRepository.findById(cliente.getId());

            if (clienteVenda.isPresent()) {

                Venda novaVenda = new Venda();
                novaVenda.setDataDaVenda(LocalDateTime.now());
                novaVenda.setICliente(clienteVenda.get());

                return new ResponseEntity<>(vendaRepository.save(novaVenda), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/adicionarProduto/{id}")
    public ResponseEntity<ItemVenda> createItemVenda(@PathVariable("id") long id, @RequestBody ItemVenda itemVenda) {
        try {
            Optional<Venda> venda = vendaRepository.findById(id);
            Optional<Produto> produto = produtoRepository.findById(itemVenda.getProduto().getId());
            Boolean possuiEstoque = estoqueRepository.quantidadeTotal(produto.get().getId()) >= itemVenda.getQuantidade();


            if (produto.isPresent() && venda.isPresent() && possuiEstoque) {

                ItemVenda novoItemVenda = new ItemVenda();
                novoItemVenda.setQuantidade(itemVenda.getQuantidade());
                novoItemVenda.setValorUnidade(itemVenda.getValorUnidade());
                novoItemVenda.setValorDeVenda(itemVenda.getValorUnidade().multiply(BigDecimal.valueOf(itemVenda.getQuantidade())));
                novoItemVenda.setProduto(produto.get());
                novoItemVenda.setVenda(venda.get());

                Estoque novaMovimentacao = new Estoque(-novoItemVenda.getQuantidade(),
                        LocalDateTime.now(),
                        "vendido",
                        novoItemVenda.getProduto());
                estoqueRepository.save(novaMovimentacao);


                return new ResponseEntity<>(itemVendaRepository.save(novoItemVenda), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/finalizar/{id}")
    public ResponseEntity<Venda> finalizarVenda(@PathVariable("id") long id) {
        try {
            Optional<Venda> vendaDesejada = vendaRepository.findById(id);


            if (vendaDesejada.isPresent()) {
                List<ItemVenda> itemVenda = itemVendaRepository.findAllByVenda(vendaDesejada.get());

                Venda atualizacao = vendaDesejada.get();

                BigDecimal valorTotal = itemVendaRepository.valorTotalVenda(id);

                if (valorTotal != null) {
                    atualizacao.setICliente(vendaDesejada.get().getCliente());
                }

                atualizacao.setDataDaVenda(LocalDateTime.now());
                atualizacao.setValorTotal(valorTotal);

                return new ResponseEntity<>(vendaRepository.save(atualizacao), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (
                Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity cancelarVenda(@PathVariable("id") long id) {
        try {
            Optional<Venda> vendaDesejada = vendaRepository.findById(id);

            if (vendaDesejada.isPresent()) {
                List<ItemVenda> itemVenda = itemVendaRepository.findAllByVenda(vendaDesejada.get());

                if (!itemVenda.isEmpty()) {
                    for (ItemVenda item : itemVenda) {
                        itemVendaRepository.deleteById(item.getId());
                    }
                }
                vendaRepository.deleteById(vendaDesejada.get().getId());
            }

            return ResponseEntity.status(HttpStatus.OK).body("Venda cancelada");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
