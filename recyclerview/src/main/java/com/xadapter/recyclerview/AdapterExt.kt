@file:Suppress("UNCHECKED_CAST")

package com.xadapter.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.xadapter.adapter.XAdapter
import com.xadapter.databinding.XDataBindingAdapter
import com.xadapter.multi.XMultiAdapter
import com.xadapter.multi.XMultiCallBack

/**
 * get [XAdapter]
 */
fun <T> RecyclerView.xAdapter() = adapter as XAdapter<T>

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
fun <T> RecyclerView.attachXAdapter() = attachXAdapter(XAdapter<T>())

/**
 * Init [XAdapter]
 */
fun <T> RecyclerView.attachXAdapter(adapter: XAdapter<T>) = also { setAdapter(adapter) }

/**
 * Init [XDataBindingAdapter]
 */
fun <T> RecyclerView.attachDataBindingAdapter(variableId: Int) = attachDataBindingAdapter(XDataBindingAdapter<T>(variableId))

/**
 * Init [XDataBindingAdapter]
 */
fun <T> RecyclerView.attachDataBindingAdapter(adapter: XDataBindingAdapter<T>) = also { setAdapter(adapter) }

/**
 * Init [XMultiAdapter]
 */
fun <T : XMultiCallBack> RecyclerView.attachMultiAdapter() = attachMultiAdapter(XMultiAdapter<T>())

/**
 * Init [XMultiAdapter]
 */
fun <T : XMultiCallBack> RecyclerView.attachMultiAdapter(adapter: XMultiAdapter<T>) = also { setAdapter(adapter) }

/**
 * Init [XAdapter]
 */
fun <T> RecyclerView.convertAdapter() = convertAdapter(XAdapter<T>())

/**
 * Init [XAdapter]
 */
fun <T> RecyclerView.convertAdapter(adapter: XAdapter<T>) = also { setAdapter(adapter) }.xAdapter<T>()

/**
 * Init [XDataBindingAdapter]
 */
fun <T> RecyclerView.convertDataBindingAdapter(variableId: Int) = convertDataBindingAdapter(XDataBindingAdapter<T>(variableId))

/**
 * Init [XDataBindingAdapter]
 */
fun <T> RecyclerView.convertDataBindingAdapter(adapter: XDataBindingAdapter<T>) = also { setAdapter(adapter) }.dataBindingAdapter<T>()

/**
 * Init [XMultiAdapter]
 */
fun <T : XMultiCallBack> RecyclerView.convertMultiAdapter() = convertMultiAdapter(XMultiAdapter<T>())

/**
 * Init [XMultiAdapter]
 */
fun <T : XMultiCallBack> RecyclerView.convertMultiAdapter(adapter: XMultiAdapter<T>) = also { setAdapter(adapter) }.multiAdapter<T>()

/**
 * check [XAdapter]
 */
fun RecyclerView.checkAdapter(): Boolean = adapter != null && adapter is XAdapter<*>

/**
 * check [XMultiAdapter]
 */
fun RecyclerView.checkMultiAdapter(): Boolean = adapter != null && adapter is XMultiAdapter<*>
