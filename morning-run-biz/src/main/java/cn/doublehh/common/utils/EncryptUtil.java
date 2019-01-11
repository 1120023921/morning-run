package cn.doublehh.common.utils;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 加密/解密工具
 *
 * @author ershuai
 * @date 2017年4月18日 上午11:27:36
 */
@Component
public class EncryptUtil {

    private final byte[] DESIV = new byte[]{0x12, 0x34, 0x56, 120, (byte) 0x90, (byte) 0xab, (byte) 0xcd, (byte) 0xef};// 向量

    private static AlgorithmParameterSpec iv = null;// 加密算法的参数接口
    private static Key key = null;

    private static String charset = "utf-8";

    /**
     * 初始化
     *
     * @param deSkey 密钥
     * @throws Exception
     */
    public EncryptUtil(@Value("${encrypt.deSkey}") String deSkey, @Value("${encrypt.charset}") String charset) throws Exception {
        if (StringUtils.isNotBlank(charset)) {
            this.charset = charset;
        }
        DESKeySpec keySpec = new DESKeySpec(deSkey.getBytes(this.charset));// 设置密钥参数
        iv = new IvParameterSpec(DESIV);// 设置向量
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
        key = keyFactory.generateSecret(keySpec);// 得到密钥对象
    }

    /**
     * 加密
     *
     * @param data
     * @return
     * @throws Exception
     * @author ershuai
     * @date 2017年4月19日 上午9:40:53
     */
    public static String encode(String data) {
        byte[] pasByte = null;
        try {
            Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher
            enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向量
            pasByte = enCipher.doFinal(data.getBytes("utf-8"));
        } catch (Exception e) {
            throw new RuntimeException("学号加密异常");
        }
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(pasByte);
    }

    /**
     * 解密
     *
     * @param data
     * @return
     * @throws Exception
     * @author ershuai
     * @date 2017年4月19日 上午9:41:01
     */
    public static String decode(String data) {
        byte[] pasByte = null;
        try {
            Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            deCipher.init(Cipher.DECRYPT_MODE, key, iv);
            BASE64Decoder base64Decoder = new BASE64Decoder();
            pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data));
            return new String(pasByte, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("学号解密异常");
        }
    }

    public static void main(String[] args) {
        try {
            String test = "2015014074";
            String key = "9ba45bfd500642328ec03ad8ef1b6e75";// 自定义密钥
            EncryptUtil des = new EncryptUtil(key, "utf-8");
            System.out.println("加密前的字符：" + test);
            System.out.println("加密后的字符：" + des.encode(test));
            System.out.println("解密后的字符：" + des.decode(des.encode(test)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}