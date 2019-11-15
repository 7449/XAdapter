package com.adapter.example.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

class StaggeredGridLayoutManagerActivity : AppCompatActivity() {
    private lateinit var xRecyclerViewAdapter: XAdapter<ExampleBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        val mainBeen = ArrayList<ExampleBean>()
        DataUtils.getData(mainBeen)
        recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        xRecyclerViewAdapter = XAdapter()
        recyclerView.adapter = xRecyclerViewAdapter
                .setItemLayoutId(R.layout.item)
                .openLoadingMore()
                .apply {
                    dataContainer = mainBeen
                }
                .setOnBind { holder, _, entity ->
                    holder.setText(R.id.tv_name, entity.name)
                    holder.setText(R.id.tv_age, entity.age.toString())
                }
                .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
    }
}
