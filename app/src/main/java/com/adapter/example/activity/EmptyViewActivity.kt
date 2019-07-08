package com.adapter.example.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapter.example.R
import com.adapter.example.data.ExampleBean
import com.xadapter.XRefreshView
import com.xadapter.adapter.XAdapter
import com.xadapter.addAll
import kotlinx.android.synthetic.main.recyclerview_layout.*

/**
 * by y on 2016/11/17
 */

class EmptyViewActivity : AppCompatActivity() {

    private lateinit var xRecyclerViewAdapter: XAdapter<ExampleBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        xRecyclerViewAdapter = XAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = xRecyclerViewAdapter
                .apply {
                    emptyView = this@EmptyViewActivity.emptyView
                    itemLayoutId = R.layout.item
                    pullRefreshEnabled = true
                    xRefreshListener = {
                        this@EmptyViewActivity.recyclerView.postDelayed({
                            xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS
                            xRecyclerViewAdapter.addAll(ArrayList())
                        }, 2000)
                    }
                    xLoadMoreListener = {

                    }
                }

        xRecyclerViewAdapter.addAll(ArrayList())
    }
}
