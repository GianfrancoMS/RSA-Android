package com.gianfranco.rsa.model;

import android.util.Base64;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

public class RSA implements Serializable {

    private final BigInteger P;
    private final BigInteger Q;
    private final BigInteger N;
    private final BigInteger Z;
    private final BigInteger D;
    private final BigInteger E;
    private final String encryptedText;
    private final String decryptedText;

    public RSA(int keySize, String plaintText) throws Exception {
        if (!isValidText(plaintText))
            throw new Exception("Invalid string for encryption");

        if (!isValidBits(keySize))
            throw new Exception("Invalid bits for encryption");

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(keySize);
        KeyPair kp = kpg.generateKeyPair();
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encodedBytes = cipher.doFinal(plaintText.getBytes());
        encryptedText = Base64.encodeToString(encodedBytes, Base64.DEFAULT);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedBytes = cipher.doFinal(encodedBytes);
        decryptedText = new String(decodedBytes);

        P = ((RSAPrivateCrtKey) privateKey).getPrimeP();
        Q = ((RSAPrivateCrtKey) privateKey).getPrimeQ();
        N = ((RSAPrivateCrtKey) privateKey).getModulus();
        Z = (P.subtract(BigInteger.ONE)).multiply(Q.subtract(BigInteger.ONE));
        E = ((RSAPublicKey) publicKey).getPublicExponent();
        D = E.modInverse(Z);
    }

    private boolean isValidText(String text) {
        return text != null && !text.isEmpty() && !text.trim().isEmpty();
    }

    private boolean isValidBits(int bits) {
        return bits > 0 && (bits & (bits - 1)) == 0;
    }

    public String pToString() {
        return P.toString();
    }

    public String qToString() {
        return Q.toString();
    }

    public String nToString() {
        return N.toString();
    }

    public String zToString() {
        return Z.toString();
    }

    public String dToString() {
        return D.toString();
    }

    public String eToString() {
        return E.toString();
    }

    public String encryptedText() {
        return encryptedText;
    }

    public String decryptedText() {
        return decryptedText;
    }

    public static class RSABuilder{

    }
}