package com.xadapter.simple

import com.xadapter.listener.XMultiCallBack

/**
 * by y on 2017/3/9
 *
 *
 *
 *
 * 简单的MultiItem，使用者可继承[XMultiCallBack] 自己定制
 */

class SimpleXMultiItem : XMultiCallBack {
    override val itemType: Int
        get() = itemMultiType
    override val position: Int
        get() = itemMultiPosition

    var message: String = ""
    var messageSuffix: String = ""
    var icon: Int = 0
    private var itemMultiType: Int = 0
    private var itemMultiPosition = -1

    /**
     * @param itemMultiType RecyclerView Type
     */
    constructor(itemMultiType: Int) {
        this.itemMultiType = itemMultiType
    }

    /**
     * @param itemMultiType     RecyclerView Type
     * @param itemMultiPosition 正确的position
     * @param message      携带信息
     */
    constructor(itemMultiType: Int, itemMultiPosition: Int, message: String) {
        this.itemMultiType = itemMultiType
        this.itemMultiPosition = itemMultiPosition
        this.message = message
    }

    /**
     * @param itemMultiType      RecyclerView Type
     * @param itemMultiPosition  正确的position
     * @param message       携带信息
     * @param messageSuffix 携带的第二个信息
     */
    constructor(itemMultiType: Int, itemMultiPosition: Int, message: String, messageSuffix: String) {
        this.itemMultiType = itemMultiType
        this.itemMultiPosition = itemMultiPosition
        this.message = message
        this.messageSuffix = messageSuffix
    }

    /**
     * @param itemMultiType      RecyclerView Type
     * @param message       携带信息
     * @param messageSuffix 携带的第二个信息
     */
    constructor(itemMultiType: Int, message: String, messageSuffix: String) {
        this.itemMultiType = itemMultiType
        this.message = message
        this.messageSuffix = messageSuffix
    }


    /**
     * @param itemMultiType     RecyclerView Type
     * @param itemMultiPosition 正确的position
     * @param message      携带信息
     * @param icon         图标或者其他参数
     */
    constructor(itemMultiType: Int, itemMultiPosition: Int, message: String, icon: Int) {
        this.itemMultiType = itemMultiType
        this.itemMultiPosition = itemMultiPosition
        this.message = message
        this.icon = icon
    }

    /**
     * @param itemMultiType      RecyclerView Type
     * @param itemMultiPosition  正确的position
     * @param message       携带信息
     * @param icon          图标或者其他参数
     * @param messageSuffix 携带的第二个信息
     */
    constructor(itemMultiType: Int, itemMultiPosition: Int, message: String, messageSuffix: String, icon: Int) {
        this.itemMultiType = itemMultiType
        this.itemMultiPosition = itemMultiPosition
        this.message = message
        this.messageSuffix = messageSuffix
        this.icon = icon
    }

}
