package com.xadapter.adapter.multi;

/**
 * by y on 2017/3/9
 */

public interface MultiCallBack {

    int TYPE_ITEM = -11;

    boolean hasClick();

    int getItemType();

    int getPosition();
}
