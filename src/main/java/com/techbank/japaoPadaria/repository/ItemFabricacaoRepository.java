package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.ItemFabricacao;
import com.techbank.japaoPadaria.model.Fabricacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ItemFabricacaoRepository extends JpaRepository<ItemFabricacao, Long> {

    @Query(value = "SELECT SUM(valor_de_custo) FROM item_fabricacao where id_fabricacao = :id_fabricacao", nativeQuery = true)
    public BigDecimal valorTotalFabricacao(@Param("id_fabricacao") Long idFabricacao);

    List<ItemFabricacao> findAllByFabricacao(Fabricacao fabricacao);

}
