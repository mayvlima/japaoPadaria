package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface VendaRepository extends JpaRepository<Venda, Long> {

    //@Query(value = "SELECT v FROM Venda v")
    List<Venda> findAllByVenda(Venda venda);


    @Query(value = "SELECT v.Movimentacao FROM Venda v")
    List<Venda> findAllByDataDeMovimentacaoBetween(LocalDateTime dataInicial, LocalDateTime dataFinal);

}
