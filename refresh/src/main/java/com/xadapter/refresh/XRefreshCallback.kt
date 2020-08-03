package com.xadapter.refresh

interface XRefreshCallback : Callback {
    val currentState: Int
    val isReady: Boolean
        get() = currentState == Callback.READY
    val isRefresh: Boolean
        get() = currentState == Callback.REFRESH

    fun onChange(state: Int)
    fun onNormal()
    fun onStart()
    fun onReady()
    fun onRefresh()
    fun onSuccess()
    fun onError()
}