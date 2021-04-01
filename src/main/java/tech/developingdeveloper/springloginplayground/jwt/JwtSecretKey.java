package tech.developingdeveloper.springloginplayground.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

/**
 * Created by Abhishek Saxena on 01-04-2021.
 */

@Configuration
public class JwtSecretKey {

    private final JwtConfig jwtConfig;

    public JwtSecretKey(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecretKey()
                .getBytes());
    }
}
