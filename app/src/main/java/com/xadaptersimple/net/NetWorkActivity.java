package com.xadaptersimple.net;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;
import com.xadapter.listener.LoadListener;
import com.xadapter.listener.OnXBindListener;
import com.xadapter.widget.SimpleLoadMore;
import com.xadapter.widget.SimpleRefresh;
import com.xadaptersimple.R;

import java.util.List;

import io.reactivex.network.manager.RxNetWork;
import io.reactivex.network.manager.RxNetWorkListener;

/**
 * by y on 2016/11/17
 */
public class NetWorkActivity extends AppCompatActivity
        implements LoadListener, OnXBindListener<NetWorkBean>, RxNetWorkListener<List<NetWorkBean>> {

    private XRecyclerViewAdapter<NetWorkBean> mAdapter;
    private int page = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setAdapter(
                mAdapter
                        .addRecyclerView(recyclerView)
                        .setLayoutId(R.layout.network_item)
                        .onXBind(this)
                        .setPullRefreshEnabled(true)
                        .setLoadingMoreEnabled(true)
                        .setLoadListener(this)
                        .refresh()
        );


    }


    @Override
    public void onRefresh() {
        mAdapter.removeAll();
        netWork();
    }

    @Override
    public void onLoadMore() {
        if (page < 1) {
            ++page;
            netWork();
        } else {
            mAdapter.loadMoreState(SimpleLoadMore.NOMORE);
        }
    }

    private void netWork() {
        RxNetWork.getInstance().cancel(getClass().getSimpleName());
        RxNetWork
                .getInstance()
                .setBaseUrl(NetApi.ZL_BASE_API)
                .getApi(getClass().getSimpleName(),
                        RxNetWork.observable(NetApi.ZLService.class)
                                .getList("daily", 20, 0), this);
    }

    @Override
    public void onXBind(XViewHolder holder, int position, NetWorkBean netWorkBean) {
        Glide
                .with(holder.getContext())
                .load(netWorkBean.getTitleImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.getImageView(R.id.list_image));
        holder.setTextView(R.id.list_tv, netWorkBean.getTitle());
    }

    @Override
    public void onNetWorkStart() {
        if (page != 0) {
            mAdapter.loadMoreState(SimpleLoadMore.LOAD);
        }
    }

    @Override
    public void onNetWorkError(Throwable e) {
        if (page == 0) {
            mAdapter.refreshState(SimpleRefresh.ERROR);
        } else {
            mAdapter.loadMoreState(SimpleLoadMore.ERROR);
        }
    }

    @Override
    public void onNetWorkComplete() {
        if (page == 0) {
            mAdapter.refreshState(SimpleRefresh.SUCCESS);
        } else {
            mAdapter.loadMoreState(SimpleLoadMore.SUCCESS);
        }
    }

    @Override
    public void onNetWorkSuccess(final List<NetWorkBean> data) {
        mAdapter.addAllData(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxNetWork.getInstance().cancel(getClass().getSimpleName());
    }
}
