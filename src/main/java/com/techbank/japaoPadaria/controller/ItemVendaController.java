package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.ItemVenda;
import com.techbank.japaoPadaria.model.ItemVenda;
import com.techbank.japaoPadaria.model.Venda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ItemVenda")

public class ItemVendaController {

    @Autowired
    private com.techbank.japaoPadaria.repository.ItemVendaRepository ItemVendaRepository;

    @GetMapping
    public List<ItemVenda> ListarTodosOsItemVenda() {

        return ItemVendaRepository.findAll();
    }
    @PostMapping
    public ItemVenda inserirItemVenda(@RequestBody ItemVenda itemVenda) {
        return ItemVendaRepository.save(ItemVenda);
    }

    @PutMapping("/{id}")
    public ItemVenda updateItemVenda(@RequestBody ItemVenda ItemVenda, @PathVariable("id") Long idItemVenda) {
        Venda ItemVendaJaExistente = ItemVendaRepository.getById(idItemVenda);

        ItemVendaJaExistente.setIdCliente(ItemVenda.hashCode());

        return ItemVendaRepository.save(ItemVendaJaExistente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<ItemVenda>> getOne(@PathVariable long id) {
        Optional<ItemVenda> optional = ItemVendaRepository.findById(id);
        if(optional.isPresent()){
            return ResponseEntity.ok().body(optional);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
