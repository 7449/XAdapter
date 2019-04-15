package com.xadaptersimple

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.addAll
import com.xadapter.holder.setText
import com.xadapter.refresh
import com.xadapter.removeAll
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView
import com.xadaptersimple.data.DataUtils
import com.xadaptersimple.data.MainBean
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

/**
 * by y on 2017/6/20.
 */

class TestActivity : AppCompatActivity() {

    private lateinit var xRecyclerViewAdapter: XRecyclerViewAdapter<MainBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        val mainBeen = ArrayList<MainBean>()
        recyclerView.layoutManager = LinearLayoutManager(this)
        xRecyclerViewAdapter = XRecyclerViewAdapter()
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = xRecyclerViewAdapter
                .apply {
                    dataContainer = mainBeen
                    recyclerView = this@TestActivity.recyclerView
                    itemLayoutId = R.layout.item
                    pullRefreshEnabled = true
                    loadingMoreEnabled = true
                    onXBindListener = { holder, position, entity ->
                        holder.setText(R.id.tv_name, entity.name)
                        holder.setText(R.id.tv_age, entity.age.toString() + "")
                    }
                    xRefreshListener = {
                        xRecyclerViewAdapter.removeAll()
                        this@TestActivity.recyclerView.postDelayed({
                            xRecyclerViewAdapter.addAll(DataUtils.getTestData(ArrayList()))
                            xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS
                            if (xRecyclerViewAdapter.dataContainer.size < 7) {
                                xRecyclerViewAdapter.loadMoreState = XLoadMoreView.NOMORE
                            }
                        }, 1500)
                    }
                    xLoadMoreListener = {
                        this@TestActivity.recyclerView.postDelayed({
                            if (xRecyclerViewAdapter.dataContainer.size < 7) {
                                xRecyclerViewAdapter.loadMoreState = XLoadMoreView.NOMORE
                            } else {
                                xRecyclerViewAdapter.loadMoreState = XLoadMoreView.ERROR
                            }
                        }, 1500)
                    }
                }
                .refresh()

    }
}
