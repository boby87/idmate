package ktv.cm.idmate.service.serviceImpl;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import ktv.cm.idmate.service.metier.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {


    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromNumber;

    @Override
    public void sendSms(String to, String body) {
        Twilio.init(accountSid, authToken);

        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(fromNumber),
                body
        ).create();

        System.out.println("SMS envoyé avec SID: " + message.getSid());
    }
}
