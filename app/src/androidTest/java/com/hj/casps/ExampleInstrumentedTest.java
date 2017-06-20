package com.hj.casps;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

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


    String s= "/v2content/upload/img/newImg/FD/min/383d7955cca54763bd19c7b2f226c306.jpg";
        String substring = s.substring(17);
        System.out.println("su"+substring);

    }
}
