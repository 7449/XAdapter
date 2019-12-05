package com.adapter.example.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapter.example.R
import com.adapter.example.supportAppbar
import com.adapter.example.data.DataUtils
import com.adapter.example.data.ExampleBean
import com.xadapter.adapter.XAdapter
import com.xadapter.addFooterView
import com.xadapter.addHeaderView
import com.xadapter.refresh.XLoadMoreView
import com.xadapter.refresh.XRefreshView
import com.xadapter.vh.setText
import kotlinx.android.synthetic.main.activity_collapsing_toolbar_layout.*
import java.util.*

class CollapsingToolbarLayoutActivity : AppCompatActivity() {

    private lateinit var xRecyclerViewAdapter: XAdapter<ExampleBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.NoActionBar)
        setContentView(R.layout.activity_collapsing_toolbar_layout)
        toolbar.setTitle(R.string.app_name)

        val mainBeen = ArrayList<ExampleBean>()
        DataUtils.getData(mainBeen)
        xRecyclerViewAdapter = XAdapter()

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = xRecyclerViewAdapter
                .apply {
                    dataContainer = mainBeen
                    itemLayoutId = R.layout.item
                    pullRefreshEnabled = true
                    loadingMoreEnabled = true
                    onXBindListener = { holder, position, entity ->
                        holder.setText(R.id.tv_name, entity.name)
                        holder.setText(R.id.tv_age, entity.age.toString())
                    }
                    xRefreshListener = {
                        recyclerview.postDelayed({ xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS }, 1500)
                    }
                    xLoadMoreListener = {
                        recyclerview.postDelayed({ xRecyclerViewAdapter.loadMoreState = XLoadMoreView.NO_MORE }, 1500)
                    }
                }
                .supportAppbar(appbar)
                .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
    }
}
