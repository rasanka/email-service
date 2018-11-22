package com.rapp.email.security.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

/**
 * Utility class to encrypt and decrypt
 * 
 * @author Rasanka Jayawardana
 *
 */
public class EncryptionUtil {

    public static final String KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
    public static final int KEY_ALGORITHM_SIZE = 256;
    public static final int KEY_GENERATION_ITERATIONS = 2048;
    public static final String ENCRYPTION_ALGORITHM = "AES/CBC/PKCS7Padding";
    public static final String ENCRYPTION_ALGORITHM_SPECIFICATION = "AES";
    public static final String ENCODING = "UTF-8";

    private static final int SECURE_RANDOM_KEY_LEN = 16;
    private static final int PREFIX_RANDOM_KEY_LEN = 4;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }


    private EncryptionUtil() {
        throw new IllegalStateException("SaltCipherUtils is an Utility class");
    }


    /**
     * Encrypt a message.
     *
     * @param plaintext the unencrypted text.
     * @param masterPassword a password for encryption
     * @return the encrypted text
     * @throws GeneralSecurityException if encryption fails for any reason
     * @throws UnsupportedEncodingException if the encrypted message cannot be cast to UTF-8
     */
    public static String encrypt(String plaintext, String masterPassword) throws GeneralSecurityException, UnsupportedEncodingException {

        byte[] salt = generateSalt(SECURE_RANDOM_KEY_LEN);

        SecretKey secretKey = generateSecretKey(salt, masterPassword);
        IvParameterSpec iv = new IvParameterSpec(salt);

        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] cipherText = cipher.doFinal(plaintext.getBytes(ENCODING));

        ByteBuffer byteBuffer = ByteBuffer.allocate(PREFIX_RANDOM_KEY_LEN + iv.getIV().length + cipherText.length);

        byteBuffer.put(generateSalt(PREFIX_RANDOM_KEY_LEN));

        byteBuffer.put(iv.getIV());

        byteBuffer.put(cipherText);

        byte[] cipherMessage = byteBuffer.array();

        return Base64.toBase64String(cipherMessage);
    }


    /**
     * Decrypt 'encrypted text' previously encrypted by {@link EncryptionUtil#encrypt(String, String)}
     * <p>
     * pattern of encryptedText = 4 bytes random characters + 16 bytes salt + encrypted message.
     * </p>
     *
     * @param encryptedText the encrypted text
     * @param masterPassword the decryption password
     * @return the decrypted text
     * @throws GeneralSecurityException if decryption fails for any reason
     * @throws UnsupportedEncodingException if the decrypted message cannot be cast to UTF-8
     */
    public static String decrypt(String encryptedText, String masterPassword) throws GeneralSecurityException,
                                                                              UnsupportedEncodingException {

        ByteBuffer byteBuffer = ByteBuffer.wrap(Base64.decode(encryptedText));
        byte[] prefix = new byte[PREFIX_RANDOM_KEY_LEN];
        byteBuffer.get(prefix);

        byte[] salt = new byte[SECURE_RANDOM_KEY_LEN];
        byteBuffer.get(salt);

        byte[] cipherText = new byte[byteBuffer.remaining()];
        byteBuffer.get(cipherText);

        IvParameterSpec iv = new IvParameterSpec(salt);
        SecretKey secretKey = generateSecretKey(salt, masterPassword);

        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        byte[] plainText = cipher.doFinal(cipherText);

        return new String(plainText, ENCODING);
    }


    /**
     * Generate a secret key.
     *
     * @param salt a random salt
     * @param password a password
     * @return a secret key
     * @throws NoSuchAlgorithmException if the specified algorithm is not available
     * @throws InvalidKeySpecException if the secret cannot be generated
     */
    static SecretKey generateSecretKey(byte[] salt, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, KEY_GENERATION_ITERATIONS, KEY_ALGORITHM_SIZE);
        SecretKey key = factory.generateSecret(spec);

        return new SecretKeySpec(key.getEncoded(), ENCRYPTION_ALGORITHM_SPECIFICATION);
    }


    /**
     * Generate secure random salt.
     *
     * @param len the length of the salt in bytes
     * @return a secure random salt in a byte array
     */
    static byte[] generateSalt(int len) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[len];
        secureRandom.nextBytes(iv);

        return iv;
    }
}
