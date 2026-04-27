package com.lyceeande.inscription.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class PhotoService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.base-url}")
    private String baseUrl;

    // Générer le QR code contenant l'URL d'upload
    public byte[] genererQrCode(Long eleveId) throws Exception {
        String urlUpload = baseUrl + "/mobile/photo/" + eleveId;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                urlUpload,
                BarcodeFormat.QR_CODE,
                300, 300
        );

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return outputStream.toByteArray();
    }

    // Sauvegarder la photo envoyée par le téléphone
    public String sauvegarderPhoto(Long eleveId, MultipartFile fichier) throws IOException {
        Path dossier = Paths.get(uploadDir);
        if (!Files.exists(dossier)) {
            Files.createDirectories(dossier);
        }

        String nomFichier = "eleve_" + eleveId + "_" + UUID.randomUUID() + ".jpg";
        Path cheminFichier = dossier.resolve(nomFichier);
        fichier.transferTo(cheminFichier);

        return "/uploads/photos/" + nomFichier;
    }
}