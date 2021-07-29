package com.techbank.japaoPadaria.model;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity(name="ItemVenda") //Anotações
@Table (name = "item_venda")
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private int Quantidade;

    @Column(name = "valor_de_venda")
    private BigDecimal ValorDeVenda;

    @ManyToOne
    @JoinColumn(name = "produto")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "id_venda")
    private Venda venda;

    /*
    @Column(name = "valor_unidade")
    private BigDecimal ValorUnidade;
    */

    public ItemVenda() {

    }

    public ItemVenda(int quantidade, BigDecimal valorDeVenda, Produto produto, Venda venda) {
        this.Quantidade = quantidade;
        this.ValorDeVenda = valorDeVenda;
        this.produto = produto;
        this.venda = venda;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.Quantidade = quantidade;
    }

    public BigDecimal getValorDeVenda() {
        return ValorDeVenda;
    }

    public void setValorDeVenda(BigDecimal valorDeVenda) {
        this.ValorDeVenda = valorDeVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    /*
    public BigDecimal getValorUnidade() {
        return ValorUnidade;
    }

    public void setValorUnidade(BigDecimal valorUnidade) {
        this.ValorUnidade = valorUnidade;
    }

     */
}


