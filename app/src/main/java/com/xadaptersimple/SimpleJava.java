package com.xadaptersimple;

import com.xadapter.adapter.XMultiAdapter;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.simple.SimpleXMultiItem;

/**
 * @author y
 */
public class SimpleJava {

    public void start() {
        XMultiAdapter<SimpleXMultiItem> simpleXMultiItemXMultiAdapter = new XMultiAdapter<>(null);
        simpleXMultiItemXMultiAdapter.setOnItemClickListener(null);
        simpleXMultiItemXMultiAdapter.setOnLongClickListener(null);
        simpleXMultiItemXMultiAdapter.setOnXMultiAdapterListener(null);

        XRecyclerViewAdapter<Object> objectXRecyclerViewAdapter = new XRecyclerViewAdapter<>();
        objectXRecyclerViewAdapter.setOnItemClickListener(null);
        objectXRecyclerViewAdapter.setItemLayoutId(0);
        objectXRecyclerViewAdapter.setOnLongClickListener(null);
    }

}
