package com.techbank.japaoPadaria.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "itens_receita")
public class ItensReceita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_receita", referencedColumnName = "id")
    private Receita idReceita;

    @ManyToOne
    @JoinColumn(name = "id_produto", referencedColumnName = "id")
    private Produto idProduto;

    @Column
    private Integer quantidade;

    public ItensReceita() {
    }

    public ItensReceita(Receita idReceita, Produto idProduto, Integer quantidade) {
        this.idReceita = idReceita;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Receita getIdReceita() {
        return idReceita;
    }

    public void setIdReceita(Receita idReceita) {
        this.idReceita = idReceita;
    }

    public Produto getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Produto idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ItensReceita{" +
                "id=" + id +
                ", idReceita=" + idReceita +
                ", idProduto=" + idProduto +
                ", quantidade=" + quantidade +
                '}';
    }
}
