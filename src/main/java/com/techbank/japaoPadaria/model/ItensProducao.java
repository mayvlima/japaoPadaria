package com.techbank.japaoPadaria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_producao")
public class ItensProducao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor_de_custo")
    private BigDecimal valorDeCusto;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "quantidade_de_medida")
    private int quantidadeDeMedida;

    @Column(name = "unidade_de_medida")
    private String unidadeDeMedida;

    @ManyToOne
    @JoinColumn(name = "id_producao")
    @JsonIgnore
    private Producao producao;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    @JsonIgnore
    private Produto produto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorDeCusto() {
        return valorDeCusto;
    }

    public void setValorDeCusto(BigDecimal valorDeCusto) {
        this.valorDeCusto = valorDeCusto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidadeDeMedida() {
        return quantidadeDeMedida;
    }

    public void setQuantidadeDeMedida(int quantidadeDeMedida) {
        this.quantidadeDeMedida = quantidadeDeMedida;
    }

    public String getUnidadeDeMedida() {
        return unidadeDeMedida;
    }

    public void setUnidadeDeMedida(String unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
    }

    public Producao getProducao() {
        return producao;
    }

    public void setProducao(Producao producao) {
        this.producao = producao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

}
