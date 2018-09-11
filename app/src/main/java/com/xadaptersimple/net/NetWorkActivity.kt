package com.xadaptersimple.net

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.OnXAdapterListener
import com.xadapter.listener.OnXBindListener
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView
import com.xadaptersimple.R
import io.reactivex.network.RxNetWork
import io.reactivex.network.RxNetWorkListener


/**
 * by y on 2016/11/17
 */
class NetWorkActivity : AppCompatActivity(), OnXAdapterListener, OnXBindListener<NetWorkBean>, RxNetWorkListener<List<NetWorkBean>> {

    private lateinit var mAdapter: XRecyclerViewAdapter<NetWorkBean>
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter = XRecyclerViewAdapter()
        recyclerView.adapter = mAdapter
                .addRecyclerView(recyclerView)
                .setOnXBind(this)
                .apply {
                    itemLayoutId = R.layout.network_item
                    pullRefreshEnabled = true
                    loadingMoreEnabled = true
                }
                .setOnXAdapterListener(this)
                .refresh()
    }


    override fun onXRefresh() {
        page = 0
        mAdapter.removeAll()
        netWork()
    }

    override fun onXLoadMore() {
        if (page < 1) {
            netWork()
            ++page
        } else {
            mAdapter.loadMoreState = XLoadMoreView.NOMORE
        }
    }

    private fun netWork() {
        RxNetWork.instance.cancel(javaClass.simpleName)
        RxNetWork.instance
                .setBaseUrl(NetApi.ZL_BASE_API)
                .getApi(javaClass.simpleName,
                        RxNetWork.observable(NetApi.ZLService::class.java)
                                .getList("daily", 20, 0), this)
    }

    override fun onXBind(holder: XViewHolder, position: Int, entity: NetWorkBean) {
        Glide
                .with(holder.context)
                .load(entity.titleImage)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.getImageView(R.id.list_image))
        holder.setTextView(R.id.list_tv, entity.title)
    }

    override fun onNetWorkStart() {
        if (page != 0) {
            mAdapter.loadMoreState = XLoadMoreView.LOAD
        }
    }

    override fun onNetWorkError(e: Throwable) {
        if (page == 0) {
            mAdapter.refreshState = XRefreshView.ERROR
        } else {
            mAdapter.loadMoreState = XLoadMoreView.ERROR
        }
    }

    override fun onNetWorkComplete() {
        if (page == 0) {
            mAdapter.refreshState = XRefreshView.SUCCESS
        } else {
            mAdapter.loadMoreState = XLoadMoreView.SUCCESS
        }
    }

    override fun onNetWorkSuccess(data: List<NetWorkBean>) {
        mAdapter.addAll(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.instance.cancel(javaClass.simpleName)
    }
}
