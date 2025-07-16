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
public class Assento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String codigoAssento;

    @Column
    private Integer posicaoX;

    @Column
    private Integer posicaoY;

    @ManyToOne
    @JoinColumn(name = "id_mapa_interno", nullable = false)
    private MapaInterno mapaInterno;
}
