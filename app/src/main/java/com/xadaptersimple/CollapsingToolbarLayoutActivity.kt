package com.xadaptersimple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.OnXAdapterListener
import com.xadapter.listener.OnXBindListener
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView
import com.xadaptersimple.data.DataUtils
import com.xadaptersimple.data.MainBean
import java.util.*

class CollapsingToolbarLayoutActivity : AppCompatActivity() {

    private lateinit var xRecyclerViewAdapter: XRecyclerViewAdapter<MainBean>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.NoActionBar)
        setContentView(R.layout.activity_collapsing_toolbar_layout)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setTitle(R.string.app_name)

        recyclerView = findViewById(R.id.recyclerview)
        val mainBeen = ArrayList<MainBean>()
        DataUtils.getData(mainBeen)
        xRecyclerViewAdapter = XRecyclerViewAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = xRecyclerViewAdapter
                .initXData(mainBeen)
                .addRecyclerView(recyclerView)
                .apply {
                    itemLayoutId = R.layout.item
                    pullRefreshEnabled = true
                    loadingMoreEnabled = true
                }
                .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
                .setOnXBind(object : OnXBindListener<MainBean> {
                    override fun onXBind(holder: XViewHolder, position: Int, entity: MainBean) {
                        holder.setTextView(R.id.tv_name, entity.name)
                        holder.setTextView(R.id.tv_age, entity.age.toString())
                    }
                })
                .setOnXAdapterListener(object : OnXAdapterListener {
                    override fun onXRefresh() {
                        recyclerView.postDelayed({ xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS }, 1500)
                    }

                    override fun onXLoadMore() {
                        recyclerView.postDelayed({ xRecyclerViewAdapter.loadMoreState = XLoadMoreView.NOMORE }, 1500)
                    }
                })
    }
}