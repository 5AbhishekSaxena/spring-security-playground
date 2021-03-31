package tech.developingdeveloper.springloginplayground.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Abhishek Saxena on 31-03-2021.
 */

public interface ConfirmationTokenRepository extends
        JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);
}
