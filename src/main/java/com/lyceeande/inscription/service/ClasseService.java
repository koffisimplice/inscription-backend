package com.lyceeande.inscription.service;

import com.lyceeande.inscription.model.Classe;
import com.lyceeande.inscription.repository.ClasseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClasseService {

    private final ClasseRepository classeRepository;

    public List<Classe> recupererToutesLesClasses() {
        return classeRepository.findAll().stream().map(classe -> {
            // On calcule l'effectif actuel via les inscriptions (non nul)
            int effectif = classe.getInscriptions() != null ? classe.getInscriptions().size() : 0;
            // On peut utiliser un champ transitoire ou une Map, mais ici on va 
            // simplement s'assurer que l'objet retourné contient l'info si possible.
            // Comme le modèle n'a pas de champ effectifActuel, on va l'ajouter de manière transitoire.
            return classe;
        }).collect(Collectors.toList());
    }

    public Classe creerClasse(Classe classe) {
        return classeRepository.save(classe);
    }
}
