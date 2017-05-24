package com.xadaptersimple;

import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

/**
 * by y on 2017/5/24.
 */

public class SimpleAdapter<T> extends XRecyclerViewAdapter<T> {


    public List<T> getData() {
        return mDatas;
    }

}
