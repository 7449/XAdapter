package com.xadapter.adapter;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.xadapter.holder.XViewHolder;
import com.xadapter.listener.FooterClickListener;
import com.xadapter.listener.LoadListener;
import com.xadapter.listener.OnItemClickListener;
import com.xadapter.listener.OnItemLongClickListener;
import com.xadapter.listener.OnXBindListener;
import com.xadapter.manager.AppBarStateChangeListener;
import com.xadapter.manager.XScrollListener;
import com.xadapter.manager.XTouchListener;
import com.xadapter.widget.SimpleLoadMore;
import com.xadapter.widget.SimpleRefresh;
import com.xadapter.widget.XLoadMoreView;
import com.xadapter.widget.XRefreshView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * by y on 2016/11/15
 * <p>
 * XBaseAdapter, all need to use the location of T should be sentenced to empty the deal
 */
public abstract class XBaseAdapter<T> extends RecyclerView.Adapter<XViewHolder>
        implements XScrollListener.XScrollBottom,
        XTouchListener.RefreshInterface {


    static final int TYPE_ITEM = -1;
    static final int TYPE_REFRESH_HEADER = 0;
    static final int TYPE_LOADMORE_FOOTER = 1;
    final ArrayList<View> mHeaderViews = new ArrayList<>();
    final ArrayList<View> mFooterViews = new ArrayList<>();
    final ArrayList<Integer> mHeaderViewType = new ArrayList<>();
    final ArrayList<Integer> mFooterViewType = new ArrayList<>();
    final int viewType = 100000;
    protected List<T> mDatas = new LinkedList<>();
    protected View mEmptyView = null;
    protected View mNetWorkErrorView = null;
    /**
     * This is the callback interface to get the data
     */
    OnXBindListener<T> mOnXBindListener = null;
    /**
     * The subclass implements the display of the layout
     */
    int ITEM_LAYOUT_ID = -1;
    /**
     * The recyclerview gets the sliding state and sets the emptyView
     */
    RecyclerView recyclerView = null;
    /**
     * Whether to open the drop-down refresh,The default is off
     */
    boolean pullRefreshEnabled = false;
    /**
     * this is pull-up layout
     */
    XRefreshView refreshView = null;
    /**
     * Whether to enable pull-up,The default is off
     */
    boolean loadingMoreEnabled = false;
    /**
     * this is loadMore layout
     */
    XLoadMoreView loadMoreView = null;
    /**
     * The listener that receives notifications when an item is clicked.
     */
    private OnItemClickListener<T> mOnItemClickListener = null;
    /**
     * The listener that receives notifications when an item is long clicked.
     */
    private OnItemLongClickListener<T> mOnLongClickListener = null;
    /**
     * Pull-down refreshes the listener that receives notification when a pull-up is loaded.
     */
    private LoadListener mLoadingListener = null;
    /**
     * Click the callback
     */
    private FooterClickListener mFooterListener = null;
    private XTouchListener touchListener = null;

    /**
     * @param footerListener The callback that will be invoked.
     */
    public XBaseAdapter<T> setFooterListener(@NonNull FooterClickListener footerListener) {
        this.mFooterListener = footerListener;
        return this;
    }

    /**
     * Register the refresh callback inside the XAdapter
     *
     * @param mLoadingListener The callback that will be invoked.
     */
    public XBaseAdapter<T> setLoadListener(@NonNull LoadListener mLoadingListener) {
        this.mLoadingListener = mLoadingListener;
        return this;
    }

    /**
     * Register a callback to be invoked when an item in this XBaseAdapter has
     * been clicked.
     *
     * @param mOnItemClickListener The callback that will be invoked.
     */
    public XBaseAdapter<T> setOnItemClickListener(@NonNull OnItemClickListener<T> mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
        return this;
    }

    /**
     * Register a callback to be invoked when an item in this XBaseAdapter has
     * been clicked and held
     *
     * @param mOnLongClickListener The callback that will run
     */
    public XBaseAdapter<T> setOnLongClickListener(@NonNull OnItemLongClickListener<T> mOnLongClickListener) {
        this.mOnLongClickListener = mOnLongClickListener;
        return this;
    }

    /**
     * @param onXBindListener The callback that will run
     */
    public XBaseAdapter<T> onXBind(@NonNull OnXBindListener<T> onXBindListener) {
        this.mOnXBindListener = onXBindListener;
        return this;
    }

    /**
     * Use this method to get the layout xml
     *
     * @param id This is the item resource
     */
    public XBaseAdapter<T> setLayoutId(int id) {
        this.ITEM_LAYOUT_ID = id;
        return this;
    }


    XBaseAdapter<T> linkRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        if (recyclerView != null) {
            initHeaderAndFooter();
        }
        return this;
    }

    private void initHeaderAndFooter() {
        if (refreshView == null) {
            refreshView = new SimpleRefresh(recyclerView.getContext());
        }
        if (loadMoreView == null) {
            loadMoreView = new SimpleLoadMore(recyclerView.getContext());
        }
        refreshView.setState(XRefreshView.NORMAL);
        loadMoreView.setState(XLoadMoreView.NORMAL);
        loadMoreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFooterListener != null)
                    mFooterListener.onXFooterClick(v);
            }
        });
        loadMoreView.setVisibility(View.GONE);
    }

    @Override
    public void onScrollBottom() {
        if (isLoad()) {
            if (isLoadingMoreEnabled() && refreshView.getState() != XRefreshView.REFRESH) {
                loadMoreView.setVisibility(View.VISIBLE);
                loadMoreView.setState(XLoadMoreView.LOAD);
                mLoadingListener.onLoadMore();
            }
        }
    }


    private boolean isLoad() {
        return mLoadingListener != null && refreshView != null && loadMoreView != null;
    }

    @Override
    public void onRefresh() {
        if (isLoad()) {
            if (isPullRefreshEnabled() && loadMoreView.getState() != XLoadMoreView.LOAD) {
                loadMoreView.setVisibility(View.GONE);
                loadMoreView.setState(XLoadMoreView.NORMAL);
                loadMoreView.hideHeight(true);
                mLoadingListener.onRefresh();
            }
        }
    }

    /**
     * get a click event
     *
     * @param holder   this is Adapter Holder
     * @param position this is position
     * @param t        this is adapter mDatas
     */
    void initClickListener(XViewHolder holder, final int position, final T t) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, position, t);
                }
            });
        }

        if (mOnLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return mOnLongClickListener.onLongClick(view, position, t);
                }
            });
        }
    }

    /**
     * Whether the pull is loaded
     */
    public boolean isLoadingMoreEnabled() {
        return loadingMoreEnabled;
    }

    /**
     * Set the pull to load true to turn false off
     *
     * @param loadingMoreEnabled loadingMore status
     */
    public XBaseAdapter<T> setLoadingMoreEnabled(boolean loadingMoreEnabled) {
        if (loadingMoreEnabled && recyclerView == null) {
            throw new NullPointerException("Detect recyclerView is null, addRecyclerView () if using pull-down refresh or pull-up load");
        }
        this.loadingMoreEnabled = loadingMoreEnabled;
        if (loadingMoreEnabled) {
            recyclerView.addOnScrollListener(new XScrollListener(this));
        }
        return this;
    }

    /**
     * Whether the drop-down refresh
     */
    public boolean isPullRefreshEnabled() {
        return pullRefreshEnabled;
    }

    /**
     * Set the pulldown refresh true to turn false off
     *
     * @param pullRefreshEnabled pullRefresh status
     */
    public XBaseAdapter<T> setPullRefreshEnabled(boolean pullRefreshEnabled) {
        if (pullRefreshEnabled && recyclerView == null) {
            throw new NullPointerException("Detect recyclerView is null, addRecyclerView () if using pull-down refresh or pull-up load");
        }
        this.pullRefreshEnabled = pullRefreshEnabled;
        touchListener = new XTouchListener(refreshView, loadMoreView, pullRefreshEnabled, this);
        recyclerView.setOnTouchListener(touchListener);
        return this;
    }


    /**
     * @param view the added header view
     */
    public XBaseAdapter<T> addHeaderView(@NonNull View view) {
        mHeaderViews.add(view);
        return this;
    }

    /**
     * gets the number of headers
     */
    public int getHeaderViewCount() {
        return mHeaderViews.size();
    }

    /**
     * @param view the added footer view
     */
    public XBaseAdapter<T> addFooterView(@NonNull View view) {
        mFooterViews.add(view);
        return this;
    }

    /**
     * gets the number of footers
     */
    public int getFooterViewCount() {
        return mFooterViews.size();
    }

    /**
     * Whether it just came in to refresh
     */
    public XBaseAdapter<T> refresh() {
        if (isLoad() && pullRefreshEnabled && mLoadingListener != null) {
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.GONE);
            }
            if (mNetWorkErrorView != null) {
                mNetWorkErrorView.setVisibility(View.GONE);
            }
            if (recyclerView != null) {
                recyclerView.setVisibility(View.VISIBLE);
            }
            refreshView.setState(XRefreshView.REFRESH);
            refreshView.onMove(refreshView.getMeasuredHeight());
            mLoadingListener.onRefresh();
            loadMoreView.setVisibility(View.GONE);
            loadMoreView.setState(XLoadMoreView.NORMAL);
            loadMoreView.hideHeight(true);
        }
        return this;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (!(getItemViewType(position) == TYPE_ITEM)) {
                        return gridManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(XViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams stagger = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            if (!(getItemViewType(holder.getLayoutPosition()) == TYPE_ITEM)) {
                stagger.setFullSpan(true);
            } else {
                stagger.setFullSpan(false);
            }
        }
        if (recyclerView == null) {
            return;
        }
        AppBarLayout appBarLayout = null;
        ViewParent p = recyclerView.getParent();
        while (p != null) {
            if (p instanceof CoordinatorLayout) {
                break;
            }
            p = p.getParent();
        }
        if (p != null) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) p;
            final int childCount = coordinatorLayout.getChildCount();
            for (int i = childCount - 1; i >= 0; i--) {
                final View child = coordinatorLayout.getChildAt(i);
                if (child instanceof AppBarLayout) {
                    appBarLayout = (AppBarLayout) child;
                    break;
                }
            }
            if (appBarLayout != null && touchListener != null) {
                appBarLayout.addOnOffsetChangedListener(
                        new AppBarStateChangeListener() {
                            @Override
                            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                                touchListener.setState(state);
                            }
                        });
            }
        }
    }

    public void refreshState(@XRefreshView.RefreshState int state) {
        if (refreshView != null) {
            refreshView.refreshState(state);
            if (refreshView.getState() == XRefreshView.SUCCESS) {
                loadMoreView.hideHeight(false);
            }
            if (refreshView.getState() == XRefreshView.REFRESH && loadMoreView != null) {
                loadMoreView.setVisibility(View.GONE);
                loadMoreView.hideHeight(true);
            }
        }
    }

    public void loadMoreState(@XLoadMoreView.LoadMoreStatus int state) {
        if (loadMoreView != null) {
            if (loadMoreView.getVisibility() == View.GONE) {
                loadMoreView.setVisibility(View.VISIBLE);
            }
            loadMoreView.setState(state);
        }
    }

    public int getLoadMoreState() {
        return loadMoreView.getState();
    }

    public int getRefreshState() {
        return refreshView.getState();
    }

}
