package com.adapter.example.page

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.adapter.example.R
import com.adapter.example.json.JsonUtils
import com.adapter.example.json.SampleEntity
import com.bumptech.glide.Glide
import com.xadapter.recyclerview.*
import com.xadapter.refresh.XLoadMoreView
import com.xadapter.refresh.XRefreshView
import com.xadapter.setLoadMoreState
import com.xadapter.setRefreshState
import com.xadapter.vh.getContext
import com.xadapter.vh.getImageView
import com.xadapter.vh.setText
import kotlinx.android.synthetic.main.layout_recyclerview.*

/**
 * by y on 2016/11/17
 */
class LinearLayoutActivity : BaseActivity(R.layout.activity_linear_manager, "LinearLayoutManagerSample") {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView
                .linearLayoutManager()
                .attachAdapter<SampleEntity>()
                .setItemLayoutId(R.layout.layout_json_item)
                .openLoadingMore()
                .openPullRefresh()
                .setScrollLoadMoreItemCount(2)
                .addHeaderView(LayoutInflater.from(applicationContext).inflate(R.layout.adapter_header_1, findViewById(android.R.id.content), false))
                .addHeaderView(LayoutInflater.from(applicationContext).inflate(R.layout.adapter_header_2, findViewById(android.R.id.content), false))
                .addHeaderView(LayoutInflater.from(applicationContext).inflate(R.layout.adapter_header_3, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(applicationContext).inflate(R.layout.adapter_footer_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(applicationContext).inflate(R.layout.adapter_footer_2, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(applicationContext).inflate(R.layout.adapter_footer_3, findViewById(android.R.id.content), false))
                .setOnBind<SampleEntity> { holder, _, entity ->
                    Glide.with(holder.getContext()).load(entity.image).into(holder.getImageView(R.id.image))
                    holder.setText(R.id.title, entity.title)
                }
                .setOnItemClickListener<SampleEntity> { _, position, _ ->
                    Toast.makeText(baseContext, "position:  $position", Toast.LENGTH_SHORT).show()
                }
                .setOnItemLongClickListener<SampleEntity> { _, _, _ ->
                    Toast.makeText(baseContext, "onLongClick", Toast.LENGTH_SHORT).show()
                    true
                }
                .setRefreshListener {
                    this@LinearLayoutActivity.recyclerView.postDelayed({
                        it.setRefreshState(XRefreshView.SUCCESS)
                    }, 1500)
                }
                .setLoadMoreListener {
                    this@LinearLayoutActivity.recyclerView.postDelayed({
                        it.setLoadMoreState(XLoadMoreView.ERROR)
                    }, 1500)
                }
                .addAll(JsonUtils.jsonList)

        recyclerView.getHeaderView(0)?.setOnClickListener {
            Toast.makeText(baseContext, "HeaderView", Toast.LENGTH_SHORT).show()
        }
    }
}
