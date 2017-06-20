package com.xadaptersimple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;
import com.xadapter.listener.LoadListener;
import com.xadapter.listener.OnXBindListener;
import com.xadapter.widget.SimpleLoadMore;
import com.xadapter.widget.SimpleRefresh;
import com.xadaptersimple.data.DataUtils;
import com.xadaptersimple.data.MainBean;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2016/11/17
 */

public class GridLayoutManagerActivity extends AppCompatActivity {
    private XRecyclerViewAdapter<MainBean> xRecyclerViewAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        List<MainBean> mainBean = new ArrayList<>();
        DataUtils.getData(mainBean);
        xRecyclerViewAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(
                xRecyclerViewAdapter
                        .initXData(mainBean)
                        .addRecyclerView(recyclerView)
                        .setLayoutId(R.layout.item)
                        .setPullRefreshEnabled(true)
                        .setLoadingMoreEnabled(true)
                        .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, (ViewGroup) findViewById(android.R.id.content), false))
                        .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, (ViewGroup) findViewById(android.R.id.content), false))
                        .onXBind(new OnXBindListener<MainBean>() {
                            @Override
                            public void onXBind(XViewHolder holder, int position, MainBean mainBean) {
                                holder.setTextView(R.id.tv_name, mainBean.getName());
                                holder.setTextView(R.id.tv_age, mainBean.getAge() + "");
                            }
                        })
                        .setLoadListener(new LoadListener() {
                            @Override
                            public void onRefresh() {
                                recyclerView.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        xRecyclerViewAdapter.refreshState(SimpleRefresh.SUCCESS);
                                    }
                                }, 1500);
                            }

                            @Override
                            public void onLoadMore() {
                                recyclerView.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        xRecyclerViewAdapter.loadMoreState(SimpleLoadMore.NOMORE);
                                    }
                                }, 1500);
                            }
                        })
        );
    }
}
