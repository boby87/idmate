package ktv.cm.idmate.service.serviceImpl;

import ktv.cm.idmate.dao.entites.DocumentType;
import ktv.cm.idmate.dao.entites.FileStorageProperties;
import ktv.cm.idmate.dao.entites.KycIdentification;
import ktv.cm.idmate.dao.repositories.KycIdentityRepository;
import ktv.cm.idmate.dao.repositories.PhoneRepository;
import ktv.cm.idmate.dto.DocumentRequest;
import ktv.cm.idmate.dto.FaceRequest;
import ktv.cm.idmate.dto.PingRequest;
import ktv.cm.idmate.dto.SignatureRequest;
import ktv.cm.idmate.service.metier.KycIdentityService;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
public class KycIdentityServiceImpl implements KycIdentityService {
    private final KycIdentityRepository kycIdentityRepository;
    private final PhoneRepository phoneRepository;
    private final FileStorageProperties fileStorageProperties;
    private final FaceCompareImpl faceCompareImpl;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public KycIdentityServiceImpl(KycIdentityRepository kycIdentityRepository, PhoneRepository phoneRepository, FileStorageProperties fileStorageProperties, FaceCompareImpl faceCompareImpl, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.kycIdentityRepository = kycIdentityRepository;
        this.phoneRepository = phoneRepository;
        this.fileStorageProperties = fileStorageProperties;
        this.faceCompareImpl = faceCompareImpl;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void addKycDocument(DocumentRequest documentRequest, String phoneNumber) {
        var phone = phoneRepository.findByPhoneNumber(phoneNumber);
        if (phone == null) {
            throw new IllegalArgumentException("PhoneNumber Not Found.");
        }
        var kycIdentification = kycIdentityRepository.save(new KycIdentification());
        Path path = storeFile(documentRequest.document());
        File file = path.toFile();
        String infoDoc = getInfoDoc(file);
        if (infoDoc.contains(phone.getUsers().getFirstName()) ||
                infoDoc.contains(phone.getUsers().getLastName())
        ){
          kycIdentification.setVerifiedDocument(true);
        }
        kycIdentification.setDocument(path.toString());
        kycIdentification.setDocumentType(DocumentType.valueOf(documentRequest.documentType()));
        kycIdentification.setUsers(phone.getUsers());
    }

    @Override
    public void addKycFace( MultipartFile face, String phoneNumber) {
        var phone = phoneRepository.findByPhoneNumber(phoneNumber);
        if (phone == null) {
            throw new IllegalArgumentException("PhoneNumber Not Found.");
        }
        var kycIdentification = phone.getUsers().getKycIdentification();
        Path path = storeFile(face);
        File file = path.toFile();
        Path path1 = Path.of(kycIdentification.getDocument());
        String faceInfo = getInfoDoc(file);
        kycIdentification.setFace(faceInfo);
        try {
            faceCompareImpl.compareFaces(pathToMultipartFile(path), pathToMultipartFile(path1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MultipartFile pathToMultipartFile(Path path) throws IOException {
        byte[] content = Files.readAllBytes(path);
        return new MultipartFile() {
            @Override
            public String getName() {
                return path.getFileName().toString();
            }

            @Override
            public String getOriginalFilename() {
                return path.getFileName().toString();
            }

            @Override
            public String getContentType() {
                try {
                    return Files.probeContentType(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public boolean isEmpty() {
                return content.length == 0;
            }

            @Override
            public long getSize() {
                return content.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return content;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return Files.newInputStream(path);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.copy(path, dest.toPath());
            }
        };
    }

    @Override
    public void addKycPing(PingRequest pingRequest, String phoneNumber) {
        var phone = phoneRepository.findByPhoneNumber(phoneNumber);
        if (phone == null) {
            throw new IllegalArgumentException("PhoneNumber Not Found.");
        }
        var kycIdentification = phone.getUsers().getKycIdentification();
        kycIdentification.setPing(bCryptPasswordEncoder.encode(pingRequest.ping()));
    }

    @Override
    public void addKycSignature(SignatureRequest signatureRequest, String phoneNumber) {
        var phone = phoneRepository.findByPhoneNumber(phoneNumber);
        if (phone == null) {
            throw new IllegalArgumentException("PhoneNumber Not Found.");
        }
        var kycIdentification = phone.getUsers().getKycIdentification();
        kycIdentification.setSignature(signatureRequest.signature());
    }
    private Path storeFile(MultipartFile file) {
        final String UPLOAD_DIR = fileStorageProperties.getDir();

        try {
            // Créer le dossier si inexistant
            Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath); // plus sûr que mkdirs()

            // Nom de fichier unique
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Chemin complet
            Path filePath = uploadPath.resolve(fileName);

            // Écriture
            Files.write(filePath, file.getBytes());

            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Error saving file: " + e.getMessage(), e);
        }
    }

    //extraire les infos de l'imagage grace a l'outil OCR
    private String getInfoDoc( File image) {  // cherchons a extraire les infos de l'image et retournons une chaine de caractèere
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("eng");
        String result = "";
        try {
            result  = tesseract.doOCR(image);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
