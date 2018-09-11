package com.xadapter.manager

import android.support.design.widget.AppBarLayout

/**
 * by y on 2016/11/17
 */

abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    private var mCurrentState = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
        when {
            i == 0 -> {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED)
                }
                mCurrentState = State.EXPANDED
            }
            Math.abs(i) >= appBarLayout.totalScrollRange -> {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED)
                }
                mCurrentState = State.COLLAPSED
            }
            else -> {
                if (mCurrentState != State.IDLE) {
                    onStateChanged(appBarLayout, State.IDLE)
                }
                mCurrentState = State.IDLE
            }
        }
    }

    protected abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State)

    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }
}


