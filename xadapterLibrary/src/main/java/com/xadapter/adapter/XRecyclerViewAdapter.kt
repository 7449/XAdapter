package com.xadapter.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.xadapter.holder.XViewHolder
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView

/**
 * by y on 2016/11/15
 *
 */

open class XRecyclerViewAdapter<T> : XBaseAdapter<T>() {


    open fun initXData(data: ArrayList<T>) = apply { mDatas = data }

    open fun setRefreshView(view: XRefreshView) = apply { refreshView = view }

    open fun setLoadMoreView(view: XLoadMoreView) = apply { loadMoreView = view }

    open fun addRecyclerView(recyclerView: RecyclerView) = apply { this.recyclerView = recyclerView }

    open fun setEmptyView(view: View) = apply { setEmptyView(view, false) }

    open fun setEmptyView(view: View, isClick: Boolean) = apply {
        mEmptyView = view
        if (isClick)
            view.setOnClickListener { refresh() }
    }

    open fun addAll(data: List<T>) {
        mDatas.addAll(data)
        isShowEmptyView()
        notifyDataSetChanged()
    }

    open fun add(data: T) {
        mDatas.add(data)
        notifyDataSetChanged()
    }

    open fun removeAll() {
        mDatas.clear()
        notifyDataSetChanged()
    }

    open fun remove(position: Int) {
        mDatas.removeAt(position)
        notifyDataSetChanged()
    }

    open fun previousItem(position: Int): T {
        return if (position == 0) {
            mDatas[0]
        } else mDatas[position - 1]
    }

    open fun getData(position: Int): T = mDatas[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XViewHolder {
        if (mHeaderViewType.contains(viewType)) {
            return XViewHolder(mHeaderViews[viewType / super.viewType])
        }
        if (mFooterViewType.contains(viewType)) {
            return XViewHolder(mFooterViews[viewType / super.viewType - mDatas.size - mHeaderViews.size])
        }
        val xViewHolder = XViewHolder(LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false))
        xViewHolder.itemView.setOnClickListener { view ->
            mOnItemClickListener?.onItemClick(view,
                    getItemPosition(xViewHolder.layoutPosition),
                    mDatas[getItemPosition(xViewHolder.layoutPosition)])
        }
        xViewHolder.itemView.setOnLongClickListener { view ->
            mOnLongClickListener?.onLongClick(view,
                    getItemPosition(xViewHolder.layoutPosition),
                    mDatas[getItemPosition(xViewHolder.layoutPosition)])
            true
        }
        return when (viewType) {
            XBaseAdapter.TYPE_REFRESH_HEADER -> XViewHolder(refreshView!!)
            XBaseAdapter.TYPE_LOAD_MORE_FOOTER -> XViewHolder(loadMoreView!!)
            else -> xViewHolder
        }
    }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) {
        if (getItemViewType(position) != XBaseAdapter.TYPE_ITEM) {
            return
        }
        val pos = getItemPosition(position)
        val t = mDatas[pos] ?: return
        mOnXBindListener.onXBind(holder, pos, t)
    }

    override fun getItemViewType(position: Int): Int {
        var mPos = position
        if (isRefreshHeaderType(mPos)) {
            return XBaseAdapter.TYPE_REFRESH_HEADER
        }
        if (isLoadMoreType(mPos)) {
            return XBaseAdapter.TYPE_LOAD_MORE_FOOTER
        }
        if (pullRefreshEnabled) {
            mPos -= 1
        }
        if (isHeaderType(mPos)) {
            mHeaderViewType.add(mPos * viewType)
            return mPos * viewType
        }
        if (isFooterType(mPos)) {
            mFooterViewType.add(mPos * viewType)
            return mPos * viewType
        }
        return XBaseAdapter.TYPE_ITEM
    }

    override fun getItemCount(): Int = dataSize + mFooterViews.size + mHeaderViews.size
    private fun isRefreshHeaderType(position: Int): Boolean = pullRefreshEnabled && position == 0
    private fun isHeaderType(position: Int): Boolean = mHeaderViews.size != 0 && position < mHeaderViews.size
    private fun isFooterType(position: Int): Boolean = mFooterViews.size != 0 && position >= mDatas.size + mHeaderViews.size
    private fun isLoadMoreType(position: Int): Boolean = loadingMoreEnabled && position == itemCount - 1
    private fun getItemPosition(position: Int): Int {
        var mPos = position
        if (pullRefreshEnabled) {
            mPos -= 1
        }
        return mPos - mHeaderViews.size
    }

    private val dataSize: Int
        get() {
            return mDatas.size + if (loadingMoreEnabled && pullRefreshEnabled) {
                2
            } else if (loadingMoreEnabled || pullRefreshEnabled) {
                1
            } else {
                0
            }
        }
}
