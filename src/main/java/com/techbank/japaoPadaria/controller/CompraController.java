package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.Compra;
import com.techbank.japaoPadaria.model.Fornecedor;
import com.techbank.japaoPadaria.repository.CompraRepository;
import com.techbank.japaoPadaria.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    CompraRepository compraRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @GetMapping()
    public ResponseEntity<List<Compra>> getAllCompras() {
        try {

            List<Compra> compras = new ArrayList<Compra>();

            compraRepository.findAll().forEach(compras::add);

            if (compras.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(compras, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> getCompraById(@PathVariable("id") long id) {
        Optional<Compra> compra = compraRepository.findById(id);

        if (compra.isPresent()) {
            return new ResponseEntity<>(compra.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   @PostMapping()
    public ResponseEntity<Compra> createCompra(@RequestBody Fornecedor fornecedor) {
        try {
            Optional<Fornecedor> fornecedorCompra = fornecedorRepository.findById(fornecedor.getId());


            if (fornecedorCompra.isPresent()) {
                Compra novaCompra = new Compra();
                novaCompra.setDataDaCompra(new Timestamp(System.currentTimeMillis()));
                novaCompra.setFornecedor(fornecedorCompra.get());

                return new ResponseEntity<>(compraRepository.save(novaCompra), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
