package ktv.cm.idmate.service.metier;

import ktv.cm.idmate.dto.ResponseUserDto;

public interface UserMetier {
    ResponseUserDto getByPhoneNumber (String phoneNumber);
}
