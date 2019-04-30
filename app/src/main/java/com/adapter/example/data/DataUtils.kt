package com.adapter.example.data

import com.adapter.example.R
import com.adapter.example.activity.MultipleXXItemActivity
import com.xadapter.simple.SimpleXMultiItem

/**
 * by y on 2016/11/17
 */

object DataUtils {

    fun getData(mainBeen: MutableList<ExampleBean>) {
        for (i in 0..30) {
            mainBeen.add(ExampleBean("$i: this is name", i))
        }
    }

    fun multipleData(): ArrayList<SimpleXMultiItem> {
        return ArrayList<SimpleXMultiItem>().apply {
            add(SimpleXMultiItem(itemMultiType = MultipleXXItemActivity.TYPE_LINE))
            add(SimpleXMultiItem(itemMultiType = MultipleXXItemActivity.TYPE_ITEM, itemMultiPosition = 0, message = "头像", icon = R.mipmap.ic_launcher))
            add(SimpleXMultiItem(itemMultiType = MultipleXXItemActivity.TYPE_LINE))
            add(SimpleXMultiItem(itemMultiType = MultipleXXItemActivity.TYPE_ITEM, itemMultiPosition = 1, message = "收藏", icon = R.mipmap.ic_launcher))
            add(SimpleXMultiItem(itemMultiType = MultipleXXItemActivity.TYPE_ITEM, itemMultiPosition = 2, message = "相册", icon = R.mipmap.ic_launcher))
            add(SimpleXMultiItem(itemMultiType = MultipleXXItemActivity.TYPE_LINE))
            add(SimpleXMultiItem(itemMultiType = MultipleXXItemActivity.TYPE_ITEM, itemMultiPosition = 3, message = "钱包", icon = R.mipmap.ic_launcher))
            add(SimpleXMultiItem(itemMultiType = MultipleXXItemActivity.TYPE_ITEM, itemMultiPosition = 4, message = "卡包", icon = R.mipmap.ic_launcher))
            add(SimpleXMultiItem(itemMultiType = MultipleXXItemActivity.TYPE_LINE))
            add(SimpleXMultiItem(itemMultiType = MultipleXXItemActivity.TYPE_ITEM, itemMultiPosition = 5, message = "表情", icon = R.mipmap.ic_launcher))
            add(SimpleXMultiItem(itemMultiType = MultipleXXItemActivity.TYPE_LINE))
            add(SimpleXMultiItem(itemMultiType = MultipleXXItemActivity.TYPE_ITEM, itemMultiPosition = 6, message = "设置", icon = R.mipmap.ic_launcher))
        }
    }
}
