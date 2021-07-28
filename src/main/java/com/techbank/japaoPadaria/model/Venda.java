package com.techbank.japaoPadaria.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity(name="Venda") //Anotações

public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private LocalDateTime Data;

    @Column(name="Id_Cliente")
    private Integer IdCliente;

    @Column(name="Valor_total")
    private BigDecimal ValorTotal;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public LocalDateTime getData() {
        return Data;
    }

    public void setData(LocalDateTime data) {
        Data = data;
    }

    public Integer getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(Integer idCliente) {
        IdCliente = idCliente;
    }

    public BigDecimal getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        ValorTotal = valorTotal;
    }
}
