package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class DataUtils {

    private static final String KEY_SPEC = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
    private static final String DEFAULT_CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";

    /***
     @ 加密，base64
     */
    public static String Encrypt(String sSrc, String sKey) {
        if (sKey == null) {
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            return null;
        }
        byte[] raw = new byte[0];
        byte[] encrypted = null;
        try {
            raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_SPEC);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM_ECB);//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    /**
     * @解密，base64
     */
    public static String DecryptByBase64(String sSrc, String sKey) {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_SPEC);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM_ECB);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    /***
     * @2进制bytes数组转化为16进制字符
     * @return
     */

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /***
     * @16进制字符串转化为2进制字节数组
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {

        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);

        }
        return result;

    }


    /***
     * @加密，16进制
     */
    public static String EncryptByHex(String sSrc, String sKey) {
        if (sKey == null) {

            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {

            return null;
        }
        byte[] raw = new byte[0];
        byte[] encrypted = null;
        try {
            raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_SPEC);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM_CBC);//"算法/模式/补码方
            IvParameterSpec ivParameterSpec = new IvParameterSpec(raw);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
            encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return parseByte2HexStr(encrypted);
    }

    /**
     * @解密，16进制
     */
    public static String DecryptByHex(String str, String sKey) {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_SPEC);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM_CBC);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(raw);

            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
            try {

                byte[] original = cipher.doFinal(parseHexStr2Byte(str));
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    /***
     * Base64数据解密
     * @return 解密后的数据
     */

    public String getDateForBase64(String key, String strs) {
        String str = null;
        String string = null;
//        JSONObject date = JSONObject.parseObject(strs);
        if (!JSONObject.isValid(strs)) {
            string = DecryptByBase64(strs, key);
            if (JSON.isValidArray(string)) {
                str = JSON.toJSONString(JSON.parseArray(string));
            } else {
                str = JSON.toJSONString(JSON.parseObject(string));
            }
        } else {
            str = JSONObject.toJSONString(strs);
        }

        return str;
    }


    /***
     * 二进制数据解密
     * @return 解密后的数据
     */
    public String getDateForHex(String key, String strs) {

        String str = null;
        String string = null;
//        JSONObject date = JSONObject.parseObject(strs);
        if (!JSONObject.isValid(strs)) {
            string = DecryptByHex(strs, key);
            if (JSON.isValidArray(string)) {
                str = JSON.toJSONString(JSON.parseArray(string));
            } else {
                str = JSON.toJSONString(JSON.parseObject(string));
            }
        } else {
            str = JSONObject.toJSONString(strs);
        }

        return str;

    }

 



}
