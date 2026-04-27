package com.lyceeande.inscription.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "parents")
@Data
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeParent type;

    private String nomComplet;
    private String profession;
    private String residence;
    private String contact;

    @ManyToOne
    @JoinColumn(name = "eleve_id")
    @JsonIgnoreProperties("parents")
    private Eleve eleve;

    public enum TypeParent { PERE, MERE }
}
