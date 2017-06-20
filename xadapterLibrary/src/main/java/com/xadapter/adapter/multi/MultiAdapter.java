package com.xadapter.adapter.multi;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xadapter.holder.XViewHolder;
import com.xadapter.listener.OnItemClickListener;
import com.xadapter.listener.OnItemLongClickListener;

import java.util.List;


/**
 * by y on 2017/3/9
 */

public class MultiAdapter<T extends MultiCallBack> extends RecyclerView.Adapter<XViewHolder> {

    protected List<T> mDatas = null;
    protected OnItemClickListener<T> mOnItemClickListener;
    protected OnItemLongClickListener<T> mOnLongClickListener;
    private XMultiAdapterListener<T> xMultiAdapterListener = null;

    public MultiAdapter(@NonNull List<T> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public XViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (xMultiAdapterListener != null) {
            return new XViewHolder(LayoutInflater.from(parent.getContext()).inflate(xMultiAdapterListener.multiLayoutId(viewType), parent, false));
        }
        throw new NullPointerException("multiLayout null !!!!");
    }

    @Override
    public void onBindViewHolder(XViewHolder holder, final int position) {
        if (mDatas == null) {
            return;
        }

        final T t = mDatas.get(position);

        if (t != null) {
            if (xMultiAdapterListener != null) {
                xMultiAdapterListener.onXMultiBind(holder, t, t.getItemType(), t.getPosition() == -1 ? position : t.getPosition());
            }

            if (mOnItemClickListener != null && t.getPosition() != -1) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v, t.getPosition() == -1 ? position : t.getPosition(), t);
                    }
                });
            }

            if (mOnLongClickListener != null && t.getPosition() != -1) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return mOnLongClickListener.onLongClick(v, t.getPosition() == -1 ? position : t.getPosition(), t);

                    }
                });
            }
        }


    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public MultiAdapter<T> setXMultiAdapterListener(XMultiAdapterListener<T> xMultiAdapterListener) {
        this.xMultiAdapterListener = xMultiAdapterListener;
        return this;
    }

    public MultiAdapter<T> setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
        return this;
    }

    public MultiAdapter<T> setOnLongClickListener(OnItemLongClickListener<T> listener) {
        this.mOnLongClickListener = listener;
        return this;
    }

    public void clearAll() {
        if (mDatas != null) {
            mDatas.clear();
            notifyDataSetChanged();
        }
    }

    public void clear(int position) {
        if (mDatas != null) {
            mDatas.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    public void add(@NonNull T t) {
        if (mDatas != null) {
            mDatas.add(t);
            notifyDataSetChanged();
        }
    }

    public void addAll(@NonNull List<T> t) {
        if (mDatas != null) {
            mDatas.addAll(t);
            notifyDataSetChanged();
        }
    }

    public T getData(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    public List<T> getData() {
        return mDatas;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager && xMultiAdapterListener != null) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return xMultiAdapterListener.getGridLayoutManagerSpanSize(getItemViewType(position), gridManager, position);
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(XViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams && xMultiAdapterListener != null) {
            StaggeredGridLayoutManager.LayoutParams stagger = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            stagger.setFullSpan(xMultiAdapterListener.getStaggeredGridLayoutManagerFullSpan(getItemViewType(holder.getLayoutPosition())));
        }
    }
}
