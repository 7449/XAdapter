package com.xadapter.databinding

fun <T> XDataBindingAdapterExecutePendingBindingsFactory(variableId: Int): XDataBindingAdapter<T> = XDataBindingAdapter(variableId, true)

fun <T> XDataBindingAdapterFactory(variableId: Int): XDataBindingAdapter<T> = XDataBindingAdapter(variableId, false)