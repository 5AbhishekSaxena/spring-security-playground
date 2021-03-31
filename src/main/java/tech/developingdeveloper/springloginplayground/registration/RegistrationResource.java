package tech.developingdeveloper.springloginplayground.registration;

import org.springframework.web.bind.annotation.*;

/**
 * Created by Abhishek Saxena on 31-03-2021.
 */

@RestController
@RequestMapping("api/registration")
public class RegistrationResource {

    private final RegistrationService registrationService;

    public RegistrationResource(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) throws IllegalAccessException {
        return registrationService.register(request);
    }

    @GetMapping("confirm")
    public String confirm(@RequestParam String token) {
        return registrationService.confirmToken(token);
    }

}
