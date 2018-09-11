package com.xadaptersimple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.listener.OnXAdapterListener
import com.xadapter.widget.XRefreshView
import com.xadaptersimple.data.MainBean

/**
 * by y on 2016/11/17
 */

class EmptyViewActivity : AppCompatActivity(), OnXAdapterListener {

    private lateinit var xRecyclerViewAdapter: XRecyclerViewAdapter<MainBean>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)

        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        xRecyclerViewAdapter = XRecyclerViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = xRecyclerViewAdapter
                .addRecyclerView(recyclerView)
                .setEmptyView(findViewById(R.id.emptyView), true)
                .apply {
                    itemLayoutId = R.layout.item
                    pullRefreshEnabled = true
                }
                .setOnXAdapterListener(this)

        xRecyclerViewAdapter.addAll(ArrayList())
    }

    override fun onXRefresh() {
        recyclerView.postDelayed({
            xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS
            xRecyclerViewAdapter.addAll(ArrayList())
        }, 2000)
    }

    override fun onXLoadMore() {

    }
}
