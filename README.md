# 

[JetBrains](https://www.jetbrains.com/?from=XAdapter)

# XAdapter

Support for the pull-down refresh loading and the addition of multiple header and footer RecyclerViewAdapter

ViewHolder:![](https://api.bintray.com/packages/ydevelop/maven/rv-adapter-viewholder/images/download.svg)

refresh:![](https://api.bintray.com/packages/ydevelop/maven/rv-adapter-refresh/images/download.svg)

adapter:![](https://api.bintray.com/packages/ydevelop/maven/rv-adapter/images/download.svg)

databinding:![](https://api.bintray.com/packages/ydevelop/maven/rv-adapter-databinding/images/download.svg)

multi:![](https://api.bintray.com/packages/ydevelop/maven/rv-adapter-multi/images/download.svg)

recyclerview:![](https://api.bintray.com/packages/ydevelop/maven/rv-adapter-recyclerview/images/download.svg)

material:![](https://api.bintray.com/packages/ydevelop/maven/rv-adapter-material/images/download.svg)

### gradle

    implementation 'com.ydevelop:rv-adapter:0.0.9.8.9'
    implementation 'com.ydevelop:rv-adapter-recyclerview:0.0.3'
    implementation 'com.ydevelop:rv-adapter-databinding:0.0.3'
    implementation 'com.ydevelop:rv-adapter-material:0.0.1'
    implementation 'com.ydevelop:rv-adapter-viewholder:0.0.3'
    implementation 'com.ydevelop:rv-adapter-refresh:0.0.2'
    implementation 'com.ydevelop:rv-adapter-multi:0.0.3'
    
### multi 

    implementation 'com.ydevelop:rv-adapter-multi:0.0.3'
    implementation 'com.ydevelop:rv-adapter-viewholder:0.0.3'

### databinding

    implementation 'com.ydevelop:rv-adapter-databinding:0.0.3'
    implementation 'com.ydevelop:rv-adapter:0.0.9.8.9'
    implementation 'com.ydevelop:rv-adapter-viewholder:0.0.3'
    
### recyclerview core

    implementation 'com.ydevelop:rv-adapter-recyclerview:0.0.3'
    
### appbar

    implementation 'com.ydevelop:rv-adapter-material:0.0.1'
    
    
    fun <T> XAdapter<T>.supportAppbar(appBarLayout: AppBarLayout) = also {
        val appBarStateChangeListener = AppBarStateChangeListener()
        appBarLayout.addOnOffsetChangedListener(appBarStateChangeListener)
        xAppbarCallback = { appBarStateChangeListener.currentState == AppBarStateChangeListener.EXPANDED }
    }

### sample

    xAdapter
            .setItemLayoutId(layoutId)
            .customRefreshView(View)
            .customLoadMoreView(View)
            .openLoadingMore()
            .openPullRefresh()
            .setScrollLoadMoreItemCount(2)
            .addHeaderView(View)
            .addFooterView(View)
            .setOnBind { holder, position, entity ->
            }
            .setOnItemClickListener { view, position, entity ->
            }
            .setOnItemLongClickListener { view, position, entity ->
                true
            }
            .setRefreshListener {
            }
            .setLoadMoreListener {
            }
            .addAll(mainBeen)
            
### recyclerview core sample

    recyclerView
            .linearLayoutManager()
            .attachAdapter<Entity>()
            .setItemLayoutId(layoutId)
            .customRefreshView(View)
            .customLoadMoreView(View)
            .openLoadingMore()
            .openPullRefresh()
            .setScrollLoadMoreItemCount(2)
            .addHeaderView(View)
            .addFooterView(View)
            .setOnBind<Entity> { holder, position, entity ->
            }
            .setOnItemClickListener<Entity> { view, position, entity ->
            }
            .setOnItemLongClickListener<Entity> { view, position, entity ->
                true
            }
            .setRefreshListener {
            }
            .setLoadMoreListener {
            }
            .addAll(mainBeen)
            
####### recyclerview core sample 

    recyclerView
            .linearLayoutManager()
            .fixedSize()
            .setAdapter<Entity> {
                loadingMore = true
                pullRefresh = true
                itemLayoutId = layoutId
                addHeaderViews(
                        view...
                )
                addFooterViews(
                        view...
                )
                onBind { holder, position, entity ->
                }
                onItemLongClickListener { view, position, entity ->
                    true
                }
                onItemClickListener { view, position, entity ->
                }
                refreshListener {
                }
                loadMoreListener {
                }
            }
            .addAll(JsonUtils.jsonList)

#### pull to refresh and load more

    xAdapter.openLoadingMore()
            .openPullRefresh()
            .setRefreshListener {
            }
            .setLoadMoreListener {
            }
    //
    .customRefreshView(View)
    .customLoadMoreView(View)
    //
    it.setRefreshState(int) // NORMAL READY REFRESH SUCCESS ERROR
    it.setLoadMoreState(int) // NORMAL LOAD SUCCESS NO_MORE ERROR

### MultipleAdapter

    recyclerView
            .attachMultiAdapter(XMultiAdapter(initData()))
            .multiSetItemLayoutId { viewType ->
                when (viewType) {
                    // return layoutId
                }
            }
            .multiSetBind<SimpleXMultiItem> { holder, entity, itemViewType, _ ->
            }
            .multiGridLayoutManagerSpanSize { itemViewType, manager, _ ->
            }
            .multiStaggeredGridLayoutManagerFullSpan {
            }
            .multiSetOnItemClickListener<SimpleXMultiItem> { view, _, entity ->
            }
            .multiSetOnItemLongClickListener<SimpleXMultiItem> { view, _, entity ->
                true
            }

#### CustomRefreshView 

    class RefreshView(context: Context) : XRefreshView(context, layoutId) {
    
        override fun initView() {
        }
    
        override fun onStart() {
        }
    
        override fun onNormal() {
        }
    
        override fun onReady() {
        }
    
        override fun onRefresh() {
        }
    
        override fun onSuccess() {
        }
    
        override fun onError() {
        }
    }

#### CustomLoadMoreView

    class LoadMoreView(context: Context) : XLoadMoreView(context, layoutId) {
    
        override fun initView() {
        }
    
        override fun onStart() {
        }
    
        override fun onLoad() {
        }
    
        override fun onNoMore() {
        }
    
        override fun onSuccess() {
        }
    
        override fun onError() {
        }
    
        override fun onNormal() {
        }
    }