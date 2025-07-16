package com.matheushnobre.LanchasNobre.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@ToString
public class Lancha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 100)
    private String nome;

    @Column(nullable = false)
    private Integer ano_fabricacao;

    @ManyToOne
    @JoinColumn(name = "id_mapa_interno", nullable = false)
    private MapaInterno mapaInterno;
}
