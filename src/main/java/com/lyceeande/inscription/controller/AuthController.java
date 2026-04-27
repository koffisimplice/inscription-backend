package com.lyceeande.inscription.controller;

import com.lyceeande.inscription.model.Utilisateur;
import com.lyceeande.inscription.repository.UtilisateurRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UtilisateurRepository utilisateurRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(loginRequest.getEmail());

        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            // Vérification basique du mot de passe (sans Spring Security pour l'instant)
            if (utilisateur.getMotDePasse().equals(loginRequest.getMotDePasse())) {
                Map<String, Object> response = new HashMap<>();
                response.put("token", "dummy-token-lycee-ande-" + utilisateur.getId()); // Token simulé
                response.put("user", utilisateur);
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(401).body(Map.of("error", "Identifiants incorrects"));
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String motDePasse;
    }
}
