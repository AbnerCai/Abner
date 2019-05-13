package top.abner.base.notch;

import android.os.Build;

/**
 * 设备工具类.
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/13 11:11
 */
public class DeviceUtils {

    /**
     * 判断该设备是否为小米设备
     * */
    public static boolean isXiaomi() {
        if ("Xiaomi".equals(Build.MANUFACTURER)) {
            return true;
        }
        return false;
    }
}
