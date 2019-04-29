@file:Suppress("LeakingThis")

package com.xadapter.widget

import android.content.Context
import android.view.View
import android.widget.FrameLayout

/**
 * @author y
 * @create 2019/3/12
 */
abstract class XLoadMoreView(context: Context, layoutId: Int) : FrameLayout(context) {

    companion object {
        const val NORMAL = -1
        const val LOAD = 0
        const val SUCCESS = 1
        const val NOMORE = 2
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
                NOMORE -> onNoMore()
                SUCCESS -> onSuccess()
                ERROR -> onError()
                NORMAL -> onNormal()
            }
            field = state
        }

    init {
        addView(loadMoreView)
        layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
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