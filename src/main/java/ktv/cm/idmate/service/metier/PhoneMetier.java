package ktv.cm.idmate.service.metier;

import jakarta.validation.Valid;
import ktv.cm.idmate.dto.LegalNameRequest;
import ktv.cm.idmate.dto.OtpDto;
import ktv.cm.idmate.dto.PhoneRequest;
import ktv.cm.idmate.dto.ResponseUserDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface PhoneMetier {
    void sendVerificationCode(PhoneRequest phoneRequest);
    OtpDto addPhoneNumber(PhoneRequest phoneRequest);
    void otpVerification( OtpDto otpDto);
    void addName(LegalNameRequest legalNameRequest);

}
