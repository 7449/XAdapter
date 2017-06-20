package com.xadapter.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * by y on 2017/6/19.
 */

public abstract class XRefreshView extends FrameLayout {

    public static final int NORMAL = 0; //初始状态
    public static final int READY = 1; //准备
    public static final int REFRESH = 2; //正在刷新
    public static final int SUCCESS = 3; // 刷新成功
    public static final int ERROR = 4; // 刷新失败
    public int mState = NORMAL;
    protected View rootView;
    protected int mMeasuredHeight;


    public XRefreshView(Context context) {
        super(context);
        init();
    }


    public XRefreshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XRefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        rootView = View.inflate(getContext(), getLayoutId(), null);
        addView(rootView, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        initView();
        measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
    }

    public int getState() {
        return mState;
    }

    public void setState(@RefreshState int state) {
        if (state == mState) {
            return;
        }
        onStart();
        switch (state) {
            case NORMAL:
                onNormal();
                break;
            case READY:
                onReady();
                break;
            case REFRESH:
                onRefresh();
                break;
            case SUCCESS:
                onSuccess();
                break;
            case ERROR:
                onError();
                break;
        }
        mState = state;
    }

    public void refreshState(int state) {
        setState(state);
        postDelayed(new Runnable() {
            public void run() {
                smoothScrollTo(0);
            }
        }, 200);
    }

    public int getVisibleHeight() {
        LayoutParams lp = (LayoutParams) rootView.getLayoutParams();
        return lp.height;
    }

    private void setVisibleHeight(int height) {
        if (height == 0) {
            setState(NORMAL);
        }
        if (height < 0) {
            height = 0;
        }
        LayoutParams lp = (LayoutParams) rootView.getLayoutParams();
        lp.height = height;
        rootView.setLayoutParams(lp);
    }

    public void onMove(float delta) {
        if (getVisibleHeight() > 0 || delta > 0) {
            setVisibleHeight((int) delta + getVisibleHeight());
            if (mState < REFRESH) {
                if (getVisibleHeight() > mMeasuredHeight) {
                    setState(READY);
                } else {
                    setState(NORMAL);
                }
            }
        }
    }

    public boolean releaseAction() {
        boolean isOnRefresh = false;
        if (getVisibleHeight() > mMeasuredHeight && mState < REFRESH) {
            setState(REFRESH);
            isOnRefresh = true;
        }
        int destHeight = 0;
        if (mState == REFRESH) {
            destHeight = mMeasuredHeight;
        }
        smoothScrollTo(destHeight);
        return isOnRefresh;
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight).setDuration(300);
        animator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        setVisibleHeight((int) animation.getAnimatedValue());
                    }
                });
        animator.start();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void onStart();

    protected abstract void onNormal();

    protected abstract void onReady();

    protected abstract void onRefresh();

    protected abstract void onSuccess();

    protected abstract void onError();

    @IntDef({NORMAL,
            READY,
            REFRESH,
            SUCCESS,
            ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RefreshState {
    }
}
