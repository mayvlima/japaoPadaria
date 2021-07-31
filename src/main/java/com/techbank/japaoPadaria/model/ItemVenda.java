package com.techbank.japaoPadaria.model;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity(name="ItemVenda") //Anotações
@Table (name = "item_venda")
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantidade;

    @Column(name = "valor_unidade")
    private BigDecimal valorUnidade;

    @Column(name = "valor_de_venda")
    private BigDecimal valorDeVenda;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "id_venda")
    private Venda venda;



    public ItemVenda() {

    }

    public ItemVenda(int quantidade, BigDecimal valorUnidade, BigDecimal valorDeVenda, Produto produto, Venda venda) {
        this.quantidade = quantidade;
        this.valorUnidade = valorUnidade;
        this.valorDeVenda = valorDeVenda;
        this.produto = produto;
        this.venda = venda;
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

    public BigDecimal getValorUnidade() {
        return valorUnidade;
    }

    public void setValorUnidade(BigDecimal valorUnidade) {
        this.valorUnidade = valorUnidade;
    }

    public BigDecimal getValorDeVenda() {
        return valorDeVenda;
    }

    public void setValorDeVenda(BigDecimal valorDeVenda) {
        this.valorDeVenda = valorDeVenda;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}


