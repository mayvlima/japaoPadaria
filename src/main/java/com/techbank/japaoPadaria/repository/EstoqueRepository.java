package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.Estoque;
import com.techbank.japaoPadaria.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    List<Estoque> findAllByDataDeMovimentacaoBetween(LocalDateTime dataInicial, LocalDateTime dataFinal);

    List<Estoque> findAllByProduto(Produto produto);

    @Query(value = "SELECT SUM(quantidade) FROM estoque where id_produto = :id_produto", nativeQuery = true)
    public Integer quantidadeTotal(@Param("id_produto") Long id);

}
