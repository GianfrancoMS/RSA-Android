package com.gianfranco.encriptacionrsa;

/**
 * Created by Familia on 11/11/2016.
 */

 import android.util.Base64;
 import java.io.Serializable;
 import java.math.BigInteger;
 import java.security.Key;
 import java.security.KeyPair;
 import java.security.KeyPairGenerator;
 import java.security.PrivateKey;
 import java.security.PublicKey;
 import java.security.interfaces.RSAPrivateCrtKey;
 import java.security.interfaces.RSAPublicKey;
 import javax.crypto.Cipher;



@SuppressWarnings("serial")

public class RSA implements Serializable{

    private BigInteger P;
    private BigInteger Q;
    private BigInteger N;
    private BigInteger Z;
    private BigInteger D;
    private BigInteger E;
    private String encryptedText;
    private String desencryptedText;
    private int sizeBits;

    RSA(int sizeBits, String plaintText)  throws Exception
    {
        this.sizeBits=sizeBits;

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(sizeBits);
        KeyPair kp=kpg.generateKeyPair();
        PublicKey publicKey=kp.getPublic();
        PrivateKey privateKey=kp.getPrivate();

        Cipher cipher= Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[]encodedBytes=cipher.doFinal(plaintText.getBytes());
        encryptedText=Base64.encodeToString(encodedBytes,Base64.DEFAULT);

        Cipher cipher1=Cipher.getInstance("RSA");
        cipher1.init(Cipher.DECRYPT_MODE,privateKey);
        byte[]decodedBytes=cipher1.doFinal(encodedBytes);
        desencryptedText=new String(decodedBytes);

        P=( (RSAPrivateCrtKey) privateKey ).getPrimeP();
        Q=( (RSAPrivateCrtKey) privateKey ).getPrimeQ();
        N=( (RSAPrivateCrtKey) privateKey ).getModulus();
        Z=(P.subtract(BigInteger.ONE)).multiply(Q.subtract(BigInteger.ONE));
        E=( (RSAPublicKey) publicKey).getPublicExponent();
        D=E.modInverse(Z);
    }

    /*
    public void encryptionAndDesencryption(String plainText)
    {
        int sizeBytes= sizeBits/4;

        if(plainText.length() >  sizeBytes )
            throw new IllegalArgumentException("No se puede encriptar un texto tan grande");

        BigInteger tempEncrypted = new BigInteger(plainText.getBytes());
        BigInteger cipherEncrypted= tempEncrypted.modPow(E, N);
        encryptedText=new String(cipherEncrypted.toByteArray());

        BigInteger tempDesencrypted = new BigInteger(cipherEncrypted.toString());
        BigInteger cipherText= tempDesencrypted.modPow(D, N);
        desencryptedText=new String(cipherText.toByteArray());
    }
    */

    public int getSizeBits() {
        return sizeBits;
    }

    public void setSizeBits(int sizeBits) {
        this.sizeBits = sizeBits;
    }

    public String getP() { return P.toString(); }

    public String getQ() {
        return Q.toString();
    }

    public String getN() {
        return N.toString();
    }

    public String getZ() {
        return Z.toString();
    }

    public String getD() {
        return D.toString();
    }

    public String getE() { return E.toString();  }

    public String getEncryptedText() {
        return encryptedText;
    }

    public String getDesencryptedText() {
        return desencryptedText;
    }
}