package rv.adapter.core.listener

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import rv.adapter.layout.XRefreshStatus

/**
 * by y on 2016/11/15
 */
internal class XTouchListener(
    private val refreshStatus: XRefreshStatus,
    private val appBarCallBack: () -> Boolean,
    private val loadMoreCallback: () -> Boolean,
    private val refreshInterface: () -> Unit
) : View.OnTouchListener {

    companion object {
        private const val DAMP = 3
    }

    private var rawY = -1f

    private val isTop: Boolean
        get() = refreshStatus.refreshParent != null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (refreshStatus.isRefresh || loadMoreCallback.invoke()) {
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
                    refreshStatus.onChangedHeight((deltaY / DAMP).toInt())
                    if (refreshStatus.visibleHeight > 0 && refreshStatus.isBegin) {
                        return true
                    }
                }
            }
            else -> {
                rawY = -1f
                if (isTop && appBarCallBack.invoke() && refreshStatus.isReleaseAction) {
                    refreshInterface()
                }
            }
        }
        return false
    }

}
