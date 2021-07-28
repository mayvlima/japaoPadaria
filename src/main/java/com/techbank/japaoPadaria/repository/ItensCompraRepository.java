package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.Compra;
import com.techbank.japaoPadaria.model.Estoque;
import com.techbank.japaoPadaria.model.ItensCompra;
import com.techbank.japaoPadaria.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ItensCompraRepository extends JpaRepository<ItensCompra, Long> {

    @Query(value = "SELECT SUM(valor_de_custo) FROM itens_compra where id_compra = :id_compra", nativeQuery = true)
    public BigDecimal valorTotalCompra(@Param("id_compra") Long idCompra);


    List<ItensCompra> findAllByCompra(Compra compra);


}
