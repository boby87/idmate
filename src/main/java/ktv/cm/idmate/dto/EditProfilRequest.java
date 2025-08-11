package ktv.cm.idmate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EditProfilRequest(
        @NotBlank(message = "Le nom complet est requis.")
        @Size(min = 2, max = 100, message = "Le nom complet doit contenir entre 2 et 100 caractères.")
        String fullName,
        @NotBlank(message = "L'adresse est requise.")
        String address,
        @NotBlank(message = "Le numéro de téléphone est requis.")
        @Pattern(regexp = "\\d{6,15}", message = "Le numéro de téléphone doit contenir entre 6 et 15 chiffres.")
        String phoneNumber,
        @NotBlank(message = "Le pays est requis.")
        String country) {
}
