package com.lyceeande.inscription.config;

import com.lyceeande.inscription.model.Classe;
import com.lyceeande.inscription.model.Utilisateur;
import com.lyceeande.inscription.repository.ClasseRepository;
import com.lyceeande.inscription.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final ClasseRepository classeRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialisation des utilisateurs de test s'ils n'existent pas
        if (utilisateurRepository.count() == 0) {
            Utilisateur admin = new Utilisateur();
            admin.setNom("Directeur");
            admin.setPrenoms("Admin");
            admin.setEmail("admin@lyceeande.ci");
            admin.setMotDePasse("admin123");
            admin.setRole(Utilisateur.Role.ADMIN);
            utilisateurRepository.save(admin);

            Utilisateur intendant = new Utilisateur();
            intendant.setNom("Kouassi");
            intendant.setPrenoms("Intendant");
            intendant.setEmail("intendant@lyceeande.ci");
            intendant.setMotDePasse("intendant123");
            intendant.setRole(Utilisateur.Role.INTENDANT);
            utilisateurRepository.save(intendant);

            Utilisateur educateur = new Utilisateur();
            educateur.setNom("Konan");
            educateur.setPrenoms("Éducateur");
            educateur.setEmail("educateur@lyceeande.ci");
            educateur.setMotDePasse("educateur123");
            educateur.setRole(Utilisateur.Role.EDUCATEUR);
            utilisateurRepository.save(educateur);
            
            System.out.println("Utilisateurs de test créés.");
        }

        // Initialisation de quelques classes pour le formulaire
        if (classeRepository.count() == 0) {
            Classe sixieme = new Classe();
            sixieme.setNom("6ème 1");
            sixieme.setNiveau(Classe.Niveau.SIXIEME);
            sixieme.setAnneeScolaire("2024-2025");
            sixieme.setCapaciteMax(50);
            classeRepository.save(sixieme);

            Classe seconde = new Classe();
            seconde.setNom("2nde A1");
            seconde.setNiveau(Classe.Niveau.SECONDE);
            seconde.setAnneeScolaire("2024-2025");
            seconde.setCapaciteMax(45);
            classeRepository.save(seconde);
            
            System.out.println("Classes de test créées.");
        }
    }
}
