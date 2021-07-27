package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.Estoque;
import com.techbank.japaoPadaria.model.Fornecedor;
import com.techbank.japaoPadaria.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findAllByDescricao(String descricao);

    Optional<Produto> findByCodigoDeBarras(String codigo);

    List<Produto> findAllByStatus(boolean status);
}
