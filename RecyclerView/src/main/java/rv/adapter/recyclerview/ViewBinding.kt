@file:Suppress("UNCHECKED_CAST")

package rv.adapter.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import rv.adapter.view.binding.XViewBindingAdapter

/**
 * get [XViewBindingAdapter]
 */
fun <T, VB : ViewBinding> RecyclerView.viewBindingAdapter() = adapter as XViewBindingAdapter<T, VB>

/**
 * Init [XViewBindingAdapter]
 */
fun <T, VB : ViewBinding> RecyclerView.convertViewBindingAdapter() =
    convertViewBindingAdapter(XViewBindingAdapter<T, VB>())

/**
 * Init [XViewBindingAdapter]
 */
fun <T, VB : ViewBinding> RecyclerView.convertViewBindingAdapter(adapter: XViewBindingAdapter<T, VB>) =
    also { setAdapter(adapter) }.viewBindingAdapter<T, VB>()

/**
 * Init [XViewBindingAdapter]
 */
fun <T, VB : ViewBinding> RecyclerView.attachViewBindingAdapter() =
    attachViewBindingAdapter(XViewBindingAdapter<T, VB>())

/**
 * Init [XViewBindingAdapter]
 */
fun <T, VB : ViewBinding> RecyclerView.attachViewBindingAdapter(adapter: XViewBindingAdapter<T, VB>) =
    also { setAdapter(adapter) }