package com.adapter.example.net

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapter.example.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.addAll
import com.xadapter.holder.getContext
import com.xadapter.holder.getImageView
import com.xadapter.holder.setText
import com.xadapter.refresh
import com.xadapter.removeAll
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView
import io.reactivex.network.RxNetWork
import io.reactivex.network.RxNetWorkListener
import kotlinx.android.synthetic.main.recyclerview_layout.*


/**
 * by y on 2016/11/17
 */
class NetWorkActivity : AppCompatActivity(), RxNetWorkListener<NetWorkBean> {

    private lateinit var mAdapter: XRecyclerViewAdapter<DataModel>
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        recyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter = XRecyclerViewAdapter()
        recyclerView.adapter = mAdapter
                .apply {
                    onXBindListener = { holder, position, entity ->
                        Glide
                                .with(holder.getContext())
                                .load(entity.title_image)
                                .apply(option)
                                .into(holder.getImageView(R.id.list_image))
                        holder.setText(R.id.list_tv, entity.title)
                    }
                    recyclerView = this@NetWorkActivity.recyclerView
                    itemLayoutId = R.layout.network_item
                    pullRefreshEnabled = true
                    loadingMoreEnabled = true
                    xRefreshListener = {
                        page = 0
                        mAdapter.removeAll()
                        netWork()
                    }
                    xLoadMoreListener = {
                        if (page < 1) {
                            netWork()
                            ++page
                        } else {
                            mAdapter.loadMoreState = XLoadMoreView.NOMORE
                        }
                    }
                }
                .refresh()
    }

    private fun netWork() {
        RxNetWork.instance.cancel(javaClass.simpleName)
        RxNetWork.instance
                .getApi(javaClass.simpleName,
                        RxNetWork.observable(NetApi.ZLService::class.java)
                                .getList("daily", 20, 0), this)
    }

    private var option = RequestOptions().error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).centerCrop()

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
