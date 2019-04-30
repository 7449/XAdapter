package com.xadapter.manager

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


/**
 * by y on 2016/11/15
 */

internal class XScrollListener(private val scrollBottom: () -> Unit) : RecyclerView.OnScrollListener() {

    companion object {
        const val NO_MANAGER = -1
        const val LINEAR = 0
        const val GRID = 1
        const val STAGGERED_GRID = 2
    }

    private var layoutManagerType: Int = LINEAR
    private lateinit var lastPositions: IntArray
    private var lastVisibleItemPosition: Int = 0
    var scrollItemCount: Int = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager
        if (layoutManagerType == NO_MANAGER) {
            layoutManagerType = when (layoutManager) {
                is GridLayoutManager -> GRID
                is LinearLayoutManager -> LINEAR
                is StaggeredGridLayoutManager -> STAGGERED_GRID
                else -> throw RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager")
            }
        }
        when (layoutManagerType) {
            LINEAR -> lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            GRID -> lastVisibleItemPosition = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            STAGGERED_GRID -> {
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
            scrollBottom()
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
}
