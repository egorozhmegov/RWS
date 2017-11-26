package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Password crypt.
 */
public class PasswordCrypt {

    private PasswordCrypt() {
        throw new IllegalStateException("PasswordCrypt is utility class");
    }

    /**
     * Gets crypt password.
     *
     * @param password String
     * @return String
     * @throws NoSuchAlgorithmException exception
     */
    public static String getPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] bytes = sha1.digest(password.getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte b: bytes){
            builder.append(b);
        }
        return builder.toString();
    }
}
