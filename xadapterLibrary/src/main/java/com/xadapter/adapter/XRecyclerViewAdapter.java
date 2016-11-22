package com.xadapter.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xadapter.holder.XViewHolder;
import com.xadapter.manager.XScrollBottom;

import java.util.List;

/**
 * by y on 2016/11/15
 * <p>
 * List Collection Data Use this Adapter
 */

public class XRecyclerViewAdapter<T> extends XBaseAdapter<T>
        implements XScrollBottom {


    public XRecyclerViewAdapter() {
    }

    /**
     * You must call this method to initialize the data
     *
     * @param data mDatas
     */
    public XRecyclerViewAdapter<T> initXData(List<T> data) {
        mDatas = data;
        return this;
    }

    /**
     * @param recyclerView this is recyclerView
     */
    public XRecyclerViewAdapter<T> addRecyclerView(RecyclerView recyclerView) {
        linkRecyclerView(recyclerView);
        return this;
    }

    /**
     * Sets the view to show if the adapter is empty
     * Called after addRecyclerView
     *
     * @param view this is emptyView
     */
    public XRecyclerViewAdapter<T> setEmptyView(View view) {
        mEmptyView = view;
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnEmptyViewListener != null) {
                        mOnEmptyViewListener.onXEmptyViewClick(view);
                    }
                }
            });
        }
        return this;
    }


    public void addAllData(List<T> data) {
        if (isDataEmpty()) {
            mDatas.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addData(T data) {
        if (isDataEmpty()) {
            mDatas.add(data);
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        if (isDataEmpty()) {
            mDatas.remove(position);
            notifyDataSetChanged();
        }
    }

    public void removeAll() {
        if (isDataEmpty()) {
            mDatas.clear();
            notifyDataSetChanged();
        }
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
                return new XViewHolder(getView(parent));
        }
    }

    @Override
    public void onBindViewHolder(XViewHolder holder, @SuppressLint("RecyclerView") final int position) {
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
        return mDatas == null ? 0 : getDataSize() + getFooterViewCount() + getHeaderViewCount();
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
     * mDatas is null?
     */
    private boolean isDataEmpty() {
        return mDatas != null;
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
//        mDatas.isEmpty()
        if (mDatas.size() == 0) {
            showEmptyView();
        } else {
            hideEmptyView();
        }
    }

    public void showEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void hideEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

}
