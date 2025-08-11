package ktv.cm.idmate.mapper;

import ktv.cm.idmate.dao.entites.Users;
import ktv.cm.idmate.dto.ResponseUserDto;

import java.util.Optional;

public class UserMapper {

    public static ResponseUserDto convertToResponseUserDto(Users user) {
        return new ResponseUserDto(
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber().getPhoneNumber(),
                user.getKycIdentification().isVerified(),
                user.getKycIdentification().getDocumentType().name(),
                user.getKycIdentification().getDocument()
        );
    }
}
