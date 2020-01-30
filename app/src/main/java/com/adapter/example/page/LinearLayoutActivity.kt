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
                .fixedSize()
                .setAdapter<SampleEntity> {
                    loadingMore = true
                    pullRefresh = true
                    itemLayoutId = R.layout.layout_json_item
                    addHeaderViews(
                            LayoutInflater.from(applicationContext).inflate(R.layout.adapter_header_1, findViewById(android.R.id.content), false),
                            LayoutInflater.from(applicationContext).inflate(R.layout.adapter_header_2, findViewById(android.R.id.content), false),
                            LayoutInflater.from(applicationContext).inflate(R.layout.adapter_header_3, findViewById(android.R.id.content), false)
                    )
                    addFooterViews(
                            LayoutInflater.from(applicationContext).inflate(R.layout.adapter_footer_1, findViewById(android.R.id.content), false),
                            LayoutInflater.from(applicationContext).inflate(R.layout.adapter_footer_2, findViewById(android.R.id.content), false),
                            LayoutInflater.from(applicationContext).inflate(R.layout.adapter_footer_3, findViewById(android.R.id.content), false)
                    )
                    onBind { holder, position, entity ->
                        Glide.with(holder.getContext()).load(entity.image).into(holder.getImageView(R.id.image))
                        holder.setText(R.id.title, entity.title)
                    }
                    onItemLongClickListener { view, position, entity ->
                        Toast.makeText(baseContext, "onLongClick", Toast.LENGTH_SHORT).show()
                        true
                    }
                    onItemClickListener { view, position, entity ->
                        Toast.makeText(baseContext, "position:  $position", Toast.LENGTH_SHORT).show()
                    }
                    refreshListener {
                        this@LinearLayoutActivity.recyclerView.postDelayed({
                            it.setRefreshState(XRefreshView.SUCCESS)
                        }, 1500)
                    }
                    loadMoreListener {
                        this@LinearLayoutActivity.recyclerView.postDelayed({
                            it.setLoadMoreState(XLoadMoreView.ERROR)
                        }, 1500)
                    }
                }
                .addAll(JsonUtils.jsonList)
        recyclerView.getHeaderView(0)?.setOnClickListener {
            Toast.makeText(baseContext, "HeaderView", Toast.LENGTH_SHORT).show()
        }
    }
}
