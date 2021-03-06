package com.example.mengchi.hellocustomui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by mengchi on 12/18/14.
 */
public class CustomView extends ImageView {

    private static final String TAG = "CustomView";


    private Bitmap imageBitmap;
    private float imageAspectRatio;
    private float imageAlpha;
    private int imagePaddingLeft;
    private int imagePaddingTop;
    private int imagePaddingRight;
    private int imagePaddingBottom;
    private int imageScaleType;
    private static final int SCALE_TYPE_FILLXY = 0;
    private static final int SCALE_TYPE_CENTER = 1;
    private Paint paint;
    private static final int MIN_SIZE = 12;


    private int mwidth;
    private int mheight;


    private final static String DEFAULT_MESSAGE = "Android";
    private String mMessage = DEFAULT_MESSAGE;

    public CustomView(Context context) {
        this(context,  null, 0);

    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initialCustomAttributes(context, attrs, defStyle);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = mwidth - getPaddingRight();
        int bottom = mheight - getPaddingBottom();

        paint.setAlpha(255);



        if (imageBitmap != null) {
            paint.setAlpha((int) (255 * imageAlpha));
            left += imagePaddingLeft;
            top += imagePaddingTop;
            right -= imagePaddingRight;
            bottom -= imagePaddingBottom;
            if (imageScaleType == SCALE_TYPE_FILLXY) {
                canvas.drawBitmap(imageBitmap, left, top, paint);


            } else if (imageScaleType == SCALE_TYPE_CENTER) {
                int bw = imageBitmap.getWidth();
                int bh = imageBitmap.getHeight();
                if (bw < right - left) {
                    int delta = (right - left - bw) / 2;
                    left += delta;
                    right -= delta;
                }
                if (bh < bottom - top) {
                    int delta = (bottom - top - bh) / 2;
                    top += delta;
                    bottom -= delta;
                }

                canvas.drawBitmap(imageBitmap, left, top, paint);
            }
        }

    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width=300;
        int height=270;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int desired = getPaddingLeft() + getPaddingRight() +
                    imagePaddingLeft + imagePaddingRight;
            desired += (imageBitmap != null) ? imageBitmap.getWidth() : 0;
            width = Math.max(MIN_SIZE, desired);
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(desired, widthSize);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int rawWidth = width - getPaddingLeft() - getPaddingRight();
            int desired = (int) (getPaddingTop() + getPaddingBottom() + imageAspectRatio * rawWidth);


            height = Math.max(MIN_SIZE, desired);
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(desired, heightSize);
            }
        }

        setMeasuredDimension(width, height);

    }

    public void initialCustomAttributes(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CustomView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomView_imageSrc:
                    imageBitmap = BitmapFactory.decodeResource(
                            getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomView_imageAspectRatio:
                    imageAspectRatio = a.getFloat(attr, 1.0f);
                    break;
                case R.styleable.CustomView_imageAlpha:
                    imageAlpha = a.getFloat(attr, 1.0f);
                    if (imageAlpha > 1.0f) imageAlpha = 1.0f;
                    if (imageAlpha < 0.0f) imageAlpha = 0.0f;
                    break;
                case R.styleable.CustomView_imagePaddingLeft:
                    imagePaddingLeft = a.getDimensionPixelSize(attr, 0);
                    break;
                case R.styleable.CustomView_imagePaddingTop:
                    imagePaddingTop = a.getDimensionPixelSize(attr, 0);
                    break;
                case R.styleable.CustomView_imagePaddingRight:
                    imagePaddingRight = a.getDimensionPixelSize(attr, 0);
                    break;
                case R.styleable.CustomView_imagePaddingBottom:
                    imagePaddingBottom = a.getDimensionPixelSize(attr, 0);
                    break;
                case R.styleable.CustomView_imageScaleType:
                    imageScaleType = a.getInt(attr, 0);
                    break;
                case R.styleable.CustomView_mMessage:
                    mMessage = a.getString(attr);
                    break;

            }
        }
        a.recycle();


        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                Toast.makeText(getContext(), mMessage, 1000).show();

                break;
            default:
                break;
        }
        return true;
    }

    public void setImageBitmap(Bitmap bitmap) {
        imageBitmap = bitmap;
        requestLayout();
        invalidate();
    }



    public Bitmap getImageBitmap() {
        return imageBitmap;
    }
    public float getImageAspectRatio() {
        return imageAspectRatio;
    }
    public void setImageAspectRatio(float imageAspectRatio) {
        this.imageAspectRatio = imageAspectRatio;
    }
    public void setImageAlpha(float imageAlpha) {
        this.imageAlpha = imageAlpha;
    }
    public int getImagePaddingLeft() {
        return imagePaddingLeft;
    }
    public void setImagePaddingLeft(int imagePaddingLeft) {
        this.imagePaddingLeft = imagePaddingLeft;
    }
    public int getImagePaddingTop() {
        return imagePaddingTop;
    }
    public void setImagePaddingTop(int imagePaddingTop) {
        this.imagePaddingTop = imagePaddingTop;
    }
    public int getImagePaddingRight() {
        return imagePaddingRight;
    }
    public void setImagePaddingRight(int imagePaddingRight) {
        this.imagePaddingRight = imagePaddingRight;
    }
    public int getImagePaddingBottom() {
        return imagePaddingBottom;
    }
    public void setImagePaddingBottom(int imagePaddingBottom) {
        this.imagePaddingBottom = imagePaddingBottom;
    }
    public int getImageScaleType() {
        return imageScaleType;
    }
    public void setImageScaleType(int imageScaleType) {
        this.imageScaleType = imageScaleType;
    }
    public String getmMessage() {
        return mMessage;
    }
    public void setmMessage(String xMessage) {
        this.mMessage = xMessage;
    }


}

