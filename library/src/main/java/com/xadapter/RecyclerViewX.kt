package com.xadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.adapter.XDataBindingAdapter
import com.xadapter.adapter.XMultiAdapter
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.listener.XMultiCallBack

/**
 * @author y
 * @create 2019/3/18
 */

@Suppress("UNCHECKED_CAST")
fun <T : XMultiCallBack> RecyclerView.multiAdapter() = adapter as XMultiAdapter<T>

fun <T : XMultiCallBack> RecyclerView.multiRemoveAll() = apply { adapter?.let { multiAdapter<T>().removeAll() } }

fun <T : XMultiCallBack> RecyclerView.multiRemove(position: Int) = apply { adapter?.let { multiAdapter<T>().remove(position) } }

fun <T : XMultiCallBack> RecyclerView.multiAddAll(entity: List<T>) = apply { adapter?.let { multiAdapter<T>().addAll(entity) } }

fun <T : XMultiCallBack> RecyclerView.multiAdd(item: T) = apply { adapter?.let { multiAdapter<T>().add(item) } }

fun <T : XMultiCallBack> RecyclerView.multiItem(position: Int) = apply { adapter?.let { multiAdapter<T>().getItem(position) } }


@Suppress("UNCHECKED_CAST")
fun <T> RecyclerView.dataBindingAdapter() = adapter as XDataBindingAdapter<T>

fun <T> RecyclerView.oservableArrayList() = apply { adapter?.let { dataBindingAdapter<T>().observableArrayList() } }

fun <T> RecyclerView.dataBindingAdd(item: T) = apply { adapter?.let { dataBindingAdapter<T>().add(item) } }

fun <T> RecyclerView.dataBindingAddAll(data: List<T>) = apply { adapter?.let { dataBindingAdapter<T>().addAll(data) } }

@Suppress("UNCHECKED_CAST")
fun <T> RecyclerView.defaultAdapter() = adapter as XRecyclerViewAdapter<T>

fun <T> RecyclerView.addHeader(view: View) = apply { adapter?.let { defaultAdapter<T>().addHeaderView(view) } }

fun <T> RecyclerView.removeHeader(view: View) = apply { adapter?.let { defaultAdapter<T>().removeHeader(view) } }

fun <T> RecyclerView.removeHeader(index: Int) = apply { adapter?.let { defaultAdapter<T>().removeHeader(index) } }

fun <T> RecyclerView.addFooter(view: View) = apply { adapter?.let { defaultAdapter<T>().addFooterView(view) } }

fun <T> RecyclerView.removeFooter(view: View) = apply { adapter?.let { defaultAdapter<T>().removeFooter(view) } }

fun <T> RecyclerView.removeFooter(index: Int) = apply { adapter?.let { defaultAdapter<T>().removeFooter(index) } }

fun <T> RecyclerView.addAll(data: List<T>) = apply { adapter?.let { defaultAdapter<T>().addAll(data) } }

fun <T> RecyclerView.add(item: T) = apply { adapter?.let { defaultAdapter<T>().add(item) } }

fun <T> RecyclerView.removeAll(view: View) = apply { adapter?.let { defaultAdapter<T>().removeAll() } }

fun <T> RecyclerView.remove(position: Int) = apply { adapter?.let { defaultAdapter<T>().remove(position) } }

fun <T> RecyclerView.previousItem(position: Int) = apply { adapter?.let { defaultAdapter<T>().previousItem(position) } }

fun <T> RecyclerView.getItem(position: Int) = apply { adapter?.let { defaultAdapter<T>().getItem(position) } }

fun <T> RecyclerView.removeAllNoItemView() = apply { adapter?.let { defaultAdapter<T>().removeAllNoItemView() } }
