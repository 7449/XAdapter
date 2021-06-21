package rv.adapter.layout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

abstract class XLoadMoreView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), XLoadMoreStatus {
    override var status: LayoutStatus = LayoutStatus.NORMAL
    override val xRootView: View
        get() = this
}