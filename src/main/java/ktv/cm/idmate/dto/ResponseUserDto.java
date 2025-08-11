package ktv.cm.idmate.dto;

import ktv.cm.idmate.dao.entites.KycIdentification;

public record ResponseUserDto(
        String firstName,
        String lastName,
        String phoneNumber,
        boolean kycIdentification,
        String documentType,
        String documentUrl
        ) {
}
