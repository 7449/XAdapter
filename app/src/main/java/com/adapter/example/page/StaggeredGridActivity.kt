package com.adapter.example.page

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.adapter.example.R
import com.adapter.example.json.JsonUtils
import com.adapter.example.json.SampleEntity
import com.bumptech.glide.Glide
import com.xadapter.recyclerview.*
import kotlinx.android.synthetic.main.layout_recyclerview.*

/**
 * by y on 2016/11/17
 */
class StaggeredGridActivity : BaseActivity(R.layout.activity_staggered_manager, "StaggeredGridLayoutManagerSample") {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView
                .staggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
                .attachXAdapter<SampleEntity>()
                .setItemLayoutId(R.layout.layout_json_item)
                .openLoadingMore()
                .setOnBind<SampleEntity> { holder, _, entity ->
                    Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                    holder.setText(R.id.title, entity.title)
                }
                .addHeaderView(LayoutInflater.from(this).inflate(R.layout.adapter_header_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(this).inflate(R.layout.adapter_footer_1, findViewById(android.R.id.content), false))
                .addAll(JsonUtils.jsonList)
    }
}
