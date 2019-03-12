package com.xadaptersimple

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.OnXAdapterListener
import com.xadapter.listener.OnXBindListener
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView
import com.xadaptersimple.data.DataUtils
import com.xadaptersimple.data.MainBean
import kotlinx.android.synthetic.main.activity_collapsing_toolbar_layout.*
import java.util.*

class CollapsingToolbarLayoutActivity : AppCompatActivity() {

    private lateinit var xRecyclerViewAdapter: XRecyclerViewAdapter<MainBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.NoActionBar)
        setContentView(R.layout.activity_collapsing_toolbar_layout)
        toolbar.setTitle(R.string.app_name)

        val mainBeen = ArrayList<MainBean>()
        DataUtils.getData(mainBeen)
        xRecyclerViewAdapter = XRecyclerViewAdapter()

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = xRecyclerViewAdapter
                .apply {
                    dataContainer = mainBeen
                    recyclerView = this@CollapsingToolbarLayoutActivity.recyclerview
                    itemLayoutId = R.layout.item
                    pullRefreshEnabled = true
                    loadingMoreEnabled = true
                    onXBindListener = object : OnXBindListener<MainBean> {
                        override fun onXBind(holder: XViewHolder, position: Int, entity: MainBean) {
                            holder.setTextView(R.id.tv_name, entity.name)
                            holder.setTextView(R.id.tv_age, entity.age.toString())
                        }
                    }
                    xAdapterListener = object : OnXAdapterListener {
                        override fun onXRefresh() {
                            recyclerview.postDelayed({ xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS }, 1500)
                        }

                        override fun onXLoadMore() {
                            recyclerview.postDelayed({ xRecyclerViewAdapter.loadMoreState = XLoadMoreView.NOMORE }, 1500)
                        }
                    }
                }
                .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
    }
}
