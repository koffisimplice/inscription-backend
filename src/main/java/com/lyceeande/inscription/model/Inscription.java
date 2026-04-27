package com.lyceeande.inscription.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "inscriptions")
@Data
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String anneeScolaire;
    private LocalDate dateInscription = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private StatutInscription statut = StatutInscription.EN_COURS;

    @Enumerated(EnumType.STRING)
    private Qualite qualite;

    private String lv2;
    private String etablissementOrigine;
    private String classeOrigine;

    private Boolean fraisPaye = false;
    private Boolean ficheEnLigne = false;
    private Boolean acteNaissanceFourni = false;
    private Boolean ficheAffectation = false;
    private Boolean bulletinTrimestre = false;

    @ManyToOne
    @JoinColumn(name = "valide_intendant_id")
    private Utilisateur valideParIntendant;

    @ManyToOne
    @JoinColumn(name = "valide_educateur_id")
    private Utilisateur valideParEducateur;

    @ManyToOne
    @JoinColumn(name = "eleve_id")
    @JsonIgnoreProperties("inscriptions")
    private Eleve eleve;

    @ManyToOne
    @JoinColumn(name = "classe_id")
    @JsonIgnoreProperties("inscriptions")
    private Classe classe;

    public enum StatutInscription {
        EN_COURS, COMPLETE, VALIDEE_INTENDANT, VALIDEE_EDUCATEUR, FINALISEE
    }

    public enum Qualite {
        BE, DEMI_BOURSE, NON_BOURSIER
    }
}
