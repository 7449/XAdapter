package com.xadapter.simple

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet

/**
 * by y.
 *
 *
 * Description:
 */
class SimpleRecyclerView : RecyclerView, SwipeRefreshLayout.OnRefreshListener {

    private var layoutManagerType: LayoutManagerType? = null
    private var lastPositions: IntArray? = null
    private var lastVisibleItemPosition: Int = 0

    private var onRefresh: OnRefreshListener? = null

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    enum class LayoutManagerType {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    fun setSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    fun setRefreshListener(onRefresh: OnRefreshListener) {
        this.onRefresh = onRefresh
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        val layoutManager = layoutManager
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
                if (lastPositions == null) {
                    lastPositions = IntArray(staggeredGridLayoutManager.spanCount)
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions)
                lastVisibleItemPosition = findMax(lastPositions!!)
            }
        }
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        val layoutManagerType = layoutManager
        val visibleItemCount = layoutManagerType.childCount
        val totalItemCount = layoutManagerType.itemCount
        if (visibleItemCount > 0
                && !swipeRefreshLayout.isRefreshing
                && state == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition == totalItemCount - 1) {
            onRefresh?.onLoadMore()
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

    override fun onRefresh() {
        onRefresh?.onRefresh()
    }


    interface OnRefreshListener {
        fun onRefresh()

        fun onLoadMore()
    }
}
