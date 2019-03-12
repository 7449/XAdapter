package com.xadapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.OnXItemClickListener
import com.xadapter.listener.OnXItemLongClickListener
import com.xadapter.listener.OnXMultiAdapterListener
import com.xadapter.listener.XMultiCallBack


/**
 * by y on 2017/3/9
 */

class XMultiAdapter<T : XMultiCallBack>(private val mMultiData: MutableList<T>) : RecyclerView.Adapter<XViewHolder>() {

    var onXItemClickListener: OnXItemClickListener<T>? = null
    var onXLongClickListener: OnXItemLongClickListener<T>? = null
    lateinit var onXMultiAdapterListener: OnXMultiAdapterListener<T>
    val data: List<T> get() = mMultiData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XViewHolder {
        val xViewHolder = XViewHolder(LayoutInflater.from(parent.context).inflate(onXMultiAdapterListener.multiLayoutId(viewType), parent, false))

        xViewHolder.itemView.setOnClickListener { view ->
            if (mMultiData[xViewHolder.layoutPosition].position == XMultiCallBack.NO_CLICK_POSITION) return@setOnClickListener
            onXItemClickListener?.onXItemClick(view, xViewHolder.layoutPosition, mMultiData[xViewHolder.layoutPosition])
        }
        xViewHolder.itemView.setOnLongClickListener { view ->
            if (mMultiData[xViewHolder.layoutPosition].position == XMultiCallBack.NO_CLICK_POSITION) return@setOnLongClickListener false
            return@setOnLongClickListener onXLongClickListener?.onXItemLongClick(view, xViewHolder.layoutPosition, mMultiData[xViewHolder.layoutPosition])
                    ?: false
        }
        return xViewHolder
    }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) {
        val t = getItem(position)
        onXMultiAdapterListener.onXMultiBind(holder, t, getItemViewType(position), position)
    }

    fun removeAll() {
        mMultiData.clear()
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        mMultiData.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    fun add(t: T) {
        mMultiData.add(t)
        notifyDataSetChanged()
    }

    fun addAll(t: List<T>) {
        mMultiData.addAll(t)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T {
        return mMultiData[position]
    }

    override fun getItemViewType(position: Int): Int = mMultiData[position].itemType
    override fun getItemCount(): Int = mMultiData.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return onXMultiAdapterListener.getGridLayoutManagerSpanSize(getItemViewType(position), manager, position)
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: XViewHolder) {
        super.onViewAttachedToWindow(holder)
        val layoutParams = holder.itemView.layoutParams
        if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            layoutParams.isFullSpan = onXMultiAdapterListener.getStaggeredGridLayoutManagerFullSpan(getItemViewType(holder.layoutPosition))
        }
    }
}
