package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.Estoque;
import com.techbank.japaoPadaria.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {


    @Query(value = "select * from estoque where data_de_movimentacao between :dataInicial and :dataFinal", nativeQuery = true)
    List<Estoque> findAllByDataDeMovimentacaoBetween(@Param("dataInicial") LocalDateTime dataInicial,@Param("dataFinal") LocalDateTime dataFinal);


    List<Estoque> findAllByProduto(Produto produto);

    @Query(value = "SELECT SUM(quantidade) FROM estoque where id_produto = :id_produto", nativeQuery = true)
    Integer quantidadeTotal(@Param("id_produto") Long id);

    @Query(value = "select * from estoque where descricao ilike '%:descricao%'", nativeQuery = true)
    List<Estoque> estoquePorDescricao(@Param("descricao") String descricao) ;

}
