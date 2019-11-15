package com.adapter.example.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.adapter.example.R
import com.adapter.example.data.DataUtils
import com.adapter.example.data.ExampleBean
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import com.xadapter.vh.setText
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

/**
 * by y on 2016/11/17
 */
class GridLayoutManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        val mainBean = ArrayList<ExampleBean>()
        DataUtils.getData(mainBean)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
        recyclerView
                .attachAdapter(XAdapter<ExampleBean>())
                .setItemLayoutId(R.layout.item)
                .openPullRefresh()
                .openLoadingMore()
                .setOnBind<ExampleBean> { holder, _, entity ->
                    holder.setText(R.id.tv_name, entity.name)
                    holder.setText(R.id.tv_age, entity.age.toString())
                }
                .setRefreshListener {
                    this@GridLayoutManagerActivity.recyclerView.postDelayed({ recyclerView.adapter<ExampleBean>().refreshState = XRefreshView.SUCCESS }, 1500)
                }
                .setLoadMoreListener {
                    this@GridLayoutManagerActivity.recyclerView.postDelayed({ recyclerView.adapter<ExampleBean>().loadMoreState = XLoadMoreView.NO_MORE }, 1500)
                }
                .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
                .addAll(mainBean)
    }
}
