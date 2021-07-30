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

    List<Venda> findAllByDataDaVendaBetween(LocalDateTime dataInicial, LocalDateTime dataFinal);

}
