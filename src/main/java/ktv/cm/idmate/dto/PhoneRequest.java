package ktv.cm.idmate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PhoneRequest(
        @NotBlank(message = "Le code pays est requis.")
        @Pattern(regexp = "\\+\\d{1,4}", message = "Le code pays doit commencer par '+' et contenir entre 1 et 4 chiffres.")
        String countryCode,
        @NotBlank(message = "Le numéro de téléphone est requis.")
       // @Pattern(regexp = "\\d{6,15}", message = "Le numéro de téléphone doit contenir entre 6 et 15 chiffres.")
        String  phoneNumber) {

}
