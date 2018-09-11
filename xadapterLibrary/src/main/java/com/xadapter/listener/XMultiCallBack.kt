package com.xadapter.listener

/**
 * by y on 2017/3/9
 */

interface XMultiCallBack {

    val itemType: Int

    val position: Int

    companion object {
        const val TYPE_ITEM = -11
    }
}
