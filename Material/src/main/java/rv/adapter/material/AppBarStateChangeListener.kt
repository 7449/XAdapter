package rv.adapter.material

import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlin.math.abs

class AppBarStateChangeListener : OnOffsetChangedListener {

    companion object {
        const val EXPANDED = 0
        const val COLLAPSED = 1
        const val IDLE = 2
    }

    var currentState = IDLE
        private set

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        currentState = when {
            verticalOffset == 0 -> EXPANDED
            abs(verticalOffset) >= appBarLayout.totalScrollRange -> COLLAPSED
            else -> IDLE
        }
    }

}