package com.techbank.japaoPadaria.repository;

import com.techbank.japaoPadaria.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



    public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    }

