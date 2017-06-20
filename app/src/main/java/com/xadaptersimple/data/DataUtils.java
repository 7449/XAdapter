package com.xadaptersimple.data;

import java.util.List;

/**
 * by y on 2016/11/17
 */

public class DataUtils {


    public static void getData(List<MainBean> mainBeen) {
        for (int i = 0; i < 30; i++) {
            mainBeen.add(new MainBean(i + ": this is name", i));
        }
    }

    public static List<MainBean> getTestData(List<MainBean> mainBeen) {
        for (int i = 0; i < 6; i++) {
            mainBeen.add(new MainBean(i + ": this is name", i));
        }
        return mainBeen;
    }

}
