package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.ItemFabricacao;
import com.techbank.japaoPadaria.model.Fabricacao;
import com.techbank.japaoPadaria.repository.ItemFabricacaoRepository;
import com.techbank.japaoPadaria.repository.FabricacaoRepository;
import com.techbank.japaoPadaria.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producao/itens")
public class ItemFabricacaoController {

    @Autowired
    ItemFabricacaoRepository itemFabricacaoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    FabricacaoRepository fabricacaoRepository;

    @GetMapping
    public ResponseEntity<List<ItemFabricacao>> getAllItensProducao() {
        try {
            List<ItemFabricacao> itemFabricacaos = itemFabricacaoRepository.findAll();

            if (itemFabricacaos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itemFabricacaos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ItemFabricacao>> getById(@PathVariable long id) {
        try {
            Optional<Fabricacao> producao = fabricacaoRepository.findById(id);

            if (producao.isPresent()) {
                List<ItemFabricacao> itensProducao = itemFabricacaoRepository.findAllByFabricacao(producao.get());


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


}
