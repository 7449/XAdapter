package com.xadapter.refresh

import android.content.Context
import android.view.View
import android.widget.FrameLayout

abstract class XLoadMoreView(context: Context, layoutId: Int) : FrameLayout(context) {


    private var loadMoreView: View = View.inflate(context, layoutId, null)

    var state: Int = Callback.NORMAL
        set(state) {
            if (state == field) {
                return
            }
            onStart()
            when (state) {
                Callback.LOAD -> onLoad()
                Callback.NO_MORE -> onNoMore()
                Callback.SUCCESS -> onSuccess()
                Callback.ERROR -> onError()
                Callback.NORMAL -> onNormal()
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