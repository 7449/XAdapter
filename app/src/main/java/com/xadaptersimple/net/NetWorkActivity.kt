package com.xadaptersimple.net

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xadapter.OnXAdapterListener
import com.xadapter.OnXBindListener
import com.xadapter.XLoadMoreView
import com.xadapter.XRefreshView
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import com.xadaptersimple.R
import io.reactivex.network.RxNetWork
import io.reactivex.network.RxNetWorkListener
import kotlinx.android.synthetic.main.recyclerview_layout.*


/**
 * by y on 2016/11/17
 */
class NetWorkActivity : AppCompatActivity(), OnXAdapterListener, OnXBindListener<DataModel>, RxNetWorkListener<NetWorkBean> {

    private lateinit var mAdapter: XRecyclerViewAdapter<DataModel>
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        recyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter = XRecyclerViewAdapter()
        recyclerView.adapter = mAdapter
                .apply {
                    onXBindListener = this@NetWorkActivity
                    recyclerView = this@NetWorkActivity.recyclerView
                    itemLayoutId = R.layout.network_item
                    pullRefreshEnabled = true
                    loadingMoreEnabled = true
                    xAdapterListener = this@NetWorkActivity
                }
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
                .getApi(javaClass.simpleName,
                        RxNetWork.observable(NetApi.ZLService::class.java)
                                .getList("daily", 20, 0), this)
    }

    private var option = RequestOptions().error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).centerCrop()
    override fun onXBind(holder: XViewHolder, position: Int, entity: DataModel) {
        Glide
                .with(holder.context)
                .load(entity.title_image)
                .apply(option)
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

    override fun onNetWorkSuccess(data: NetWorkBean) {
        mAdapter.addAll(data.data)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.instance.cancel(javaClass.simpleName)
    }
}
