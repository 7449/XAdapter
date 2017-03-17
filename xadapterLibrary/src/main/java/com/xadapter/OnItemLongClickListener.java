package com.xadapter;

import android.view.View;

/**
 * by y on 2017/3/18.
 * <p>
 * Interface definition for a callback to be invoked when an item in this
 * view has been clicked and held.
 */

public interface OnItemLongClickListener<T> {
    /**
     * Callback method to be invoked when an item in this view has been
     * clicked and held.
     * <p>
     * If you use T, in order to reduce unnecessary crashes, the proposed empty sentence processing
     *
     * @param view     The view within the XBaseAdapter that was clicked
     * @param position The position of the view in the adapter.
     * @param info     The adapter's data
     */
    boolean onLongClick(View view, int position, T info);
}

