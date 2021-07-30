package com.techbank.japaoPadaria.controller;


import com.techbank.japaoPadaria.model.Compra;
import com.techbank.japaoPadaria.model.ItemCompra;
import com.techbank.japaoPadaria.model.ItemVenda;
import com.techbank.japaoPadaria.model.Venda;
import com.techbank.japaoPadaria.repository.ItemVendaRepository;
import com.techbank.japaoPadaria.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/venda/itens")
public class ItemVendaController {

    @Autowired
    ItemVendaRepository itemVendaRepository;

    @Autowired
    VendaRepository vendaRepository;


    @GetMapping
    public ResponseEntity<List<ItemVenda>> getAllItensVenda() {
        try {

            List<ItemVenda> itemVenda = itemVendaRepository.findAll();

            if (itemVenda.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(itemVenda, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ItemVenda>> getItensCompraByIdVenda(@PathVariable("id") long id) {

        try {
            Optional<Venda> venda = vendaRepository.findById(id);

            if (venda.isPresent()) {
                List<ItemVenda> itemVendas = itemVendaRepository.findAllByVenda(venda.get());

                if (itemVendas.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<>(itemVendas, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


