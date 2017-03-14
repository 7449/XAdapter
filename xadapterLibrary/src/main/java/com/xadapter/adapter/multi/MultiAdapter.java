package com.xadapter.adapter.multi;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xadapter.holder.XViewHolder;

import java.util.List;


/**
 * by y on 2017/3/9
 */

public abstract class MultiAdapter<T extends MultiCallBack> extends RecyclerView.Adapter<XViewHolder> {

    private List<T> mDatas = null;
    private OnItemClickListener<T> mOnItemClickListener;
    private OnItemLongClickListener<T> mOnLongClickListener;

    public MultiAdapter(@NonNull List<T> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public XViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new XViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false));
    }

    @Override
    public void onBindViewHolder(XViewHolder holder, final int position) {
        if (mDatas == null) {
            return;
        }

        final T t = mDatas.get(position);

        if (t != null) {
            onBind(holder, t, t.getItemType(), t.getPosition() == -1 ? position : t.getPosition());

            if (mOnItemClickListener != null && t.hasClick()) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v, t.getPosition() == -1 ? position : t.getPosition(), t);
                    }
                });
            }

            if (mOnLongClickListener != null && t.hasClick()) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnLongClickListener.onLongClick(v, t.getPosition() == -1 ? position : t.getPosition(), t);
                        return true;
                    }
                });
            }
        }


    }

    protected abstract void onBind(XViewHolder holder, T mData, int itemType, int position);

    protected abstract int getLayoutId(int viewType);


    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnLongClickListener(OnItemLongClickListener<T> listener) {
        this.mOnLongClickListener = listener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T info);
    }

    public interface OnItemLongClickListener<T> {
        void onLongClick(View view, int position, T info);
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
            notifyItemChanged(position);
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
        return mDatas.get(position);
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
                    if (!(getItemViewType(position) == MultiCallBack.TYPE_ITEM)) {
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
            if (!(getItemViewType(holder.getLayoutPosition()) == MultiCallBack.TYPE_ITEM)) {
                stagger.setFullSpan(true);
            } else {
                stagger.setFullSpan(false);
            }
        }
    }
}
