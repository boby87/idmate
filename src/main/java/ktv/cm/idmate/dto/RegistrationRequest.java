package ktv.cm.idmate.dto;

import jakarta.validation.constraints.NotNull;

public record RegistrationRequest(
        @NotNull(message = "Les informations de téléphone sont requises.")
        PhoneRequest phone,
        @NotNull(message = "Le codeRequest est requises.")
        VerificationCodeRequest verification,
        @NotNull(message = "le nom est  requises.")
        LegalNameRequest name,
        @NotNull(message = "Les informations du pays est requises.")
        CountryRequest country,
        @NotNull(message = "Les informations de la signature requises.")
        SignatureDto signature,
        @NotNull(message = "pin requis.")
        PinDto pin) {
}
