package com.xadapter;

/**
 * by y on 2017/3/18.
 * The callback interface definition to invoke in the project
 */
public interface LoadListener {

    /**
     * Drop-down refresh callback
     */
    void onRefresh();

    /**
     * The pull-up callback is loaded
     */
    void onLoadMore();
}