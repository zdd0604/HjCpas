package com.hj.casps;

import com.hj.casps.util.NetUtil2;

import org.junit.Test;

/**
 * Created by 鑫 Administrator on 2017/5/4.
 */

public class XinTest {
    @Test
    public void test1() throws InterruptedException {
        System.out.println(NetUtil2.checkEnableDo(this));
        System.out.println(NetUtil2.checkEnableDo(this));
        Thread.sleep(1000);
        System.out.println(NetUtil2.checkEnableDo(this));
        Thread.sleep(1000);
        System.out.println(NetUtil2.checkEnableDo(this));
        Thread.sleep(2000);
        System.out.println(NetUtil2.checkEnableDo(this));
        Thread.sleep(1000);
        System.out.println(NetUtil2.checkEnableDo(this));
        Thread.sleep(1000);
        System.out.println(NetUtil2.checkEnableDo(this));
    }

    @Test
    public void name()   {
        fsd();
    }

    private void fsd() {
        System.out.print("dfs");
    }

    @Test
    public void test2(){

    }
}
