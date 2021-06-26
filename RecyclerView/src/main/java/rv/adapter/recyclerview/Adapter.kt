@file:Suppress("UNCHECKED_CAST")

package rv.adapter.recyclerview

import androidx.recyclerview.widget.RecyclerView
import rv.adapter.core.XAdapter

/**
 * get [XAdapter]
 */
fun <T> RecyclerView.xAdapter() = adapter as XAdapter<T>

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
 * Init [XAdapter]
 */
fun <T> RecyclerView.attachXAdapter() = attachXAdapter(XAdapter<T>())

/**
 * Init [XAdapter]
 */
fun <T> RecyclerView.attachXAdapter(adapter: XAdapter<T>) = also { setAdapter(adapter) }
