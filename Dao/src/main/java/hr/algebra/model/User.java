/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author K33zo
 */
public final class User {

    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 128;

    private int id;
    private String username;
    private String password;
    private int accountTypeId;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        setAccountType(2);

    }

    public User(int id, String username, int accountTypeId) {
        this.id = id;
        this.username = username;
        this.accountTypeId = accountTypeId;
    }

    public User(String username, int accountTypeId) {

        this.username = username;
        this.accountTypeId = accountTypeId;
    }

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(String username, String password, int accountTypeId) {
        this(username, password);
        this.accountTypeId = accountTypeId;
    }

    public User(int id, String username, String password, int accountTypeId) {
        this(username, password, accountTypeId);
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountType(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String generateHash(String password, String salt) {
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = Base64.getDecoder().decode(salt);

        KeySpec spec = new PBEKeySpec(passwordChars, saltBytes, ITERATIONS, KEY_LENGTH);

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Failed to hash the password.", e);
        }
    }

    @Override
    public String toString() {
        return username;
    }

}
