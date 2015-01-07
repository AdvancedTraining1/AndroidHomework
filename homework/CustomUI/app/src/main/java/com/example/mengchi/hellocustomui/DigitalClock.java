package com.example.mengchi.hellocustomui;

/**
 * Created by mengchi on 12/23/14.
 */

import java.util.Calendar;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;


public class DigitalClock extends TextView {
    private final static String TAG = "DigitalClock";

    private Calendar mCalendar;
    private final static String FORMAT = "yyyy.M.d E";
    private String mFormat = FORMAT;
    private Runnable mTicker;
    private Handler mHandler;
    private boolean mTickerStopped = false;
    private final static String DEFAULT_MESSAGE = "Android";
    private String mMessage = DEFAULT_MESSAGE;

    public DigitalClock(Context context) {
        super(context);
    //    initClock(context);
    }

    public DigitalClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialCustomAttributes(attrs);
        initClock(context);
    }


    private void initClock(Context context) {
        if (mCalendar == null) {
            mCalendar = Calendar.getInstance();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        mTickerStopped = false;
        super.onAttachedToWindow();
        mHandler = new Handler();

        mTicker = new Runnable() {
            public void run() {
                if (mTickerStopped)
                    return;
                mCalendar.setTimeInMillis(System.currentTimeMillis());
                // setText(mSimpleDateFormat.format(mCalendar.getTime()));
                setText(DateFormat.format(mFormat, mCalendar));

                invalidate();
                long now = SystemClock.uptimeMillis();
                // long next = now + (1000 - now % 1000);
                long next = now + (1000 - System.currentTimeMillis() % 1000);

                // Debug
                Log.d(TAG, "" + now);
                Log.d(TAG, "" + next);
                Log.d(TAG, "" + mCalendar.getTimeInMillis());

                // TODO
                mHandler.postAtTime(mTicker, next);
            }
        };
        mTicker.run();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTickerStopped = true;
    }

    public void setFormat(String format) {
        mFormat = format;
    }


    public void initialCustomAttributes(AttributeSet attrs){
        TypedArray attributeArray = getContext().obtainStyledAttributes(attrs,R.styleable.DigitalClock);
        mFormat = attributeArray.getString(R.styleable.DigitalClock_my_time_type);
        mMessage = attributeArray.getString(R.styleable.DigitalClock_mMessage);

        if(mFormat==null)
            mFormat=FORMAT;


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

}

