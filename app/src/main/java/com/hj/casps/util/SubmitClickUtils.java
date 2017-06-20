package com.hj.casps.util;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import com.hj.casps.BuildConfig;

/**
 * Created by YaoChen on 2017/4/26.
 * 自动增长的随机数
 */

public class SubmitClickUtils {
    public static final int MinDoInterval = 5 * 1000; /*按纽两次提交的间距内，事件无效*/
    private View view;
    private MyCountDownTimer myCountDownTimer;

    private SubmitClickUtils(final View view) {
        this.view = view;
        this.view.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void sendAccessibilityEvent(View host, int eventType) {
                log("sendAccessibilityEvent() called with: " + "host = [" + host + "], eventType = [" + eventType + "]");
                super.sendAccessibilityEvent(host, eventType);
                if (host == SubmitClickUtils.this.view && eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
                    timeStart(SubmitClickUtils.this.view);
                    log("sendAccessibilityEvent() timeStart called with: " + "host = [" + host + "], eventType = [" + eventType + "]");
                }
            }
        });
        this.view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                timeFinish();
                SubmitClickUtils.this.view.removeOnAttachStateChangeListener(this);
            }
        });
    }

    private void timeFinish() {
        if (myCountDownTimer != null) {
            myCountDownTimer.onFinish();
        }
    }

    private void timeStart(View view) {
        myCountDownTimer = new MyCountDownTimer(MinDoInterval, view, 1000);
        myCountDownTimer.start();
    }

    /**
     * @param view
     */
    public static void addView(View... view) {
        for (View item : view) {
            new SubmitClickUtils(item);
        }
    }

    private class MyCountDownTimer extends CountDownTimer {
        View view;

        public MyCountDownTimer(long millisInFuture, View view, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.view = view;
            log("MyCountDownTimer() called with: " + "millisInFuture = [" + millisInFuture + "], countDownInterval = [" + countDownInterval + "]");
        }


        @Override
        public void onFinish() {
            log("onFinish() called with: " + "");
            if (view != null) {
                view.setFocusable(true);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            log("onTick() called with: " + "millisUntilFinished = [" + millisUntilFinished + "]");
            view.setFocusable(false);
        }
    }

    private void log(String s) {
        if (BuildConfig.DEBUG) {
            Log.d(getClass().getSimpleName(), s);
        }
    }


}
