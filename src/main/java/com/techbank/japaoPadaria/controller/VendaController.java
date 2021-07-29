package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.Venda;
import com.techbank.japaoPadaria.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private VendaRepository VendaRepository;

    @GetMapping
    public List<Venda> ListarTodasAsVendas(){

        return VendaRepository.findAll();
    }

    @PostMapping
    public Venda inserirVenda(@RequestBody Venda venda) {
        return VendaRepository.save(venda);
    }

    @PutMapping("/{id}")
    public Venda updateVenda(@RequestBody Venda Venda, @PathVariable("id") Long idVenda) {
        Venda vendaJaExistente = VendaRepository.getById(idVenda);

        vendaJaExistente.setIdCliente(Venda.getIdCliente());

        return VendaRepository.save(vendaJaExistente);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Venda>> getOne(@PathVariable long id) {
        Optional<Venda> optional = VendaRepository.findById(id);
        if(optional.isPresent()){
            return ResponseEntity.ok().body(optional);
        }else{
            return ResponseEntity.notFound().build();
        }
    }


}
