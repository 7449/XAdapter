# XAdapter
Support for the pull-down refresh loading and the addition of multiple header and footer RecyclerViewAdapter


Blog:[https://7449.github.io/Android_XAdapter/](https://7449.github.io/2016/11/12/Android_XAdapter/)

# Screenshots

![](https://github.com/7449/XAdapter/blob/master/xadapter.gif)

[https://github.com/7449/XAdapter/blob/master/xadapter.gif](https://github.com/7449/XAdapter/blob/master/xadapter.gif)


[中文文档](https://7449.github.io/2016/11/12/Android_XAdapter/)

### gradle

    implementation 'com.ydevelop:rv-adapter:0.0.9.8.1'
    implementation 'com.google.android.material:material:1.0.0'

onXBind

Achieve data display

        adapter.onXBindListener = { holder, position, entity ->
            
        }

## emptyView

>Whether to display manually determined by the user's own network exceptions or data is empty when the call xRecyclerViewAdapter.isShowEmptyView (); specific examples of simple
	
    mRecyclerView.adapter = xRecyclerViewAdapter
            .apply {
                emptyView = findViewById(R.id.emptyView)
                recyclerView = mRecyclerView
            }

## pull to refresh and load more

The default is not open, if necessary, please manually open, and addRecyclerView

        adapter.xRefreshListener = {

        }
        adapter.xLoadMoreListener = {
            
        }

When the drop-down refresh is complete

It is up to the user to choose whether the load fails or is successful

>xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS

When the pull-up is complete

It is up to the user to choose whether the load fails or is successful

>xRecyclerViewAdapter.loadMoreState = XLoadMoreView.NOMORE

### addHeader addFooter

    xRecyclerViewAdapter
     .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, (ViewGroup) findViewById(android.R.id.content), false))
     .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, (ViewGroup) findViewById(android.R.id.content), false))
		 
### MultipleAdapter

see [multi](https://github.com/7449/XAdapter/tree/master/library/src/main/java/com/xadapter/adapter/XMultiAdapter.kt)

#### RefreshView 

    class RefreshView : XRefreshView {
    
        constructor(context: Context) : super(context)
    
        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    
        constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    
        public override fun initView() {
        }
    
        override fun getLayoutId(): Int {
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

#### LoadMoreView

    class LoadMoreView : XLoadMoreView {
    
        constructor(context: Context) : super(context)
    
        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    
        constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    
        override fun initView() {
        }
    
        override fun getLayoutId(): Int {
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

