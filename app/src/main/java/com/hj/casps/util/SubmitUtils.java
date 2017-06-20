package com.hj.casps.util;

import android.os.CountDownTimer;
import android.view.View;

/**
 * Created by YaoChen on 2017/4/26.
 * 自动增长的随机数
 */

public class SubmitUtils {
    View view;
    int addNum;
    String date;
    MyCountDownTimer myCountDownTimer;

    public SubmitUtils(View view) {
        this.view = view;
    }
//
//    public String setUuid() {
//        uuid = uuid + 1;
//        System.out.println(String.format("%05d", uuid));
//        return String.format("%05d", uuid);
//    }

    public void addTimeClock() {
        myCountDownTimer = new MyCountDownTimer(0, 5000);
        myCountDownTimer.start();
    }

    public void deleteTimeClock() {
        myCountDownTimer.onFinish();
    }

    class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            view.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            view.setEnabled(false);
        }
    }


}
