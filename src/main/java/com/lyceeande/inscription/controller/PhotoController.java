package com.lyceeande.inscription.controller;

import com.lyceeande.inscription.service.EleveService;
import com.lyceeande.inscription.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;
    private final EleveService eleveService;

    // Générer un QR code pour un élève
    @GetMapping("/qrcode/{eleveId}")
    public ResponseEntity<byte[]> genererQrCode(@PathVariable Long eleveId) throws Exception {
        byte[] qrCode = photoService.genererQrCode(eleveId);
        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .body(qrCode);
    }

    // Recevoir la photo depuis le téléphone
    @PostMapping("/upload/{eleveId}")
    public ResponseEntity<Map<String, String>> uploadPhoto(
            @PathVariable Long eleveId,
            @RequestParam("photo") MultipartFile photo) throws IOException {

        String photoUrl = photoService.sauvegarderPhoto(eleveId, photo);
        eleveService.mettreAJourPhoto(eleveId, photoUrl);
        return ResponseEntity.ok(Map.of(
                "message", "Photo enregistrée avec succès",
                "photoUrl", photoUrl
        ));
    }

    // Vérifier si la photo est arrivée (polling depuis le frontend)
    @GetMapping("/statut/{eleveId}")
    public ResponseEntity<?> statutPhoto(@PathVariable Long eleveId) {
        return eleveService.trouverParId(eleveId)
                .map(eleve -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("photoDisponible", eleve.getPhotoUrl() != null);
                    response.put("photoUrl", eleve.getPhotoUrl() != null ? eleve.getPhotoUrl() : "");
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}