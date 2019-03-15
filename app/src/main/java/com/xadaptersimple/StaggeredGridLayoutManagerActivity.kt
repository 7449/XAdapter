package com.xadaptersimple

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.addFooterView
import com.xadapter.addHeaderView
import com.xadapter.holder.XViewHolder
import com.xadapter.holder.setText
import com.xadapter.listener.OnXBindListener
import com.xadaptersimple.data.DataUtils
import com.xadaptersimple.data.MainBean
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

/**
 * by y on 2016/11/17
 */

class StaggeredGridLayoutManagerActivity : AppCompatActivity() {
    private lateinit var xRecyclerViewAdapter: XRecyclerViewAdapter<MainBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        val mainBeen = ArrayList<MainBean>()
        DataUtils.getData(mainBeen)
        recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        xRecyclerViewAdapter = XRecyclerViewAdapter()
        recyclerView.adapter = xRecyclerViewAdapter
                .apply {
                    dataContainer = mainBeen
                    itemLayoutId = R.layout.item
                    onXBindListener = object : OnXBindListener<MainBean> {
                        override fun onXBind(holder: XViewHolder, position: Int, entity: MainBean) {
                            holder.setText(R.id.tv_name, entity.name)
                            holder.setText(R.id.tv_age, entity.age.toString())
                        }
                    }
                }
                .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
    }
}
