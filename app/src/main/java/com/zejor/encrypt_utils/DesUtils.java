package com.zejor.encrypt_utils;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES加密 解密算法
 * 
 * @author lifq
 * @date 2015-3-17 上午10:12:11
 */
public class DesUtils {

	private final static String DES = "DES";
	private final static String ENCODE = "UTF-8";
	private final static String defaultKey = "5a61399d52253808b144fa3259dfcdc5";

	public static void main(String[] args) throws Exception {
		String data = "{\"funCode\":\"100015\",\"pmType\":\"1\",\"softType\":\"android_weixiaojie_v2.0\",\"version\":\"1.0\"}";
		System.out.println(encrypt(data));
		System.out.println(decrypt(encrypt(data)));

	}

	/**
	 * 使用 默认key 加密
	 * 
	 * @return String
	 * @author lifq
	 * @date 2015-3-17 下午02:46:43
	 */
	public static String encrypt(String data) throws Exception {
		byte[] bt = encrypt(data.getBytes(ENCODE), defaultKey.getBytes(ENCODE));
		String strs = Base64.encode(bt);
		return strs;
	}

	/**
	 * 使用 默认key 解密
	 * 
	 * @return String
	 * @author lifq
	 * @date 2015-3-17 下午02:49:52
	 */
	public static String decrypt(String data) throws IOException, Exception {
		if (data == null)
			return null;
		byte[] buf = Base64.decode(data);
		byte[] bt = decrypt(buf, defaultKey.getBytes(ENCODE));
		return new String(bt, ENCODE);
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws Exception {
		byte[] bt = encrypt(data.getBytes(ENCODE), defaultKey.getBytes(ENCODE));
		String strs = Base64.encode(bt);
		return strs;
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decrypt(String data, String key) throws IOException, Exception {
		if (data == null)
			return null;
		byte[] buf = Base64.decode(data);
		byte[] bt = decrypt(buf, key.getBytes(ENCODE));
		return new String(bt, ENCODE);
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
}