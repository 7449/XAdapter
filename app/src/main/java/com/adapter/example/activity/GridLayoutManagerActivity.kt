package com.adapter.example.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.adapter.example.R
import com.adapter.example.data.DataUtils
import com.adapter.example.data.ExampleBean
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.addFooterView
import com.xadapter.addHeaderView
import com.xadapter.holder.setText
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

/**
 * by y on 2016/11/17
 */

class GridLayoutManagerActivity : AppCompatActivity() {
    private lateinit var xRecyclerViewAdapter: XRecyclerViewAdapter<ExampleBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        val mainBean = ArrayList<ExampleBean>()
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
            onXBindListener = { holder, position, entity ->
                holder.setText(R.id.tv_name, entity.name)
                holder.setText(R.id.tv_age, entity.age.toString())
            }
            xRefreshListener = {
                this@GridLayoutManagerActivity.recyclerView.postDelayed({ xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS }, 1500)
            }
            xLoadMoreListener = {
                this@GridLayoutManagerActivity.recyclerView.postDelayed({ xRecyclerViewAdapter.loadMoreState = XLoadMoreView.NOMORE }, 1500)
            }
        }
                .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
    }
}
