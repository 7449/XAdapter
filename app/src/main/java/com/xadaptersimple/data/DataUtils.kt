package com.xadaptersimple.data

/**
 * by y on 2016/11/17
 */

object DataUtils {


    fun getData(mainBeen: MutableList<MainBean>) {
        for (i in 0..1000) {
            mainBeen.add(MainBean("$i: this is name", i))
        }
    }

    fun getTestData(mainBeen: MutableList<MainBean>): MutableList<MainBean> {
        for (i in 0..5) {
            mainBeen.add(MainBean("$i: this is name", i))
        }
        return mainBeen
    }

}
