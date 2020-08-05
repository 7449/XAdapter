package com.xadapter.refresh

interface XLoadMoreCallback : Callback {
    val currentState: Int
    val isLoading: Boolean
        get() = currentState == Callback.LOAD
    val isError: Boolean
        get() = currentState == Callback.ERROR
    val isNoMore: Boolean
        get() = currentState == Callback.NO_MORE
    val isSuccess: Boolean
        get() = currentState == Callback.SUCCESS
    val isNormal: Boolean
        get() = currentState == Callback.NORMAL

    fun onChange(state: Int)
    fun onNormal()
    fun onLoad()
    fun onSuccess()
    fun onError()
    fun onNoMore()
}