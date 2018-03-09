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

import static com.gianfranco.rsa.model.Utils.checkNotNull;

public final class RSA implements Serializable {
    BigInteger P;
    BigInteger Q;
    BigInteger N;
    BigInteger Z;
    BigInteger D;
    BigInteger E;
    String encryptedText;
    String decryptedText;

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

    public static final class Builder {
        private final String RSA = "RSA";

        private final String message;

        private byte[] encodedBytes;
        private byte[] decodedBytes;

        private String encryptedText;
        private String decryptedText;

        private final KeyPairGenerator keyPairGenerator;
        private final KeyPair keyPair;
        private final PublicKey publicKey;
        private final PrivateKey privateKey;

        public Builder(int keySize, String message) {
            try {
                if (!areValidBits(keySize))
                    throw new IllegalArgumentException("Invalid bits for encryption");

                if (!isValidMessage(message))
                    throw new IllegalArgumentException("Invalid message for encryption");

                this.message = message;

                keyPairGenerator = KeyPairGenerator.getInstance(RSA);
                keyPairGenerator.initialize(keySize);
                keyPair = keyPairGenerator.generateKeyPair();
                publicKey = keyPair.getPublic();
                privateKey = keyPair.getPrivate();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        public Builder encodeMessage() {
            try {
                Cipher cipher = Cipher.getInstance(RSA);
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);

                encodedBytes = cipher.doFinal(message.getBytes());
                encryptedText = Base64.encodeToString(encodedBytes, Base64.DEFAULT);

                return this;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        public Builder decodeMessage() {
            try {
                checkNotNull(encodedBytes, "encodedBytes == null. You must call encodeMessage() before");

                Cipher cipher = Cipher.getInstance(RSA);
                cipher.init(Cipher.DECRYPT_MODE, privateKey);

                decodedBytes = cipher.doFinal(encodedBytes);
                decryptedText = new String(decodedBytes);

                return this;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        public RSA build() {
            checkNotNull(encodedBytes, "encodedBytes == null. You must call encodeMessage() before");
            checkNotNull(decodedBytes, "decodedBytes == null. You must call decodeMessage() before");

            BigInteger P = ((RSAPrivateCrtKey) privateKey).getPrimeP();
            BigInteger Q = ((RSAPrivateCrtKey) privateKey).getPrimeQ();
            BigInteger N = ((RSAPrivateCrtKey) privateKey).getModulus();
            BigInteger Z = (P.subtract(BigInteger.ONE)).multiply(Q.subtract(BigInteger.ONE));
            BigInteger E = ((RSAPublicKey) publicKey).getPublicExponent();
            BigInteger D = E.modInverse(Z);

            RSA rsa = new RSA();
            rsa.P = P;
            rsa.Q = Q;
            rsa.N = N;
            rsa.Z = Z;
            rsa.E = E;
            rsa.D = D;
            rsa.encryptedText = encryptedText;
            rsa.decryptedText = decryptedText;

            return rsa;
        }

        private boolean isValidMessage(String message) {
            return message != null && !message.isEmpty() && !message.trim().isEmpty();
        }

        private boolean areValidBits(int bits) {
            return bits > 0 && (bits & (bits - 1)) == 0;
        }
    }
}