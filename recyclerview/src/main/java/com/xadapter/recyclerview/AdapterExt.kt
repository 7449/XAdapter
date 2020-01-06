@file:Suppress("UNCHECKED_CAST")

package com.xadapter.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.xadapter.adapter.XAdapter
import com.xadapter.databinding.XDataBindingAdapter
import com.xadapter.databinding.XDataBindingAdapterExecutePendingBindingsFactory
import com.xadapter.multi.XMultiAdapter
import com.xadapter.multi.XMultiCallBack

/**
 * get [XAdapter]
 */
fun <T> RecyclerView.adapter() = adapter as XAdapter<T>

/**
 * get [XDataBindingAdapter]
 */
fun <T> RecyclerView.dataBindingAdapter() = adapter as XDataBindingAdapter<T>

/**
 * get [XMultiAdapter]
 */
fun <T : XMultiCallBack> RecyclerView.multiAdapter() = adapter as XMultiAdapter<T>

/**
 * Init [XAdapter]
 */
fun <T> RecyclerView.attachAdapter() = attachAdapter(XAdapter<T>())

/**
 * Init [XAdapter]
 */
fun <T> RecyclerView.attachAdapter(adapter: XAdapter<T>) = also { setAdapter(adapter) }

/**
 * Init [XDataBindingAdapter]
 */
fun <T> RecyclerView.attachDataBindingAdapter(variableId: Int) = attachDataBindingAdapter(XDataBindingAdapterExecutePendingBindingsFactory<T>(variableId))

/**
 * Init [XDataBindingAdapter]
 */
fun <T> RecyclerView.attachDataBindingAdapter(adapter: XDataBindingAdapter<T>) = also { setAdapter(adapter) }

/**
 * Init [XMultiAdapter]
 */
fun <T : XMultiCallBack> RecyclerView.attachMultiAdapter(array: ArrayList<T> = ArrayList()) = attachMultiAdapter(XMultiAdapter(array))

/**
 * Init [XMultiAdapter]
 */
fun <T : XMultiCallBack> RecyclerView.attachMultiAdapter(adapter: XMultiAdapter<T>) = also { setAdapter(adapter) }

/**
 * check [XAdapter]
 */
fun RecyclerView.checkAdapter(): Boolean = adapter != null && adapter is XAdapter<*>

/**
 * check [XMultiAdapter]
 */
fun RecyclerView.checkMultiAdapter(): Boolean = adapter != null && adapter is XMultiAdapter<*>
