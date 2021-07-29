package com.techbank.japaoPadaria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "producao")
public class Producao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor_de_custo_unitario")
    private BigDecimal valorDeCustoUnitario;

    @Column(name = "data_de_producao")
    private LocalDateTime dataDeProducao;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "finalizada")
    private boolean finalizada;

    @OneToMany(mappedBy = "producao")
    @JsonIgnore
    private List<ItemProducao> itemProducao;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    @JsonIgnoreProperties("producao")
    private Produto produto;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorDeCustoUnitario() {
        return valorDeCustoUnitario;
    }

    public void setValorDeCustoUnitario(BigDecimal valorDeCustoUnitario) {
        this.valorDeCustoUnitario = valorDeCustoUnitario;
    }

    public LocalDateTime getDataDeProducao() {
        return dataDeProducao;
    }

    public void setDataDeProducao(LocalDateTime dataDeProducao) {
        this.dataDeProducao = dataDeProducao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public List<ItemProducao> getItemProducao() {
        return itemProducao;
    }

    public void setItensProducao(List<ItemProducao> itensProducao) {
        this.itemProducao = itensProducao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }
}
