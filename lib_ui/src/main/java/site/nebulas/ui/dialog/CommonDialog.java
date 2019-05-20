package site.nebulas.ui.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import site.nebulas.ui.R;

/**
 * @author Nebula
 * @version 1.0.0
 * @date 2018/7/31
 */
public class CommonDialog extends DialogFragment {

    private TextView mTvTitle,
            mTvContent,
            mTvCancel,
            mTvConfirm;

    private String mTitle;
    private String mContent;
    private String mCancel;
    private String mConfirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_common, null);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvContent = view.findViewById(R.id.tv_content);
        mTvCancel = view.findViewById(R.id.tv_cancel);
        mTvConfirm = view.findViewById(R.id.tv_confirm);

        if (isNonEmpty(mTitle)) {
            mTvTitle.setText(mTitle);
        }

        if (isNonEmpty(mContent)) {
            mTvContent.setText(mContent);
        }

        if (isNonEmpty(mCancel)) {
            mTvCancel.setText(mCancel);
        }

        if (isNonEmpty(mConfirm)) {
            mTvConfirm.setText(mConfirm);
        }

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                if (null != mCancelListener) {
                    mCancelListener.cancel();
                }

                if (null != mCommonDialogListener) {
                    mCommonDialogListener.cancel();
                }
            }
        });
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                if (null != mConfirmListener) {
                    mConfirmListener.confirm();
                }

                if (null != mCommonDialogListener) {
                    mCommonDialogListener.confirm();
                }
            }
        });

        builder.setCancelable(true);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        // WindowManager.LayoutParams.MATCH_PARENT
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        params.width = (int) (size.x * 0.75);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    /**
     * 接口.
     * */
    private CommonDialogListener mCommonDialogListener;
    @Deprecated
    public interface CommonDialogListener<T> {
        void result(T result);
        void cancel();
        void confirm();
    }

    private ConfirmListener mConfirmListener;
    public interface ConfirmListener {
        void confirm();
    }

    private ResultListener mResultListener;
    public interface ResultListener<T> {
        void result(T result);
    }

    private CancelListener mCancelListener;
    public interface CancelListener {
        void cancel();
    }

    /**
     * 建造者模式.
     * */
    public CommonDialog() {}

    @SuppressLint("ValidFragment")
    private CommonDialog(Builder builder) {
        if (isNonEmpty(builder.mTitle)) {
            this.mTitle = builder.mTitle;
        }

        if (isNonEmpty(builder.mContent)) {
            this.mContent = builder.mContent;
        }

        if (isNonEmpty(builder.mCancel)) {
            this.mCancel = builder.mCancel;
        }

        if (isNonEmpty(builder.mConfirm)) {
            this.mConfirm = builder.mConfirm;
        }

        this.mCommonDialogListener = builder.mCommonDialogListener;
        this.mConfirmListener = builder.mConfirmListener;
        this.mResultListener = builder.mResultListener;
        this.mCancelListener = builder.mCancelListener;
    }

    public static class Builder {
        private ConfirmListener mConfirmListener;
        private ResultListener mResultListener;
        private CancelListener mCancelListener;
        private CommonDialogListener mCommonDialogListener;
        private String mTitle;
        private String mContent;
        private String mCancel;
        private String mConfirm;

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setContent(String content) {
            this.mContent = content;
            return this;
        }

        public Builder setCancel(String cancel) {
            this.mCancel = cancel;
            return this;
        }

        public Builder setConfirm(String confirm) {
            this.mConfirm = confirm;
            return this;
        }

        public Builder setConfirmListener(ConfirmListener confirmListener) {
            this.mConfirmListener = confirmListener;
            return this;
        }

        public Builder setResultListener(ResultListener resultListener) {
            this.mResultListener = resultListener;
            return this;
        }

        public Builder setCancelListener(CancelListener cancelListener) {
            this.mCancelListener = cancelListener;
            return this;
        }

        @Deprecated
        public Builder setCommonDialogListener(CommonDialogListener commonDialogListener) {
            this.mCommonDialogListener = commonDialogListener;
            return this;
        }

        public CommonDialog build() {
            return new CommonDialog(this);
        }
    }

    private boolean isNonEmpty(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        return true;
    }
}

/*

        new CommonDialog.Builder()
                .setTitle("标题")
                .setContent("内容")
                .setCancel("取消")
                .setConfirm("确定")
                .setCommonDialogListener(new CommonDialog.CommonDialogListener() {
                    @Override
                    public void result(Object result) {

                    }

                    @Override
                    public void cancel() {
                        toast("取消");
                    }

                    @Override
                    public void confirm() {
                        toast("确认");
                    }
                })
                .build()
                .show(getFragmentManager(), "ad");

*/
