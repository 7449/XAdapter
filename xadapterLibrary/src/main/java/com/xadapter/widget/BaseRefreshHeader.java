package com.xadapter.widget;

/**
 * by y on 15/11/22
 */
public interface BaseRefreshHeader {


    void onMove(float delta);

    boolean releaseAction();

    void refreshComplete(@HeaderLayout.RefreshState int state);


}