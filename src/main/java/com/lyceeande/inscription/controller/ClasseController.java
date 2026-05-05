package com.lyceeande.inscription.controller;

import com.lyceeande.inscription.model.Classe;
import com.lyceeande.inscription.service.ClasseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClasseController {

    private final ClasseService classeService;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllClasses() {
        List<Classe> classes = classeService.recupererToutesLesClasses();
        // On transforme en Map pour inclure l'effectif sans modifier le modèle Entity de manière risquée
        List<Map<String, Object>> response = classes.stream().map(c -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", c.getId());
            map.put("nom", c.getNom());
            map.put("niveau", c.getNiveau());
            map.put("anneeScolaire", c.getAnneeScolaire());
            map.put("capaciteMax", c.getCapaciteMax());
            // L'effectif est calculé ici dans la session Open EntityManager
            map.put("effectifActuel", c.getInscriptions() != null ? c.getInscriptions().size() : 0);
            return map;
        }).collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Classe> createClasse(@RequestBody Classe classe) {
        return ResponseEntity.ok(classeService.creerClasse(classe));
    }
}
