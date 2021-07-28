package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.ItensCompra;
import com.techbank.japaoPadaria.model.ItensReceita;
import com.techbank.japaoPadaria.model.Receita;
import com.techbank.japaoPadaria.repository.ItensReceitaRepository;
import com.techbank.japaoPadaria.repository.ReceitaRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itensreceita")
public class ItensReceitaController {

    @Autowired
    ItensReceitaRepository itensReceitaRepository;

    @Autowired
    ReceitaRepository receitaRepository;

    @GetMapping
    public ResponseEntity<List<ItensReceita>> listarTodosItensdeTodasReceitas() {
        try {
            List<ItensReceita> itensReceitas = new ArrayList<>();

            itensReceitaRepository.findAll().forEach(itensReceitas::add);

            if (itensReceitas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(itensReceitas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ItensReceita>> listarItensPorReceita(@PathVariable("id") long id) {
        try {
            Optional<Receita> receita = receitaRepository.findById(id);

            if (receita.isPresent()) {
                List<ItensReceita> itensReceitas = new ArrayList<>();
                itensReceitaRepository.findAllByIdReceita(receita.get()).forEach(itensReceitas::add);

                if (itensReceitas.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);

                } else {
                    return new ResponseEntity<>(HttpStatus.OK);

                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<ItensReceita> adicionarItensDaReceita(@RequestBody ItensReceita itensReceita) {
        try {
            Optional<Receita> receita = receitaRepository.findById(itensReceita.getIdReceita().getId());

            if (receita.isPresent()) {
                ItensReceita itensReceitas = new ItensReceita(itensReceita.getIdReceita(),
                        itensReceita.getIdProduto(), itensReceita.getQuantidade());
                return new ResponseEntity<>(itensReceitaRepository.save(itensReceitas), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}


