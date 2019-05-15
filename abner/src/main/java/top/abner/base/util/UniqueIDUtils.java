package top.abner.base.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.UUID;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;

/**
 *
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/6 15:51
 */
public class UniqueIDUtils {

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getUUID(Context context) {

        StringBuffer serial = null;

        StringBuffer mDevIDShort = new StringBuffer("35");
        mDevIDShort.append(Build.BOARD.length() % 10);
        mDevIDShort.append(Build.BRAND.length() % 10);
        mDevIDShort.append(Build.CPU_ABI.length() % 10);
        mDevIDShort.append(Build.DEVICE.length() % 10);
        mDevIDShort.append(Build.DISPLAY.length() % 10);
        mDevIDShort.append(Build.HOST.length() % 10);
        mDevIDShort.append(Build.ID.length() % 10);
        mDevIDShort.append(Build.MANUFACTURER.length() % 10);
        mDevIDShort.append(Build.MODEL.length() % 10);
        mDevIDShort.append(Build.PRODUCT.length() % 10);
        mDevIDShort.append(Build.TAGS.length() % 10);
        mDevIDShort.append(Build.TYPE.length() % 10);
        mDevIDShort.append(Build.USER.length() % 10);

        try {
            boolean versionCheck = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
            boolean permissionCheck = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
            if (versionCheck && permissionCheck) {
                serial = new StringBuffer(Build.getSerial());
            } else {
                serial = new StringBuffer(Build.SERIAL);
            }
            // API>=9 使用serial号
            return new UUID(mDevIDShort.toString().hashCode(), serial.toString().hashCode()).toString();
        } catch (Exception exception) {
            // serial需要一个初始化
            serial = new StringBuffer("serial"); // 随便一个初始化
        }
        // 使用硬件信息拼凑出来的15位号码
        return new UUID(mDevIDShort.toString().hashCode(), serial.toString().hashCode()).toString();
    }  
}
