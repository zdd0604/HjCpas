package com.hj.casps;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.hj.casps.commodity.FragementTemporaryPic;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.user.UserBean;
import com.hj.casps.user.UserBeanUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
     /*   Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.hj.casps", appContext.getPackageName());*/


    String s= "http://192.168.0.120:8081";
        String str=s.substring(s.indexOf("//")+2,s.lastIndexOf(":"));
        System.out.println("su"+str);


    }
}
