package site.nebulas.ui.ad.splash;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import site.nebulas.ui.R;
import site.nebulas.ui.ad.splash.model.AdModel;

/**
 * 广告页
 * @author Nebula
 * @version 1.0.0
 * @date 2018/7/18
 */
public class SplashDialog extends Dialog {

    private Context mContext;
    // 展示的
    AdModel adModel;

    // 背景照片
    private ImageView img_background;
    // 倒计时
    private CountDownView count_down_view;

    private OnSplashDetailClickListener onSplashDetailClickListener;

    public SplashDialog(Context context, AdModel adModel) {
        super(context, R.style.ADDialog);
        mContext = context;
        this.adModel = adModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_splash);
        fullScreen(true);
//        fullScreen((Activity) mContext);
        initView();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        fullScreen(false);
    }

    /**
     * 全屏.
     * @param enable false 显示，true 隐藏
     */
    private void fullScreen(boolean enable) {
        WindowManager.LayoutParams p = this.getWindow().getAttributes();
        if (enable) {
            //|=：或等于，取其一
            p.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;

        } else {
            //&=：与等于，取其二同时满足，     ~ ： 取反
            p.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
        getWindow().setAttributes(p);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    public static void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // 5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                // 两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                // 导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }


    private void initView() {

        img_background = (ImageView) findViewById(R.id.img_background);
        Glide.with(mContext).load(adModel.getImgUrl()).into(img_background);
        // 广告倒计时
        count_down_view = (CountDownView) findViewById(R.id.count_down_view);
        count_down_view.setCountDownTimerListener(new CountDownView.CountDownTimerListener() {
            @Override
            public void onStartCount() {

            }

            @Override
            public void onChangeCount(int second) {

            }

            @Override
            public void onFinishCount() {
                dismiss();
            }
        });
        // 启动倒计时
        count_down_view.start();
        // 点击跳过按钮
        count_down_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // 点击事件
        img_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSplashDetailClickListener != null) {
                    onSplashDetailClickListener.onSplashDetailClick(adModel);
                }
                dismiss();
            }
        });

    }

    public void setOnSplashDetailClickListener(OnSplashDetailClickListener onSplashDetailClickListener) {
        this.onSplashDetailClickListener = onSplashDetailClickListener;
    }

    public interface OnSplashDetailClickListener {
        void onSplashDetailClick(AdModel adModel);
    }
}
