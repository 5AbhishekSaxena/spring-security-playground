package tech.developingdeveloper.springloginplayground.registration;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.developingdeveloper.springloginplayground.appuser.AppUser;
import tech.developingdeveloper.springloginplayground.appuser.AppUserRole;
import tech.developingdeveloper.springloginplayground.appuser.AppUserService;
import tech.developingdeveloper.springloginplayground.registration.token.ConfirmationToken;
import tech.developingdeveloper.springloginplayground.registration.token.ConfirmationTokenService;

import java.time.LocalDateTime;

/**
 * Created by Abhishek Saxena on 31-03-2021.
 */

@Service
public class RegistrationService {

    private final EmailValidator emailValidator;

    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;

    public RegistrationService(EmailValidator emailValidator, AppUserService appUserService, ConfirmationTokenService confirmationTokenService) {
        this.emailValidator = emailValidator;
        this.appUserService = appUserService;
        this.confirmationTokenService = confirmationTokenService;
    }

    public String register(RegistrationRequest request) throws IllegalAccessException {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalAccessException("Email not valid");
        }

        AppUser appUser = new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER
        );

        return appUserService.signUpUser(appUser);
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalArgumentException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser()
                        .getEmail()
        );
        return "confirmed!";
    }
}
