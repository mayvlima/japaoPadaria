package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.ItemProducao;
import com.techbank.japaoPadaria.model.Producao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ItemProducaoRepository extends JpaRepository<ItemProducao, Long> {

    @Query(value = "SELECT SUM(valor_de_custo) FROM item_producao where id_produto = :id_produto", nativeQuery = true)
    public BigDecimal valorTotalProducao(@Param("id_produto") Long idCompra);

    List<ItemProducao> findAllByProducao(Producao producao);

}
