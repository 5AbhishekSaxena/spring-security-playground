package tech.developingdeveloper.springloginplayground.web.rest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.developingdeveloper.springloginplayground.appuser.AppUserService;

/**
 * Created by Abhishek Saxena on 01-04-2021.
 */

@RestController
@RequestMapping("someData")
public class SomeResource {

    private final AppUserService appUserService;

    public SomeResource(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public String helloWorld() {
        return "hello world";
    }


    @GetMapping("{username}")
    public UserDetails getUserByUsername(@PathVariable String username) {
        return appUserService.loadUserByUsername(username);
    }
}
