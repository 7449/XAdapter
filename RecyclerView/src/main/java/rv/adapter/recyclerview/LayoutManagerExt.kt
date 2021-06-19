package rv.adapter.recyclerview

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

fun RecyclerView.linearLayoutManager() = also { layoutManager = LinearLayoutManager(this.context) }

fun RecyclerView.gridLayoutManager(spanCount: Int = 2) =
    also { layoutManager = GridLayoutManager(this.context, spanCount) }

fun RecyclerView.staggeredGridLayoutManager(spanCount: Int, orientation: Int) =
    also { layoutManager = StaggeredGridLayoutManager(spanCount, orientation) }

fun RecyclerView.horizontalStaggeredGridLayoutManager(spanCount: Int) = also {
    layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL)
}

fun RecyclerView.orientationStaggeredGridLayoutManager(spanCount: Int) = also {
    layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
}