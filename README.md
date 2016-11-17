# XAdapter
Support for the pull-down refresh loading and the addition of multiple header and footer RecyclerViewAdapter


#Screenshots

![](https://github.com/7449/XAdapter/blob/master/xadapter.gif)

[https://github.com/7449/XAdapter/blob/master/xadapter.gif](https://github.com/7449/XAdapter/blob/master/xadapter.gif)

###gradle

>compile 'com.xadapter:xadapter:0.0.1'

##pull to refresh and load more

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

>xRecyclerViewAdapter.refreshComplete(BaseRefreshHeader.STATE_DONE);


When the pull-up is complete

It is up to the user to choose whether the load fails or is successful

>xRecyclerViewAdapter.loadMoreComplete(XFooterLayout.STATE_NOMORE);


###addHeader addFooter

		xRecyclerViewAdapter
		 .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, (ViewGroup) findViewById(android.R.id.content), false))
		 .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, (ViewGroup) findViewById(android.R.id.content), false))

###Load the animation

XAdapter's refresh header and the bottom are derived from the [XRecyclerView](https://github.com/jianghejie/XRecyclerView), so support for [XRecyclerView](https://github.com/jianghejie/XRecyclerView) support animation, and the head and the bottom of the extension, you can set the background color, font color.

              	 xRecyclerViewAdapter
                        .initXData(mainBeen)
                        .addRecyclerView(recyclerView)
                        .setLayoutId(R.layout.item)
                        .setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
                        .setLoadingMoreProgressStyle(ProgressStyle.BallRotate)
                        .setImageView(R.drawable.iconfont_downgrey)
                        .setHeaderBackgroundColor(R.color.colorBlack)
                        .setFooterBackgroundColor(R.color.colorBlack)
                        .setHeaderTextColor(R.color.textColor)
                        .setFooterTextColor(R.color.textColor)

#Thanks

[https://github.com/jianghejie/XRecyclerView](https://github.com/jianghejie/XRecyclerView)


License
--
    Copyright (C) 2016 yuezhaoyang7449@163.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

