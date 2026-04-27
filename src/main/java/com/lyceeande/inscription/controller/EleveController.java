package com.lyceeande.inscription.controller;

import com.lyceeande.inscription.model.Eleve;
import com.lyceeande.inscription.service.EleveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eleves")
@RequiredArgsConstructor
public class EleveController {

    private final EleveService eleveService;

    // Créer une inscription
    @PostMapping
    public ResponseEntity<Eleve> creer(@RequestBody Eleve eleve) {
        return ResponseEntity.ok(eleveService.creerEleve(eleve));
    }

    // Lister toutes les inscriptions
    @GetMapping
    public ResponseEntity<List<Eleve>> lister() {
        return ResponseEntity.ok(eleveService.listerEleves());
    }

    // Obtenir un élève par ID
    @GetMapping("/{id}")
    public ResponseEntity<Eleve> obtenir(@PathVariable Long id) {
        return eleveService.trouverParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Modifier une inscription
    @PutMapping("/{id}")
    public ResponseEntity<Eleve> modifier(@PathVariable Long id, @RequestBody Eleve eleve) {
        return ResponseEntity.ok(eleveService.modifierEleve(id, eleve));
    }

    // Supprimer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        eleveService.supprimerEleve(id);
        return ResponseEntity.noContent().build();
    }
}