package com.matheushnobre.LanchasNobre.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Passagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_viagem")
    private Viagem viagem;

    @ManyToOne
    @JoinColumn(name = "id_assento")
    private Assento assento;

    @ManyToOne
    @JoinColumn(name = "id_passageiro")
    private Usuario passageiro;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;
}
