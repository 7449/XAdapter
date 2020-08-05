package com.xadapter.refresh

import android.view.View

interface Callback {
    companion object {
        const val NULL = -1
        const val NORMAL = 0
        const val READY = 1
        const val REFRESH = 2
        const val SUCCESS = 3
        const val ERROR = 4
        const val LOAD = 5
        const val NO_MORE = 6
    }

    val xRootView: View
}