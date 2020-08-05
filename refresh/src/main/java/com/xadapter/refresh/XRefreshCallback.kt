package com.xadapter.refresh

import android.view.ViewParent

interface XRefreshCallback : Callback {
    val currentState: Int
    val visibleHeight: Int
    val isReleaseAction: Boolean
    val refreshParent: ViewParent?
    val isNormal: Boolean
        get() = currentState == Callback.NORMAL
    val isReady: Boolean
        get() = currentState == Callback.READY
    val isRefresh: Boolean
        get() = currentState == Callback.REFRESH
    val isSuccess: Boolean
        get() = currentState == Callback.SUCCESS
    val isError: Boolean
        get() = currentState == Callback.ERROR
    val isDone: Boolean
        get() = isSuccess || isError
    val isBegin: Boolean
        get() = isNormal || isReady

    fun onChange(state: Int)
    fun onChangeHeight(value: Int)
    fun onChangeMoveHeight(value: Int)
    fun onNormal()
    fun onReady()
    fun onRefresh()
    fun onSuccess()
    fun onError()
}