package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.ItensProducao;
import com.techbank.japaoPadaria.model.Producao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItensProducaoRepository extends JpaRepository<ItensProducao, Long> {

    List<ItensProducao> findAllByProducao(Producao producao);
}
