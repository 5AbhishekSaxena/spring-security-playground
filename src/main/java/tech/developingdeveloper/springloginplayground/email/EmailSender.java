package tech.developingdeveloper.springloginplayground.email;

/**
 * Created by Abhishek Saxena on 01-04-2021.
 */

public interface EmailSender {
    void send(String to, String email);
}
