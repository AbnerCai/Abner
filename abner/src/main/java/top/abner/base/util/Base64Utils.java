package top.abner.base.util;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * BASE64编码解码工具包.
 */  
public class Base64Utils {  
  
    /**
     * 文件读取缓冲区大小 
     */  
    private static final int CACHE_SIZE = 1024;  
      
    /**
     * BASE64字符串解码为二进制数据
     *
     * @param base64 
     * @return 
     * @throws Exception
     */  
    public static byte[] decode(String base64) throws Exception {
        return Base64.decode(base64.getBytes("UTF-8"), Base64.DEFAULT);
    }

    /**
     * BASE64字符串解码为二进制数据
     *
     * @param base64
     * @return
     * @throws Exception
     */
    public static String decodeStr(String base64) throws Exception {
        return new String(decode(base64), "UTF-8");
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes 
     * @return 
     * @throws Exception
     */  
    public static String encode(byte[] bytes) {
        return new String(Base64.encode(bytes, Base64.DEFAULT));
    }

    /**
     * 字符串数据编码为BASE64字符串
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static String encodeStr(String content) {
        return new String(Base64.encode(content.getBytes(), Base64.DEFAULT));
    }
      
    /**
     * 将文件编码为BASE64字符串
     *
     * 大文件慎用，可能会导致内存溢出
     *
     * @param filePath 文件绝对路径 
     * @return 
     * @throws Exception
     */  
    public static String encodeFile(String filePath) throws Exception {
        byte[] bytes = fileToByte(filePath);  
        return encode(bytes);  
    }  
      
    /**
     * BASE64字符串转回文件
     *
     * @param filePath 文件绝对路径 
     * @param base64 编码字符串 
     * @throws Exception
     */  
    public static void decodeToFile(String filePath, String base64) throws Exception {
        byte[] bytes = decode(base64);  
        byteArrayToFile(bytes, filePath);  
    }  
      
    /**
     * 文件转换为二进制数组
     *
     * @param filePath 文件路径 
     * @return 
     * @throws Exception
     */  
    private static byte[] fileToByte(String filePath) throws IOException {
        byte[] data = new byte[0];  
        File file = new File(filePath);
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        if (file.exists()) {
            try {
                in = new FileInputStream(file);
                out = new ByteArrayOutputStream(2048);
                byte[] cache = new byte[CACHE_SIZE];
                int nRead = 0;
                while ((nRead = in.read(cache)) != -1) {
                    out.write(cache, 0, nRead);
                    out.flush();
                }
                data = out.toByteArray();
            } catch (Exception e) {
                throw e;
            } finally {
                try {
                    if (null != in) {
                        in.close();
                    }
                    if (null != out) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
         }
        return data;  
    }  
      
    /**
     * 二进制数据写文件
     *
     * @param bytes 二进制数据 
     * @param filePath 文件生成目录 
     */  
    private static void byteArrayToFile(byte[] bytes, String filePath) {
        InputStream in = new ByteArrayInputStream(bytes);
        OutputStream out = null;
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {  
            destFile.getParentFile().mkdirs();  
        }
        try {
            destFile.createNewFile();
            out = new FileOutputStream(destFile);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
