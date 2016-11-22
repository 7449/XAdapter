package com.xadapter.adapter;

import android.annotation.SuppressLint;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.xadapter.holder.XViewHolder;
import com.xadapter.manager.AppBarStateChangeListener;
import com.xadapter.manager.XScrollBottom;
import com.xadapter.manager.XScrollListener;
import com.xadapter.manager.XTouchListener;
import com.xadapter.progressindicator.ProgressStyle;
import com.xadapter.widget.BaseRefreshHeader;
import com.xadapter.widget.XFooterLayout;
import com.xadapter.widget.XHeaderLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * by y on 2016/11/15
 * <p>
 * XBaseAdapter, all need to use the location of T should be sentenced to empty the deal
 */
public abstract class XBaseAdapter<T> extends RecyclerView.Adapter<XViewHolder>
        implements XScrollBottom, XTouchListener.RefreshInterface, XFooterLayout.FooterLayoutInterface {
    List<T> mDatas = new LinkedList<>();
    final ArrayList<View> mHeaderViews = new ArrayList<>();
    final ArrayList<View> mFooterViews = new ArrayList<>();
    final ArrayList<Integer> mHeaderViewType = new ArrayList<>();
    final ArrayList<Integer> mFooterViewType = new ArrayList<>();
    final int viewType = 100000; // Here the number set big point, avoid and users of the item conflict
    private int mRefreshProgressStyle = ProgressStyle.SysProgress;
    private int mLoadingMoreProgressStyle = ProgressStyle.SysProgress;
    View mEmptyView = null;

    /**
     * The listener that receives notifications when an emptyView is clicked
     */
    OnXEmptyViewListener mOnEmptyViewListener = null;

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
    private LoadingListener mLoadingListener = null;
    /**
     * Click the callback
     */
    private FooterListener mFooterListener = null;
    /**
     * This is the callback interface to get the data
     */
    OnXBindListener<T> mOnXBindListener = null;
    /**
     * The subclass implements the display of the layout
     */
    private int ITEM_LAYOUT_ID = -1;

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
    XHeaderLayout mHeaderLayout = null;

    /**
     * Whether to enable pull-up,The default is off
     */
    boolean loadingMoreEnabled = false;

    /**
     * this is loadMore layout
     */
    XFooterLayout mFooterLayout = null;

    static final int TYPE_ITEM = -1;
    static final int TYPE_REFRESH_HEADER = 0;
    static final int TYPE_LOADMORE_FOOTER = 1;

    /**
     * This is the emptyView click-callback event
     */
    public interface OnXEmptyViewListener {
        void onXEmptyViewClick(View view);
    }

    /**
     * @param mOnEmptyViewListener The callback this will be invoked
     */
    public XBaseAdapter<T> setOnXEmptyViewListener(OnXEmptyViewListener mOnEmptyViewListener) {
        this.mOnEmptyViewListener = mOnEmptyViewListener;
        return this;
    }

    /**
     * This is the FooterLayout click-callback event
     */
    public interface FooterListener {
        void onXFooterClick(View view);
    }

    /**
     * @param footerListener The callback that will be invoked.
     */
    public XBaseAdapter<T> setFooterListener(FooterListener footerListener) {
        this.mFooterListener = footerListener;
        return this;
    }

    /**
     * The callback interface definition to invoke in the project
     */
    public interface LoadingListener {

        /**
         * Drop-down refresh callback
         */
        void onRefresh();

        /**
         * The pull-up callback is loaded
         */
        void onLoadMore();
    }


    /**
     * Register the refresh callback inside the XAdapter
     *
     * @param mLoadingListener The callback that will be invoked.
     */
    public XBaseAdapter<T> setLoadingListener(LoadingListener mLoadingListener) {
        this.mLoadingListener = mLoadingListener;
        return this;
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * XBaseAdapter has been clicked.
     */
    public interface OnItemClickListener<T> {

        /**
         * Callback method to be invoked when an item in this XBaseAdapter has
         * been clicked.
         * <p>
         * If you use T, in order to reduce unnecessary crashes, the proposed empty sentence processing
         *
         * @param view     The view within the XBaseAdapter that was clicked
         * @param position The position of the view in the adapter.
         * @param info     The adapter's data
         */
        void onItemClick(View view, int position, T info);
    }

    /**
     * Register a callback to be invoked when an item in this XBaseAdapter has
     * been clicked.
     *
     * @param mOnItemClickListener The callback that will be invoked.
     */
    public XBaseAdapter<T> setOnItemClickListener(OnItemClickListener<T> mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
        return this;
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * view has been clicked and held.
     */
    public interface OnItemLongClickListener<T> {

        /**
         * Callback method to be invoked when an item in this view has been
         * clicked and held.
         * <p>
         * If you use T, in order to reduce unnecessary crashes, the proposed empty sentence processing
         *
         * @param view     The view within the XBaseAdapter that was clicked
         * @param position The position of the view in the adapter.
         * @param info     The adapter's data
         */
        void onLongClick(View view, int position, T info);

    }

    /**
     * Register a callback to be invoked when an item in this XBaseAdapter has
     * been clicked and held
     *
     * @param mOnLongClickListener The callback that will run
     */
    public XBaseAdapter<T> setOnLongClickListener(OnItemLongClickListener<T> mOnLongClickListener) {
        this.mOnLongClickListener = mOnLongClickListener;
        return this;
    }

    /**
     * The callback to invoke when registering data in the XBaseAdapter
     *
     * @param <T> this is mDatas
     */
    public interface OnXBindListener<T> {
        void onXBind(XViewHolder holder, int position, T t);
    }

    /**
     * @param onXBindListener The callback that will run
     */
    public XBaseAdapter<T> onXBind(OnXBindListener<T> onXBindListener) {
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


    /**
     * get recyclerview
     *
     * @param recyclerView recyclerview
     */
    XBaseAdapter<T> linkRecyclerView(final RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            mHeaderLayout = new XHeaderLayout(recyclerView.getContext());
            mHeaderLayout.setProgressStyle(mRefreshProgressStyle);
            mFooterLayout = new XFooterLayout(recyclerView.getContext(), this);
            mFooterLayout.setProgressStyle(mLoadingMoreProgressStyle);
            mFooterLayout.setVisibility(View.GONE);
            recyclerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View view) {
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
                        if (appBarLayout != null && xTouchListener != null) {
                            appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
                                @Override
                                public void onStateChanged(AppBarLayout appBarLayout, State state) {
                                    xTouchListener.setState(state);
                                }
                            });
                        }
                    }
                }

                @Override
                public void onViewDetachedFromWindow(View view) {

                }
            });

        }
        return this;
    }

    private void settingScrollListener() {
        if (recyclerView != null) {
            recyclerView.addOnScrollListener(new XScrollListener(this));
        } else {
            Log("recyclerView is null,Check if addRecyclerView is used");
        }
    }

    private XTouchListener xTouchListener = null;

    private void settingTouchListener() {
        if (recyclerView != null) {
            xTouchListener = new XTouchListener(mHeaderLayout, pullRefreshEnabled, this);
            recyclerView.setOnTouchListener(xTouchListener);
        } else {
            Log("recyclerView is null,Check if addRecyclerView is used");
        }
    }


    /**
     * get the view
     */
    View getView(ViewGroup viewGroup) {
        if (ITEM_LAYOUT_ID == -1) {
            throw new NullPointerException("Please check if your Adapter is setLayoutId");
        }
        return LayoutInflater.from(viewGroup.getContext()).inflate(ITEM_LAYOUT_ID, viewGroup, false);
    }

    @Override
    public void onScrollBottom() {
        /**
         *The recyclerview is now at the bottom and can be loaded up
         */
        if (mLoadingListener != null) {
            if (mFooterLayout != null) {
                mFooterLayout.setVisibility(View.VISIBLE);
            }
            mLoadingListener.onLoadMore();
        }
    }

    @Override
    public void onRefresh() {
        /**
         *Touch the recyclerview drop-down to refresh the callback
         */
        if (mLoadingListener != null) {
            if (mFooterLayout != null) {
                mFooterLayout.setVisibility(View.GONE);
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
    void initClickListener(XViewHolder holder, @SuppressLint("RecyclerView") final int position, final T t) {
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
                    mOnLongClickListener.onLongClick(view, position, t);
                    return true;
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
        if (pullRefreshEnabled && recyclerView == null) {
            throw new NullPointerException("Detect recyclerView is null, addRecyclerView () if using pull-down refresh or pull-up load");
        }
        this.loadingMoreEnabled = loadingMoreEnabled;
        if (loadingMoreEnabled) {
            settingScrollListener();
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
            settingTouchListener();
        }
        return this;
    }


    /**
     * @param view the added header view
     */
    public XBaseAdapter<T> addHeaderView(View view) {
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
    public XBaseAdapter<T> addFooterView(View view) {
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
    public XBaseAdapter<T> setImageView(int resId) {
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
                recyclerView.setVisibility(View.VISIBLE);
            }
            mHeaderLayout.setState(BaseRefreshHeader.STATE_REFRESHING);
            mHeaderLayout.onMove(mHeaderLayout.getMeasuredHeight());
            mLoadingListener.onRefresh();
        }
        return this;
    }

    /**
     * @param color refreshHeader backgroundColor
     */
    public XBaseAdapter<T> setHeaderBackgroundColor(int color) {
        if (mHeaderLayout != null) {
            mHeaderLayout.setViewBackgroundColor(color);
        }
        return this;
    }

    /**
     * @param color refreshHeader textColor
     */
    public XBaseAdapter<T> setHeaderTextColor(int color) {
        if (mHeaderLayout != null) {
            mHeaderLayout.setTextColor(color);
        }
        return this;
    }

    /**
     * @param color loadMoreFooter backgroundColor
     */
    public XBaseAdapter<T> setFooterBackgroundColor(int color) {
        if (mFooterLayout != null) {
            mFooterLayout.setViewBackgroundColor(color);
        }
        return this;
    }

    /**
     * @param color loadMoreFooter textColor
     */
    public XBaseAdapter<T> setFooterTextColor(int color) {
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

    /**
     * Update the status of the list based on the empty parameter.  If empty is true and
     * we have an empty view, display it.  In all the other cases, make sure that the listview
     * is VISIBLE and that the empty view is GONE (if it's not null).
     */
    void updateEmptyStatus(boolean empty) {
        if (recyclerView == null) {
            throw new NullPointerException("The emptyView needs recyclerView, call addRecyclerView");
        }
        if (mEmptyView != null) {
            if (empty) {
                mEmptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                mEmptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
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
    }

    @Override
    public void onXFooterClick(View view) {
        if (mFooterListener != null) {
            mFooterListener.onXFooterClick(view);
        }
    }

    private void Log(Object o) {
        Log.i(getClass().getSimpleName(), o.toString());
    }

    public void refreshComplete(int state) {
        mHeaderLayout.refreshComplete(state);
    }

    public void loadMoreComplete(int state) {
        mFooterLayout.setState(state);
    }
}
