package ktv.cm.idmate.controller;

import jakarta.validation.Valid;
import ktv.cm.idmate.dto.*;
import ktv.cm.idmate.service.metier.KycIdentityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/kyc-identify")
@CrossOrigin("*")
public class KycIdentifyController {
    private final KycIdentityService kycIdentityService;

    public KycIdentifyController(KycIdentityService kycIdentityService) {
        this.kycIdentityService = kycIdentityService;
    }


    @PostMapping("/{phoneNumber}")
    public ResponseEntity<String> addKycDocument(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("documentType") String documentType,
                                                 @PathVariable String phoneNumber) {
        try {
            kycIdentityService.addKycDocument(new DocumentRequest(documentType, file), phoneNumber);
            return ResponseEntity.ok("Document added successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/face/{phoneNumber}")
    public ResponseEntity<String> addKycFace(@RequestParam("face") MultipartFile face, @PathVariable String phoneNumber) {
        try {
            kycIdentityService.addKycFace(face, phoneNumber);
            return ResponseEntity.ok("Face added successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/ping/{phoneNumber}")
    public ResponseEntity<String> addKycPing(@Valid @RequestBody PingRequest pingRequest, @PathVariable String phoneNumber) {
        try {
            kycIdentityService.addKycPing(pingRequest, phoneNumber);
            return ResponseEntity.ok("Ping added successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/signature/{phoneNumber}")
    public ResponseEntity<String> addKycSignature(@Valid @RequestBody SignatureRequest signatureDto, @PathVariable String phoneNumber) {
        try {
            kycIdentityService.addKycSignature(signatureDto, phoneNumber);
            return ResponseEntity.ok("SIGNATURE added successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
