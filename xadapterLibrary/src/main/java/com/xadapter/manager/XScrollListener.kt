package com.xadapter.manager

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * by y on 2016/11/15
 */

class XScrollListener(private val scrollBottom: XScrollBottom) : RecyclerView.OnScrollListener() {

    private var layoutManagerType: LayoutManagerType? = null
    private lateinit var lastPositions: IntArray
    private var lastVisibleItemPosition: Int = 0
    var scrollItemCount: Int = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager
        if (layoutManagerType == null) {
            layoutManagerType = when (layoutManager) {
                is GridLayoutManager -> LayoutManagerType.GRID
                is LinearLayoutManager -> LayoutManagerType.LINEAR
                is StaggeredGridLayoutManager -> LayoutManagerType.STAGGERED_GRID
                else -> throw RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager")
            }
        }
        when (layoutManagerType) {
            XScrollListener.LayoutManagerType.LINEAR -> lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            XScrollListener.LayoutManagerType.GRID -> lastVisibleItemPosition = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            XScrollListener.LayoutManagerType.STAGGERED_GRID -> {
                val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions)
                lastVisibleItemPosition = findMax(lastPositions)
            }
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val layoutManager = recyclerView.layoutManager
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= totalItemCount - scrollItemCount) {
            scrollBottom.onScrollBottom()
        }
    }

    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }

    private enum class LayoutManagerType {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    interface XScrollBottom {
        fun onScrollBottom()
    }
}
