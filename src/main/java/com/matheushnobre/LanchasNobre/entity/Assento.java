package com.matheushnobre.LanchasNobre.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
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

    @JsonIgnore
    @OneToMany(mappedBy = "assento")
    private List<Passagem> passagens;
}
