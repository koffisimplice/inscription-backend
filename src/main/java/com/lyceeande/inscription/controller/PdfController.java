package com.lyceeande.inscription.controller;

import com.lyceeande.inscription.model.Eleve;
import com.lyceeande.inscription.service.EleveService;
import com.lyceeande.inscription.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {

    private final PdfService pdfService;
    private final EleveService eleveService;

    @GetMapping("/{eleveId}")
    public ResponseEntity<byte[]> genererPdf(@PathVariable Long eleveId) throws Exception {
        Eleve eleve = eleveService.trouverParId(eleveId)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé"));

        byte[] pdf = pdfService.genererFicheInscription(eleve);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=fiche_" + eleveId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}