package top.abner.base.util;

import java.security.MessageDigest;


/**
 * @author Nebula
 * */
public class DigestUtils {
  private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
      '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

  /**
   * 计算并获取sha1值
   * */
  public static String getSHA1(String content) {
    return encode("sha1", content);
  }

  /**
   * 计算并获取sha256值
   * */
  public static String getSHA256(String content) {
    return encode("sha-256", content);
  }

  /**
   * 计算并获取sha512值
   * */
  public static String getSHA512(String content) {
    return encode("sha-512", content);
  }

  /**
   * 计算并获取md5值.
   * */
  public static String getMD5(String content) {
    return encode("md5", content);
  }

  private static String encode(String algorithm, String value) {
    if (value == null) {
      return null;
    }
    try {
      MessageDigest messageDigest
          = MessageDigest.getInstance(algorithm);
      messageDigest.update(value.getBytes());
      return getFormattedText(messageDigest.digest());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static String getFormattedText(byte[] bytes) {
    int len = bytes.length;
    StringBuilder buf = new StringBuilder(len * 2);
    for (int j = 0; j < len; j++) {
      buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
      buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
    }
    return buf.toString();
  }

}
