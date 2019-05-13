package top.abner.base.notch;

import android.content.Context;

/**
 *
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/13 10:55
 */
public class OppoNotchHelper {

    /**
     * 判断是否有刘海屏
     * Android P 以下 OPPO
     * @param context
     * @return true：有刘海屏；false：没有刘海屏
     */
    public static boolean hasNotch(Context context) {
        return context.getPackageManager()
                .hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }
}
