package com.xadaptersimple;

import com.xadapter.adapter.XMultipleAdapter;
import com.xadapter.holder.XMultipleViewHolder;

import java.util.List;

/**
 * by y on 2017/1/12.
 */

public class MultipleAdapter extends XMultipleAdapter {


    private static final int TYPE_1 = 0;
    private static final int TYPE_2 = 1;

    private List<String> strings;

    public MultipleAdapter(List<String> strings) {
        this.strings = strings;
    }


    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0:
                return TYPE_2;
            default:
                return TYPE_1;
        }
    }

    @Override
    protected int getItemLayout(int viewType) {
        switch (viewType) {
            case TYPE_1:
                return R.layout.item_type1;
            case TYPE_2:
                return R.layout.item_type2;
            default:
                return 0;
        }
    }

    @Override
    protected void onBindHolder(XMultipleViewHolder holder, int position, int viewType) {
        switch (viewType) {
            case TYPE_1:
                holder.setTextView(R.id.tv_multiple, strings.get(position - 1));
                break;
            case TYPE_2:
                holder.setTextView(R.id.tv_type2, "this is type2 item");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return strings.size() + 1;
    }
}
