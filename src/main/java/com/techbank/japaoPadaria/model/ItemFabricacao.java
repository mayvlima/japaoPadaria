package com.techbank.japaoPadaria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item_fabricacao")
public class ItemFabricacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor_de_custo")
    private BigDecimal valorDeCusto;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "quantidade_de_medida")
    private Integer quantidadeDeMedida;

    @Column(name = "unidade_de_medida")
    private String unidadeDeMedida;

    @ManyToOne
    @JoinColumn(name = "id_fabricacao")
    @JsonIgnore
    private Fabricacao fabricacao;

    @ManyToOne
    @JoinColumn(name = "id_produto")
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

    public Fabricacao getFabricacao() {
        return fabricacao;
    }

    public void setFabricacao(Fabricacao fabricacao) {
        this.fabricacao = fabricacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }


}
