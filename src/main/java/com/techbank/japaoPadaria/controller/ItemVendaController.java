package com.techbank.japaoPadaria.controller;


import com.techbank.japaoPadaria.model.ItemVenda;
import com.techbank.japaoPadaria.repository.ItemVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

public class ItemVendaController {

    @Autowired
    ItemVendaRepository itemVendaRepository;

    @GetMapping
    public List<ItemVenda> ListarItemVenda() {
        return itemVendaRepository.findAll();
    }

    @PostMapping
    public ItemVenda InserirItemVenda(@RequestBody ItemVenda itemVenda) {
        return itemVendaRepository.save(itemVenda);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<ItemVenda>> getOne(@PathVariable long id) {
        Optional<ItemVenda> optional = itemVendaRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok().body(optional);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


