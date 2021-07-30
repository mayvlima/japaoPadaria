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

    @Column(name = "data_da_venda")
    private LocalDateTime dataDaVenda;

    @Column(name="valor_total")
    private BigDecimal valorTotal;

    @ManyToOne //usada para associar duas entidades.
    @JoinColumn(name="id_cliente")//responsavel pelo relacionamento
    private Cliente cliente;

    @OneToMany(mappedBy = "venda")//indica o lado não dominante da relação.
    List<ItemVenda> itemVenda;

    @Column(name = "finalizada")
    private boolean finalizada;

    public Venda(){

    }

    public Long getId() {

        return Id;
    }

    public void setId(Long id) {

        this.Id= id;
    }

    public LocalDateTime getDataDaVenda() {
        return dataDaVenda;
    }

    public void setDataDaVenda(LocalDateTime dataDaVenda) {
        this.dataDaVenda = dataDaVenda;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {

        return cliente;
    }

    public void setICliente(Cliente cliente) {

        this.cliente = cliente;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }
}
