package com.xadapter.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xadapter.holder.XViewHolder;

import java.util.List;

/**
 * by y on 2016/11/15
 * <p>
 * List Collection Data Use this Adapter
 */

public class XRecyclerViewAdapter<T> extends XBaseAdapter<T> {

    /**
     * You must call this method to initialize the data
     *
     * @param data mDatas
     */
    public XRecyclerViewAdapter<T> initXData(@NonNull List<T> data) {
        mDatas = data;
        return this;
    }

    /**
     * @param recyclerView this is recyclerView
     */
    public XRecyclerViewAdapter<T> addRecyclerView(@NonNull RecyclerView recyclerView) {
        linkRecyclerView(recyclerView);
        return this;
    }

    /**
     * Sets the view to show if the adapter is empty
     * Called after addRecyclerView
     *
     * @param view this is emptyView
     */
    public XRecyclerViewAdapter<T> setEmptyView(@NonNull View view) {
        mEmptyView = view;
        mEmptyView.setVisibility(View.GONE);
        return this;
    }

    /**
     * Sets the view to show if the adapter is empty
     * Called after addRecyclerView
     *
     * @param view    this is emptyView
     * @param isClick Whether to enable click trigger trigger drop
     */
    public XRecyclerViewAdapter<T> setEmptyView(@NonNull View view, boolean isClick) {
        mEmptyView = view;
        if (isClick)
            mEmptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRefreshing(true);
                }
            });
        mEmptyView.setVisibility(View.GONE);
        return this;
    }

    /**
     * Sets the view to show if the adapter is netWorkErrorView
     * Called after addRecyclerView
     *
     * @param view this is netWorkErrorView
     */
    public XRecyclerViewAdapter<T> setNetWorkErrorView(@NonNull View view) {
        mNetWorkErrorView = view;
        mNetWorkErrorView.setVisibility(View.GONE);
        return this;
    }

    /**
     * Sets the view to show if the adapter is netWorkErrorView
     * Called after addRecyclerView
     *
     * @param view    this is netWorkErrorView
     * @param isClick Whether to enable click trigger trigger drop
     */
    public XRecyclerViewAdapter<T> setNetWorkErrorView(@NonNull View view, boolean isClick) {
        mNetWorkErrorView = view;
        if (isClick) {
            mNetWorkErrorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRefreshing(true);
                }
            });
        }
        mNetWorkErrorView.setVisibility(View.GONE);
        return this;
    }

    public T previousItem(int position) {
        if (position == 0) {
            return mDatas.get(0);
        }
        return mDatas.get(position - 1);
    }

    public void addAllData(@NonNull List<T> data) {
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(@NonNull T data) {
        mDatas.add(data);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void removeAll() {
        mDatas.clear();
        notifyDataSetChanged();
    }


    @Override
    public XViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViewType.contains(viewType)) {
            return new XViewHolder(mHeaderViews.get(viewType / super.viewType));
        }
        if (mFooterViewType.contains(viewType)) {
            return new XViewHolder(mFooterViews.get(viewType / super.viewType - mDatas.size() - getHeaderViewCount()));
        }
        switch (viewType) {
            case TYPE_REFRESH_HEADER:
                return new XViewHolder(mHeaderLayout);
            case TYPE_LOADMORE_FOOTER:
                return new XViewHolder(mFooterLayout);
            default:
                return new XViewHolder(LayoutInflater.from(parent.getContext()).inflate(ITEM_LAYOUT_ID, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(XViewHolder holder, int position) {
        if (getItemViewType(position) != TYPE_ITEM) {
            return;
        }
        final int pos = getItemPosition(position);
        final T t = mDatas.get(pos);
        if (t == null) {
            return;
        }
        initClickListener(holder, pos, t);
        if (mOnXBindListener != null) {
            mOnXBindListener.onXBind(holder, pos, t);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (isRefreshHeaderType(position)) {
            return TYPE_REFRESH_HEADER;
        }
        if (isLoadMoreType(position)) {
            return TYPE_LOADMORE_FOOTER;
        }
        if (isPullRefreshEnabled()) {
            position -= 1;
        }
        if (isHeaderType(position)) {
            mHeaderViewType.add(position * viewType);
            return position * viewType;
        }
        if (isFooterType(position)) {
            mFooterViewType.add(position * viewType);
            return position * viewType;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return getDataSize() + getFooterViewCount() + getHeaderViewCount();
    }

    private int getDataSize() {
        int tempPosition;
        if (loadingMoreEnabled && pullRefreshEnabled) {
            tempPosition = 2;
        } else if (loadingMoreEnabled || pullRefreshEnabled) {
            tempPosition = 1;
        } else {
            tempPosition = 0;
        }
        return mDatas.size() + tempPosition;
    }

    /**
     * whether it is refreshHeader position
     */
    private boolean isRefreshHeaderType(int position) {
        return pullRefreshEnabled && position == 0;
    }

    /**
     * whether it is header position
     */
    private boolean isHeaderType(int position) {
        return getHeaderViewCount() != 0 && position < getHeaderViewCount();
    }

    /**
     * whether it is footer position
     */
    private boolean isFooterType(int position) {
        return getFooterViewCount() != 0 && position >= mDatas.size() + getHeaderViewCount();
    }

    /**
     * Whether it is loaded position
     */
    private boolean isLoadMoreType(int position) {
        return loadingMoreEnabled && position == getItemCount() - 1;
    }

    /**
     * gets the correct position
     */
    private int getItemPosition(int position) {
        if (pullRefreshEnabled) {
            position -= 1;
        }
        return position - getHeaderViewCount();
    }

    /**
     * Whether to display emptyView requires the user to manually invoke it
     */
    public void isShowEmptyView() {
        if (recyclerView == null || mEmptyView == null) {
            return;
        }
        if (mDatas.size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Whether to display netWorkErrorView requires the user to manually invoke it
     */
    public void isShowNetWorkErrorView() {
        if (recyclerView == null || mNetWorkErrorView == null) {
            return;
        }
        if (mDatas.size() == 0) {
            mNetWorkErrorView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            mNetWorkErrorView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
