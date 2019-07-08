package com.adapter.example.net

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapter.example.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import io.reactivex.network.RxNetWork
import io.reactivex.network.RxNetWorkListener
import io.reactivex.network.getApi
import kotlinx.android.synthetic.main.recyclerview_layout.*


/**
 * by y on 2016/11/17
 */
class NetWorkActivity : AppCompatActivity(), RxNetWorkListener<NetWorkBean> {

    private lateinit var mAdapter: XAdapter<DataModel>
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "NetWork Example"
        setContentView(R.layout.recyclerview_layout)
        recyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter = XAdapter()
        recyclerView.adapter = mAdapter
        recyclerView.adapter<DataModel>()
                .openLoadingMore()
                .openPullRefresh()
                .setItemLayoutId(R.layout.network_item)
                .setOnBind { holder, _, entity ->
                    Glide
                            .with(holder.getContext())
                            .load(entity.image)
                            .apply(option)
                            .into(holder.getImageView(R.id.list_image))
                    holder.setText(R.id.list_tv, entity.title)
                }
                .setRefreshListener {
                    page = 0
                    mAdapter.removeAll()
                    netWork()
                }
                .setLoadMoreListener {
                    if (page < 1) {
                        netWork()
                        ++page
                    } else {
                        mAdapter.loadMoreState = XLoadMoreView.NO_MORE
                    }
                }
                .attach(recyclerView)
                .refresh()

    }

    private fun netWork() {
        RxNetWork.cancelX(javaClass.simpleName)
        RxNetWork.observable(NetApi.ZLService::class.java)
                .getList()
                .getApi(javaClass.simpleName, this)
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
        mAdapter.addAll(data.top_stories)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.cancelX(javaClass.simpleName)
    }
}
