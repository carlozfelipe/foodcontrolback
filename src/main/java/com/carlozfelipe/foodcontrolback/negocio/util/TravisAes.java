package com.carlozfelipe.foodcontrolback.negocio.util;

import org.apache.logging.log4j.*;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

@Service
public class TravisAes {

    public static final String PASSPHRASE = "f00dc0n7r0l*2020";

    public enum DataTypeEnum {
        HEX,
        BASE64
    }

    //private static final LOGGER = null;
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int IV_SIZE = 128;
    private static final int IV_LENGTH = IV_SIZE / 4;

    private int keySize = 256;
    private int iterationCount = 1989;
    private DataTypeEnum dataType = DataTypeEnum.BASE64;
    private Cipher cipher;
    private int saltLength;

    public TravisAes() {
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            saltLength = this.keySize / 4;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            ////LOGGER.error(e);
        }
    }

    public TravisAes(int keySize, int iterationCount) {
        this.keySize = keySize;
        this.iterationCount = iterationCount;
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            saltLength = this.keySize / 4;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            //LOGGER.error(e);
        }
    }

    public String encrypt(String salt, String iv, String passPhrase, String plainText) {
        try {
            SecretKey key = generateKey(salt, passPhrase);
            byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, key, iv, plainText.getBytes(StandardCharsets.UTF_8));
            String cipherText;
            if (dataType.equals(DataTypeEnum.HEX)) {
                cipherText = toHex(encrypted);
            } else {
                cipherText = toBase64(encrypted);
            }
            return cipherText;
        } catch (Exception e) {
            //LOGGER.error(e);
            return null;
        }
    }

    public String encrypt(String plainText) {
        try {
            String salt = toHex(generateRandom(keySize / 8));
            String iv = toHex(generateRandom(IV_SIZE / 8));
            String cipherText = encrypt(salt, iv, PASSPHRASE, plainText);
            return salt + iv + cipherText;
        } catch (Exception e) {
            //LOGGER.error(e);
            return null;
        }
    }

    public String decrypt(String salt, String iv, String passPhrase, String cipherText) {
        try {
            SecretKey key = generateKey(salt, passPhrase);
            byte[] encrypted;
            if (dataType.equals(DataTypeEnum.HEX)) {
                encrypted = fromHex(cipherText);
            } else {
                encrypted = fromBase64(cipherText);
            }
            byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, key, iv, encrypted);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            //LOGGER.error(e);
            return null;
        }
    }

    public String decrypt(String cipherText) {
        try {
            String salt = cipherText.substring(0, saltLength);
            String iv = cipherText.substring(saltLength, saltLength + IV_LENGTH);
            String ct = cipherText.substring(saltLength + IV_LENGTH);
            return decrypt(salt, iv, PASSPHRASE, ct);
        } catch (Exception e) {
            //LOGGER.error(e);
            return null;
        }
    }

    private static byte[] generateRandom(int length) {
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return randomBytes;
    }

    private byte[] doFinal(int encryptMode, SecretKey key, String iv, byte[] bytes) {
        try {
            cipher.init(encryptMode, key, new IvParameterSpec(fromHex(iv)));
            return cipher.doFinal(bytes);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
                | BadPaddingException e) {
            //LOGGER.error(e);
            return null;
        }
    }

    private SecretKey generateKey(String salt, String passphrase) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
            KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), fromHex(salt), iterationCount, keySize);
            return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        } catch (Exception e) {
            //LOGGER.error(e);
            return null;
        }
    }

    private static byte[] fromBase64(String str) {
        return DatatypeConverter.parseBase64Binary(str);
    }

    private static String toBase64(byte[] ba) {
        return DatatypeConverter.printBase64Binary(ba);
    }

    private static byte[] fromHex(String str) {
        return DatatypeConverter.parseHexBinary(str);
    }

    private static String toHex(byte[] ba) {
        return DatatypeConverter.printHexBinary(ba);
    }

    public DataTypeEnum getDataType() {
        return dataType;
    }

    public void setDataType(DataTypeEnum dataType) {
        this.dataType = dataType;
    }

    public static void main(String[] args){
        TravisAes travisAes = new TravisAes();
        String encrypted = travisAes.encrypt( "felipe");
        String decrypted = travisAes.decrypt(encrypted);
        System.out.println("encrypted: " + encrypted);
        System.out.println("decrypted: " + decrypted);
        //aaeca5fbac5a1a940940fdef82715cdac5a85730eecb8cc55b4b6b2b0febe6ca95ca4d0d6775cb800ab52dc202121738qrJfHX/OMWYhBHijyUa3wA==
    }
}