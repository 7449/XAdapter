package com.xadaptersimple.net;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xadapter.LoadListener;
import com.xadapter.OnXBindListener;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;
import com.xadapter.widget.LoadMore;
import com.xadapter.widget.Refresh;
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

    private boolean isFirstRefresh = true;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setAdapter(
                mAdapter
                        .setEmptyView(findViewById(R.id.emptyView))
                        .addRecyclerView(recyclerView)
                        .setLayoutId(R.layout.network_item)
                        .onXBind(this)
                        .setPullRefreshEnabled(true)
                        .setLoadingMoreEnabled(true)
                        .setLoadListener(this)
        );


        //进来就进入刷新状态
        mAdapter.setRefreshing(true);
    }


    @Override
    public void onRefresh() {
        isFirstRefresh = true;
        mAdapter.removeAll();
        netWork();
    }

    @Override
    public void onLoadMore() {
        isFirstRefresh = false;
        netWork();
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
        if (!isFirstRefresh) {
            mAdapter.loadMoreComplete(LoadMore.LOAD);
        }
    }

    @Override
    public void onNetWorkError(Throwable e) {
        if (isFirstRefresh) {
            mAdapter.refreshComplete(Refresh.ERROR);
        } else {
            mAdapter.loadMoreComplete(LoadMore.ERROR);
        }
    }

    @Override
    public void onNetWorkComplete() {
        Toast.makeText(getApplicationContext(), "DONE", Toast.LENGTH_SHORT).show();
        if (isFirstRefresh) {
            mAdapter.refreshComplete(Refresh.COMPLETE);
        } else {
            mAdapter.loadMoreComplete(LoadMore.COMPLETE);
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
