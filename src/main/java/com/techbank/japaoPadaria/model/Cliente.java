package com.techbank.japaoPadaria.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Cliente")
public class Cliente {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private String Nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    //encapsulamento do campo.

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + Id +
                ", nome='" + Nome + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
