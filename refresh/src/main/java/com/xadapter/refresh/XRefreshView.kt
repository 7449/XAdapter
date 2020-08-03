package com.xadapter.refresh

import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.widget.FrameLayout

abstract class XRefreshView(context: Context, layoutId: Int) : FrameLayout(context), XRefreshCallback {

    private val refreshView: View = View.inflate(context, layoutId, null)
    private val animator: ValueAnimator = ValueAnimator.ofInt().setDuration(300)
    private var mMeasuredHeight: Int = 0
    private var state: Int = Callback.NORMAL

    override fun onChange(state: Int) {
        if (state == this.state) {
            return
        }
        onStart()
        when (state) {
            Callback.NORMAL -> onNormal()
            Callback.READY -> onReady()
            Callback.REFRESH -> onRefresh()
            Callback.SUCCESS -> onSuccess()
            Callback.ERROR -> onError()
        }
        this.state = state
    }

    override val currentState: Int
        get() = state

    var visibleHeight: Int
        get() {
            return refreshView.layoutParams.height
        }
        private set(height) {
            if (height == 0) {
                onChange(Callback.NORMAL)
            }
            val lp = refreshView.layoutParams
            lp.height = if (height < 0) 0 else height
            refreshView.layoutParams = lp
        }

    init {
        addView(refreshView, LayoutParams(LayoutParams.MATCH_PARENT, 0))
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        mMeasuredHeight = measuredHeight
        animator.addUpdateListener { animation -> visibleHeight = animation.animatedValue as Int }
    }

    fun refreshState(mState: Int) {
        onChange(mState)
        postDelayed({ smoothScrollTo(0) }, 200)
    }

    fun onMove(delta: Float) {
        if (visibleHeight > 0 || delta > 0) {
            visibleHeight += delta.toInt()
            if (state < Callback.REFRESH) {
                onChange(if (visibleHeight > mMeasuredHeight) Callback.READY else Callback.NORMAL)
            }
        }
    }

    fun releaseAction(): Boolean {
        var isOnRefresh = false
        if (visibleHeight > mMeasuredHeight && state < Callback.REFRESH) {
            onChange(Callback.REFRESH)
            isOnRefresh = true
        }
        var destHeight = 0
        if (isRefresh) {
            destHeight = mMeasuredHeight
        }
        smoothScrollTo(destHeight)
        return isOnRefresh
    }

    private fun smoothScrollTo(destHeight: Int) {
        animator.setIntValues(visibleHeight, destHeight)
        animator.start()
    }
}
