package com.xadapter.listener

import com.xadapter.holder.XViewHolder

/**
 * @author y
 * @create 2019/3/12
 */
interface OnXBindListener<T> {
    fun onXBind(holder: XViewHolder, position: Int, entity: T)
}