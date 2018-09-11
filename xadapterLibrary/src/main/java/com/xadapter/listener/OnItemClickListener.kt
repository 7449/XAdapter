package com.xadapter.listener

import android.view.View

/**
 * by y on 2017/3/18.
 *
 *
 * Interface definition for a callback to be invoked when an item in this
 * adapter has been clicked.
 */

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

