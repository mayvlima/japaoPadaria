package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.Estoque;
import com.techbank.japaoPadaria.model.Fornecedor;
import com.techbank.japaoPadaria.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "select * from produto where descricao ilike %:descricao%", nativeQuery = true)
    List<Produto> findAllByDescricao(@Param("descricao") String descricao) ;

    Optional<Produto> findByCodigoDeBarras(String codigo);

    List<Produto> findAllByStatus(boolean status);
}
