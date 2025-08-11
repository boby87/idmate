package ktv.cm.idmate.config.certificat;



import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class GeneratedCertificate {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        // Générer la paire de clés RSA
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Encodage des clés
        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] privateKey = keyPair.getPrivate().getEncoded();

        // Définir le chemin du répertoire "certs" dans resources
        Path certsDirectory = Paths.get("authServer/src/main/resources/certs");
        if (!Files.exists(certsDirectory)) {
            Files.createDirectories(certsDirectory);
        }

        // Écrire la clé publique dans le fichier public.pem
        try (PemWriter pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(certsDirectory.resolve("public.pem").toFile())))) {
            PemObject pemObject = new PemObject("PUBLIC KEY", publicKey);
            pemWriter.writeObject(pemObject);
        }

        // Écrire la clé privée dans le fichier private.pem
        try (PemWriter pemWriterPrivate = new PemWriter(new OutputStreamWriter(new FileOutputStream(certsDirectory.resolve("private.pem").toFile())))) {
            PemObject pemObjectPrivate = new PemObject("PRIVATE KEY", privateKey);
            pemWriterPrivate.writeObject(pemObjectPrivate);
        }

    }
}
