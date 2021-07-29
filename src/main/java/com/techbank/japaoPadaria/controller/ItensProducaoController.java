package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.ItensProducao;
import com.techbank.japaoPadaria.model.ItensReceita;
import com.techbank.japaoPadaria.model.Producao;
import com.techbank.japaoPadaria.model.Produto;
import com.techbank.japaoPadaria.model.Receita;
import com.techbank.japaoPadaria.repository.ItensProducaoRepository;
import com.techbank.japaoPadaria.repository.ProducaoRepository;
import com.techbank.japaoPadaria.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itensproducao")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItensProducaoController {

    @Autowired
    ItensProducaoRepository itensProducaoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ProducaoRepository producaoRepository;

    @GetMapping
    public ResponseEntity<List<ItensProducao>> getAllItensProducao() {
        try {
            List<ItensProducao> itensProducaos = new ArrayList<>();
            itensProducaoRepository.findAll().forEach(itensProducaos::add);

            if (itensProducaos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itensProducaos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ItensProducao> getById(@PathVariable long id) {
        return itensProducaoRepository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.notFound().build());
    }

    //TODO atualmente ele deixa criar sem informar os ids de produto e producao
    @PostMapping
    public ResponseEntity<ItensProducao> post(@RequestBody ItensProducao itensProducao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itensProducaoRepository.save(itensProducao));
    }

    @PutMapping
    public ResponseEntity<ItensProducao> adicionarItensNaProducao(@RequestBody ItensProducao itensProducao) {
        try {
            Optional<Produto> verificaExistenciaDoProduto = produtoRepository.findById(itensProducao.getProduto().getId());
            Optional<Producao> verificaExistenciaDoProducao = producaoRepository.findById(itensProducao.getProducao().getId());


            if (verificaExistenciaDoProduto.isPresent() && verificaExistenciaDoProducao.isPresent()) {
                ItensProducao adicionaItem = new ItensProducao();
                adicionaItem.setValorDeCusto(itensProducao.getValorDeCusto());
                adicionaItem.setQuantidade(itensProducao.getQuantidade());
                adicionaItem.setQuantidadeDeMedida(itensProducao.getQuantidadeDeMedida());
                adicionaItem.setUnidadeDeMedida(itensProducao.getUnidadeDeMedida());
                adicionaItem.setProducao(itensProducao.getProducao());
                adicionaItem.setProduto(itensProducao.getProduto());

                return new ResponseEntity<>(itensProducaoRepository.save(adicionaItem), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        itensProducaoRepository.deleteById(id);
    }

}
