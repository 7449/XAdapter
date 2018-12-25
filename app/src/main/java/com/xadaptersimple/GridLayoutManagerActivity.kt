package com.xadaptersimple

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.xadapter.OnXAdapterListener
import com.xadapter.OnXBindListener
import com.xadapter.XLoadMoreView
import com.xadapter.XRefreshView
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import com.xadaptersimple.data.DataUtils
import com.xadaptersimple.data.MainBean
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

/**
 * by y on 2016/11/17
 */

class GridLayoutManagerActivity : AppCompatActivity() {
    private lateinit var xRecyclerViewAdapter: XRecyclerViewAdapter<MainBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        val mainBean = ArrayList<MainBean>()
        DataUtils.getData(mainBean)
        xRecyclerViewAdapter = XRecyclerViewAdapter()
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = xRecyclerViewAdapter.apply {
            dataContainer = mainBean
            recyclerView = this@GridLayoutManagerActivity.recyclerView
            itemLayoutId = R.layout.item
            pullRefreshEnabled = true
            loadingMoreEnabled = true
            onXBindListener = object : OnXBindListener<MainBean> {
                override fun onXBind(holder: XViewHolder, position: Int, entity: MainBean) {
                    holder.setTextView(R.id.tv_name, entity.name)
                    holder.setTextView(R.id.tv_age, entity.age.toString())
                }
            }
            xAdapterListener = object : OnXAdapterListener {
                override fun onXRefresh() {
                    this@GridLayoutManagerActivity.recyclerView.postDelayed({ xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS }, 1500)
                }

                override fun onXLoadMore() {
                    this@GridLayoutManagerActivity.recyclerView.postDelayed({ xRecyclerViewAdapter.loadMoreState = XLoadMoreView.NOMORE }, 1500)
                }
            }
        }
                .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
    }
}
