package tech.developingdeveloper.springloginplayground.registration.token;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by Abhishek Saxena on 31-03-2021.
 */

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        Optional<ConfirmationToken> optionalConfirmationToken =
                confirmationTokenRepository.findByToken(token);
        ConfirmationToken confirmationToken = optionalConfirmationToken.orElseThrow(() ->
                new IllegalArgumentException("Token doesn't exist"));

        confirmationToken.setConfirmedAt(LocalDateTime.now());

    }
}
