package com.xadaptersimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;
import com.xadapter.listener.OnXBindListener;
import com.xadaptersimple.data.DataUtils;
import com.xadaptersimple.data.MainBean;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2016/11/17
 */

public class StaggeredGridLayoutManagerActivity extends AppCompatActivity {
    private XRecyclerViewAdapter<MainBean> xRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        List<MainBean> mainBeen = new ArrayList<>();
        DataUtils.getData(mainBeen);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        xRecyclerViewAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setAdapter(
                xRecyclerViewAdapter
                        .initXData(mainBeen)
                        .setLayoutId(R.layout.item)
                        .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, (ViewGroup) findViewById(android.R.id.content), false))
                        .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, (ViewGroup) findViewById(android.R.id.content), false))
                        .onXBind(new OnXBindListener<MainBean>() {
                            @Override
                            public void onXBind(XViewHolder holder, int position, MainBean mainBean) {
                                holder.setTextView(R.id.tv_name, mainBean.getName());
                                holder.setTextView(R.id.tv_age, mainBean.getAge() + "");
                            }
                        })
        );
    }
}
