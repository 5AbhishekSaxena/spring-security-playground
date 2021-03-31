package tech.developingdeveloper.springloginplayground.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 * Created by Abhishek Saxena on 31-03-2021.
 */

@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String email) {
        String emailRegex = "[0-9a-zA-Z_]+@[a-z]+[.][a-z]+";
        return email != null && email.matches(emailRegex);
    }
}
