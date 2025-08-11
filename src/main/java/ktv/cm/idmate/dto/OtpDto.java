package ktv.cm.idmate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record OtpDto(@NotBlank(message = "Le numéro de téléphone est requis.")
                      @Pattern(
                              regexp = "^\\+?[1-9]\\d{5,14}$",
                              message = "Le numéro de téléphone est invalide (format international requis, ex: +2376...)"
                      )
                      String phoneNumber,

                     @NotBlank(message = "Le code OTP est requis.")
                      @Pattern(
                              regexp = "^\\d{4,6}$",
                              message = "Le code OTP doit contenir entre 4 et 6 chiffres."
                      )
                      String otp) {
}
