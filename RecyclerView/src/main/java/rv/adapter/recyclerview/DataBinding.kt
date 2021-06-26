@file:Suppress("UNCHECKED_CAST")

package rv.adapter.recyclerview

import androidx.recyclerview.widget.RecyclerView
import rv.adapter.data.binding.XDataBindingAdapter

/**
 * get [XDataBindingAdapter]
 */
fun <T> RecyclerView.dataBindingAdapter() = adapter as XDataBindingAdapter<T>

/**
 * Init [XDataBindingAdapter]
 */
fun <T> RecyclerView.convertDataBindingAdapter(variableId: Int) =
    convertDataBindingAdapter(XDataBindingAdapter<T>(variableId))

/**
 * Init [XDataBindingAdapter]
 */
fun <T> RecyclerView.convertDataBindingAdapter(adapter: XDataBindingAdapter<T>) =
    also { setAdapter(adapter) }.dataBindingAdapter<T>()

/**
 * Init [XDataBindingAdapter]
 */
fun <T> RecyclerView.attachDataBindingAdapter(variableId: Int) =
    attachDataBindingAdapter(XDataBindingAdapter<T>(variableId))

/**
 * Init [XDataBindingAdapter]
 */
fun <T> RecyclerView.attachDataBindingAdapter(adapter: XDataBindingAdapter<T>) =
    also { setAdapter(adapter) }
