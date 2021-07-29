package com.techbank.japaoPadaria.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "valor_de_custo")
    private BigDecimal valorDeCusto;

    @Column(name = "valor_de_venda")
    private BigDecimal valorDeVenda;

    @Column(name = "codigo_de_barras", nullable = false, unique = true)
    private String codigoDeBarras;

    @Column(name = "quantidade_medida")
    private BigDecimal quantidadeDeMedida;

    @Column(name = "unidade_de_medida")
    private String unidadeDeMedida;

    @Column(nullable = false)
    private boolean status;

    @OneToMany(mappedBy = "produto")
    private List<ItemProducao> itemProducao;

    @OneToMany(mappedBy = "produto")
    private List<Producao> producoes;


    public Produto() {

    }

    public Produto(String descricao, BigDecimal valorDeCusto, BigDecimal valorDeVenda, String codigoDeBarras, BigDecimal quantidadeMedida, String unidadeDeMedida, boolean status) {
        this.descricao = descricao;
        this.valorDeCusto = valorDeCusto;
        this.valorDeVenda = valorDeVenda;
        this.codigoDeBarras = codigoDeBarras;
        this.quantidadeDeMedida = quantidadeMedida;
        this.unidadeDeMedida = unidadeDeMedida;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorDeCusto() {
        return valorDeCusto;
    }

    public void setValorDeCusto(BigDecimal valorDeCusto) {
        this.valorDeCusto = valorDeCusto;
    }

    public BigDecimal getValorDeVenda() {
        return valorDeVenda;
    }

    public void setValorDeVenda(BigDecimal valorDeVenda) {
        this.valorDeVenda = valorDeVenda;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public BigDecimal getQuantidadeDeMedida() {
        return quantidadeDeMedida;
    }

    public void setQuantidadeDeMedida(BigDecimal quantidadeDeMedida) {
        this.quantidadeDeMedida = quantidadeDeMedida;
    }

    public String getUnidadeDeMedida() {
        return unidadeDeMedida;
    }

    public void setUnidadeDeMedida(String unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", valorDeCusto=" + valorDeCusto +
                ", valorDeVenda=" + valorDeVenda +
                ", codigoDeBarras='" + codigoDeBarras + '\'' +
                ", quantidadeMedida=" + quantidadeDeMedida +
                ", unidadeDeMedida=" + unidadeDeMedida +
                ", status=" + status +
                '}';
    }
}
