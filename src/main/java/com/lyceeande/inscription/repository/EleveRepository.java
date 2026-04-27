package com.lyceeande.inscription.repository;

import com.lyceeande.inscription.model.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EleveRepository extends JpaRepository<Eleve, Long> {

    List<Eleve> findByNomContainingIgnoreCase(String nom);
    List<Eleve> findByPrenomsContainingIgnoreCase(String prenoms);
    List<Eleve> findByMatricule(String matricule);
}