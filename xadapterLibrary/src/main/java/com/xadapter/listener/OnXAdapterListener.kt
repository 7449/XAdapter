package com.xadapter.listener

/**
 * by y on 2017/3/18.
 * The callback interface definition to invoke in the project
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