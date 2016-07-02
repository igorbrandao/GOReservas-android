package br.ufg.inf.goreservas.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {

    public String encryptPassword(String password) throws NoSuchAlgorithmException {
        return encryptPassword(password, "SHA-256");
    }

    public String encryptPassword(String password, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(password.getBytes());
        return new String(messageDigest.digest());
    }

}
