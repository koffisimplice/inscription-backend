package com.lyceeande.inscription.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "utilisateurs")
@Data
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenoms;
    private String email;
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean actif = true;

    public enum Role {
        ADMIN,        // Directeur — accès total
        INTENDANT,    // Valide les frais
        EDUCATEUR     // Valide les documents
    }
}