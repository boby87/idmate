package ktv.cm.idmate.service.serviceImpl;

import ktv.cm.idmate.service.metier.PinEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PinEncoderImpl implements PinEncoder {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PinEncoderImpl() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String pin) {
        return bCryptPasswordEncoder.encode(pin);
    }


}
