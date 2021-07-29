package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.Compra;
import com.techbank.japaoPadaria.model.Estoque;
import com.techbank.japaoPadaria.model.ItemCompra;
import com.techbank.japaoPadaria.model.ItemProducao;
import com.techbank.japaoPadaria.model.Producao;
import com.techbank.japaoPadaria.model.Produto;
import com.techbank.japaoPadaria.repository.EstoqueRepository;
import com.techbank.japaoPadaria.repository.ItemProducaoRepository;
import com.techbank.japaoPadaria.repository.ProducaoRepository;
import com.techbank.japaoPadaria.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producao")
public class ProducaoController {

    @Autowired
    ProducaoRepository producaoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ItemProducaoRepository itemProducaoRepository;

    @Autowired
    EstoqueRepository estoqueRepository;



    @GetMapping
    public ResponseEntity<List<Producao>> getAllProducoes() {
        try {
            List<Producao> producao = producaoRepository.findAll();

            if (producao.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(producao, HttpStatus.OK);

        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producao> getProducaoById(@PathVariable long id) {
        Optional<Producao> producao = producaoRepository.findById(id);

        return producao.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //TODO verificar com o Anderson como criar este metodo
    @PostMapping
    public ResponseEntity createProducao(@RequestBody Producao producao) {
        try {
            Optional<Produto> produtoProduzido = produtoRepository.findById(producao.getProduto().getId());

            if (produtoProduzido.isPresent()) {
                Producao novaProducao = new Producao();
                novaProducao.setDataDeProducao(LocalDateTime.now());
                novaProducao.setProduto(produtoProduzido.get());
                novaProducao.setQuantidade(producao.getQuantidade());
                novaProducao.setFinalizada(false);
                return new ResponseEntity<>(producaoRepository.save(novaProducao), HttpStatus.OK);


            } else {
//                produtoController.createProduto(producao.getProduto()).getBody();
//                criaProducao(producao);
                return ResponseEntity.status(HttpStatus.OK).body("Cadastre o Produto antes de produzi-lo");
            }

        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

   @PostMapping("/adicionarproduto/{id}")
    public ResponseEntity<ItemProducao> createItemProduçãp(@PathVariable("id") long id, @RequestBody ItemProducao itemProducao) {
        try {
            Optional<Producao> producao = producaoRepository.findById(id);
            Optional<Produto> produto = produtoRepository.findById(itemProducao.getProduto().getId());


            if (produto.isPresent() && producao.isPresent() && !producao.get().isFinalizada()) {

                ItemProducao novoItemProducao = new ItemProducao();

                novoItemProducao.setProducao(producao.get());
                novoItemProducao.setProduto(produto.get());
                novoItemProducao.setQuantidade(itemProducao.getQuantidade());
                novoItemProducao.setQuantidadeDeMedida(itemProducao.getQuantidadeDeMedida());
                novoItemProducao.setUnidadeDeMedida(itemProducao.getUnidadeDeMedida());

                Estoque novaMovimentacao = new Estoque(-itemProducao.getQuantidade(),
                        LocalDateTime.now(),
                        "consumido",
                        itemProducao.getProduto());
                estoqueRepository.save(novaMovimentacao);

                return new ResponseEntity<>(itemProducaoRepository.save(novoItemProducao), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping
    public ResponseEntity atualizaProducaoJaCriada(@RequestBody Producao producao) {
        try {
            Optional<Produto> produtoProduzido = produtoRepository.findById(producao.getProduto().getId());
            Optional<Producao> atualizaProducao = producaoRepository.findById(producao.getId());


            if (produtoProduzido.isPresent() && atualizaProducao.isPresent()) {
                Producao novaProducao = producaoRepository.getById(producao.getId());
                novaProducao.setDataDeProducao(LocalDateTime.now()); //TODO devemos atualizar a data?
                novaProducao.setProduto(produtoProduzido.get());
                novaProducao.setQuantidade(producao.getQuantidade());
                novaProducao.setFinalizada(producao.isFinalizada());
                return new ResponseEntity<>(producaoRepository.save(novaProducao), HttpStatus.OK);


            } else {
                return ResponseEntity.status(HttpStatus.OK).body("Cadastre o Produto antes de produzi-lo");
            }

        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("cancelar/{id}")
    public ResponseEntity cancelaProducao(@PathVariable("id") long id) {
        try {
            Optional<Producao> producaoCancelada = producaoRepository.findById(id);

            if (producaoCancelada.isPresent() && !producaoCancelada.get().isFinalizada()) {
                List<ItemProducao> itensDaProducaoCancelada = itemProducaoRepository.findAllByProducao(producaoCancelada.get());

                if (!itensDaProducaoCancelada.isEmpty()) {
                    for (ItemProducao item : itensDaProducaoCancelada) {
                        itemProducaoRepository.deleteById(item.getId());
                    }
                }

                producaoRepository.deleteById(producaoCancelada.get().getId());
            }
            return ResponseEntity.status(HttpStatus.OK).body("Produção cancelada");
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity finalizarProducao(@PathVariable("id") long id) {
        try {
            Optional<Producao> producaoDesejada = producaoRepository.findById(id);

            if (producaoDesejada.isPresent() && !producaoDesejada.get().isFinalizada()) {

                List<ItemProducao> itemProducao = itemProducaoRepository.findAllByProducao(producaoDesejada.get());



                Producao atualizacao = producaoDesejada.get();
                BigDecimal valorTotal = itemProducaoRepository.valorTotalProducao(id);
                atualizacao.setValorDeCustoUnitario(valorTotal);
                atualizacao.setFinalizada(true);

                Estoque novaMovimentacao = new Estoque(producaoDesejada.get().getQuantidade(),
                        LocalDateTime.now(),
                        "produzido",
                        producaoDesejada.get().getProduto());
                estoqueRepository.save(novaMovimentacao);


                return new ResponseEntity<>(producaoRepository.save(atualizacao), HttpStatus.OK);
            }

            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
