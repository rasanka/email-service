package com.rapp.email.security.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

public class EncryptionUtilTest {

    private static final String MASTER_PASSWORD = "masterpassword";

    @Test
    public void encrypt() throws Exception {
        String apiKey = "secretapikey";
        String encryptedApiKey = EncryptionUtil.encrypt(apiKey, MASTER_PASSWORD);

        assertNotEquals(encryptedApiKey, apiKey);
    }

    @Test
    public void decrypt() throws Exception {
        String apiKey = "secretapikey";
        String encryptedApiKey = EncryptionUtil.encrypt(apiKey, MASTER_PASSWORD);
        String decryptedClientId = EncryptionUtil.decrypt(encryptedApiKey, MASTER_PASSWORD);
        assertThat(decryptedClientId, equalTo(apiKey));
    }

    @Test
    public void generateSecretKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecretKeySpec result = (SecretKeySpec) EncryptionUtil.generateSecretKey(EncryptionUtil.generateSalt(16), MASTER_PASSWORD);
        assertThat(result.getAlgorithm(), equalTo(EncryptionUtil.ENCRYPTION_ALGORITHM_SPECIFICATION));
    }

    @Test
    public void generateSalt() {
        byte[] result = EncryptionUtil.generateSalt(16);
        assertThat(result.length, is(16));
    }
}
