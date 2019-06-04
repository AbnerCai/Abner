package top.abner.webview.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 缓存帮助类.
 *
 * @author Nebula
 * @version 1.0.0
 * @date 2019/1/4
 */
public class CacheHelper {

    private String TAG = CacheHelper.class.getSimpleName();

    private LruCache<String, Bitmap> mMemoryLruCache;

    private DiskLruCache mDiskLruCache;

    private final long MAX_DISK_SIZE = 10 * 1024 * 1024;

    private ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(
            5,
            20,
            200,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(50),
            new CacheThreadFactory(TAG));

    private CacheHelper(){
    }

    private static class SingletonHolder{
        private final static CacheHelper INSTANCE = new CacheHelper();
    }
    public static CacheHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 初始化内存缓存
     * */
    public void initMemoryCache() {
        // 获取手机最大内存的1/8
        long memory = Runtime.getRuntime().maxMemory() / 8;
        mMemoryLruCache = new LruCache<String, Bitmap>((int)memory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    /**
     * 检验内存缓存初始化.
     * */
    private void checkInitMemoryCache(){
        if (null == mMemoryLruCache) {
            throw new NullPointerException("please call initMemoryCache() first.");
        }
    }

    /**
     * 读取图片.
     * @param url
     * @return
     */
    public Bitmap getBitmapFromMemory(String url) {
        checkInitMemoryCache();
        return mMemoryLruCache.get(url);
    }

    /**
     * 写入缓存.
     * */
    public void setBitmapToMemory(String url, Bitmap bitmap) {
        checkInitMemoryCache();
        if (null == getBitmapFromMemory(url)) {
            mMemoryLruCache.put(url, bitmap);
        }
    }

    /**
     * 初始化磁盘缓存.
     * */
    public void initDiskCache(Context context) {
        try {
            File cacheDir = getDiskCacheDir(context, "bitmap");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, MAX_DISK_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检验磁盘初始化.
     * */
    private void checkInitDiskCache(){
        if (null == mDiskLruCache) {
            throw new NullPointerException("please call initDiskCache() first.");
        }
    }

    /**
     * 读取缓存.
     * */
    public Bitmap getBitmapFromDisk(String imageUrl) {
        try {
            String key = hashKeyForDisk(imageUrl);
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {
                InputStream is = snapShot.getInputStream(0);
                return BitmapFactory.decodeStream(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 读取缓存.
     * */
    public InputStream getInputStreamFromDisk(String fileUrl) {
        Log.i(TAG, "fileUrl: " + fileUrl);
        try {
            String key = hashKeyForDisk(fileUrl);
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {
                InputStream is = snapShot.getInputStream(0);
                return is;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写入缓存
     * */
    public void setBitmapToDisk(final String imageUrl, final CacheCallback cacheCallback) {
        setFileToDisk(imageUrl, cacheCallback);
    }

    /**
     * 写入缓存
     * */
    public void setFileToDisk(final String fileUrl, final CacheCallback cacheCallback) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String key = hashKeyForDisk(fileUrl);
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (downloadUrlToStream(fileUrl, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    mDiskLruCache.flush();
                    cacheCallback.onFinish(fileUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                    cacheCallback.onError(e);
                }
            }
        });
    }

    /**
     * 移除缓存
     * */
    public void removeBitmapFromDisk(String imageUrl) {
        try {
            String key = hashKeyForDisk(imageUrl);
            mDiskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface CacheCallback {
        void onFinish(String fileUrl);
        void onError(Exception e);
    }

    /**
     * 获取缓存地址
     * */
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 获取APP版本
     * */
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 下載
     * */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     *
     * */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    /**
     *
     * */
    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    class CacheThreadFactory implements ThreadFactory {

        final AtomicInteger threadNumber = new AtomicInteger(1);

        final String namePrefix;

        CacheThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix + "-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r,namePrefix + threadNumber.getAndIncrement());
            if (t.isDaemon()) {
                t.setDaemon(true);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}