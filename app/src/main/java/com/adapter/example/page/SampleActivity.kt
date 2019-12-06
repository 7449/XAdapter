package com.adapter.example.page

import android.os.Bundle
import com.adapter.example.R
import com.adapter.example.json.JsonUtils
import com.adapter.example.json.SampleEntity
import com.bumptech.glide.Glide
import com.xadapter.recyclerview.*
import com.xadapter.vh.getContext
import com.xadapter.vh.getImageView
import com.xadapter.vh.setText
import kotlinx.android.synthetic.main.layout_recyclerview.*

class SampleActivity : BaseActivity(R.layout.activity_sample, "SampleAdapter") {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView
                .linearLayoutManager()
                .attachAdapter<SampleEntity>()
                .setItemLayoutId(R.layout.layout_json_item)
                .setOnBind<SampleEntity> { holder, _, entity ->
                    Glide.with(holder.getContext()).load(entity.image).into(holder.getImageView(R.id.image))
                    holder.setText(R.id.title, entity.title)
                }
                .addAll(JsonUtils.jsonList)
    }

}