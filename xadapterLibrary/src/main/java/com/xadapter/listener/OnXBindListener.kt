package com.xadapter.listener

import com.xadapter.holder.XViewHolder

/**
 * by y on 2017/3/18.
 * The callback to invoke when registering data in the XBaseAdapter
 *
 * @param <T> this is mDatas
</T> */
interface OnXBindListener<T> {
    fun onXBind(holder: XViewHolder, position: Int, entity: T)
}

