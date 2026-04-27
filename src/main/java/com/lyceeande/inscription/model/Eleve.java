package com.lyceeande.inscription.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "eleves")
@Data
public class Eleve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenoms;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String numeroActeNaissance;
    private LocalDate dateActeNaissance; // Date de l'acte (le "du ...")
    private String etabliA;
    private String matricule;

    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    private String nationalite;
    private String adressePostale;
    private String telephone;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String photoUrl;

    @OneToMany(mappedBy = "eleve", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("eleve")
    private List<Inscription> inscriptions;

    @OneToMany(mappedBy = "eleve", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("eleve")
    private List<Parent> parents;

    @OneToOne(mappedBy = "eleve", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("eleve")
    private Tuteur tuteur;

    public enum Sexe { M, F }
}
