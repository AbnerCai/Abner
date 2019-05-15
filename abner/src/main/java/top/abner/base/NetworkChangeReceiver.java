package top.abner.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/15 11:32
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 网络变化广播
        Toast.makeText(context, "网络状态改变", Toast.LENGTH_SHORT).show();
    }
}
