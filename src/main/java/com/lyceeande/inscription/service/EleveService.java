package com.lyceeande.inscription.service;

import com.lyceeande.inscription.model.Eleve;
import com.lyceeande.inscription.model.Inscription;
import com.lyceeande.inscription.model.Parent;
import com.lyceeande.inscription.model.Tuteur;
import com.lyceeande.inscription.repository.EleveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EleveService {

    private final EleveRepository eleveRepository;

    @Transactional
    public Eleve creerEleve(Eleve eleve) {
        // ... (code de création inchangé)
        if (eleve.getParents() != null) eleve.getParents().forEach(p -> p.setEleve(eleve));
        if (eleve.getTuteur() != null) eleve.getTuteur().setEleve(eleve);
        if (eleve.getInscriptions() != null) eleve.getInscriptions().forEach(i -> i.setEleve(eleve));
        return eleveRepository.save(eleve);
    }

    public List<Eleve> listerEleves() {
        return eleveRepository.findAll();
    }

    public Optional<Eleve> trouverParId(Long id) {
        return eleveRepository.findById(id);
    }

    @Transactional
    public Eleve modifierEleve(Long id, Eleve eleveModifie) {
        return eleveRepository.findById(id).map(eleve -> {
            // 1. Mise à jour des champs de l'élève (Uniquement si non vides)
            if (eleveModifie.getNom() != null && !eleveModifie.getNom().trim().isEmpty()) eleve.setNom(eleveModifie.getNom());
            if (eleveModifie.getPrenoms() != null && !eleveModifie.getPrenoms().trim().isEmpty()) eleve.setPrenoms(eleveModifie.getPrenoms());
            if (eleveModifie.getDateNaissance() != null) eleve.setDateNaissance(eleveModifie.getDateNaissance());
            if (eleveModifie.getLieuNaissance() != null && !eleveModifie.getLieuNaissance().trim().isEmpty()) eleve.setLieuNaissance(eleveModifie.getLieuNaissance());
            if (eleveModifie.getNumeroActeNaissance() != null && !eleveModifie.getNumeroActeNaissance().trim().isEmpty()) eleve.setNumeroActeNaissance(eleveModifie.getNumeroActeNaissance());
            if (eleveModifie.getDateActeNaissance() != null) eleve.setDateActeNaissance(eleveModifie.getDateActeNaissance());
            if (eleveModifie.getEtabliA() != null && !eleveModifie.getEtabliA().trim().isEmpty()) eleve.setEtabliA(eleveModifie.getEtabliA());
            if (eleveModifie.getMatricule() != null && !eleveModifie.getMatricule().trim().isEmpty()) eleve.setMatricule(eleveModifie.getMatricule());
            if (eleveModifie.getSexe() != null) eleve.setSexe(eleveModifie.getSexe());
            if (eleveModifie.getNationalite() != null && !eleveModifie.getNationalite().trim().isEmpty()) eleve.setNationalite(eleveModifie.getNationalite());
            if (eleveModifie.getAdressePostale() != null && !eleveModifie.getAdressePostale().trim().isEmpty()) eleve.setAdressePostale(eleveModifie.getAdressePostale());
            if (eleveModifie.getTelephone() != null && !eleveModifie.getTelephone().trim().isEmpty()) eleve.setTelephone(eleveModifie.getTelephone());
            if (eleveModifie.getPhotoUrl() != null && !eleveModifie.getPhotoUrl().trim().isEmpty()) eleve.setPhotoUrl(eleveModifie.getPhotoUrl());

            // 2. Gestion intelligente des Parents (Mise à jour sans effacement brutal)
            if (eleveModifie.getParents() != null) {
                for (Parent newParent : eleveModifie.getParents()) {
                    newParent.setEleve(eleve);
                    if (newParent.getId() != null) {
                        eleve.getParents().stream()
                            .filter(p -> p.getId().equals(newParent.getId()))
                            .findFirst()
                            .ifPresent(existing -> {
                                existing.setNomComplet(newParent.getNomComplet());
                                existing.setProfession(newParent.getProfession());
                                existing.setResidence(newParent.getResidence());
                                existing.setContact(newParent.getContact());
                            });
                    }
                }
            }

            // 3. Gestion du Tuteur
            if (eleveModifie.getTuteur() != null) {
                Tuteur newTuteur = eleveModifie.getTuteur();
                newTuteur.setEleve(eleve);
                if (eleve.getTuteur() != null) {
                    Tuteur existing = eleve.getTuteur();
                    existing.setNomComplet(newTuteur.getNomComplet());
                    existing.setProfession(newTuteur.getProfession());
                    existing.setDomicile(newTuteur.getDomicile());
                    existing.setContact(newTuteur.getContact());
                } else {
                    eleve.setTuteur(newTuteur);
                }
            }

            // 4. Gestion des Inscriptions
            if (eleveModifie.getInscriptions() != null) {
                for (Inscription newIns : eleveModifie.getInscriptions()) {
                    if (newIns.getId() != null) {
                        eleve.getInscriptions().stream()
                            .filter(ins -> ins.getId().equals(newIns.getId()))
                            .findFirst()
                            .ifPresent(existing -> {
                                existing.setStatut(newIns.getStatut());
                                existing.setQualite(newIns.getQualite());
                                existing.setLv2(newIns.getLv2());
                                existing.setEtablissementOrigine(newIns.getEtablissementOrigine());
                                existing.setClasseOrigine(newIns.getClasseOrigine());
                                existing.setFraisPaye(newIns.getFraisPaye());
                                existing.setFicheEnLigne(newIns.getFicheEnLigne());
                                existing.setActeNaissanceFourni(newIns.getActeNaissanceFourni());
                                existing.setFicheAffectation(newIns.getFicheAffectation());
                                existing.setBulletinTrimestre(newIns.getBulletinTrimestre());
                                if (newIns.getClasse() != null) existing.setClasse(newIns.getClasse());
                            });
                    }
                }
            }

            return eleveRepository.save(eleve);
        }).orElseThrow(() -> new RuntimeException("Élève non trouvé"));
    }

    public void supprimerEleve(Long id) {
        eleveRepository.deleteById(id);
    }

    public void mettreAJourPhoto(Long id, String photoUrl) {
        eleveRepository.findById(id).ifPresent(eleve -> {
            eleve.setPhotoUrl(photoUrl);
            eleveRepository.save(eleve);
        });
    }
}