package by.ladzislau.gusakov.accountmanagement.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.days}")
    private int jwtTokenExpirationDays;

    @Value("${spring.application.name}")
    private String issuer;

    public String generateToken(String email) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusDays(jwtTokenExpirationDays).toInstant());
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String validateTokenAndRetrieveSubject(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret))
                .withIssuer(issuer)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }
}
