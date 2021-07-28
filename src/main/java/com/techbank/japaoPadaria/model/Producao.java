package com.techbank.japaoPadaria.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "producao")
    private List<ItensProducao> itensProducao;

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

    public List<ItensProducao> getItensProducao() {
        return itensProducao;
    }

    public void setItensProducao(List<ItensProducao> itensProducao) {
        this.itensProducao = itensProducao;
    }

    public Produto getProduto() {
        return produto;
    }
}
