package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface VendaRepository extends JpaRepository<Venda, Long> {

}
