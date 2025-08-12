package ktv.cm.idmate.controller;


import ktv.cm.idmate.config.services.BuildJwtService;
import ktv.cm.idmate.dto.LoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/token")
@Validated

public class UserToken {
    private final BuildJwtService buildJwtService;

    public UserToken(BuildJwtService buildJwtService) {
        this.buildJwtService = buildJwtService;
    }


    @PostMapping()
    public ResponseEntity<Map<String, String>> jwtToken(@RequestBody LoginDto userLogin) {
        return new ResponseEntity<>(buildJwtService.buildJWT(userLogin), HttpStatus.OK);
    }
}
