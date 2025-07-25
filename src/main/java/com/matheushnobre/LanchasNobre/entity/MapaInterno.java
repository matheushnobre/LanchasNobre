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
public class MapaInterno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer capacidade;

    @Column(nullable = false, length = 512)
    private String descricao;

    @OneToMany(mappedBy = "mapaInterno")
    @JsonIgnore
    private List<Lancha> lanchaEntities;

    @OneToMany(mappedBy = "mapaInterno", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Assento> assentos;
}
