package com.xadaptersimple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.OnXAdapterListener
import com.xadapter.listener.OnXBindListener
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView
import com.xadaptersimple.data.DataUtils
import com.xadaptersimple.data.MainBean
import java.util.*

/**
 * by y on 2016/11/17
 */

class GridLayoutManagerActivity : AppCompatActivity() {
    private lateinit var xRecyclerViewAdapter: XRecyclerViewAdapter<MainBean>
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        mRecyclerView = findViewById(R.id.recyclerView)
        val mainBean = ArrayList<MainBean>()
        DataUtils.getData(mainBean)
        xRecyclerViewAdapter = XRecyclerViewAdapter()
        mRecyclerView.layoutManager = GridLayoutManager(this, 2)
        mRecyclerView.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
        mRecyclerView.adapter = xRecyclerViewAdapter.apply {
            dataContainer = mainBean
            recyclerView = mRecyclerView
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
                    mRecyclerView.postDelayed({ xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS }, 1500)
                }

                override fun onXLoadMore() {
                    mRecyclerView.postDelayed({ xRecyclerViewAdapter.loadMoreState = XLoadMoreView.NOMORE }, 1500)
                }
            }
        }
                .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
    }
}
