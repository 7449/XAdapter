package com.xadapter.multi

/**
 * by y on 2017/3/9
 *
 *
 *
 *
 * 简单的MultiItem，使用者可继承[XMultiCallBack] 定制
 */
data class SimpleXMultiItem(
        var message: String = "",
        var messageSuffix: String = "",
        var icon: Int = 0,
        var itemMultiType: Int = 0,
        var itemMultiPosition: Int = XMultiCallBack.NO_CLICK_POSITION
) : XMultiCallBack {
    override val itemType: Int = itemMultiType
    override val position: Int = itemMultiPosition
}