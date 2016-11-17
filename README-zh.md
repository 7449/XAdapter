# XAdapter
支持下拉刷新加载和添加多个header和footer的RecyclerViewAdapter


#Screenshots

![](https://github.com/7449/XAdapter/blob/master/xadapter.gif)

[https://github.com/7449/XAdapter/blob/master/xadapter.gif](https://github.com/7449/XAdapter/blob/master/xadapter.gif)

###gradle

>compile 'com.xadapter:xadapter:0.0.1'

##下拉刷新和上拉加载

默认不打开，如果有必要，请手动打开，并调用addRecyclerView

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

下拉刷新完成之后

这取决于用户选择刷新是否失败或成功

>xRecyclerViewAdapter.refreshComplete(BaseRefreshHeader.STATE_DONE);


上拉加载完成之后

这取决于用户选择加载是否失败或成功

>xRecyclerViewAdapter.loadMoreComplete(XFooterLayout.STATE_NOMORE);


###添加header和footer

		xRecyclerViewAdapter
		 .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, (ViewGroup) findViewById(android.R.id.content), false))
		 .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, (ViewGroup) findViewById(android.R.id.content), false))

###加载动画

XAdapter 的刷新头部以及底部都是来自 [XRecyclerView](https://github.com/jianghejie/XRecyclerView), 所以 [XRecyclerView](https://github.com/jianghejie/XRecyclerView) 支持的动画XAdapter 都支持,并且对Layout进行扩展，可以设置背景色和字体色，字体大小

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

#感谢

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

