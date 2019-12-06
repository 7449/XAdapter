package com.adapter.example

import com.google.android.material.appbar.AppBarLayout
import com.xadapter.adapter.XAdapter
import com.xadapter.material.AppBarStateChangeListener

fun <T> XAdapter<T>.supportAppbar(appBarLayout: AppBarLayout) = also {
    val appBarStateChangeListener = AppBarStateChangeListener()
    appBarLayout.addOnOffsetChangedListener(appBarStateChangeListener)
    xAppbarCallback = { appBarStateChangeListener.currentState == AppBarStateChangeListener.EXPANDED }
}