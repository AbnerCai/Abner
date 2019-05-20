package site.nebulas.ui.module;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import site.nebulas.ui.R;

/**
 * 标题栏.
 * <p>自定义组合控件.</p>
 * @author Nebula
 * @version 1.0.0
 * */
public class CustomTitleBar extends RelativeLayout {
    private Button mTitleBarLeftBtn;
    private Button mTitleBarRightBtn;
    private TextView mTitleBarTitle;
    private ProgressBar mtitleBarProgress;

    public CustomTitleBar(Context context) {
        this(context, null);
    }

    public CustomTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_title_bar, this, true);
        mTitleBarLeftBtn = (Button) findViewById(R.id.title_bar_left);
        mTitleBarRightBtn = (Button) findViewById(R.id.title_bar_right);
        mTitleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        mtitleBarProgress = (ProgressBar) findViewById(R.id.title_bar_progress);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar);
        if (attributes != null) {
            // 处理titleBar背景色
            int titleBarBackGround = attributes.getResourceId(R.styleable.CustomTitleBar_title_background_color, Color.GREEN);
            setBackgroundResource(titleBarBackGround);
            // 先处理左边按钮
            // 获取是否要显示左边按钮
            boolean leftButtonVisible = attributes.getBoolean(R.styleable.CustomTitleBar_left_button_visible, true);
            if (leftButtonVisible) {
                mTitleBarLeftBtn.setVisibility(View.VISIBLE);
            } else {
                mTitleBarLeftBtn.setVisibility(View.INVISIBLE);
            }
            // 设置左边按钮的文字
            String leftButtonText = attributes.getString(R.styleable.CustomTitleBar_left_button_text);
            if (!TextUtils.isEmpty(leftButtonText)) {
                mTitleBarLeftBtn.setText(leftButtonText);
                // 设置左边按钮文字颜色
                int leftButtonTextColor = attributes.getColor(R.styleable.CustomTitleBar_left_button_text_color, Color.WHITE);
                mTitleBarLeftBtn.setTextColor(leftButtonTextColor);
            } else {
                // 设置左边图片icon 这里是二选一 要么只能是文字 要么只能是图片
                int leftButtonDrawable = attributes.getResourceId(R.styleable.CustomTitleBar_left_button_drawable, -1);
                if (leftButtonDrawable != -1) {
                    mTitleBarLeftBtn.setBackgroundResource(leftButtonDrawable);
                }
            }

            // 处理标题
            // 先获取标题是否要显示图片icon
            int titleTextDrawable = attributes.getResourceId(R.styleable.CustomTitleBar_title_text_drawable, -1);
            if (titleTextDrawable != -1) {
                mTitleBarTitle.setBackgroundResource(titleTextDrawable);
            } else {
                // 如果不是图片标题 则获取文字标题
                String titleText = attributes.getString(R.styleable.CustomTitleBar_title_text);
                if (!TextUtils.isEmpty(titleText)) {
                    mTitleBarTitle.setText(titleText);
                }
                // 获取标题显示颜色
                int titleTextColor = attributes.getColor(R.styleable.CustomTitleBar_title_text_color, Color.WHITE);
                mTitleBarTitle.setTextColor(titleTextColor);
            }

            // 先处理右边按钮
            // 获取是否要显示右边按钮
            boolean rightButtonVisible = attributes.getBoolean(R.styleable.CustomTitleBar_right_button_visible, true);
            if (rightButtonVisible) {
                mTitleBarRightBtn.setVisibility(View.VISIBLE);
            } else {
                mTitleBarRightBtn.setVisibility(View.INVISIBLE);
            }
            // 设置右边按钮的文字
            String rightButtonText = attributes.getString(R.styleable.CustomTitleBar_right_button_text);
            if (!TextUtils.isEmpty(rightButtonText)) {
                mTitleBarRightBtn.setText(rightButtonText);
                // 设置右边按钮文字颜色
                int rightButtonTextColor = attributes.getColor(R.styleable.CustomTitleBar_right_button_text_color, Color.WHITE);
                mTitleBarRightBtn.setTextColor(rightButtonTextColor);
            } else {
                // 设置右边图片icon 这里是二选一 要么只能是文字 要么只能是图片
                int rightButtonDrawable = attributes.getResourceId(R.styleable.CustomTitleBar_right_button_drawable, -1);
                if (rightButtonDrawable != -1) {
                    mTitleBarRightBtn.setBackgroundResource(rightButtonDrawable);
                }
            }

            // 获取是否显示进度条
            boolean progressVisible = attributes.getBoolean(R.styleable.CustomTitleBar_progress_visible, false);
            if (progressVisible) {
                mtitleBarProgress.setVisibility(View.VISIBLE);
            } else {
                mtitleBarProgress.setVisibility(View.GONE);
            }
            attributes.recycle();
        }
    }

    public void setTitleClickListener(OnClickListener onClickListener) {
        if (onClickListener != null) {
            mTitleBarLeftBtn.setOnClickListener(onClickListener);
            mTitleBarRightBtn.setOnClickListener(onClickListener);
        }
    }

    public Button getTitleBarLeftBtn() {
        return mTitleBarLeftBtn;
    }

    public Button getTitleBarRightBtn() {
        return mTitleBarRightBtn;
    }

    public TextView getTitleBarTitle() {
        return mTitleBarTitle;
    }

    public ProgressBar getTitleBarProgress() {
        return mtitleBarProgress;
    }
}
