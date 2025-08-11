package ktv.cm.idmate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CountryRequest(
        @NotBlank(message = "Le numéro de téléphone est requis.")
        @Pattern(regexp = "\\d{6,15}", message = "Le numéro de téléphone doit contenir entre 6 et 15 chiffres.")
        String phoneNumber,// ex: "CM"
        @NotBlank(message = "Le nom du pays est requis.")
        @Size(min = 2, max = 56, message = "Le nom du pays doit contenir entre 2 et 56 caractères.")
        String name) {
}
