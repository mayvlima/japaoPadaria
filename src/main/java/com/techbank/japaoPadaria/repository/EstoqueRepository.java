package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.Estoque;
import com.techbank.japaoPadaria.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    List<Estoque> findAllByDataDeMovimentacaoBetween(LocalDateTime dataInicial, LocalDateTime dataFinal);

    List<Estoque> findAllByProduto(Produto produto);

}
