package ktv.cm.idmate.config.services;




import ktv.cm.idmate.dto.LoginDto;

import java.util.Map;

public interface BuildJwtService {
    Map<String, String> buildJWT(LoginDto users); // méthode pour construire le JWT à partir des informations de l'utilisateuret Jwt sera construire dans une carte (cle-valeur) pour faciliter la sérialisation en JSON.
}
