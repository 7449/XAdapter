package com.xadapter.manager

import com.google.android.material.appbar.AppBarLayout


/**
 * by y on 2016/11/17
 */

abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    private var mCurrentState = IDLE

    companion object {
        const val EXPANDED = 0
        const val COLLAPSED = 1
        const val IDLE = 2
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
        when {
            i == 0 -> {
                if (mCurrentState != EXPANDED) {
                    onStateChanged(appBarLayout, EXPANDED)
                }
                mCurrentState = EXPANDED
            }
            Math.abs(i) >= appBarLayout.totalScrollRange -> {
                if (mCurrentState != COLLAPSED) {
                    onStateChanged(appBarLayout, COLLAPSED)
                }
                mCurrentState = COLLAPSED
            }
            else -> {
                if (mCurrentState != IDLE) {
                    onStateChanged(appBarLayout, IDLE)
                }
                mCurrentState = IDLE
            }
        }
    }

    protected abstract fun onStateChanged(appBarLayout: AppBarLayout, state: Int)
}


