package com.xadapter.manager;

import android.view.MotionEvent;
import android.view.View;

import com.xadapter.widget.FooterLayout;
import com.xadapter.widget.HeaderLayout;

/**
 * by y on 2016/11/15
 */

public class XTouchListener implements View.OnTouchListener {
    private HeaderLayout mRefreshHeaderLayout = null;
    private FooterLayout footerLayout = null;
    private boolean isRefreshHeader = false;
    private float rawY = -1;
    private RefreshInterface refreshInterface = null;
    private static final int DAMP = 4;
    private AppBarStateChangeListener.State state = AppBarStateChangeListener.State.EXPANDED;

    public void setState(AppBarStateChangeListener.State state) {
        this.state = state;
    }


    public XTouchListener(
            HeaderLayout mRefreshHeaderLayout,
            FooterLayout mFooterLayout, boolean isRefreshHeader,
            RefreshInterface refreshInterface) {
        this.footerLayout = mFooterLayout;
        this.mRefreshHeaderLayout = mRefreshHeaderLayout;
        this.isRefreshHeader = isRefreshHeader;
        this.refreshInterface = refreshInterface;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (null == mRefreshHeaderLayout
                || !isRefreshHeader
                || mRefreshHeaderLayout.getState() == HeaderLayout.STATE_REFRESHING
                || (footerLayout != null && footerLayout.getState() == FooterLayout.STATE_LOADING)) {
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
                    mRefreshHeaderLayout.onMove(deltaY / DAMP);
                    if (mRefreshHeaderLayout.getVisibleHeight() > 0 && mRefreshHeaderLayout.getState() < HeaderLayout.STATE_DONE) {
                        return true;
                    }
                }
                break;
            default:
                rawY = -1;
                if (isTop() && isRefreshHeader && state == AppBarStateChangeListener.State.EXPANDED) {
                    if (mRefreshHeaderLayout.releaseAction()) {
                        if (refreshInterface != null) {
                            refreshInterface.onRefresh();
                        }
                    }
                }
                break;
        }
        return false;
    }

    private boolean isTop() {
        return mRefreshHeaderLayout != null && mRefreshHeaderLayout.getParent() != null;
    }

    public interface RefreshInterface {
        void onRefresh();
    }
}
