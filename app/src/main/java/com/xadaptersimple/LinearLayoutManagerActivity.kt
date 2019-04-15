package com.xadaptersimple

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.setText
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView
import com.xadaptersimple.data.DataUtils
import com.xadaptersimple.data.MainBean
import com.xadaptersimple.view.LoadMoreView
import com.xadaptersimple.view.RefreshView
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

/**
 * by y on 2016/11/17
 */

class LinearLayoutManagerActivity : AppCompatActivity() {

    private lateinit var xRecyclerViewAdapter: XRecyclerViewAdapter<MainBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        val mainBeen = ArrayList<MainBean>()
        DataUtils.getData(mainBeen)
        recyclerView.layoutManager = LinearLayoutManager(this)
        xRecyclerViewAdapter = XRecyclerViewAdapter()
        recyclerView.adapter = xRecyclerViewAdapter.apply {
            dataContainer = mainBeen
            loadMoreView = LoadMoreView(applicationContext)
            refreshView = RefreshView(applicationContext)
            recyclerView = this@LinearLayoutManagerActivity.recyclerView
            pullRefreshEnabled = true
            loadingMoreEnabled = true
            scrollLoadMoreItemCount = 10
            headerViewContainer.apply {
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_2, findViewById(android.R.id.content), false))
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_3, findViewById(android.R.id.content), false))
            }
            footerViewContainer.apply {
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_2, findViewById(android.R.id.content), false))
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_3, findViewById(android.R.id.content), false))
            }
            onXBindListener = { holder, position, entity ->
                holder.setText(R.id.tv_name, entity.name)
                holder.setText(R.id.tv_age, entity.age.toString() + "")
            }
            onXLongClickListener = { _, _, _ ->
                Toast.makeText(baseContext, "onLongClick...", Toast.LENGTH_SHORT).show()
                true
            }
            onXItemClickListener = { _, position, entity ->
                Toast.makeText(baseContext, "name:  $entity.name  age:  $entity.age  position:  $position", Toast.LENGTH_SHORT).show()
            }
            onXFooterListener = {
                Toast.makeText(baseContext, "loadMore error onClick", Toast.LENGTH_SHORT).show()
            }
            xRefreshListener = {
                this@LinearLayoutManagerActivity.recyclerView.postDelayed({
                    xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS
                    Toast.makeText(baseContext, "refresh...", Toast.LENGTH_SHORT).show()
                }, 1500)
            }
            xLoadMoreListener = {
                this@LinearLayoutManagerActivity.recyclerView.postDelayed({
                    xRecyclerViewAdapter.loadMoreState = XLoadMoreView.ERROR
                    Log.d(javaClass.simpleName, xRecyclerViewAdapter.scrollLoadMoreItemCount.toString())
                    Toast.makeText(baseContext, "loadMore...", Toast.LENGTH_SHORT).show()
                }, 1500)
            }
            itemLayoutId = R.layout.item
        }
    }
}
