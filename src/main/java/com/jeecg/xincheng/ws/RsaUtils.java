package com.jeecg.xincheng.ws;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * RSA加密工具类。 Date: 2018/11/20 17:28
 */
public class RsaUtils {

	/** */
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/** */
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/** */
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/** */
	/**
	 * <p>
	 * 公钥加密
	 * </p>
	 * 
	 * @param data      源数据
	 * @param publicKey 公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes());
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		// KEY_ALGORITHM为加密算法 → RSA
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] encryptedData;
		try {
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				// MAX_ENCRYPT_BLOCK为RSA最大加密明文大小 → 117
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			encryptedData = out.toByteArray();
		} finally {
			out.close();
		}
		return encryptedData;
	}

	/**
	 * java端公钥加密
	 * 
	 * @param data      需要加密的明文字符串
	 * @param publicKey 公钥（目前即提供的appSecret）
	 */
	public static String encryptedString(String data, String publicKey) throws Exception {
		// try {
		return newString(Base64.encodeBase64(encryptByPublicKey(data.getBytes(), publicKey), false), "UTF-8");
		// return Base64.encodeBase64String(encryptByPublicKey(data.getBytes(), publicKey));
		// } catch (Exception e) {
		// // TODO 进行加密失败的处理，如果需要捕获，可以在取消注释，并添加自己的处理
		// e.printStackTrace();
		// }
	}

	public static String newString(byte bytes[], String charsetName) {
		if (bytes == null)
			return null;
		try {
			return new String(bytes, charsetName);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(charsetName + ": " + e);
		}
	}

	public void testRsaEncryptedString() {
		String jsonData = "{paraKey:\"paraValue\",paraKey:\"paraValue\",paraKey:\"paraValue\"}";
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcd+0zTY9Gn94iqkQJTlxYnEnCeFsLkk0a7hoAvi2B74VzDVV3xH0ZO9RkXvo1SgCB+uzbEWdrgQkzTqyjfTtgOguu3OnkVxIMJF34ibchTY0LWHGxq1m2gLGuVVqrlu1LtdV0X7xo/5zc8Mr+46veWb86kSpqe6rOAm69WWo5GwIDAQAB";
		try {
			String security = RsaUtils.encryptedString(jsonData, publicKey);
			System.err.println("加密后的密文：\n" + security);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
