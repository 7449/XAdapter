package com.xadapter.refresh

import android.content.Context
import android.view.View
import android.widget.FrameLayout

abstract class XLoadMoreView(context: Context, layoutId: Int) : FrameLayout(context) {

    companion object {
        const val NORMAL = -1
        const val LOAD = 0
        const val SUCCESS = 1
        const val NO_MORE = 2
        const val ERROR = 3
    }

    private var loadMoreView: View = View.inflate(context, layoutId, null)

    var state: Int = NORMAL
        set(state) {
            if (state == field) {
                return
            }
            onStart()
            when (state) {
                LOAD -> onLoad()
                NO_MORE -> onNoMore()
                SUCCESS -> onSuccess()
                ERROR -> onError()
                NORMAL -> onNormal()
            }
            field = state
        }

    init {
        addView(loadMoreView)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        initView()
    }

    protected abstract fun initView()
    protected abstract fun onStart()
    protected abstract fun onLoad()
    protected abstract fun onNoMore()
    protected abstract fun onSuccess()
    protected abstract fun onError()
    protected abstract fun onNormal()
}