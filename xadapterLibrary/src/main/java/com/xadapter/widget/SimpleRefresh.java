package com.xadapter.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.xadapter.R;

/**
 * by y on 2016/11/16
 */

public class SimpleRefresh extends XRefreshView {

    private AppCompatImageView progressBar;
    private AppCompatTextView mText;
    private AnimationDrawable animationDrawable;
    private AppCompatImageView mTipsIv;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    public SimpleRefresh(Context context) {
        super(context);
    }

    public SimpleRefresh(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleRefresh(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initView() {
        progressBar = (AppCompatImageView) findViewById(R.id.progressbar);
        mText = (AppCompatTextView) findViewById(R.id.tv_tips);
        mTipsIv = (AppCompatImageView) findViewById(R.id.iv_tips);
        animationDrawable = (AnimationDrawable) progressBar.getBackground();
        mText.setText("下拉刷新");
        initAnimation();
    }

    private void initAnimation() {
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(180);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(180);
        mRotateDownAnim.setFillAfter(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.simple_refresh;
    }

    @Override
    protected void onStart() {
        mTipsIv.clearAnimation();
        mTipsIv.setVisibility(VISIBLE);
        progressBar.setVisibility(INVISIBLE);
    }

    @Override
    protected void onNormal() {
        if (mState == READY) {
            mTipsIv.startAnimation(mRotateDownAnim);
        } else {
            mTipsIv.clearAnimation();
        }
        mTipsIv.setVisibility(VISIBLE);
        progressBar.setVisibility(INVISIBLE);
        animationDrawable.stop();
        mText.setText("下拉刷新");
    }

    @Override
    protected void onReady() {
        mTipsIv.startAnimation(mRotateUpAnim);
        progressBar.setVisibility(INVISIBLE);
        mTipsIv.setVisibility(VISIBLE);
        animationDrawable.stop();
        mText.setText("释放立即刷新");
    }

    @Override
    protected void onRefresh() {
        progressBar.setVisibility(VISIBLE);
        mTipsIv.setVisibility(INVISIBLE);
        animationDrawable.start();
        mText.setText("正在刷新...");
    }

    @Override
    protected void onSuccess() {
        progressBar.setVisibility(INVISIBLE);
        mTipsIv.setVisibility(INVISIBLE);
        animationDrawable.stop();
        mTipsIv.clearAnimation();
        mText.setText("刷新成功");
    }

    @Override
    protected void onError() {
        progressBar.setVisibility(INVISIBLE);
        mTipsIv.setVisibility(INVISIBLE);
        animationDrawable.stop();
        mTipsIv.clearAnimation();
        mText.setText("刷新失败");
    }


}
