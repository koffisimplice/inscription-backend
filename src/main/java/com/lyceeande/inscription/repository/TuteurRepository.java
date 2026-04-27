package com.lyceeande.inscription.repository;

import com.lyceeande.inscription.model.Tuteur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TuteurRepository extends JpaRepository<Tuteur, Long> {
    Optional<Tuteur> findByEleveId(Long eleveId);
}