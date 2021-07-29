package com.techbank.japaoPadaria.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.List;


@Entity //Anotações
@Table(name = "Venda")

public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private LocalDateTime Data;

    @Column(name="Valor_total")
    private BigDecimal ValorTotal;

    @ManyToOne //usada para associar duas entidades.
    @JoinColumn(name="Id_Cliente")//responsavel pelo relacionamento
    private Cliente cliente;

    @OneToMany(mappedBy = "venda")//indica o lado não dominante da relação.
    List<ItemVenda> itemVenda;

    public Venda(){

    }

    public Long getId() {

        return Id;
    }

    public void setId(Long id) {

        this.Id= id;
    }

    public LocalDateTime getDataDaVenda() {
        return getDataDaVenda();
    }


    public void setDataDaVenda(LocalDateTime dataDaVenda) {
        this.Data = dataDaVenda;
    }

    public BigDecimal getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.ValorTotal = valorTotal;
    }

    public Cliente getCliente() {

        return cliente;
    }

    public void setICliente(Cliente cliente) {

        this.cliente = cliente;
    }
}
