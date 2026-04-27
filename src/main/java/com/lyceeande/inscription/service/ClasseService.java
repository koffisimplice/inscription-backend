package com.lyceeande.inscription.service;

import com.lyceeande.inscription.model.Classe;
import com.lyceeande.inscription.repository.ClasseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClasseService {

    private final ClasseRepository classeRepository;

    public List<Classe> recupererToutesLesClasses() {
        return classeRepository.findAll();
    }

    public Classe creerClasse(Classe classe) {
        return classeRepository.save(classe);
    }
}
