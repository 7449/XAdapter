package com.xadapter.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import com.xadapter.R;

/**
 * by y on 2016/9/29
 */

public class SimpleLoadMore extends XLoadMoreView {

    private AppCompatImageView progressBar;
    private AppCompatTextView mText;
    private AnimationDrawable animationDrawable;

    public SimpleLoadMore(Context context) {
        super(context);
    }

    public SimpleLoadMore(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleLoadMore(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.simple_load_more;
    }

    @Override
    public void initView() {
        progressBar = (AppCompatImageView) findViewById(R.id.progressbar);
        mText = (AppCompatTextView) findViewById(R.id.tv_tips);
        animationDrawable = (AnimationDrawable) progressBar.getBackground();
        mText.setText("上拉加载");
    }

    @Override
    protected void onStart() {
        progressBar.setVisibility(GONE);
        animationDrawable.stop();
    }

    @Override
    protected void onLoad() {
        progressBar.setVisibility(View.VISIBLE);
        animationDrawable.start();
        mText.setText("正在加载...");
    }

    @Override
    protected void onNoMore() {
        progressBar.setVisibility(View.GONE);
        animationDrawable.stop();
        mText.setText("没有数据了");
    }

    @Override
    protected void onSuccess() {
        progressBar.setVisibility(View.GONE);
        animationDrawable.stop();
        mText.setText("加载成功");
    }

    @Override
    protected void onError() {
        progressBar.setVisibility(View.GONE);
        animationDrawable.stop();
        mText.setText("加载失败");
    }

    @Override
    protected void onNormal() {
        progressBar.setVisibility(View.GONE);
        animationDrawable.stop();
        mText.setText("上拉加载");
    }
}
