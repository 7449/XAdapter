package com.xadaptersimple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.*
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView
import com.xadaptersimple.data.DataUtils
import com.xadaptersimple.data.MainBean
import com.xadaptersimple.view.LoadMoreView
import com.xadaptersimple.view.RefreshView
import java.util.*

/**
 * by y on 2016/11/17
 */

class LinearLayoutManagerActivity : AppCompatActivity(),
        OnXBindListener<MainBean>, OnItemLongClickListener<MainBean>,
        OnItemClickListener<MainBean>, OnFooterClickListener, OnXAdapterListener {

    private lateinit var xRecyclerViewAdapter: XRecyclerViewAdapter<MainBean>
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        mRecyclerView = findViewById(R.id.recyclerView)
        val mainBeen = ArrayList<MainBean>()
        DataUtils.getData(mainBeen)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        xRecyclerViewAdapter = XRecyclerViewAdapter()
        mRecyclerView.adapter = xRecyclerViewAdapter.apply {
            mDatas = mainBeen
            loadMoreView = LoadMoreView(applicationContext)
            refreshView = RefreshView(applicationContext)
            recyclerView = mRecyclerView
            pullRefreshEnabled = true
            loadingMoreEnabled = true
            mHeaderViews.apply {
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_2, findViewById(android.R.id.content), false))
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_3, findViewById(android.R.id.content), false))
            }
            mFooterViews.apply {
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_2, findViewById(android.R.id.content), false))
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_3, findViewById(android.R.id.content), false))
            }
            mOnXBindListener = this@LinearLayoutManagerActivity
            mOnLongClickListener = this@LinearLayoutManagerActivity
            mOnItemClickListener = this@LinearLayoutManagerActivity
            mXAdapterListener = this@LinearLayoutManagerActivity
            mOnFooterListener = this@LinearLayoutManagerActivity
            itemLayoutId = R.layout.item
        }
    }

    override fun onXBind(holder: XViewHolder, position: Int, entity: MainBean) {
        holder.setTextView(R.id.tv_name, entity.name)
        holder.setTextView(R.id.tv_age, entity.age.toString() + "")
    }

    override fun onItemClick(view: View, position: Int, entity: MainBean) {
        Toast.makeText(baseContext, "name:  $entity.name  age:  $entity.age  position:  $position", Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(view: View, position: Int, entity: MainBean): Boolean {
        Toast.makeText(baseContext, "onLongClick...", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onXFooterClick(view: View) {
        Toast.makeText(baseContext, "loadMore error onClick", Toast.LENGTH_SHORT).show()
    }

    override fun onXRefresh() {
        mRecyclerView.postDelayed({
            xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS
            Toast.makeText(baseContext, "refresh...", Toast.LENGTH_SHORT).show()
        }, 1500)
    }

    override fun onXLoadMore() {
        mRecyclerView.postDelayed({
            xRecyclerViewAdapter.loadMoreState = XLoadMoreView.ERROR
            Toast.makeText(baseContext, "loadMore...", Toast.LENGTH_SHORT).show()
        }, 1500)
    }
}