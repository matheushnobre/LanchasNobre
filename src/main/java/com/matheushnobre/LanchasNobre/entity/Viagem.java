package com.matheushnobre.LanchasNobre.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Future
    @Column(nullable = false)
    private LocalDateTime dataPartida;

    @Future
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

    @Positive
    @Column(nullable = false)
    private Double preco;

    @OneToMany(mappedBy = "viagem")
    @JsonIgnore
    private List<Passagem> passagens;
}
