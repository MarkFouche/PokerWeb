package services;

import repositories.NoDatabase;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthServiceTest {
    AuthService authService = new AuthService();

    @Test
    public void shouldRegisterUser() {
        String name = "Mark";
        String pass = "password";

        authService.setDatabase(new NoDatabase());
        authService.registerUser(name, pass);

        assertTrue(authService.isValidUser(name, pass));
    }

    @Test
    public void shouldRegisterTwoUsers() {
        String name1 = "Mark1";
        String name2 = "Mark2";
        String pass = "password";

        authService.setDatabase(new NoDatabase());
        authService.registerUser(name1, pass);
        authService.registerUser(name2, pass);

        assertTrue(authService.isValidUser(name1, pass));
        assertTrue(authService.isValidUser(name2, pass));
    }

    @Test
    public void shouldNotRegisterSameUser() {
        String name = "Mark";
        String pass = "password";

        authService.setDatabase(new NoDatabase());
        authService.registerUser(name, pass);

        try {
            authService.registerUser(name, pass);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }
}