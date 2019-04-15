package com.xadapter.manager

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


/**
 * by y on 2016/11/15
 */

class XScrollListener(private val scrollBottom: (() -> Unit)) : RecyclerView.OnScrollListener() {

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
            LayoutManagerType.LINEAR -> lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            LayoutManagerType.GRID -> lastVisibleItemPosition = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            LayoutManagerType.STAGGERED_GRID -> {
                val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions)
                lastVisibleItemPosition = findMax(lastPositions)
            }
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val layoutManager = recyclerView.layoutManager
        val visibleItemCount = layoutManager?.childCount ?: 0
        val totalItemCount = layoutManager?.itemCount ?: 0
        if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= totalItemCount - scrollItemCount) {
            scrollBottom.invoke()
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
}
