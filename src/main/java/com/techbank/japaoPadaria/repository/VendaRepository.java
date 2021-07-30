package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface VendaRepository extends JpaRepository<Venda, Long> {


    @Query(value = "SELECT v FROM venda v WHERE v.data_da_venda BETWEEN :data_inicial AND :data_final", nativeQuery = true)
    List<Venda> findAllByDataDeMovimentacaoBetween(@Param("data_inicial")LocalDateTime dataInicial,
                                                   @Param("data_final") LocalDateTime dataFinal);

}
