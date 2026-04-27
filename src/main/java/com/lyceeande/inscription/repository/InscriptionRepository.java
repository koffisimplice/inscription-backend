package com.lyceeande.inscription.repository;

import com.lyceeande.inscription.model.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    List<Inscription> findByAnneeScolaire(String anneeScolaire);
    List<Inscription> findByEleveId(Long eleveId);
    List<Inscription> findByClasseId(Long classeId);
    List<Inscription> findByStatut(Inscription.StatutInscription statut);
    Optional<Inscription> findByEleveIdAndAnneeScolaire(Long eleveId, String anneeScolaire);
}