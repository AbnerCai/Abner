package site.nebulas.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;

/**
 * @author Nebula
 * @version 1.0.0
 * @date 2018/8/9
 */
public class MenuBar extends LinearLayout {

    private Context mContext;

    ImageView mLeftImg, mRightImg;
    TextView mTvContent, mRightTv;
    RelativeLayout mRightRl;
    Switch mRightSwitch;

    public MenuBar(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    public MenuBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(context);
        init(context, attrs,0);
    }

    public MenuBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(context);
        init(context, attrs,defStyleAttr);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.menu_bar, this, true);
        mLeftImg = (ImageView) findViewById(R.id.img_left);
        mRightRl = (RelativeLayout) findViewById(R.id.right_rl);
        mRightImg = (ImageView) findViewById(R.id.img_right);
        mRightTv = (TextView) findViewById(R.id.tv_right);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mRightSwitch = (Switch) findViewById(R.id.switch_right);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mMenuBarOnClickListener) {
                    mMenuBarOnClickListener.onClick(v);
                }
            }
        });
    }


    @SuppressLint("ResourceAsColor")
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MenuBar);
        if (attributes != null) {
            // 处理MenuBar背景色
            int menuBarBackGround = attributes.getResourceId(R.styleable.MenuBar_menu_bar_background_color, Color.WHITE);
            if (menuBarBackGround != -1) {
                setBackgroundColor(menuBarBackGround);
            }

            // 处理左边图片,获取是否要显示左边图片
            boolean leftImageVisible = attributes.getBoolean(R.styleable.MenuBar_left_image_visible, false);
            if (!leftImageVisible) {
                mLeftImg.setVisibility(View.INVISIBLE);
            } else {
                mLeftImg.setVisibility(View.VISIBLE);

                // 设置左边图片icon
                int leftImageDrawable = attributes.getResourceId(R.styleable.MenuBar_left_image_drawable, -1);
                if (leftImageDrawable != -1) {
                    mLeftImg.setBackgroundResource(leftImageDrawable);
                }
            }

            // 处理右边区域,获取是否要显示右边图片,图片优先于文字
            boolean rightVisible = attributes.getBoolean(R.styleable.MenuBar_right_visible, true);
            if (!rightVisible) {
                mRightRl.setVisibility(View.INVISIBLE);
            } else {
                mRightRl.setVisibility(View.VISIBLE);
                // 设置宽度
                float rightWidth = attributes.getDimension(R.styleable.MenuBar_right_width, 2);
                Log.d("MenuBar", "rightWidth: " + rightWidth);
                Log.d("MenuBar", "rightWidth dip2px: " + dip2px(mContext, rightWidth));
                Log.d("MenuBar", "rightWidth px2dip: " + px2dip(mContext, rightWidth));
                LayoutParams layoutParams = new LayoutParams(px2dip(mContext, rightWidth), LayoutParams.MATCH_PARENT);
                layoutParams.weight = px2dip(mContext, rightWidth);
                mRightRl.setLayoutParams(layoutParams);

                // 右边图片
                boolean rightImageVisible = attributes.getBoolean(R.styleable.MenuBar_right_image_visible, false);
                if (rightImageVisible) {
                    mRightImg.setVisibility(View.VISIBLE);
                    mRightTv.setVisibility(View.GONE);

                    // 设置右边图片icon
                    int rightImageDrawable = attributes.getResourceId(R.styleable.MenuBar_right_image_drawable, -1);
                    if (rightImageDrawable != -1) {
                        mRightImg.setBackgroundResource(rightImageDrawable);
                    }
                } else {
                    mRightImg.setVisibility(View.GONE);

                    // 右边文字
                    boolean rightTextVisible = attributes.getBoolean(R.styleable.MenuBar_right_text_visible, false);
                    if (rightTextVisible) {
                        mRightTv.setVisibility(View.VISIBLE);
                        // 文字内容
                        String rightText = attributes.getString(R.styleable.MenuBar_right_text);
                        if (!TextUtils.isEmpty(rightText)) {
                            mRightTv.setText(rightText);
                            // 内容颜色
                            int rightColor = attributes.getColor(R.styleable.MenuBar_right_text_color, Color.WHITE);
                            mRightTv.setTextColor(rightColor);

                            float rightSize = attributes.getDimension(R.styleable.MenuBar_right_text_size, 16);
                            mRightTv.getPaint().setTextSize(rightSize);
                        }
                    } else {
                        mRightTv.setVisibility(View.GONE);
                    }
                }

                // 右边开关
                boolean rightSwitchVisible = attributes.getBoolean(R.styleable.MenuBar_right_switch_visible, false);
                if (rightSwitchVisible) {
                    mRightImg.setVisibility(View.GONE);
                    mRightTv.setVisibility(View.GONE);
                    mRightSwitch.setVisibility(View.VISIBLE);
                    mRightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (null != mOnCheckedChangeListener) {
                                mOnCheckedChangeListener.onCheckedChanged(buttonView, isChecked);
                            }
                        }
                    });
                }
            }

            // 处理中间内容
            String contentText = attributes.getString(R.styleable.MenuBar_content_text);
            if (!TextUtils.isEmpty(contentText)) {
                mTvContent.setText(contentText);
                // 内容颜色
                int contentColor = attributes.getColor(R.styleable.MenuBar_content_text_color, Color.WHITE);
                mTvContent.setTextColor(contentColor);

                float contentSize = attributes.getDimension(R.styleable.MenuBar_content_text_size, 16);
                mTvContent.getPaint().setTextSize(contentSize);
            }
        }
    }

    public interface MenuBarOnClickListener {
        void onClick(View v);
    }

    private MenuBarOnClickListener mMenuBarOnClickListener;

    public void setMenuBarOnClickListener(MenuBarOnClickListener menuBarOnClickListener) {
        this.mMenuBarOnClickListener = menuBarOnClickListener;
    }

    public void setRightText(String text) {
        if (null != mRightTv && null != text) {
            mRightTv.setText(text);
        }
    }

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale+0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
