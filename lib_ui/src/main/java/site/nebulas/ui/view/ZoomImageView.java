package site.nebulas.ui.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author Nebula
 * @version 1.0.0
 * @date 2018/10/22
 */
public class ZoomImageView extends AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener,
        ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {

    private Context mContext;

    private boolean mOnce;

    /**
     * 初始化时缩放的值
     * */
    private float mInitScale;

    /**
     * 双击放大时达到的值
     * */
    private float mMidScale;

    /**
     * 放大的最大值
     * */
    private float mMaxScale;

    /**
     * 缩放矩阵
     * */
    private Matrix mScaleMatrix;

    private ScaleGestureDetector mScaleGestureDetector;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);

        mScaleGestureDetector = new ScaleGestureDetector(mContext, this);
        setOnTouchListener(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @SuppressWarnings("AliDeprecation")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    /**
     * 获取ImageView加载完成的图片
     * */
    @Override
    public void onGlobalLayout() {
        if (!mOnce) {
            // 得到控件的宽和高
            int width = getWidth();
            int height = getHeight();

            // 获取图片及宽高
            Drawable drawable = getDrawable();
            if (null == drawable) {
                return;
            }
            int dw = drawable.getIntrinsicWidth();
            int dh = drawable.getIntrinsicHeight();

            // 缩放比例
            float scale = 1.0f;

            /**
             * 如果图片的宽度大于控件宽度，但是高小于控件高度；我们将其缩小
             * */
            if (dw > width && dh < height) {
               scale = width * 1.0f / dw;
            }

            /**
             * 如果图片的高度大于控件高度，但是宽小于控件宽度；我们将其缩小
             * */
            if (dh > height && dw < width) {
                scale = height * 1.0f / dh;
            }

            /**
             * 如果图片的宽高小于控件宽高；我们将其放大
             * */
            if ((dw > width && dh > height) || (dw < width && dh < height)) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }

            /**
             * 得到初始化时的缩放比例
             * */
            mInitScale = scale;
            mMaxScale = mInitScale * 4;
            mMidScale = mInitScale * 2;

            /**
             * 将图片移动至控件的中心
             * */
            int dx = getWidth()/2 - dw/2;
            int dy = getHeight()/2 - dh/2;

            mScaleMatrix.postTranslate(dx, dy);
            mScaleMatrix.postScale(mInitScale, mInitScale, width/2, height/2);
            setImageMatrix(mScaleMatrix);

            mOnce = true;
        }
    }

    /**
     * 获取当前图片的缩放值.
     * */
    public float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 缩放区间: mInitScale mMaxScale
     * */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();

        if (null == getDrawable()) {
            return true;
        }

        // 缩放范围的控制
        if ((scale < mMaxScale && scaleFactor > 1.0f) ||
                (scale > mInitScale && scaleFactor < 1.0f)) {
            // 最小值控制
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale/scale;
            }

            // 最大值控制
            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale/scale;
            }
            mScaleMatrix.postScale(scaleFactor, scaleFactor,
                    detector.getFocusX()/2.0f, detector.getFocusY()/2.0f);
            
            checkBorderAndCenterWhenScale();
            
            setImageMatrix(mScaleMatrix);
        }

        return true;
    }

    /**
     * 在缩放的时候进行边界控制以及位置控制
     * */
    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 水平方向控制
        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }
            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }

        // 竖直方向控制
        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }

        // 如果宽度或者高度小于控件的宽或高，则居中
        if (rectF.width() < width) {
            deltaX =  width/2f - rectF.right + rectF.width()/2f;
        }
        if (rectF.height() < height) {
            deltaY = height/2f - rectF.bottom + rectF.height()/2f;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 获得图片放大缩小以后的宽高
     * */
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();

        Drawable drawable = getDrawable();
        if (null != drawable) {
            rectF.set(0, 0,
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        return true;
    }
}
