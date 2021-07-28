package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.ItensReceita;
import com.techbank.japaoPadaria.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItensReceitaRepository extends JpaRepository<ItensReceita, Long> {

    List<ItensReceita> findAllByIdReceita(Receita receita);
}
