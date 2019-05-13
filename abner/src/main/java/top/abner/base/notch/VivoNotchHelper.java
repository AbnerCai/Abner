package top.abner.base.notch;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/13 10:42
 */
public class VivoNotchHelper {
    
    private final static String TAG = VivoNotchHelper.class.getSimpleName();

    /**
     * 是否有刘海
     * */
    private static final int VIVO_NOTCH = 0x00000020;

    /**
     * 是否有圆角
     * */
    private static final int VIVO_FILLET = 0x00000008;

    /**
     * 判断是否有刘海屏
     * Android P 以下 VIVO
     * @param context
     * @return true：有刘海屏；false：没有刘海屏
     */
    public static boolean hasNotch(Context context) {
        return hasFeature(context, VIVO_NOTCH);
    }

    /**
     * 判断是否有圆角
     * Android P 以下 VIVO
     * @param context
     * @return true：有圆角；false：没有圆角
     */
    public static boolean hasFillet(Context context) {
        return hasFeature(context, VIVO_FILLET);
    }

    /**
     * 获取 VIVO 的特征值.
     * @param context
     * @param feature
     */
    private static boolean hasFeature(Context context, int feature) {
        boolean ret = false;
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class FtFeature = classLoader.loadClass("android.util.FtFeature");
            Method method = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) method.invoke(FtFeature, feature);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "hasNotchAtVivo ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "hasNotchAtVivo NoSuchMethodException");
        } catch (Exception e) {
            Log.e(TAG, "hasNotchAtVivo Exception");
        } finally {
            return ret;
        }
    }
}
