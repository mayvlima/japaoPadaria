package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.Compra;
import com.techbank.japaoPadaria.model.ItensCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItensCompraRepository extends JpaRepository<ItensCompra, Long> {

}
