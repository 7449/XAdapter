package rv.adapter.layout

import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.ViewParent
import android.widget.FrameLayout

abstract class XRefreshView(context: Context, layoutId: Int) : FrameLayout(context),
    XRefreshCallback {

    private val refreshView: View = View.inflate(context, layoutId, null)
    private val animator: ValueAnimator = ValueAnimator.ofInt().setDuration(300)
    private var initHeight: Int = 0
    private var state: Int = Callback.NORMAL

    override val currentState: Int
        get() = state

    override val visibleHeight: Int
        get() = refreshView.layoutParams.height

    override val refreshParent: ViewParent?
        get() = refreshView.parent

    override val xRootView: View
        get() = this

    override val isReleaseAction: Boolean
        get() {
            if (isReady) {
                onChange(Callback.REFRESH)
            }
            smoothScrollTo(if (isRefresh) initHeight else 0)
            return isRefresh
        }

    override fun onChange(state: Int) {
        if (state == this.state) {
            return
        }
        when (state) {
            Callback.NORMAL -> onNormal()
            Callback.READY -> onReady()
            Callback.REFRESH -> onRefresh()
            Callback.SUCCESS -> onSuccess()
            Callback.ERROR -> onError()
        }
        this.state = state
        if (isDone) {
            postDelayed({ smoothScrollTo(0) }, 200)
        }
    }

    override fun onChangeHeight(value: Int) {
        val lastValue = if (value <= 0) 0 else value
        if (lastValue == 0) {
            onChange(Callback.NORMAL)
        }
        val lp = refreshView.layoutParams
        lp.height = lastValue
        refreshView.layoutParams = refreshView.layoutParams
    }

    override fun onChangeMoveHeight(value: Int) {
        if (visibleHeight < 0 && value < 0) {
            return
        }
        onChangeHeight(visibleHeight + value)
        if (isBegin) {
            onChange(if (visibleHeight > initHeight) Callback.READY else Callback.NORMAL)
        }
    }

    private fun smoothScrollTo(destHeight: Int) {
        animator.setIntValues(visibleHeight, destHeight)
        animator.start()
    }

    init {
        @Suppress("LeakingThis")
        addView(refreshView, LayoutParams(LayoutParams.MATCH_PARENT, 0))
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        initHeight = measuredHeight
        animator.addUpdateListener { animation -> onChangeHeight(animation.animatedValue as Int) }
    }

}
