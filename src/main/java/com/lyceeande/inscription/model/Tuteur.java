package com.lyceeande.inscription.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tuteurs")
@Data
public class Tuteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomComplet;
    private String profession;
    private String domicile;
    private String contact;

    @OneToOne
    @JoinColumn(name = "eleve_id")
    @JsonIgnoreProperties("tuteur")
    private Eleve eleve;
}
