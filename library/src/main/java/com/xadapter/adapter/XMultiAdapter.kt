package com.xadapter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.getItem
import com.xadapter.holder.XViewHolder
import com.xadapter.holder.superViewHolder
import com.xadapter.listener.XMultiCallBack

/**
 * by y on 2017/3/9
 */
class XMultiAdapter<T : XMultiCallBack>(val mMultiData: MutableList<T>) : XBaseAdapter<T>() {

    lateinit var itemLayoutId: ((itemViewType: Int) -> Int)

    lateinit var xMultiBind: ((holder: XViewHolder, entity: T, itemViewType: Int, position: Int) -> Unit)

    var gridLayoutManagerSpanSize: ((itemViewType: Int, manager: GridLayoutManager, position: Int) -> Int)? = null

    var staggeredGridLayoutManagerFullSpan: ((itemViewType: Int) -> Boolean)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XViewHolder = superViewHolder(parent, itemLayoutId(viewType)).apply { multiViewHolderClick(this@XMultiAdapter).multiViewHolderLongClick(this@XMultiAdapter) }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) = xMultiBind(holder, getItem(position), getItemViewType(position), position)

    override fun getItemViewType(position: Int): Int = getItem(position).itemType

    override fun getItemCount(): Int = mMultiData.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) = internalOnAttachedToRecyclerView(recyclerView)

    override fun onViewAttachedToWindow(holder: XViewHolder) = internalOnViewAttachedToWindow(holder)

    private fun <T : XMultiCallBack> XViewHolder.multiViewHolderClick(xMultiAdapter: XMultiAdapter<T>): XViewHolder {
        itemView.setOnClickListener {
            if (xMultiAdapter.mMultiData[layoutPosition].position == XMultiCallBack.NO_CLICK_POSITION) return@setOnClickListener
            xMultiAdapter.onXItemClickListener?.invoke(it, layoutPosition, xMultiAdapter.mMultiData[layoutPosition])
        }
        return this
    }

    private fun <T : XMultiCallBack> XViewHolder.multiViewHolderLongClick(xMultiAdapter: XMultiAdapter<T>) {
        itemView.setOnLongClickListener {
            if (xMultiAdapter.mMultiData[layoutPosition].position == XMultiCallBack.NO_CLICK_POSITION) return@setOnLongClickListener false
            return@setOnLongClickListener xMultiAdapter.onXItemLongClickListener?.invoke(itemView, layoutPosition, xMultiAdapter.mMultiData[layoutPosition])
                    ?: false
        }
    }
}
