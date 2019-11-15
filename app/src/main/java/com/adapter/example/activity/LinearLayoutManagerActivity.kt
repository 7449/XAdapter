package com.adapter.example.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adapter.example.R
import com.adapter.example.data.DataUtils
import com.adapter.example.data.ExampleBean
import com.adapter.example.view.LoadMoreView
import com.adapter.example.view.RefreshView
import com.xadapter.recyclerview.*
import com.xadapter.refresh.XLoadMoreView
import com.xadapter.refresh.XRefreshView
import com.xadapter.vh.setText
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

/**
 * by y on 2016/11/17
 */
class LinearLayoutManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        val mainBeen = ArrayList<ExampleBean>()
        DataUtils.getData(mainBeen)


        recyclerView
                .linearLayoutManager()
                .attachAdapter<ExampleBean>()
                .setItemLayoutId(R.layout.item)
                .customRefreshView(RefreshView(applicationContext))
                .customLoadMoreView(LoadMoreView(applicationContext))
                .openLoadingMore()
                .openPullRefresh()
                .setScrollLoadMoreItemCount(2)
                .addHeaderView(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
                .addHeaderView(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_2, findViewById(android.R.id.content), false))
                .addHeaderView(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_3, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_2, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_3, findViewById(android.R.id.content), false))
                .setOnBind<ExampleBean> { holder, position, entity ->
                    holder.setText(R.id.tv_name, entity.name)
                    holder.setText(R.id.tv_age, entity.age.toString() + "")
                }
                .setOnItemClickListener<ExampleBean> { view, position, entity ->
                    Toast.makeText(baseContext, "name:  $entity.name  age:  $entity.age  position:  $position", Toast.LENGTH_SHORT).show()
                }
                .setOnItemLongClickListener<ExampleBean> { view, position, entity ->
                    Toast.makeText(baseContext, "onLongClick...", Toast.LENGTH_SHORT).show()
                    true
                }
                .setRefreshListener {
                    this@LinearLayoutManagerActivity.recyclerView.postDelayed({
                        it.refreshState = XRefreshView.SUCCESS
                        Toast.makeText(baseContext, "refresh...", Toast.LENGTH_SHORT).show()
                    }, 1500)
                }
                .setLoadMoreListener {
                    this@LinearLayoutManagerActivity.recyclerView.postDelayed({
                        it.loadMoreState = XLoadMoreView.ERROR
                        Log.d(javaClass.simpleName, it.scrollLoadMoreItemCount.toString())
                        Toast.makeText(baseContext, "loadMore...", Toast.LENGTH_SHORT).show()
                    }, 1500)
                }
                .addAll(mainBeen)
    }
}
