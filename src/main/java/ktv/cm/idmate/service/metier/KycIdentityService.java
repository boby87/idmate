package ktv.cm.idmate.service.metier;

import ktv.cm.idmate.dto.DocumentRequest;
import ktv.cm.idmate.dto.FaceRequest;
import ktv.cm.idmate.dto.PingRequest;
import ktv.cm.idmate.dto.SignatureRequest;
import org.springframework.web.multipart.MultipartFile;

public interface KycIdentityService {
    void addKycDocument(DocumentRequest documentRequest, String phoneNumber);
    void addKycFace(MultipartFile face, String phoneNumber);
    void addKycPing(PingRequest pingRequest, String phoneNumber);
    void addKycSignature(SignatureRequest signatureRequest, String phoneNumber);
}
