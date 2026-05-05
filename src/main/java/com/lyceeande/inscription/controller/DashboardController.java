package com.lyceeande.inscription.controller;

import com.lyceeande.inscription.model.Inscription;
import com.lyceeande.inscription.repository.InscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final InscriptionRepository inscriptionRepository;

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        List<Inscription> allInscriptions = inscriptionRepository.findAll();

        long totalInscriptions = allInscriptions.size();
        long paiementsConfirmes = allInscriptions.stream()
                .filter(i -> Boolean.TRUE.equals(i.getFraisPaye()))
                .count();
        long dossiersFinalises = allInscriptions.stream()
                .filter(i -> i.getStatut() == Inscription.StatutInscription.FINALISEE)
                .count();
        long enAttente = totalInscriptions - paiementsConfirmes;

        // Calcul du pourcentage de finalisation
        int pourcentageFinalise = totalInscriptions > 0 
                ? (int) ((dossiersFinalises * 100) / totalInscriptions) 
                : 0;

        // Récupération des 5 dernières activités
        List<Map<String, String>> activites = allInscriptions.stream()
                .sorted((a, b) -> b.getId().compareTo(a.getId()))
                .limit(5)
                .map(i -> {
                    Map<String, String> act = new HashMap<>();
                    act.put("id", i.getId().toString());
                    String nomComplet = "N/A";
                    if (i.getEleve() != null) {
                        nomComplet = i.getEleve().getNom() + " " + i.getEleve().getPrenoms();
                    }
                    act.put("nom", nomComplet);
                    act.put("classe", i.getClasse() != null ? i.getClasse().getNom() : "N/A");
                    act.put("statut", i.getStatut() != null ? i.getStatut().toString() : "N/A");
                    return act;
                })
                .collect(Collectors.toList());

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalInscriptions", totalInscriptions);
        stats.put("paiementsConfirmes", paiementsConfirmes);
        stats.put("dossiersFinalises", dossiersFinalises);
        stats.put("enAttentePaiement", enAttente);
        stats.put("pourcentageFinalise", pourcentageFinalise);
        stats.put("recentActivity", activites);

        return stats;
    }
}
