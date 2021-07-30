package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.ItemProducao;
import com.techbank.japaoPadaria.model.Producao;
import com.techbank.japaoPadaria.model.Produto;
import com.techbank.japaoPadaria.repository.ItemProducaoRepository;
import com.techbank.japaoPadaria.repository.ProducaoRepository;
import com.techbank.japaoPadaria.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producao/itens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItensProducaoController {

    @Autowired
    ItemProducaoRepository itemProducaoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ProducaoRepository producaoRepository;

    @GetMapping
    public ResponseEntity<List<ItemProducao>> getAllItensProducao() {
        try {
            List<ItemProducao> itemProducaos = itemProducaoRepository.findAll();

            if (itemProducaos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itemProducaos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ItemProducao>> getById(@PathVariable long id) {
        try {
            Optional<Producao> producao = producaoRepository.findById(id);

            if (producao.isPresent()) {
                List<ItemProducao> itensProducao = itemProducaoRepository.findAllByProducao(producao.get());


                if (itensProducao.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<>(itensProducao, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<ItemProducao> adicionarItensNaProducao(@RequestBody ItemProducao itemProducao) {
        try {
            Optional<Produto> verificaExistenciaDoProduto = produtoRepository.findById(itemProducao.getProduto().getId());
            Optional<Producao> verificaExistenciaDoProducao = producaoRepository.findById(itemProducao.getProducao().getId());


            if (verificaExistenciaDoProduto.isPresent() && verificaExistenciaDoProducao.isPresent()) {
                ItemProducao adicionaItem = new ItemProducao();
                adicionaItem.setValorDeCusto(itemProducao.getValorDeCusto());
                adicionaItem.setQuantidade(itemProducao.getQuantidade());
                adicionaItem.setQuantidadeDeMedida(itemProducao.getQuantidadeDeMedida());
                adicionaItem.setUnidadeDeMedida(itemProducao.getUnidadeDeMedida());
                adicionaItem.setProducao(itemProducao.getProducao());
                adicionaItem.setProduto(itemProducao.getProduto());

                return new ResponseEntity<>(itemProducaoRepository.save(adicionaItem), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
