package services;

import com.google.inject.Inject;
import repositories.IDatabaseAdapter;
import models.database.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Mark on 2015-01-16.
 */
public class AuthService implements IAuthService {
    private static final int HASH_ITERATIONS = 1000;
    private static final int HASH_KEY_LENGTH = 192;

    @Inject
    IDatabaseAdapter database;

    public void setDatabase(IDatabaseAdapter newDatabase) {
        database = newDatabase;
    }

    private static String generateSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }

    private static String generateStrongPasswordHash(String password, String saltString)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = saltString.getBytes();

        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, HASH_ITERATIONS, HASH_KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return String.format("%x", new BigInteger(hash));
    }

    @Override
    public void registerUser(String userName, String password) {
        if (database.isUserNameInDatabase(userName)) {
            throw new IllegalArgumentException("User is in database: " + userName);
        }
        try {
            String salt = generateSalt();
            String passwordHash = generateStrongPasswordHash(password, salt);
            User user = new User(userName, salt, passwordHash);
            database.addUserToDatabase(user);

        } catch (NoSuchAlgorithmException e) {
            System.err.println("NoSuchAlgorithmException: " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            System.err.println("InvalidKeySpecException: " + e.getMessage());
        }
    }

    @Override
    public boolean isValidUser(String userName, String password){
        if (!database.isUserNameInDatabase(userName)) {
            return false;
        }
        try {
            String salt = database.getUserSalt(userName);
            String passwordHash = generateStrongPasswordHash(password, salt);
            User user = new User(userName, salt, passwordHash);
            return database.isUserPasswordCorrect(user);

        } catch (NoSuchAlgorithmException e) {
            System.err.println("NoSuchAlgorithmException: " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            System.err.println("InvalidKeySpecException: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean doesUserExist(String userName) {
        return database.isUserNameInDatabase(userName);
    }
}
