package com.xadapter

import androidx.recyclerview.widget.RecyclerView
import com.xadapter.adapter.XDataBindingAdapter

@Suppress("UNCHECKED_CAST")
fun <T> RecyclerView.dataBindingAdapter() = adapter as XDataBindingAdapter<T>

fun <T> RecyclerView.attachDataBindingAdapter(adapter: XDataBindingAdapter<T>) = also { setAdapter(adapter) }.dataBindingAdapter<T>()

//fun <T> RecyclerView.oservableArrayList() = dataBindingAdapter<T>().observableArrayList()
//
//fun <T> RecyclerView.dataBindingAdd(item: T) = dataBindingAdapter<T>().add(item)
//
//fun <T> RecyclerView.dataBindingAddAll(data: List<T>) = dataBindingAdapter<T>().addAll(data)

fun <T> XDataBindingAdapterExecutePendingBindingsFactory(variableId: Int): XDataBindingAdapter<T> = XDataBindingAdapter(variableId, true)

fun <T> XDataBindingAdapterFactory(variableId: Int): XDataBindingAdapter<T> = XDataBindingAdapter(variableId, false)