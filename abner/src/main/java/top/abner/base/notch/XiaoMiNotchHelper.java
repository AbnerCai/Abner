package top.abner.base.notch;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Window;

import java.lang.reflect.Method;

/**
 * XiaoMi Notch 设备适配
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/13 10:14
 */
public class XiaoMiNotchHelper {

    private final static String TAG = XiaoMiNotchHelper.class.getSimpleName();

    /**
     * 判断是否有刘海屏
     * Android P 以下 小米
     * @param context
     * @return true：有刘海屏；false：没有刘海屏
     */
    public static boolean hasNotch(Context context) {
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");
            Method get = SystemProperties.getMethod("getInt", String.class, int.class);
            ret = (Integer) get.invoke(SystemProperties, "ro.miui.notch", 0) == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return ret;
        }
    }

    /**
     * 获取小米刘海屏高度.
     * Android P 以下 小米
     * @param context
     * @return
     * */
    public static int getNotchHeightMI(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("notch_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取小米刘海屏宽度.
     * Android P 以下 小米
     * @param context
     * @return
     * */
    public static int getNotchWidthMI(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("notch_width", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 开启配置
     * */
    public static final int FLAG_NOTCH_SUPPORT = 0x00000100;

    /**
     * 竖屏配置
     * */
    public static final int FLAG_NOTCH_PORTRAIT = 0x00000200;

    /**
     * 横屏配置.
     * */
    public static final int FLAG_NOTCH_HORIZONTAL = 0x00000400;

    /**
     * 设置使用刘海屏.
     * @param activity
     * */
    public static void setUseNotch(Activity activity) {
        int flag = FLAG_NOTCH_SUPPORT | FLAG_NOTCH_PORTRAIT | FLAG_NOTCH_HORIZONTAL;
        try {
            Method method = Window.class.getMethod("addExtraFlags",
                    int.class);
            method.invoke(activity.getWindow(), flag);
        } catch (Exception e) {
            Log.e(TAG, "addExtraFlags not found.");
        }
    }

    /**
     * 获取状态栏高度.
     * @param context
     * */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
