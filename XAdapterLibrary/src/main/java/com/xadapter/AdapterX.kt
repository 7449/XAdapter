package com.xadapter

import com.xadapter.adapter.XDataBindingAdapter
import com.xadapter.adapter.XMultiAdapter
import com.xadapter.listener.XMultiCallBack

/**
 * @author y
 * @create 2019/3/12
 */
fun <T> XDataBindingAdapterExecutePendingBindingsFactory(variableId: Int): XDataBindingAdapter<T> = XDataBindingAdapter(variableId, true)

fun <T> XDataBindingAdapterFactory(variableId: Int): XDataBindingAdapter<T> = XDataBindingAdapter(variableId, false)

fun <T : XMultiCallBack> XMultiAdapter(): XMultiAdapter<T> = com.xadapter.adapter.XMultiAdapter(ArrayList())