package com.lyceeande.inscription.service;

import com.lyceeande.inscription.model.Inscription;
import com.lyceeande.inscription.repository.InscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;

    public List<Inscription> recupererToutesLesInscriptions() {
        return inscriptionRepository.findAll();
    }

    public Optional<Inscription> trouverParId(Long id) {
        return inscriptionRepository.findById(id);
    }

    public Inscription mettreAJourInscription(Long id, Inscription inscriptionDetails) {
        return inscriptionRepository.findById(id).map(inscription -> {
            inscription.setStatut(inscriptionDetails.getStatut());
            inscription.setFraisPaye(inscriptionDetails.getFraisPaye());
            inscription.setFicheEnLigne(inscriptionDetails.getFicheEnLigne());
            inscription.setActeNaissanceFourni(inscriptionDetails.getActeNaissanceFourni());
            inscription.setFicheAffectation(inscriptionDetails.getFicheAffectation());
            inscription.setBulletinTrimestre(inscriptionDetails.getBulletinTrimestre());
            inscription.setLv2(inscriptionDetails.getLv2());
            inscription.setQualite(inscriptionDetails.getQualite());
            return inscriptionRepository.save(inscription);
        }).orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
    }

    public void supprimerInscription(Long id) {
        inscriptionRepository.deleteById(id);
    }
}
