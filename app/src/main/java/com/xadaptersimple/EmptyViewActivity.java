package com.xadaptersimple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.xadapter.adapter.XBaseAdapter;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2016/11/17
 */

public class EmptyViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        List<MainBean> mainBean = new ArrayList<>();
        XRecyclerViewAdapter<MainBean> xRecyclerViewAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(
                xRecyclerViewAdapter
                        .initXData(mainBean)
                        .addRecyclerView(recyclerView)
                        .setEmptyView(findViewById(R.id.emptyView))
                        .setLayoutId(R.layout.item)
                        .setOnXEmptyViewListener(new XBaseAdapter.OnXEmptyViewListener() {
                            @Override
                            public void onXEmptyViewClick(View view) {
                                Toast.makeText(EmptyViewActivity.this, "emptyView", Toast.LENGTH_SHORT).show();
                            }
                        })
        );

        xRecyclerViewAdapter.isShowEmptyView();
    }
}
