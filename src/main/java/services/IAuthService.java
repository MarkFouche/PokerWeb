package services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Mark on 2015-01-16.
 */
public interface IAuthService {
    public void registerUser(String userName, String password);
    public boolean isValidUser(String userName, String password);
    public boolean doesUserExist(String userName);
}
