package com.xadapter.adapter;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.xadapter.FooterClickListener;
import com.xadapter.LoadListener;
import com.xadapter.OnItemClickListener;
import com.xadapter.OnItemLongClickListener;
import com.xadapter.OnXBindListener;
import com.xadapter.holder.XViewHolder;
import com.xadapter.manager.AppBarStateChangeListener;
import com.xadapter.manager.XScrollListener;
import com.xadapter.manager.XTouchListener;
import com.xadapter.progressindicator.ProgressStyle;
import com.xadapter.widget.LoadMore;
import com.xadapter.widget.Refresh;

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


    protected List<T> mDatas = new LinkedList<>();
    final ArrayList<View> mHeaderViews = new ArrayList<>();
    final ArrayList<View> mFooterViews = new ArrayList<>();
    final ArrayList<Integer> mHeaderViewType = new ArrayList<>();
    final ArrayList<Integer> mFooterViewType = new ArrayList<>();
    final int viewType = 100000;
    private int mRefreshProgressStyle = ProgressStyle.SysProgress;
    private int mLoadingMoreProgressStyle = ProgressStyle.SysProgress;
    protected View mEmptyView = null;
    protected View mNetWorkErrorView = null;

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
    Refresh mHeaderLayout = null;

    /**
     * Whether to enable pull-up,The default is off
     */
    boolean loadingMoreEnabled = false;

    /**
     * this is loadMore layout
     */
    LoadMore mFooterLayout = null;

    private XTouchListener touchListener = null;

    static final int TYPE_ITEM = -1;
    static final int TYPE_REFRESH_HEADER = 0;
    static final int TYPE_LOADMORE_FOOTER = 1;

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
            recyclerView.setHasFixedSize(true);
            initHeaderAndFooter();
        }
        return this;
    }

    private void initHeaderAndFooter() {
        mHeaderLayout = new Refresh(recyclerView.getContext());
        mFooterLayout = new LoadMore(recyclerView.getContext());
        refreshComplete(Refresh.READY);
        loadMoreComplete(LoadMore.NOT_LOAD);
        mHeaderLayout.setProgressStyle(mRefreshProgressStyle);
        mFooterLayout.setProgressStyle(mLoadingMoreProgressStyle);
        if (mFooterListener != null) {
            mFooterLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFooterListener.onXFooterClick(v);
                }
            });
        }
    }

    @Override
    public void onScrollBottom() {
        if (mLoadingListener != null && mFooterLayout != null) {
            if (mHeaderLayout != null && isLoadMore()) {
                return;
            }
            if (mFooterLayout.getVisibility() == View.GONE) {
                showFootLayout();
            }
            mFooterLayout.setState(LoadMore.LOAD);
            mLoadingListener.onLoadMore();
        }
    }

    private boolean isLoadMore() {
        return mHeaderLayout.getState() == Refresh.REFRESH
                ||
                mFooterLayout.getState() == LoadMore.LOAD;
    }

    @Override
    public void onRefresh() {
        if (mLoadingListener != null) {
            if (mFooterLayout != null && mFooterLayout.getVisibility() == View.VISIBLE) {
                hideFootLayout();
            }
            mLoadingListener.onRefresh();
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
        if (pullRefreshEnabled) {
            touchListener = new XTouchListener(mHeaderLayout, mFooterLayout, true, this);
            recyclerView.setOnTouchListener(touchListener);
        }
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
    int getHeaderViewCount() {
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
    int getFooterViewCount() {
        return mFooterViews.size();
    }

    /**
     * Sets the drop-down animation to be invoked after addRecyclerView
     */
    public XBaseAdapter<T> setRefreshProgressStyle(int style) {
        mRefreshProgressStyle = style;
        if (mHeaderLayout != null) {
            mHeaderLayout.setProgressStyle(style);
        }
        return this;
    }

    /**
     * The image to display when loaded
     */
    public XBaseAdapter<T> setImageView(@DrawableRes int resId) {
        if (mHeaderLayout != null) {
            mHeaderLayout.setImageView(resId);
        }
        return this;
    }

    /**
     * Sets the pull-up animation to be called after addRecyclerView
     */
    public XBaseAdapter<T> setLoadingMoreProgressStyle(int style) {
        mLoadingMoreProgressStyle = style;
        if (mFooterLayout != null) {
            mFooterLayout.setProgressStyle(style);
        }
        return this;
    }

    /**
     * Whether it just came in to refresh
     */
    public XBaseAdapter<T> setRefreshing(boolean refreshing) {
        if (refreshing && pullRefreshEnabled && mLoadingListener != null) {
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.GONE);
            }
            if (mNetWorkErrorView != null) {
                mNetWorkErrorView.setVisibility(View.GONE);
            }
            if (recyclerView != null) {
                recyclerView.setVisibility(View.VISIBLE);
            }
            hideFootLayout();
            mHeaderLayout.setState(Refresh.REFRESH);
            mHeaderLayout.onMove(mHeaderLayout.getMeasuredHeight());
            mLoadingListener.onRefresh();
        }
        return this;
    }

    /**
     * @param color refreshHeader backgroundColor
     */
    public XBaseAdapter<T> setHeaderBackgroundColor(@ColorRes int color) {
        if (mHeaderLayout != null) {
            mHeaderLayout.setViewBackgroundColor(color);
        }
        return this;
    }

    /**
     * @param color refreshHeader textColor
     */
    public XBaseAdapter<T> setHeaderTextColor(@ColorRes int color) {
        if (mHeaderLayout != null) {
            mHeaderLayout.setTextColor(color);
        }
        return this;
    }

    /**
     * @param color loadMoreFooter backgroundColor
     */
    public XBaseAdapter<T> setFooterBackgroundColor(@ColorRes int color) {
        if (mFooterLayout != null) {
            mFooterLayout.setViewBackgroundColor(color);
        }
        return this;
    }

    /**
     * @param color loadMoreFooter textColor
     */
    public XBaseAdapter<T> setFooterTextColor(@ColorRes int color) {
        if (mFooterLayout != null) {
            mFooterLayout.setTextColor(color);
        }
        return this;
    }

    /**
     * @param height loadMoreFooter height
     */
    public XBaseAdapter<T> setFooterHeight(int height) {
        if (mFooterLayout != null) {
            mFooterLayout.setFooterHeight(height);
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

    public void refreshComplete(@Refresh.RefreshState int state) {
        if (mHeaderLayout != null) {
            mHeaderLayout.refreshComplete(state);
            if (loadingMoreEnabled && mFooterLayout != null && state == Refresh.REFRESH) {
                hideFootLayout();
                mFooterLayout.setState(LoadMore.NOT_LOAD);
            }
        }
    }

    public void loadMoreComplete(@LoadMore.LoadMoreStatus int state) {
        if (mFooterLayout != null) {
            mFooterLayout.setState(state);
        }
    }

    public void hideFootLayout() {
        if (mFooterLayout != null) {
            mFooterLayout.setVisibility(View.GONE);
        }
    }

    public void showFootLayout() {
        if (mFooterLayout != null) {
            mFooterLayout.setVisibility(View.VISIBLE);
        }
    }
}
