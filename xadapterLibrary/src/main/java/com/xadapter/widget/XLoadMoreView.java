package com.xadapter.widget;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * by y on 2017/6/19.
 */

public abstract class XLoadMoreView extends FrameLayout {
    public final static int NORMAL = -1;
    public final static int LOAD = 0;
    public final static int SUCCESS = 1;
    public final static int NOMORE = 2;
    public final static int ERROR = 3;
    public int mState;
    protected View rootView;

    public XLoadMoreView(Context context) {
        super(context);
        init();
    }


    public XLoadMoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XLoadMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        rootView = View.inflate(getContext(), getLayoutId(), null);
        addView(rootView);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        initView();
    }

    protected abstract void initView();

    protected abstract int getLayoutId();

    protected abstract void onStart();

    protected abstract void onLoad();

    protected abstract void onNoMore();

    protected abstract void onSuccess();

    protected abstract void onError();

    protected abstract void onNormal();

    public int getState() {
        return mState;
    }

    public void setState(@XLoadMoreView.LoadMoreStatus int state) {
        if (state == mState) {
            return;
        }
        onStart();
        switch (state) {
            case LOAD:
                onLoad();
                break;
            case NOMORE:
                onNoMore();
                break;
            case SUCCESS:
                onSuccess();
                break;
            case ERROR:
                onError();
                break;
            case NORMAL:
                onNormal();
                break;
        }
        mState = state;
    }

    @IntDef({LOAD,
            NORMAL,
            SUCCESS,
            NOMORE,
            ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadMoreStatus {
    }

}
