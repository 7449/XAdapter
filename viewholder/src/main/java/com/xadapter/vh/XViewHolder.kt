@file:Suppress("UNCHECKED_CAST")

package com.xadapter.vh

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

open class XViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

fun superViewHolder(parent: ViewGroup, layoutId: Int) = XViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

fun superViewHolder(view: View) = XViewHolder(view)

fun <T : View> XViewHolder.getView(@IdRes id: Int): T {
    var viewSparseArray: SparseArray<View>? = itemView.tag as SparseArray<View>?
    if (null == viewSparseArray) {
        viewSparseArray = SparseArray()
        itemView.tag = viewSparseArray
    }
    var childView: View? = viewSparseArray.get(id)
    if (null == childView) {
        childView = itemView.findViewById(id)
        viewSparseArray.put(id, childView)
    }
    return childView as T
}