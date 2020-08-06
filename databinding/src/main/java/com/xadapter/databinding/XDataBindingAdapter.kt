package com.xadapter.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import com.xadapter.adapter.XAdapter
import com.xadapter.vh.XViewHolder

/**
 * @author y
 * @create 2018/12/25
 */
open class XDataBindingAdapter<T>(private val variableId: Int, private val executePendingBindings: Boolean = true) : XAdapter<T>() {

    var mData: ObservableArrayList<T> = ObservableArrayList()

    override var dataContainer: MutableList<T>
        get() = mData
        set(value) {
            mData.addAll(value)
        }

    override fun defaultViewHolder(parent: ViewGroup): XViewHolder {
        return XDataBindingHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), itemLayoutId, parent, false)).viewHolderClick().viewHolderLongClick()
    }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) {
        if (getItemViewType(position) != TYPE_ITEM) {
            return
        }
        val pos = currentItemPosition(position)
        holder as XDataBindingHolder
        holder.viewDataBinding.setVariable(variableId, dataContainer[pos])
        if (executePendingBindings) {
            holder.viewDataBinding.executePendingBindings()
        }
    }
}