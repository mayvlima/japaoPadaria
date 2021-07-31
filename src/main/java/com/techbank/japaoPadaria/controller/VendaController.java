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

        return venda.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Venda>> getAllVendaByData(@RequestParam String datainicial, @RequestParam String datafinal) {
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            datainicial = datainicial + " 00:00:00";
            datafinal = datafinal + " 23:59:59";


            LocalDateTime fDataInical = LocalDateTime.parse(datainicial, formatter);
            LocalDateTime fDataFinal = LocalDateTime.parse(datafinal, formatter);

            List<Venda> venda = vendaRepository.findAllByDataDaVendaBetween(fDataInical, fDataFinal);

            if (venda.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(venda, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


   @PostMapping
    public ResponseEntity<Venda> createVenda(@RequestBody(required = false) Cliente cliente) {
        try {

            Venda novaVenda = new Venda();
            novaVenda.setDataDaVenda(LocalDateTime.now());
            novaVenda.setFinalizada(false);

            if( cliente != null) {
                Optional<Cliente> clienteVenda = clienteRepository.findById(cliente.getId());

                if (clienteVenda.isPresent()) {
                    novaVenda.setICliente(clienteVenda.get());
                }else{
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
            }

            return new ResponseEntity<>(vendaRepository.save(novaVenda), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/adicionarproduto/{id}")
    public ResponseEntity createItemVenda(@PathVariable("id") long id, @RequestBody ItemVenda itemVenda) {
        try {
            Optional<Venda> venda = vendaRepository.findById(id);
            Optional<Produto> produto = produtoRepository.findById(itemVenda.getProduto().getId());
            Boolean possuiEstoque = estoqueRepository.quantidadeTotal(produto.get().getId()) >= itemVenda.getQuantidade();


            if (produto.isPresent() && venda.isPresent() && possuiEstoque && !venda.get().isFinalizada()) {

                ItemVenda novoItemVenda = new ItemVenda();
                novoItemVenda.setQuantidade(itemVenda.getQuantidade());
                novoItemVenda.setValorUnidade(itemVenda.getValorUnidade());
                novoItemVenda.setValorDeVenda(itemVenda.getValorUnidade().multiply(BigDecimal.valueOf(itemVenda.getQuantidade())));
                novoItemVenda.setProduto(produto.get());
                novoItemVenda.setVenda(venda.get());

                Estoque novaMovimentacao = new Estoque(-novoItemVenda.getQuantidade(),
                        LocalDateTime.now(),
                        "venda",
                        novoItemVenda.getProduto());
                estoqueRepository.save(novaMovimentacao);


                return new ResponseEntity<>(itemVendaRepository.save(novoItemVenda), HttpStatus.CREATED);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ação não permitida");
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/finalizar/{id}")
    public ResponseEntity<Venda> finalizarVenda(@PathVariable("id") long id) {
        try {
            Optional<Venda> vendaDesejada = vendaRepository.findById(id);

        if (vendaDesejada.isPresent() && !vendaDesejada.get().isFinalizada()) {

                Venda atualizacao = vendaDesejada.get();

                BigDecimal valorTotal = itemVendaRepository.valorTotalVenda(id);

                if (valorTotal == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    atualizacao.setValorTotal(valorTotal);
                    atualizacao.setFinalizada(true);

                    return new ResponseEntity<>(vendaRepository.save(atualizacao), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity cancelarVenda(@PathVariable("id") long id) {
        try {
            Optional<Venda> vendaDesejada = vendaRepository.findById(id);

            if (vendaDesejada.isPresent() && !vendaDesejada.get().isFinalizada()) {
                List<ItemVenda> itemVenda = itemVendaRepository.findAllByVenda(vendaDesejada.get());

                if (!itemVenda.isEmpty()) {
                    for (ItemVenda item : itemVenda) {
                        itemVendaRepository.deleteById(item.getId());
                        Estoque novaMovimentacao = new Estoque(item.getQuantidade(),
                                LocalDateTime.now(),
                                "estorno",
                                item.getProduto());
                        estoqueRepository.save(novaMovimentacao);
                    }
                }
                vendaRepository.deleteById(vendaDesejada.get().getId());

                return ResponseEntity.status(HttpStatus.OK).body("Venda cancelada");
            }

                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Não é permitido cancelar uma venda que não existe ou já foi finalizada");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cancelar/itens/{id}")
    public ResponseEntity cancelarItemVenda(@PathVariable("id") long id) {
        try {
            Optional<ItemVenda> itensDaVenda = itemVendaRepository.findById(id);

            if (itensDaVenda.isPresent() && !itensDaVenda.get().getVenda().isFinalizada()) {
                itemVendaRepository.deleteById(id);
                Estoque novaMovimentacao = new Estoque(itensDaVenda.get().getQuantidade(),
                        LocalDateTime.now(),
                        "estorno",
                        itensDaVenda.get().getProduto());
                estoqueRepository.save(novaMovimentacao);

                return ResponseEntity.status(HttpStatus.OK).body("Item estornado da venda");
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Não é permitido cancelar uma venda que não existe ou já foi finalizada");

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

