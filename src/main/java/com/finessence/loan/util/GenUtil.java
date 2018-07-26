package com.finessence.loan.util;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter; 

/**
 *
 * @author patrick
 */
public class GenUtil {

	//private final static Logger LOG = Logger.getLogger(GenUtil.class);

	private static Random random = new Random();

	public static int generateRand(int min, int max) {
		// return random.nextInt(50) + min;
		return random.nextInt(max - min + 1) + min;
	}

	public static String generateId(int size) {
		return UUID.randomUUID().toString().substring(0, size);
	}

	public static String generateId(String prefix, int size) {
		return prefix + UUID.randomUUID().toString().substring(0, size);
	}

	public static String getAbsolutePath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}

	public String toString(Class<?> cls) {

		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append(this.getClass().getName());
		result.append(" Object {");
		result.append(newLine);

		// determine fields declared in this class only (no fields of
		// superclass)
		Field[] fields = cls.getClass().getDeclaredFields();

		// print field names paired with their values
		for (Field field : fields) {
			result.append("  ");
			try {
				result.append(field.getName());
				result.append(": ");
				// requires access to private field:
				result.append(field.get(cls));
			} catch (IllegalAccessException ex) {
				System.out.println(ex);
			}
			result.append(newLine);
		}

		result.append("}");

		return result.toString();
	}

	public static String hashPassword(String input, boolean withPadding) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");

		md.update(input.getBytes(StandardCharsets.UTF_8));

		byte[] hash = md.digest();

		StringBuffer hexString = new StringBuffer();
		StringBuffer hexString1 = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			hexString.append(Integer.toHexString(0xFF & hash[i]));
			hexString1.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
		}

		if (withPadding) {
			return hexString1.toString();
		} else {
			return hexString.toString();
		}

		// return DatatypeConverter.printBase64Binary(sb.toString().getBytes());
	}

	public static String hashPassword(Long clientId, String password, String timestamp)
			throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update((clientId + password + timestamp).getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
		}
		return DatatypeConverter.printBase64Binary(sb.toString().getBytes());

	}

	public static String hashPassword() throws NoSuchAlgorithmException {

		// Long clientId = 2;
		String password = "xipos";
		String timestamp = "20160226091923";

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update((2 + password + timestamp).getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
		}

		String hash = DatatypeConverter.printBase64Binary(sb.toString().getBytes());

		return hash;
	}

	@SuppressWarnings("unused")
	private void timeWasting(long time) {
		try {
			// long time = 5000L;
			Thread.sleep(time);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

}
