package ktv.cm.idmate.config.services.impl;


import ktv.cm.idmate.config.services.BuildJwtService;
import ktv.cm.idmate.dao.entites.Phone;
import ktv.cm.idmate.dao.entites.Users;
import ktv.cm.idmate.dao.repositories.PhoneRepository;
import ktv.cm.idmate.dto.LoginDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class BuildJwtImpl implements BuildJwtService {
    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authenticationManager;
    private final PhoneRepository phoneRepository;

    public BuildJwtImpl(JwtEncoder jwtEncoder, AuthenticationManager authenticationManager, PhoneRepository phoneRepository) {
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
        this.phoneRepository = phoneRepository;
    }

    @Override
    public Map<String, String> buildJWT(LoginDto userLogin) {
        String subject;
        String scope;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.phoneNumber(), userLogin.password())
        );
        subject = authentication.getName();
        scope = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Phone phoneNumber = phoneRepository.findByPhoneNumber(userLogin.phoneNumber());
        Map<String, String> idToken = new HashMap<>();
        String jwtAccessToken = buildToken(subject, scope, phoneNumber.getUsers());
        idToken.put("accessToken", jwtAccessToken);

        return idToken;
    }

    private String buildToken(String subject, String scope, Users users) {
        Instant instant = Instant.now();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(50000, ChronoUnit.MINUTES))
                .issuer("security-service")
                .claim("scope", scope)
                .claim("firstName", users.getFirstName())
                .claim("lastName", users.getLastName())
                .claim("phoneNumber", users.getPhoneNumber())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }


}
