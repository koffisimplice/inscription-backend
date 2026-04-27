package com.lyceeande.inscription.service;

import com.lyceeande.inscription.model.Eleve;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    private static final DateTimeFormatter FORMAT_DATE =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public byte[] genererFicheInscription(Eleve eleve) throws Exception {
        // On utilisera iText — la logique sera complétée
        // quand on ajoutera iText au pom.xml
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // TODO : génération PDF avec iText
        return out.toByteArray();
    }

    private String valeurOuVide(String valeur) {
        return valeur != null ? valeur : "";
    }
}