package com.xadapter.material;

import com.google.android.material.appbar.AppBarLayout;

public class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    public static final int EXPANDED = 0;
    public static final int COLLAPSED = 1;
    public static final int IDLE = 2;

    private int currentState = IDLE;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            currentState = EXPANDED;
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            currentState = COLLAPSED;
        } else {
            currentState = IDLE;
        }
    }

    public int getCurrentState() {
        return currentState;
    }
}
