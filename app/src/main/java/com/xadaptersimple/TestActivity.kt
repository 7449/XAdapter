package com.xadaptersimple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.OnItemClickListener
import com.xadapter.listener.OnXAdapterListener
import com.xadapter.listener.OnXBindListener
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView
import com.xadaptersimple.data.DataUtils
import com.xadaptersimple.data.MainBean
import java.util.*

/**
 * by y on 2017/6/20.
 */

class TestActivity : AppCompatActivity(), OnXBindListener<MainBean>, OnXAdapterListener {

    private lateinit var xRecyclerViewAdapter: XRecyclerViewAdapter<MainBean>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        recyclerView = findViewById(R.id.recyclerView)
        val mainBeen = ArrayList<MainBean>()
        recyclerView.layoutManager = LinearLayoutManager(this)
        xRecyclerViewAdapter = XRecyclerViewAdapter()
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = xRecyclerViewAdapter
                .initXData(mainBeen)
                .addRecyclerView(recyclerView)
                .apply {
                    itemLayoutId = R.layout.item
                    pullRefreshEnabled = true
                    loadingMoreEnabled = true
                }
                .setOnXBind(this)
                .setOnXAdapterListener(this)
                .setOnItemClickListener(object : OnItemClickListener<MainBean> {
                    override fun onItemClick(view: View, position: Int, entity: MainBean) {
                        Log.i("onItemClick", position.toString())
                        xRecyclerViewAdapter.remove(position)
                    }

                })
                .refresh()

    }

    override fun onXBind(holder: XViewHolder, position: Int, entity: MainBean) {
        holder.setTextView(R.id.tv_name, entity.name)
        holder.setTextView(R.id.tv_age, entity.age.toString() + "")
    }

    override fun onXRefresh() {
        xRecyclerViewAdapter.removeAll()
        recyclerView.postDelayed({
            xRecyclerViewAdapter.addAll(DataUtils.getTestData(ArrayList()))
            xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS
            if (xRecyclerViewAdapter.mDatas.size < 7) {
                xRecyclerViewAdapter.loadMoreState = XLoadMoreView.NOMORE
            }
        }, 1500)
    }

    override fun onXLoadMore() {
        recyclerView.postDelayed({
            if (xRecyclerViewAdapter.mDatas.size < 7) {
                xRecyclerViewAdapter.loadMoreState = XLoadMoreView.NOMORE
            } else {
                xRecyclerViewAdapter.loadMoreState = XLoadMoreView.ERROR
            }
        }, 1500)
    }
}