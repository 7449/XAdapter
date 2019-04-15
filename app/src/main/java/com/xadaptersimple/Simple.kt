package com.xadaptersimple

import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.simple.SimpleXMultiItem

/**
 * @author y
 * @create 2019/4/15
 */
class Simple {

    lateinit var adapter: XRecyclerViewAdapter<SimpleXMultiItem>


    fun simple() {


        adapter.itemLayoutId = 0
        adapter.onXBindListener = { holder, position, entity ->

        }

        adapter.xRefreshListener = {

        }
        adapter.xLoadMoreListener = {

        }
    }
}
