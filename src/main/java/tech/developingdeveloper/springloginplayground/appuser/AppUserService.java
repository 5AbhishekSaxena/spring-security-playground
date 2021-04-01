package tech.developingdeveloper.springloginplayground.appuser;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.developingdeveloper.springloginplayground.registration.token.ConfirmationToken;
import tech.developingdeveloper.springloginplayground.registration.token.ConfirmationTokenService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Abhishek Saxena on 31-03-2021.
 */

@Service
public class AppUserService implements UserDetailsService {

    private final ConfirmationTokenService confirmationTokenService;

    private final AppUserRepository appUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String USER_NOT_FOUND =
            "User with email %s not found";

    public AppUserService(ConfirmationTokenService confirmationTokenService,
                          AppUserRepository appUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.confirmationTokenService = confirmationTokenService;
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public AppUser loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findByEmail(appUser.getEmail())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("Email already exists");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID()
                .toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public void enableAppUser(String email) {
        Optional<AppUser> optionalAppUser = appUserRepository.findByEmail(email);
        AppUser appUser = optionalAppUser.orElseThrow(() ->
                new IllegalArgumentException(String.format("User with email %s doesn't exist", email)));

        appUser.setEnabled(true);
    }
}
