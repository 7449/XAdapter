package com.xadaptersimple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xadapter.OnXBindListener;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;
import com.xadaptersimple.net.NetWorkBean;

import java.util.ArrayList;


/**
 * by y on 2016/11/22
 */

public class SwipeRefreshLayoutActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, OnXBindListener<NetWorkBean> {

    private XRecyclerViewAdapter<NetWorkBean> mAdapter;
    private SwipeRefreshLayout mRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.srf_layout);
        mAdapter = new XRecyclerViewAdapter<>();
        mRefresh.setOnRefreshListener(this);
        mRefresh.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter
                .setEmptyView(findViewById(R.id.emptyView))
                .initXData(new ArrayList<NetWorkBean>())
                .addRecyclerView(recyclerView)
                .setLayoutId(R.layout.network_item)
                .onXBind(this)
        );
    }

    @Override
    public void onRefresh() {
        mAdapter.removeAll();
    }

    @Override
    public void onXBind(XViewHolder holder, int position, NetWorkBean tngouBean) {
    }
}
