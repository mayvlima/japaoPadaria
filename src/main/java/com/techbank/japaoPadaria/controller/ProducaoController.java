package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.ItensProducao;
import com.techbank.japaoPadaria.model.Producao;
import com.techbank.japaoPadaria.model.Produto;
import com.techbank.japaoPadaria.repository.ItensProducaoRepository;
import com.techbank.japaoPadaria.repository.ProducaoRepository;
import com.techbank.japaoPadaria.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producao")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProducaoController {

    @Autowired
    ProducaoRepository producaoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ItensProducaoRepository itensProducaoRepository;

    ProdutoController produtoController;

    @GetMapping
    public ResponseEntity<List<Producao>> getAllCompras() {
        try {
            List<Producao> producao = new ArrayList<Producao>();

            producaoRepository.findAll().forEach(producao::add);
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

        if (producao.isPresent()) {
            return new ResponseEntity<>(producao.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //TODO verificar com o Anderson como criar este metodo
    @PostMapping
    public ResponseEntity criaProducao(@RequestBody Producao producao) {
        try {
            Optional<Produto> produtoProduzido = produtoRepository.findById(producao.getProduto().getId());

            if (produtoProduzido.isPresent()) {
                Producao novaProducao = new Producao();
                novaProducao.setDataDeProducao(LocalDateTime.now());
                novaProducao.setProduto(produtoProduzido.get());
                novaProducao.setQuantidade(producao.getQuantidade());
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


    @PatchMapping
    public ResponseEntity atualizaProducaoJaCriada(@RequestBody Producao producao) {
        try {
            Optional<Produto> produtoProduzido = produtoRepository.findById(producao.getProduto().getId());
            Optional<Producao> atualizaProducao = producaoRepository.findById(producao.getId());


            if (produtoProduzido.isPresent() && atualizaProducao.isPresent()) {
                Producao novaProducao = producaoRepository.getById(producao.getId());
                novaProducao.setDataDeProducao(LocalDateTime.now()); //TODO devemos atualizar a data?
                novaProducao.setProduto(produtoProduzido.get());
                novaProducao.setQuantidade(producao.getQuantidade());
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

            if (producaoCancelada.isPresent()) {
                List<ItensProducao> itensDaProducaoCancelada = new ArrayList<>();

                itensProducaoRepository.findAllByProducao(producaoCancelada.get()).forEach(itensDaProducaoCancelada::add);

                if (!itensDaProducaoCancelada.isEmpty()) {
                    for (ItensProducao item : itensDaProducaoCancelada) {
                        itensProducaoRepository.deleteById(item.getId());
                    }
                }

                producaoRepository.deleteById(producaoCancelada.get().getId());
            }
            return ResponseEntity.status(HttpStatus.OK).body("Produção cancelada");
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
