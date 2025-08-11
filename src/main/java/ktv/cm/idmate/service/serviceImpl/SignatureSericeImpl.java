package ktv.cm.idmate.service.serviceImpl;

import ktv.cm.idmate.service.metier.SignatureStorageMetier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
@Transactional
public class SignatureSericeImpl implements SignatureStorageMetier {
    private static final String SIGNATURE_FOLDER = "signatures"; // relative or absolute path
    @Override
    public String store(String base64Signature) {
        try {
            // Créer le dossier s’il n'existe pas
            File directory = new File(SIGNATURE_FOLDER);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Générer un nom de fichier unique
            String fileName = UUID.randomUUID() + ".png";
            File file = new File(directory, fileName);

            // Décoder le base64 en bytes
            byte[] imageBytes = Base64.getDecoder().decode(base64Signature);

            // Écrire dans un fichier
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(imageBytes);
            }

            // Retourner le chemin relatif ou absolu
            return file.getAbsolutePath(); // ou juste fileName si tu veux stocker uniquement le nom
        } catch (IOException e) {
            throw new RuntimeException("Error storing signature", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid Base64 signature", e);
        }
    }
    }

