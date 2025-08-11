package ktv.cm.idmate.controller;

import jakarta.validation.Valid;
import ktv.cm.idmate.dto.LegalNameRequest;
import ktv.cm.idmate.dto.OtpDto;
import ktv.cm.idmate.dto.PhoneRequest;
import ktv.cm.idmate.dto.ResponseUserDto;
import ktv.cm.idmate.service.metier.PhoneMetier;
import ktv.cm.idmate.service.metier.UserMetier;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/phone")
@CrossOrigin("*")
public class PhoneController {
    private final PhoneMetier phoneMetier;
    private final UserMetier userMetier;

    public PhoneController(PhoneMetier phoneMetier, UserMetier userMetier) {
        this.phoneMetier = phoneMetier;
        this.userMetier = userMetier;
    }

    @PostMapping("/send-code")
    public ResponseEntity<OtpDto> sendVerificationCode(@Valid @RequestBody PhoneRequest phoneRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(phoneMetier.addPhoneNumber(phoneRequest)); // 200 OK sans contenu
    }

    @PostMapping("/otp-verification")
    public void otpVerification(@Valid @RequestBody OtpDto otpDto) {
        phoneMetier.otpVerification(otpDto); // 200 OK sans contenu
    }

    @GetMapping("/get-by-phone/{phoneNumber}")
    public ResponseEntity<ResponseUserDto> getByPhoneNumber(@PathVariable String phoneNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userMetier.getByPhoneNumber(phoneNumber));

    }


    @GetMapping("/ifo-doc")
    public ResponseEntity<String> getInfoDoc() {
        File image = new File("src/main/resources/images/CONTRAT.pdf");
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("eng");
        String result = "";
        try {
           result  = tesseract.doOCR(image);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);

    }

@PostMapping("addFullName")
    void addFullName(@RequestBody LegalNameRequest legalNameRequest){
    phoneMetier.addName(legalNameRequest);

    }

}
