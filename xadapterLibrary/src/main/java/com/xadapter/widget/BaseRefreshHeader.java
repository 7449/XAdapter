package com.xadapter.widget;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * by y on 15/11/22
 */
public interface BaseRefreshHeader {


    void onMove(float delta);

    boolean releaseAction();

    void refreshComplete(@RefreshState int state);

    @IntDef({HeaderLayout.STATE_NORMAL,
            HeaderLayout.STATE_RELEASE_TO_REFRESH,
            HeaderLayout.STATE_REFRESHING,
            HeaderLayout.STATE_DONE,
            HeaderLayout.STATE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    @interface RefreshState {
    }

}