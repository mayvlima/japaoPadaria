package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.ItemVenda;
import com.techbank.japaoPadaria.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;



    public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

        List<ItemVenda>findAllByVenda(Venda venda);


        @Query(value = "SELECT SUM(valor_de_venda) FROM item_venda where id_venda = :id_venda", nativeQuery = true)
        public BigDecimal valorTotalVenda(@Param("id_venda") Long idVenda);

    }

