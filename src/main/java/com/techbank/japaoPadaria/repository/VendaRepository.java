package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.Estoque;
import com.techbank.japaoPadaria.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Query(value = "select * from venda where data_da_venda between :dataInicial and :dataFinal", nativeQuery = true)
    List<Venda> findAllByDataDaVendaBetween(@Param("dataInicial") LocalDateTime dataInicial,@Param("dataFinal") LocalDateTime dataFinal);

}
