package com.xadapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xadapter.OnItemClickListener
import com.xadapter.OnItemLongClickListener
import com.xadapter.OnXMultiAdapterListener
import com.xadapter.XMultiCallBack
import com.xadapter.holder.XViewHolder


/**
 * by y on 2017/3/9
 */

open class XMultiAdapter<T : XMultiCallBack>(private val mMultiData: MutableList<T>) : RecyclerView.Adapter<XViewHolder>() {

    var onItemClickListener: OnItemClickListener<T>? = null
    var onLongClickListener: OnItemLongClickListener<T>? = null
    lateinit var onXMultiAdapterListener: OnXMultiAdapterListener<T>
    val data: List<T> get() = mMultiData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XViewHolder {
        val xViewHolder = XViewHolder(LayoutInflater.from(parent.context).inflate(onXMultiAdapterListener.multiLayoutId(viewType), parent, false))
        xViewHolder.itemView.setOnClickListener { view ->
            if (mMultiData[xViewHolder.layoutPosition].position == -1) return@setOnClickListener
            onItemClickListener?.onItemClick(view,
                    if (mMultiData[xViewHolder.layoutPosition].position == -1) xViewHolder.layoutPosition
                    else mMultiData[xViewHolder.layoutPosition].position,
                    mMultiData[xViewHolder.layoutPosition])
        }
        xViewHolder.itemView.setOnLongClickListener { view ->
            if (mMultiData[xViewHolder.layoutPosition].position == -1) {
                return@setOnLongClickListener false
            }
            onLongClickListener?.onLongClick(view,
                    if (mMultiData[xViewHolder.layoutPosition].position == -1) xViewHolder.layoutPosition
                    else mMultiData[xViewHolder.layoutPosition].position,
                    mMultiData[xViewHolder.layoutPosition])
            true
        }
        return xViewHolder
    }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) {
        val t = getItem(position)
        onXMultiAdapterListener.onXMultiBind(holder, t, t.itemType, if (t.position == -1) position else t.position)
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
        if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            layoutParams.isFullSpan = onXMultiAdapterListener.getStaggeredGridLayoutManagerFullSpan(getItemViewType(holder.layoutPosition))
        }
    }
}
