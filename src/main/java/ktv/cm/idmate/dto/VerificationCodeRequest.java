package ktv.cm.idmate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record VerificationCodeRequest(

        @NotBlank(message = "Le numéro de téléphone est requis.")
        @Pattern(regexp = "\\+?[0-9]{6,15}", message = "Le numéro de téléphone est invalide.")
        String phoneNumber,
        @NotBlank(message = "Le code de vérification est requis.")
        @Pattern(regexp = "\\d{4,6}", message = "Le code doit contenir entre 4 et 6 chiffres.")
        String code) {
}
