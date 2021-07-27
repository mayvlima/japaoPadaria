package com.techbank.japaoPadaria.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "estoque")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantidade;

    @Column(name = "data_de_movimentacao")
    private LocalDateTime dataDeMovimentacao;

    @Column(name = "tipo_da_movimentacao")
    private String tipoDaMovimentacao;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    public Estoque(){

    }

    public Estoque(int quantidade, LocalDateTime dataDeMovimentacao, String tipoDaMovimentacao, Produto produto) {
        this.quantidade = quantidade;
        this.dataDeMovimentacao = dataDeMovimentacao;
        this.tipoDaMovimentacao = tipoDaMovimentacao;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataDeMovimentacao() {
        return dataDeMovimentacao;
    }

    public void setDataDeMovimentacao(LocalDateTime dataDeMovimentacao) {
        this.dataDeMovimentacao = dataDeMovimentacao;
    }

    public String getTipoDaMovimentacao() {
        return tipoDaMovimentacao;
    }

    public void setTipoDaMovimentacao(String tipoDaMovimentacao) {
        this.tipoDaMovimentacao = tipoDaMovimentacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
