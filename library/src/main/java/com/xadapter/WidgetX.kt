@file:Suppress("LeakingThis")

package com.xadapter

import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.widget.FrameLayout

abstract class XRefreshView(context: Context, layoutId: Int) : FrameLayout(context) {

    companion object {
        const val NORMAL = 0
        const val READY = 1
        const val REFRESH = 2
        const val SUCCESS = 3
        const val ERROR = 4
    }

    private var refreshView: View = View.inflate(context, layoutId, null)
    private var mMeasuredHeight: Int = 0
    private val animator: ValueAnimator = ValueAnimator.ofInt().setDuration(300)

    var state: Int = NORMAL
        set(state) {
            if (state == field) {
                return
            }
            onStart()
            when (state) {
                NORMAL -> onNormal()
                READY -> onReady()
                REFRESH -> onRefresh()
                SUCCESS -> onSuccess()
                ERROR -> onError()
            }
            field = state
        }

    var visibleHeight: Int
        get() {
            return refreshView.layoutParams.height
        }
        private set(height) {
            if (height == 0) {
                state = NORMAL
            }
            val lp = refreshView.layoutParams
            lp.height = if (height < 0) 0 else height
            refreshView.layoutParams = lp
        }

    init {
        addView(refreshView, LayoutParams(LayoutParams.MATCH_PARENT, 0))
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        initView()
        measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        mMeasuredHeight = measuredHeight
        animator.addUpdateListener { animation -> visibleHeight = animation.animatedValue as Int }
    }

    fun refreshState(mState: Int) {
        state = mState
        postDelayed({ smoothScrollTo(0) }, 200)
    }

    fun onMove(delta: Float) {
        if (visibleHeight > 0 || delta > 0) {
            visibleHeight += delta.toInt()
            if (state < REFRESH) {
                state = if (visibleHeight > mMeasuredHeight) READY else NORMAL
            }
        }
    }

    fun releaseAction(): Boolean {
        var isOnRefresh = false
        if (visibleHeight > mMeasuredHeight && state < REFRESH) {
            state = REFRESH
            isOnRefresh = true
        }
        var destHeight = 0
        if (state == REFRESH) {
            destHeight = mMeasuredHeight
        }
        smoothScrollTo(destHeight)
        return isOnRefresh
    }

    private fun smoothScrollTo(destHeight: Int) {
        animator.setIntValues(visibleHeight, destHeight)
        animator.start()
    }

    protected abstract fun initView()
    protected abstract fun onStart()
    protected abstract fun onNormal()
    protected abstract fun onReady()
    protected abstract fun onRefresh()
    protected abstract fun onSuccess()
    protected abstract fun onError()

}

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