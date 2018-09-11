package com.xadaptersimple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.OnXBindListener
import com.xadaptersimple.data.DataUtils
import com.xadaptersimple.data.MainBean
import java.util.*

/**
 * by y on 2016/11/17
 */

class StaggeredGridLayoutManagerActivity : AppCompatActivity() {
    private lateinit var xRecyclerViewAdapter: XRecyclerViewAdapter<MainBean>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        recyclerView = findViewById(R.id.recyclerView)
        val mainBeen = ArrayList<MainBean>()
        DataUtils.getData(mainBeen)
        recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        xRecyclerViewAdapter = XRecyclerViewAdapter()
        recyclerView.adapter = xRecyclerViewAdapter
                .initXData(mainBeen)
                .apply {
                    itemLayoutId = R.layout.item
                }
                .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
                .setOnXBind(object : OnXBindListener<MainBean> {
                    override fun onXBind(holder: XViewHolder, position: Int, entity: MainBean) {
                        holder.setTextView(R.id.tv_name, entity.name)
                        holder.setTextView(R.id.tv_age, entity.age.toString())
                    }
                })
    }
}
