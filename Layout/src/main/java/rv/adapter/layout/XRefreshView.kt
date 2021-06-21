package rv.adapter.layout

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewParent
import android.widget.FrameLayout
import kotlin.math.max

abstract class XRefreshView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), XRefreshStatus {

    private val animator: ValueAnimator = ValueAnimator.ofInt().setDuration(300)
    private val childView by lazy { getChildAt(0) }
    private val initHeight by lazy {
        measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        measuredHeight
    }

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        animator.addUpdateListener { animation -> onChangedHeights(animation.animatedValue as Int) }
    }

    override var status: LayoutStatus = LayoutStatus.NORMAL

    override val visibleHeight: Int
        get() = childView.layoutParams.height

    override val refreshParent: ViewParent?
        get() = parent

    override val xRootView: View
        get() = this

    override val isReleaseAction: Boolean
        get() {
            if (isReady) {
                onChanged(LayoutStatus.REFRESH)
            }
            smoothScrollTo(if (isRefresh) initHeight else 0)
            return isRefresh
        }

    override fun onStartRefresh() {
        onChanged(LayoutStatus.REFRESH)
        animator.setIntValues(1, initHeight)
        animator.start()
    }

    override fun onChanged(status: LayoutStatus) {
        super.onChanged(status)
        if (isDone) {
            postDelayed({ smoothScrollTo(0) }, 200)
        }
    }

    override fun onChangedHeight(height: Int) {
        if (visibleHeight < 0 || height < 0) {
            return
        }
        onChangedHeights(visibleHeight + height)
        if (isBegin) {
            onChanged(if (visibleHeight > initHeight) LayoutStatus.READY else LayoutStatus.NORMAL)
        }
    }

    private fun onChangedHeights(height: Int) {
        val lastValue = max(0, height)
        if (lastValue == 0) {
            onChanged(LayoutStatus.NORMAL)
        }
        val lp = childView.layoutParams
        lp.height = lastValue
        childView.layoutParams = lp
    }

    private fun smoothScrollTo(destHeight: Int) {
        animator.setIntValues(visibleHeight, destHeight)
        animator.start()
    }

}
