package rv.adapter.core.listener

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import rv.adapter.layout.XRefreshStatus

/**
 * by y on 2016/11/15
 */
internal class XTouchListener(
    private val refreshStatus: () -> XRefreshStatus?,
    private val appBar: () -> Boolean,
    private val loadMoreCallback: () -> Boolean,
    private val refreshInterface: () -> Unit
) : View.OnTouchListener {

    companion object {
        private const val DAMP = 3
    }

    private var rawY = -1f

    private val isTop: Boolean
        get() = refreshStatus.invoke()?.refreshParent != null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val refreshView = refreshStatus.invoke()
        if (refreshView == null || refreshView.isRefresh || loadMoreCallback.invoke()) {
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
                if (isTop && appBar.invoke()) {
                    refreshView.onChangedHeight((deltaY / DAMP).toInt())
                    if (refreshView.visibleHeight > 0 && refreshView.isBegin) {
                        return true
                    }
                }
            }
            else -> {
                rawY = -1f
                if (isTop && appBar.invoke() && refreshView.isReleaseAction) {
                    refreshInterface()
                }
            }
        }
        return false
    }

}
