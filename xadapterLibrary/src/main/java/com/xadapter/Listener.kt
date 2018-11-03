package com.xadapter

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.xadapter.holder.XViewHolder

interface OnXAdapterListener {

    /**
     * Drop-down refresh callback
     */
    fun onXRefresh()

    /**
     * The pull-up callback is loaded
     */
    fun onXLoadMore()
}

interface OnXBindListener<T> {
    fun onXBind(holder: XViewHolder, position: Int, entity: T)
}

interface OnItemClickListener<T> {
    /**
     * Callback method to be invoked when an item in this XBaseAdapter has
     * been clicked.
     *
     *
     * If you use T, in order to reduce unnecessary crashes, the proposed empty sentence processing
     *
     * @param view     The view within the XBaseAdapter that was clicked
     * @param position The position of the view in the adapter.
     * @param entity     The adapter's data
     */
    fun onItemClick(view: View, position: Int, entity: T)
}

interface OnItemLongClickListener<T> {
    /**
     * Callback method to be invoked when an item in this view has been
     * clicked and held.
     *
     *
     * If you use T, in order to reduce unnecessary crashes, the proposed empty sentence processing
     *
     * @param view     The view within the XBaseAdapter that was clicked
     * @param position The position of the view in the adapter.
     * @param entity     The adapter's data
     */
    fun onLongClick(view: View, position: Int, entity: T)
}

interface OnFooterClickListener {
    fun onXFooterClick(view: View)
}

interface OnLoadMoreRetryListener {
    fun onXLoadMoreRetry()
}

interface OnXEmptyListener {
    fun onXEmptyClick(view: View)
}

interface XMultiCallBack {

    val itemType: Int

    val position: Int

    companion object {
        const val TYPE_ITEM = -11
    }
}

interface OnXMultiAdapterListener<T> {
    fun multiLayoutId(viewItemType: Int): Int

    fun getGridLayoutManagerSpanSize(itemViewType: Int, gridManager: GridLayoutManager, position: Int): Int

    fun getStaggeredGridLayoutManagerFullSpan(itemViewType: Int): Boolean

    fun onXMultiBind(holder: XViewHolder, entity: T, itemViewType: Int, position: Int)
}

