# XAdapter
Support for the pull-down refresh loading and the addition of multiple header and footer RecyclerViewAdapter


Blog:[https://7449.github.io/Android_XAdapter/](https://7449.github.io/2016/11/12/Android_XAdapter/)



[notrefresh](https://github.com/7449/XAdapter/tree/notrefresh):分支删掉了下拉刷新和上拉加载



# Screenshots

![](https://github.com/7449/XAdapter/blob/master/xadapter.gif)

[https://github.com/7449/XAdapter/blob/master/xadapter.gif](https://github.com/7449/XAdapter/blob/master/xadapter.gif)


[中文文档](https://7449.github.io/2016/11/12/Android_XAdapter/)

### gradle

>compile 'com.ydevelop:rv-adapter:0.0.4


## tips

It should be noted that, initXData () is not mandatory, only when the beginning of the RecyclerView need to have a data List must call initXData ()

## Full example

        recyclerView.setAdapter(
                xRecyclerViewAdapter
                        .initXData(mainBeen)
                        .setLoadMoreView(View)
                        .setRefreshView(View)
                        .addRecyclerView(recyclerView)
                        .setLayoutId(R.layout.item)
                        .setPullRefreshEnabled(true)
                        .setLoadingMoreEnabled(true)
                        .addHeaderView(View)
                        .addFooterView(View)
                        .onXBind(this)
                        .setOnLongClickListener(this)
                        .setOnItemClickListener(this)
                        .setLoadListener(this)
                        .setFooterListener(this)
                        .refresh()
        );

onXBind  
Achieve data display

    @Override
    public void onXBind(XViewHolder holder, int position, MainBean mainBean) {
        holder.setTextView(R.id.tv_name, mainBean.getName());
        holder.setTextView(R.id.tv_age, mainBean.getAge() + "");
    }

## emptyView

>Whether to display manually determined by the user's own network exceptions or data is empty when the call xRecyclerViewAdapter.isShowEmptyView (); specific examples of simple
	
	 recyclerView.setAdapter(
	                xRecyclerViewAdapter
	                        .initXData(mainBean)
	                        .setEmptyView(findViewById(R.id.emptyView))
	                        .addRecyclerView(recyclerView)
	                        .setLayoutId(R.layout.item)
	   );


## pull to refresh and load more

The default is not open, if necessary, please manually open, and addRecyclerView

                xRecyclerViewAdapter
					.initXData(mainBean)
	                .setLayoutId(R.layout.item)
	                .addRecyclerView(recyclerView)
	                .setPullRefreshEnabled(true)
	                .setPullRefreshEnabled(true)
	                .setLoadingListener(new XBaseAdapter.LoadingListener() {
	                    @Override
	                    public void onRefresh() {
	                        
	                    }
	
	                    @Override
	                    public void onLoadMore() {
	
	                    }
	                })

When the drop-down refresh is complete

It is up to the user to choose whether the load fails or is successful

>xRecyclerViewAdapter.refreshState(XRefresh.SUCCESS);

When the pull-up is complete

It is up to the user to choose whether the load fails or is successful

>xRecyclerViewAdapter.loadMoreState(XLoadMore.ERROR);


### addHeader addFooter

		xRecyclerViewAdapter
		 .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, (ViewGroup) findViewById(android.R.id.content), false))
		 .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, (ViewGroup) findViewById(android.R.id.content), false))
		 
### MultipleAdapter

see [multi](https://github.com/7449/XAdapter/tree/master/xadapterLibrary/src/main/java/com/xadapter/adapter/multi)

### RefreshView and LoadMoreView

	public class RefreshView extends XRefreshView {
	
	    public RefreshView(Context context) {
	        super(context);
	    }
	
	    public RefreshView(Context context, @Nullable AttributeSet attrs) {
	        super(context, attrs);
	    }
	
	    public RefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
	        super(context, attrs, defStyleAttr);
	    }
	
	    @Override
	    public void initView() {
	    }
	    
	    @Override
	    protected int getLayoutId() {
	        return 0;
	    }
	
	    @Override
	    protected void onStart() {
	    }
	
	    @Override
	    protected void onNormal() {
	    }
	
	    @Override
	    protected void onReady() {
	    }
	
	    @Override
	    protected void onRefresh() {
	    }
	
	    @Override
	    protected void onSuccess() {
	    }
	
	    @Override
	    protected void onError() {
	    }
	
	
	}


	public class LoadMoreView extends XLoadMoreView {
	
	
	    public LoadMoreView(Context context) {
	        super(context);
	    }
	
	    public LoadMoreView(Context context, @Nullable AttributeSet attrs) {
	        super(context, attrs);
	    }
	
	    public LoadMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
	        super(context, attrs, defStyleAttr);
	    }
	
	    @Override
	    protected void initView() {
	    }
	
	    @Override
	    protected int getLayoutId() {
	        return 0;
	    }
	
	    @Override
	    protected void onStart() {
	    }
	
	    @Override
	    protected void onLoad() {
	    }
	
	    @Override
	    protected void onNoMore() {
	    }
	
	    @Override
	    protected void onSuccess() {
	    }
	
	    @Override
	    protected void onError() {
	    }
	
	    @Override
	    protected void onNormal() {
	    }
	}


License
--
    Copyright (C) 2016 yuebigmeow@gamil.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

