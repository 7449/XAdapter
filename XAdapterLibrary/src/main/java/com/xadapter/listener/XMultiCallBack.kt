package com.xadapter.listener

/**
 * @author y
 * @create 2019/3/12
 */
interface XMultiCallBack {

    val itemType: Int
    val position: Int

    companion object {
        const val NO_CLICK_POSITION = -10001
    }
}