package com.xadapter.manager;

import android.view.MotionEvent;
import android.view.View;

import com.xadapter.widget.XLoadMoreView;
import com.xadapter.widget.XRefreshView;

/**
 * by y on 2016/11/15
 */

public class XTouchListener implements View.OnTouchListener {
    private static final int DAMP = 3;
    private XRefreshView refreshView = null;
    private XLoadMoreView loadMoreView = null;
    private boolean isRefreshHeader = false;
    private float rawY = -1;
    private RefreshInterface refreshInterface = null;
    private AppBarStateChangeListener.State state = AppBarStateChangeListener.State.EXPANDED;

    public XTouchListener(
            XRefreshView refreshView,
            XLoadMoreView loadMoreView,
            boolean isRefreshHeader,
            RefreshInterface refreshInterface) {
        this.loadMoreView = loadMoreView;
        this.refreshView = refreshView;
        this.isRefreshHeader = isRefreshHeader;
        this.refreshInterface = refreshInterface;
    }

    public void setState(AppBarStateChangeListener.State state) {
        this.state = state;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (null == refreshView
                || !isRefreshHeader
                || refreshView.getState() == XRefreshView.REFRESH
                || (loadMoreView != null && loadMoreView.getState() == XLoadMoreView.LOAD)) {
            return false;
        }
        if (rawY == -1) {
            rawY = motionEvent.getRawY();
        }
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                rawY = motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = motionEvent.getRawY() - rawY;
                rawY = motionEvent.getRawY();
                if (isTop() && isRefreshHeader && state == AppBarStateChangeListener.State.EXPANDED) {
                    refreshView.onMove(deltaY / DAMP);
                    if (refreshView.getVisibleHeight() > 0 && refreshView.getState() < XRefreshView.SUCCESS) {
                        return true;
                    }
                }
                break;
            default:
                rawY = -1;
                if (isTop() && isRefreshHeader && state == AppBarStateChangeListener.State.EXPANDED) {
                    if (refreshView.releaseAction() && refreshInterface != null) {
                        refreshInterface.onRefresh();
                    }
                }
                break;
        }
        return false;
    }

    private boolean isTop() {
        return refreshView != null && refreshView.getParent() != null;
    }

    public interface RefreshInterface {
        void onRefresh();
    }
}
