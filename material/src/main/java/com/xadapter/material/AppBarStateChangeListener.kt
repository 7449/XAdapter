package com.xadapter.material

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

/**
 * by y on 2016/11/17
 */
class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    var currentState = IDLE

    companion object {
        const val EXPANDED = 0
        const val COLLAPSED = 1
        const val IDLE = 2
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
        currentState = when {
            i == 0 -> EXPANDED
            abs(i) >= appBarLayout.totalScrollRange -> COLLAPSED
            else -> IDLE
        }
    }
}


