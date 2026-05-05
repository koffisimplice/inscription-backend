package com.lyceeande.inscription.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "classes")
@Data
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Enumerated(EnumType.STRING)
    private Niveau niveau;

    private String anneeScolaire;
    private int capaciteMax;

    @OneToMany(mappedBy = "classe")
    @JsonIgnore
    private List<Inscription> inscriptions;

    @Transient
    public int getEffectifActuel() {
        return inscriptions != null ? inscriptions.size() : 0;
    }

    public enum Niveau {
        SIXIEME, CINQUIEME, QUATRIEME, TROISIEME,
        SECONDE, PREMIERE, TERMINALE
    }
}
