package com.hj.casps;

import com.hj.casps.commodity.FragmentMyPic;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        FragmentMyPic.PictureLevelEntity entity1 = new FragmentMyPic.PictureLevelEntity("aa");
        FragmentMyPic.PictureLevelEntity entity2 = new FragmentMyPic.PictureLevelEntity("aa");
        FragmentMyPic.PictureLevelEntity entity3 = new FragmentMyPic.PictureLevelEntity("aa");
        entity1.addSubItem(entity2);
        entity2.addSubItem(entity3);
        System.out.println(entity1.getLevel());
        System.out.println(entity2.getLevel());
        System.out.println(entity3.getLevel());
    }
}