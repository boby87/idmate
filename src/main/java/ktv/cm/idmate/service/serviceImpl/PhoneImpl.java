package ktv.cm.idmate.service.serviceImpl;

import ktv.cm.idmate.dao.entites.Phone;
import ktv.cm.idmate.dao.entites.Users;
import ktv.cm.idmate.dao.repositories.PhoneRepository;
import ktv.cm.idmate.dao.repositories.UserRepository;
import ktv.cm.idmate.dto.LegalNameRequest;
import ktv.cm.idmate.dto.OtpDto;
import ktv.cm.idmate.dto.PhoneRequest;
import ktv.cm.idmate.service.metier.PhoneMetier;
import ktv.cm.idmate.service.metier.SmsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional
public class PhoneImpl implements PhoneMetier {
    private final PhoneRepository phoneRepository;
    private final UserRepository userRepository;
    private final SmsService smsService;

    public PhoneImpl(PhoneRepository phoneRepository, UserRepository userRepository, SmsService smsService) {
        this.phoneRepository = phoneRepository;
        this.userRepository = userRepository;
        this.smsService = smsService;
    }

    @Override
    public void sendVerificationCode(PhoneRequest phoneRequest) {
        if (phoneRequest.countryCode() == null || phoneRequest.phoneNumber() == null) {
            throw new IllegalArgumentException("Missing country code or phone number.");
        }// Construction du numéro complet
        String fullNumber = phoneRequest.countryCode() + phoneRequest.phoneNumber();

    }

    @Override
    public OtpDto addPhoneNumber(PhoneRequest phoneRequest) {
        var phone = phoneRepository.findByPhoneNumber(phoneRequest.phoneNumber());
        if (phone == null) {
            phone = new Phone();
            phone.setPhoneNumber(phoneRequest.phoneNumber());
            phoneRepository.save(phone);
            var user = userRepository.save(new Users());
            phone.setUsers(user);
             Random random = new Random();
            String fullNumber = phoneRequest.countryCode() + phoneRequest.phoneNumber();
            String otp = String.format("%04d", random.nextInt(10000));
            user.setOtp(otp);
            // Appel à la logique métier de vérification
            smsService.sendSms(fullNumber, otp);
            return new OtpDto( phoneRequest.phoneNumber(), otp);
        }else {
            Random random = new Random();
            String fullNumber = phoneRequest.countryCode() + phoneRequest.phoneNumber();
            String otp = String.format("%04d", random.nextInt(10000));
            phone.getUsers().setOtp(otp);
            // Appel à la logique métier de vérification
            smsService.sendSms(fullNumber, otp);
            return new OtpDto(phone.getPhoneNumber(), otp);
        }

    }

    @Override
    public void otpVerification(OtpDto otpDto) {
        Phone phone = phoneRepository.findByPhoneNumber(otpDto.phoneNumber());
        if (phone == null || !phone.getUsers().getOtp().equals(otpDto.otp())) {
            throw new IllegalArgumentException("Invalid OTP or phone number not found.");
        }
        // Marquer le téléphone comme vérifié
        phone.getUsers().setValidOtp(true);
    }

    @Override
    public void addName(LegalNameRequest legalNameRequest) {
       Phone phone= phoneRepository.findByPhoneNumber(legalNameRequest.phoneNumber());
       phone.getUsers().setFirstName(legalNameRequest.firstName());
       phone.getUsers().setLastName(legalNameRequest.lastName());
       
    }




}
