package com.matheushnobre.LanchasNobre.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Viagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataPartida;

    @Column(nullable = false)
    private LocalDateTime dataChegada;

    @ManyToOne
    @JoinColumn(name = "cidade_origem_id")
    private Cidade cidadeOrigem;

    @ManyToOne
    @JoinColumn(name = "cidade_destino_id")
    private Cidade cidadeDestino;

    @ManyToOne
    @JoinColumn(name = "lancha_id")
    private Lancha lancha;

    @Column(nullable = false)
    private Double preco;

    @OneToMany(mappedBy = "viagem")
    @JsonIgnore
    private List<Passagem> passagens;
}
