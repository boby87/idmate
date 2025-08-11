package ktv.cm.idmate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserProfileDto(
        @NotNull
        Long id,
        @NotBlank(message = "Le nom complet est requis.")
        String fullName,
        @NotBlank(message = "L'adresse est requise.")
        String address,
        @NotBlank(message = "Le numéro de téléphone est requis.")
        String phoneNumber,
        @NotBlank(message = "Le pays est requis.")
        @NotNull(message = "Le pays ne peut pas être nul.")
        String country,

        boolean verified) {
}
