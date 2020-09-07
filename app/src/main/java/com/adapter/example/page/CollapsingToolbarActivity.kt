package com.adapter.example.page

import android.os.Bundle
import android.view.LayoutInflater
import com.adapter.example.R
import com.adapter.example.json.JsonUtils
import com.adapter.example.json.SampleEntity
import com.adapter.example.supportAppbar
import com.bumptech.glide.Glide
import com.xadapter.recyclerview.*
import com.xadapter.refresh.Callback
import kotlinx.android.synthetic.main.activity_collapsing_toolbar.*

class CollapsingToolbarActivity : BaseActivity(R.layout.activity_collapsing_toolbar, "CollapsingToolbarSample") {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView
                .linearLayoutManager()
                .attachXAdapter<SampleEntity>()
                .setItemLayoutId(R.layout.layout_json_item)
                .openPullRefresh()
                .openLoadingMore()
                .setOnBind<SampleEntity> { holder, _, entity ->
                    Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                    holder.setText(R.id.title, entity.title)
                }
                .setRefreshListener {
                    this@CollapsingToolbarActivity.recyclerView.postDelayed({ recyclerView.setRefreshState(Callback.SUCCESS) }, 1500)
                }
                .setLoadMoreListener {
                    this@CollapsingToolbarActivity.recyclerView.postDelayed({ recyclerView.setLoadMoreState(Callback.NO_MORE) }, 1500)
                }
                .addHeaderView(LayoutInflater.from(this).inflate(R.layout.adapter_header_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(this).inflate(R.layout.adapter_footer_1, findViewById(android.R.id.content), false))
                .xAdapter<SampleEntity>()
                .supportAppbar(appbar)
                .addAll(JsonUtils.jsonList)
    }
}
