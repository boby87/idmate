package ktv.cm.idmate.controller;

import jakarta.validation.Valid;
import ktv.cm.idmate.dto.CountryRequest;
import ktv.cm.idmate.service.metier.CountryMetier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/country")
@CrossOrigin("*")
@Validated
public class CountryController {

    private final CountryMetier countryMetier;

    public CountryController(CountryMetier countryMetier) {
        this.countryMetier = countryMetier;
    }

    @PutMapping ("/update")
    public ResponseEntity<Void> saveCountry(@Valid @RequestBody CountryRequest countryRequest) {
        countryMetier.saveCountry(countryRequest);
        return ResponseEntity.ok().build();
    }


}
