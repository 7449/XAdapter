package com.xadaptersimple

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.OnXAdapterListener
import com.xadapter.XRefreshView
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadaptersimple.data.MainBean

/**
 * by y on 2016/11/17
 */

class EmptyViewActivity : AppCompatActivity(), OnXAdapterListener {

    private lateinit var xRecyclerViewAdapter: XRecyclerViewAdapter<MainBean>
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)

        mRecyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        xRecyclerViewAdapter = XRecyclerViewAdapter()
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = xRecyclerViewAdapter
                .apply {
                    emptyView = findViewById(R.id.emptyView)
                    recyclerView = mRecyclerView
                    itemLayoutId = R.layout.item
                    pullRefreshEnabled = true
                    xAdapterListener = this@EmptyViewActivity
                }

        xRecyclerViewAdapter.addAll(ArrayList())
    }

    override fun onXRefresh() {
        mRecyclerView.postDelayed({
            xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS
            xRecyclerViewAdapter.addAll(ArrayList())
        }, 2000)
    }

    override fun onXLoadMore() {

    }
}
