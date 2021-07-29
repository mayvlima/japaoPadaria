package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.Compra;
import com.techbank.japaoPadaria.model.ItemCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long> {

    @Query(value = "SELECT SUM(valor_da_compra) FROM item_compra where id_compra = :id_compra", nativeQuery = true)
    public BigDecimal valorTotalCompra(@Param("id_compra") Long idCompra);


    List<ItemCompra> findAllByCompra(Compra compra);


}
