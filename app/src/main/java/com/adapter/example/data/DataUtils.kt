package com.adapter.example.data

/**
 * by y on 2016/11/17
 */

object DataUtils {
    fun getData(mainBeen: MutableList<ExampleBean>) {
        for (i in 0..1000) {
            mainBeen.add(ExampleBean("$i: this is name", i))
        }
    }
}
