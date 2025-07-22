package IHC.Portafolio.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.security.Key;

@Configuration
public class JwtConfig {


    private final SecretKey secretKey;
    @Value("${jwt.expiration:300000}")
    private int jwtExpiration;
    public JwtConfig() {

        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public Key getSigningKey() {
        return secretKey;
    }

    public int getJwtExpiration() {
        return jwtExpiration;
    }
}