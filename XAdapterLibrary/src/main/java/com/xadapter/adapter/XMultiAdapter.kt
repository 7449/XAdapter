package com.xadapter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.getItem
import com.xadapter.holder.MultiViewHolderClick
import com.xadapter.holder.MultiViewHolderLongClick
import com.xadapter.holder.SuperViewHolder
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.OnXMultiAdapterListener
import com.xadapter.listener.XMultiCallBack

/**
 * by y on 2017/3/9
 */

class XMultiAdapter<T : XMultiCallBack>(val mMultiData: MutableList<T>) : XBaseAdapter<T>() {

    lateinit var onXMultiAdapterListener: OnXMultiAdapterListener<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XViewHolder = SuperViewHolder(parent, onXMultiAdapterListener.multiLayoutId(viewType)).apply { MultiViewHolderClick(this@XMultiAdapter).apply { MultiViewHolderLongClick(this@XMultiAdapter) } }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) = onXMultiAdapterListener.onXMultiBind(holder, getItem(position), getItemViewType(position), position)

    override fun getItemViewType(position: Int): Int = getItem(position).itemType

    override fun getItemCount(): Int = mMultiData.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) = internalOnAttachedToRecyclerView(recyclerView)

    override fun onViewAttachedToWindow(holder: XViewHolder) = internalOnViewAttachedToWindow(holder)

}
