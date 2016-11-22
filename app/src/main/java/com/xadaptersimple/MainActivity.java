package com.xadaptersimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.network).setOnClickListener(this);
        findViewById(R.id.LinearLayout).setOnClickListener(this);
        findViewById(R.id.GridLayout).setOnClickListener(this);
        findViewById(R.id.StaggeredGridLayout).setOnClickListener(this);
        findViewById(R.id.collapsingToolbar).setOnClickListener(this);
        findViewById(R.id.emptyView).setOnClickListener(this);
        findViewById(R.id.SwipeRefreshLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.network:
                startActivity(NetWorkActivity.class);
                break;
            case R.id.LinearLayout:
                startActivity(LinearLayoutManagerActivity.class);
                break;
            case R.id.GridLayout:
                startActivity(GridLayoutManagerActivity.class);
                break;
            case R.id.StaggeredGridLayout:
                startActivity(StaggeredGridLayoutManagerActivity.class);
                break;
            case R.id.collapsingToolbar:
                startActivity(CollapsingToolbarLayoutActivity.class);
                break;
            case R.id.emptyView:
                startActivity(EmptyViewActivity.class);
                break;
            case R.id.SwipeRefreshLayout:
                startActivity(SwipeRefreshLayoutActivity.class);
                break;
        }
    }

    private void startActivity(Class<?> clz) {
        Intent intent = new Intent(getApplicationContext(), clz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
