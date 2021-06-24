@file:Suppress("UNCHECKED_CAST")

package rv.adapter.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import rv.adapter.core.XAdapter
import rv.adapter.data.binding.XDataBindingAdapter
import rv.adapter.multiple.XMultiAdapter
import rv.adapter.multiple.XMultiCallBack
import rv.adapter.view.binding.XViewBindingAdapter

/**
 * get [XAdapter]
 */
fun <T> RecyclerView.xAdapter() = adapter as XAdapter<T>

/**
 * get [XDataBindingAdapter]
 */
fun <T> RecyclerView.dataBindingAdapter() = adapter as XDataBindingAdapter<T>

/**
 * get [XViewBindingAdapter]
 */
fun <T, VB : ViewBinding> RecyclerView.viewBindingAdapter() = adapter as XViewBindingAdapter<T, VB>

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
fun <T> RecyclerView.attachDataBindingAdapter(variableId: Int) =
    attachDataBindingAdapter(XDataBindingAdapter<T>(variableId))

/**
 * Init [XDataBindingAdapter]
 */
fun <T> RecyclerView.attachDataBindingAdapter(adapter: XDataBindingAdapter<T>) =
    also { setAdapter(adapter) }

/**
 * Init [XMultiAdapter]
 */
fun <T : XMultiCallBack> RecyclerView.attachMultiAdapter() = attachMultiAdapter(XMultiAdapter<T>())

/**
 * Init [XMultiAdapter]
 */
fun <T : XMultiCallBack> RecyclerView.attachMultiAdapter(adapter: XMultiAdapter<T>) =
    also { setAdapter(adapter) }

/**
 * Init [XAdapter]
 */
fun <T> RecyclerView.convertAdapter() = convertAdapter(XAdapter<T>())

/**
 * Init [XAdapter]
 */
fun <T> RecyclerView.convertAdapter(adapter: XAdapter<T>) =
    also { setAdapter(adapter) }.xAdapter<T>()

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
fun <T, VB : ViewBinding> RecyclerView.convertViewBindingAdapter(adapter: XViewBindingAdapter<T, VB>) =
    also { setAdapter(adapter) }.viewBindingAdapter<T, VB>()

/**
 * Init [XMultiAdapter]
 */
fun <T : XMultiCallBack> RecyclerView.convertMultiAdapter() =
    convertMultiAdapter(XMultiAdapter<T>())

/**
 * Init [XMultiAdapter]
 */
fun <T : XMultiCallBack> RecyclerView.convertMultiAdapter(adapter: XMultiAdapter<T>) =
    also { setAdapter(adapter) }.multiAdapter<T>()

/**
 * check [XAdapter]
 */
fun RecyclerView.checkAdapter(): Boolean = adapter != null && adapter is XAdapter<*>

/**
 * check [XMultiAdapter]
 */
fun RecyclerView.checkMultiAdapter(): Boolean = adapter != null && adapter is XMultiAdapter<*>
