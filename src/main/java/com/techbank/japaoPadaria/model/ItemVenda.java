package com.techbank.japaoPadaria.model;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity(name="ItemVenda") //Anotações

public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "Id_Venda")
    private Integer IdVenda;

    @Column(name = "Id_Produto")
    private Integer IdProduto;

    @Column(name = "Valor_Unidade")
    private BigDecimal ValorUnidade;

    @Column
    private Integer Quantidade;

    @Column(name = "Valor_de_Venda")
    private BigDecimal ValorDeVenda;


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getIdVenda() {
        return IdVenda;
    }

    public void setIdVenda(Integer idVenda) {
        IdVenda = idVenda;
    }

    public Integer getIdProduto() {
        return IdProduto;
    }

    public void setIdProduto(Integer idProduto) {
        IdProduto = idProduto;
    }

    public BigDecimal getValorUnidade() {
        return ValorUnidade;
    }

    public void setValorUnidade(BigDecimal valorUnidade) {
        ValorUnidade = valorUnidade;
    }

    public Integer getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        Quantidade = quantidade;
    }

    public BigDecimal getValorDeVenda() {
        return ValorDeVenda;
    }

    public void setValorDeVenda(BigDecimal valorDeVenda) {
        ValorDeVenda = valorDeVenda;
    }
}
