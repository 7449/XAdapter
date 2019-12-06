package com.xadapter.listener

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import com.xadapter.refresh.XRefreshView

/**
 * by y on 2016/11/15
 */
internal class XTouchListener(
        private val appBarCallBack: () -> Boolean,
        private val loadMoreCallback: () -> Boolean,
        private val refreshView: XRefreshView,
        private val refreshInterface: () -> Unit) : View.OnTouchListener {

    companion object {
        private const val DAMP = 3
    }

    private var rawY = -1f

    private val isTop: Boolean
        get() = refreshView.parent != null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (refreshView.state == XRefreshView.REFRESH || loadMoreCallback.invoke()) {
            return false
        }
        if (rawY == -1f) {
            rawY = motionEvent.rawY
        }
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> rawY = motionEvent.rawY
            MotionEvent.ACTION_MOVE -> {
                val deltaY = motionEvent.rawY - rawY
                rawY = motionEvent.rawY
                if (isTop && appBarCallBack.invoke()) {
                    refreshView.onMove(deltaY / DAMP)
                    if (refreshView.visibleHeight > 0 && refreshView.state < XRefreshView.SUCCESS) {
                        return true
                    }
                }
            }
            else -> {
                rawY = -1f
                if (isTop && appBarCallBack.invoke()) {
                    if (refreshView.releaseAction()) {
                        refreshInterface()
                    }
                }
            }
        }
        return false
    }
}
