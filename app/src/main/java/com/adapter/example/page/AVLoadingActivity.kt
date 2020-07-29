package com.adapter.example.page

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.adapter.example.R
import com.adapter.example.json.JsonUtils
import com.adapter.example.json.SampleEntity
import com.bumptech.glide.Glide
import com.xadapter.recyclerview.*
import com.xadapter.refresh.Callback
import com.xadapter.refresh.extensions.AVLoadingIndicatorLoadMoreView
import com.xadapter.refresh.extensions.AVLoadingIndicatorRefreshView
import com.xadapter.refresh.extensions.AVType
import com.xadapter.refresh.extensions.getIndicator
import kotlinx.android.synthetic.main.layout_recyclerview.*

/**
 * by y on 2016/11/17
 */
class AVLoadingActivity : BaseActivity(R.layout.activity_avloading_indicator, "AVLoadingIndicatorViewSample") {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView
                .linearLayoutManager()
                .attachAdapter<SampleEntity>()
                .customLoadMoreView(AVLoadingIndicatorLoadMoreView(applicationContext).apply { indicator = getIndicator(AVType.SQUARE_SPIN) })
                .customRefreshView(AVLoadingIndicatorRefreshView(applicationContext))
                .setItemLayoutId(R.layout.layout_json_item)
                .openLoadingMore()
                .openPullRefresh()
                .addHeaderView(LayoutInflater.from(applicationContext).inflate(R.layout.adapter_header_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(applicationContext).inflate(R.layout.adapter_footer_1, findViewById(android.R.id.content), false))
                .setOnBind<SampleEntity> { holder, _, entity ->
                    Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                    holder.setText(R.id.title, entity.title)
                }
                .setRefreshListener {
                    this@AVLoadingActivity.recyclerView.postDelayed({
                        it.setRefreshState(Callback.SUCCESS)
                    }, 1500)
                }
                .setLoadMoreListener {
                    this@AVLoadingActivity.recyclerView.postDelayed({
                        it.setLoadMoreState(Callback.ERROR)
                    }, 1500)
                }
                .addAll(JsonUtils.jsonList)

        recyclerView.getHeaderView(0)?.setOnClickListener {
            Toast.makeText(baseContext, "HeaderView", Toast.LENGTH_SHORT).show()
        }
    }
}
