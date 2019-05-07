package top.abner.base.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * 剪切板.
 * @author Nebula
 * @version 1.0.0
 * @date 2018/8/2
 */
public class ClipboardUtils {

    private static ClipboardManager mClipboardManager;

    private static void init(Context context) {
        if (null == context) {
            throw new NullPointerException();
        }

        if (null == mClipboardManager) {
            mClipboardManager =(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        }

        // 添加剪贴板数据改变监听器
        mClipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                // 剪贴板中的数据被改变，此方法将被回调
                System.out.println("onPrimaryClipChanged()");
            }
        });
    }

    /**
     * 设置剪切板数据.
     * */
    public static void setPrimaryClip(Context context, String text) {
        init(context);
        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
        ClipData mClipData = ClipData.newPlainText("Label", text);

        // 把数据集设置（复制）到剪贴板
        mClipboardManager.setPrimaryClip(mClipData);
    }

    /**
     * 获取剪切板数据.
     * */
    public static String getPrimaryClip(Context context) {
        init(context);
        ClipData mClipData = mClipboardManager.getPrimaryClip();
        if (mClipData != null && mClipData.getItemCount() > 0) {
            // 从数据集中获取（粘贴）第一条文本数据
            CharSequence text = mClipData.getItemAt(0).getText();
            return text.toString();
        }
        return "";
    }
}
