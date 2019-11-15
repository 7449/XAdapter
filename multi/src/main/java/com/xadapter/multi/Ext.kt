package com.xadapter.multi

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xadapter.vh.XViewHolder

fun <T : XMultiCallBack> XMultiAdapter(): XMultiAdapter<T> = XMultiAdapter(ArrayList())

fun <T : XMultiCallBack> XMultiAdapter<T>.getItem(position: Int): T = mMultiData[position]

fun <T : XMultiCallBack> XMultiAdapter<T>.setItemLayoutId(action: (itemViewType: Int) -> Int) = also { this.itemLayoutId = action }

fun <T : XMultiCallBack> XMultiAdapter<T>.setMultiBind(action: (holder: XViewHolder, entity: T, itemViewType: Int, position: Int) -> Unit) = also { this.xMultiBind = action }

fun <T : XMultiCallBack> XMultiAdapter<T>.gridLayoutManagerSpanSize(action: (itemViewType: Int, manager: GridLayoutManager, position: Int) -> Int) = also { gridLayoutManagerSpanSize = action }

fun <T : XMultiCallBack> XMultiAdapter<T>.staggeredGridLayoutManagerFullSpan(action: (itemViewType: Int) -> Boolean) = also { staggeredGridLayoutManagerFullSpan = action }

fun <T : XMultiCallBack> XMultiAdapter<T>.setOnItemClickListener(action: (view: View, position: Int, entity: T) -> Unit) = also { onXItemClickListener = action }

fun <T : XMultiCallBack> XMultiAdapter<T>.setOnItemLongClickListener(action: (view: View, position: Int, entity: T) -> Boolean) = also { onXItemLongClickListener = action }

fun <T : XMultiCallBack> XMultiAdapter<T>.removeAll() = also { mMultiData.clear() }.notifyDataSetChanged()

fun <T : XMultiCallBack> XMultiAdapter<T>.remove(position: Int) = also { mMultiData.removeAt(position) }.also { notifyItemRemoved(position) }.notifyItemRangeChanged(position, itemCount)

fun <T : XMultiCallBack> XMultiAdapter<T>.addAll(t: List<T>) = also { mMultiData.addAll(t) }.notifyDataSetChanged()

fun <T : XMultiCallBack> XMultiAdapter<T>.add(t: T) = also { mMultiData.add(t) }.notifyDataSetChanged()

internal fun <T : XMultiCallBack> XMultiAdapter<T>.internalOnAttachedToRecyclerView(recyclerView: RecyclerView) {
    val manager = recyclerView.layoutManager
    if (manager is GridLayoutManager) {
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return gridLayoutManagerSpanSize?.invoke(getItemViewType(position), manager, position)
                        ?: 0
            }
        }
    }
}

internal fun <T : XMultiCallBack> XMultiAdapter<T>.internalOnViewAttachedToWindow(viewHolder: RecyclerView.ViewHolder) {
    val layoutParams = viewHolder.itemView.layoutParams
    if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
        layoutParams.isFullSpan = staggeredGridLayoutManagerFullSpan?.invoke(getItemViewType(viewHolder.layoutPosition))
                ?: false
    }
}