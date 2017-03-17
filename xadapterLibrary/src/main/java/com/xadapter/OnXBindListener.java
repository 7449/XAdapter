package com.xadapter;

import com.xadapter.holder.XViewHolder;

/**
 * by y on 2017/3/18.
 * The callback to invoke when registering data in the XBaseAdapter
 *
 * @param <T> this is mDatas
 */
public interface OnXBindListener<T> {
    void onXBind(XViewHolder holder, int position, T t);
}

