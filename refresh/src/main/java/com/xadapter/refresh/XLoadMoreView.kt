package com.xadapter.refresh

import android.content.Context
import android.view.View
import android.widget.FrameLayout

abstract class XLoadMoreView(context: Context) : FrameLayout(context), XLoadMoreCallback {

    private var state: Int = Callback.NORMAL

    override fun onChange(state: Int) {
        if (state == this.state) {
            return
        }
        when (state) {
            Callback.NORMAL -> onNormal()
            Callback.LOAD -> onLoad()
            Callback.SUCCESS -> onSuccess()
            Callback.ERROR -> onError()
            Callback.NO_MORE -> onNoMore()
        }
        this.state = state
    }

    override val currentState: Int
        get() = state

    override val xRootView: View
        get() = this
}