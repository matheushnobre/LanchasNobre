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
public class Lancha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 100)
    private String nome;

    @Column(nullable = false)
    private Integer anoFabricacao;

    @ManyToOne
    @JoinColumn(name = "id_mapa_interno", nullable = false)
    private MapaInterno mapaInterno;

    @JsonIgnore
    @OneToMany(mappedBy = "lancha")
    private List<Viagem> viagens;
}
