package site.nebulas.ui.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import site.nebulas.ui.R;

/**
 * @author Nebula
 * @version 1.0.0
 * @date 2018/7/30
 */
public class HomeAdDialog extends DialogFragment {

    private ImageView mImageView, mClose;

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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_home_ad, null);
        mImageView = view.findViewById(R.id.iv_icon);
        mClose = view.findViewById(R.id.iv_close);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mDialogResultListener) {
                    mDialogResultListener.result(null);
                }
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
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
        params.width = (int) (size.x * 0.85);
        params.height = (int) (size.y * 0.85);
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mDialogDismissListener != null) {
            mDialogDismissListener.dismiss(this);
        }
    }

    private DialogResultListener mDialogResultListener;
    private DialogDismissListener mDialogDismissListener;

    public interface DialogResultListener<T> {
        void result(T result);
    }

    public interface DialogDismissListener{
        void dismiss(DialogFragment dialog);
    }



    public HomeAdDialog() {}

    @SuppressLint("ValidFragment")
    private HomeAdDialog(Builder builder) {
        this.mDialogResultListener = builder.mDialogResultListener;
        this.mDialogDismissListener = builder.mDialogDismissListener;
    }

    public static class Builder {
        private DialogResultListener mDialogResultListener;
        private DialogDismissListener mDialogDismissListener;

        public Builder setDialogResultListener(DialogResultListener dialogResultListener) {
            this.mDialogResultListener = dialogResultListener;
            return this;
        }

        public Builder setDialogDismissListener(DialogDismissListener dialogDismissListener){
            this.mDialogDismissListener = dialogDismissListener;
            return this;
        }

        public HomeAdDialog build() {
            return new HomeAdDialog(this);
        }
    }
}


/*

        new HomeAdDialog.Builder()
                .setDialogResultListener(new HomeAdDialog.DialogResultListener() {
                    @Override
                    public void result(Object result) {
                        toast("广告被点击");
                    }
                })
                .setDialogDismissListener(new HomeAdDialog.DialogDismissListener() {
                    @Override
                    public void dismiss(DialogFragment dialog) {
                        toast("广告关闭了");
                    }
                })
                .build()
                .show(getFragmentManager(), "ad");

*/