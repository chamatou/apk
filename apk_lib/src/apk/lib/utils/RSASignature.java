package apk.lib.utils;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
/**
 * RSA数据签名
 *
 */
public class RSASignature {
	private static final String algorithm="RSA";
	private  PrivateKey privateKey;
	private  PublicKey publicKey;
	private static final String signAlgorith="SHA1WithRSA";
	private Signature sign;
	private Signature verify;
	private String charset;
	/**
	 * 
	 * @param pk 签名私钥
	 * @param pubk 签名公钥
	 * @param charset 字符集
	 */
	public RSASignature(String pk, String pubk,String charset) {
		try {
			this.charset=charset;
			if(pk!=null){
				privateKey=buildRSAPrivateKey(pk);		
				sign=Signature.getInstance(signAlgorith);
				sign.initSign(privateKey);
			}
			if(pubk!=null){
				publicKey=buildRSAPublicKey(pubk);	
				verify=Signature.getInstance(signAlgorith);
				verify.initVerify(publicKey);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 用自有秘钥进行P1签名
	 * @param text 签名原文
	 * @return 签名失败返回原文
	 */
	public  String signByP1(String text){
		byte[] bytes=null;
		try {
			bytes = text.getBytes(charset);
			sign.update(bytes);
			byte[] result=sign.sign();
			return Base64.encodeBase64String(result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return text;
	}
	/**
	 * 验证签名
	 * @param text 原文
	 * @param b64Sign 签名值得base64编码
	 * @return
	 */
	public  boolean verifyP1(String text,String b64Sign){
		try {
			byte[] bytes=text.getBytes(charset);
			byte[] sign=Base64.decodeBase64(b64Sign);
			verify.update(bytes);
			return verify.verify(sign);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 构建私钥
	 * @param bytes 私钥的二进制编码
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static final  PrivateKey buildRSAPrivateKey(byte[] bytes)throws InvalidKeySpecException, NoSuchAlgorithmException{
		KeyFactory keyFactory;
		keyFactory = KeyFactory.getInstance(algorithm);
		PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(bytes);
		return keyFactory.generatePrivate(pkcs8KeySpec);
	}
	
	/**
	 * 构建私钥
	 * @param pk Base64编码的私钥
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException 
	 */
	public static final  PrivateKey buildRSAPrivateKey(String pk)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		byte[] bytes=Base64.decodeBase64(pk);
		return buildRSAPrivateKey(bytes);
	}
	public static final  PublicKey buildRSAPublicKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException{
		KeyFactory keyFactory;
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
		keyFactory = KeyFactory.getInstance(algorithm);
		return keyFactory.generatePublic(keySpec);
	}
	/**
	 * 构建公钥
	 * @param pubk Base64编码的公钥
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static final  PublicKey buildRSAPublicKey(String pubk) throws NoSuchAlgorithmException, InvalidKeySpecException{
		byte[] bytes=Base64.decodeBase64(pubk);
		return buildRSAPublicKey(bytes);
	}
	public static final RSASignature DEFAULT=new RSASignature("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIHmXHtAKiepyArxh1gvP7840xmc0gVdGNwO0YfRDEFfKVzoCVImE5TYuNKf+lcbzQiC+hzbYMuAg6uommhcfuEpBizBb7iLjFCnbwtonqQeq4wXgMMib9h8WTiI7Lq1bMvOjHRN0+uhVrW9YVaOW3lPp09SzALRTywEuNVRQsNhAgMBAAECgYBBOXUYYeiIz+RlMk+eRONCVfsRcj+2d7+Cx2IISzxXGrw/LTd46yuL4qIzCCcAVDJIYtPJZ5IbvKTgraESY9dE6jeJU1y5K8JkVmzo/nB1qZF88VOxv2nPTtxkq9ToywaAYud9dJ/pTJR2TErpxktKZC4PsxEGR3QQCzvWSeSuLQJBAL3g3GBQn6isJCaWlMemgLaCw01anpn5srw6YWZQASiEe1pNHenB12RXLewNtQR8AcNo136sG5yct6d4EOb8cocCQQCvIpRDmw02liSzrsMTQR/x3PIfognXO34A8QvM+JCRirFMRP+GLE/nOSd8bWdqk1LdI7cfIv8nu9krMV4X7czXAkBkwKdPOBiv8L+x9h72HYSY06P8LMSRoWxU8olyN6uVq06k6Nyhh+jZ5mSIp/FQctigrsYCAiMYpBPTeiNF3diLAkEAmYg57A6IhdsYL8E8WKvWAmVMiXi3Ic2Qx+iJDNa3fi/VAGTYkVpVbgIH4KfUanTexuihh5VZlA608o2jzFx3HwJAYeDg8FRBfz2Ni2jwpoYBHP4t8gZ8MbJBqzRqrZiX3P7/MKFthmDclCeOgPJdoCQAYWC8aiKCTaVREXO6onzVJg==", null, "utf-8");
	
}