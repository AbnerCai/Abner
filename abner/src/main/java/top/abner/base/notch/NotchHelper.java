package top.abner.base.notch;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;

import java.util.List;


/**
 * 刘海屏适配
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/9 16:53
 */
public class NotchHelper {

    private final static String TAG = NotchHelper.class.getSimpleName();

    /**
     * 设置透明状态栏
     * @param activity
     * */
    public static void setImmersiveStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置全屏显示
     * @param activity
     * */
    public static void setFullscreen(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return statusBarHeight
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 判断是否有刘海屏
     * @param activity
     * */
    public static boolean hasNotch(Activity activity) {
        return hasNotchAndroidP(activity)
                | HuaWeiNotchHelper.hasNotch(activity)
                | XiaoMiNotchHelper.hasNotch(activity)
                | VivoNotchHelper.hasNotch(activity)
                | OppoNotchHelper.hasNotch(activity);
    }

    private static boolean hasNotchAndroidP(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            final View decorView = activity.getWindow().getDecorView();
            if (null != decorView) {
                WindowInsets windowInsets = decorView.getRootWindowInsets();
                if (null != windowInsets) {
                    DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                    if (null != displayCutout) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获得刘海区域信息
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.P)
    private static void getNotchParams(Activity activity) {
        final View decorView = activity.getWindow().getDecorView();
        if (decorView != null) {
            decorView.post(new Runnable() {
                @Override
                public void run() {
                    WindowInsets windowInsets = decorView.getRootWindowInsets();
                    if (windowInsets != null) {
                        // 当全屏顶部显示黑边时，getDisplayCutout()返回为null
                        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                        if (null == displayCutout) {
                            Log.e(TAG, "全屏顶部显示黑边");
                            return;
                        }
                        Log.e("TAG", "安全区域距离屏幕左边的距离 SafeInsetLeft:" + displayCutout.getSafeInsetLeft());
                        Log.e("TAG", "安全区域距离屏幕右部的距离 SafeInsetRight:" + displayCutout.getSafeInsetRight());
                        Log.e("TAG", "安全区域距离屏幕顶部的距离 SafeInsetTop:" + displayCutout.getSafeInsetTop());
                        Log.e("TAG", "安全区域距离屏幕底部的距离 SafeInsetBottom:" + displayCutout.getSafeInsetBottom());
                        // 获得刘海区域
                        List<Rect> rects = displayCutout.getBoundingRects();
                        if (rects == null || rects.size() == 0) {
                            Log.e("TAG", "不是刘海屏");
                        } else {
                            Log.e("TAG", "刘海屏数量:" + rects.size());
                            for (Rect rect : rects) {
                                Log.e("TAG", "刘海屏区域：" + rect);
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * 使用刘海屏.
     * @param activity
     * */
    public static void setUseNotch(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            setLayoutMode(activity, WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
        } else {
            if (DeviceUtils.isXiaomi()) {
                XiaoMiNotchHelper.setUseNotch(activity);
            }
        }
    }

    /**
     * 不使用刘海屏.
     * @param activity
     * */
    public static void setNotUseNotch(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            setLayoutMode(activity, WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT);
        } else {

        }
    }

    /**
     * 设置布局模式.
     *
     * LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT：
     *      默认的布局模式，仅当刘海区域完全包含在状态栏之中时，才允许窗口延伸到刘海区域显示，
     *      也就是说，如果没有设置为全屏显示模式，就允许窗口延伸到刘海区域，否则不允许。
     * LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER：
     *      永远不允许窗口延伸到刘海区域。
     * LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES：
     *      始终允许窗口延伸到屏幕短边上的刘海区域，窗口永远不会延伸到屏幕长边上的刘海区域。
     * */
    private static void setLayoutMode(Activity activity, int mode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = mode;
            activity.getWindow().setAttributes(lp);
        }
    }
}
