package com.skip.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skip.config.ApplicationConfig;

@Component
public class AESSkipEncryption {

	private static String password;
	private static String salt;
	private static int pswdIterations = 65536;
	private static int keySize = 256;
	private static byte[] ivBytes = new byte[16];
	private SecretKeySpec secret;
	ApplicationConfig applicationConfig;

	@Autowired
	public AESSkipEncryption(ApplicationConfig appConfig) throws Exception {
		this.applicationConfig = appConfig;
		password = appConfig.getEncryptionPassword();
		salt = appConfig.getEncryptionSalt();
		byte[] saltBytes = salt.getBytes("UTF-8");
		SecretKeyFactory factory = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes,
				pswdIterations, keySize);

		SecretKey secretKey = factory.generateSecret(spec);
		secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
		readIVFile();

	}

	public String encrypt(String plainText) throws Exception {

		// encrypt the message
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(ivBytes));

		AlgorithmParameters params = cipher.getParameters();
		params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));

		return new Base64().encodeAsString(encryptedTextBytes);
	}

	@SuppressWarnings("static-access")
	public String decrypt(String encryptedText) throws Exception {

		byte[] encryptedTextBytes = new Base64().decodeBase64(encryptedText);

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));

		byte[] decryptedTextBytes = null;
		try {
			decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return new String(decryptedTextBytes);
	}

	public void readIVFile() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		this.ivBytes=IOUtils.toByteArray(classLoader.getResourceAsStream("skipiv"));
		

	}

}
