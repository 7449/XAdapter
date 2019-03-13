package com.xadapter.listener

/**
 * @author y
 * @create 2019/3/12
 */
interface OnXAdapterListener {

    /**
     * Drop-down refresh callback
     */
    fun onXRefresh()

    /**
     * The pull-up callback is loaded
     */
    fun onXLoadMore()
}