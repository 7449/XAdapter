package rv.adapter.multiple

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import rv.adapter.view.holder.XViewHolder

/**
 * by y on 2017/3/9
 */
class XMultiAdapter<T : XMultiCallBack>(private val multiData: MutableList<T> = ArrayList()) :
    RecyclerView.Adapter<XViewHolder>() {

    var onXItemClickListener: ((view: View, position: Int, entity: T) -> Unit)? = null

    var onXItemLongClickListener: ((view: View, position: Int, entity: T) -> Boolean)? = null

    lateinit var itemLayoutId: ((itemViewType: Int) -> Int)

    lateinit var xMultiBind: ((holder: XViewHolder, entity: T, itemViewType: Int, position: Int) -> Unit)

    var gridLayoutManagerSpanSize: ((itemViewType: Int, manager: GridLayoutManager, position: Int) -> Int)? =
        null

    var staggeredGridLayoutManagerFullSpan: ((itemViewType: Int) -> Boolean)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XViewHolder =
        XViewHolder(
            LayoutInflater.from(parent.context).inflate(itemLayoutId(viewType), parent, false)
        )
            .apply { multiViewHolderClick(this@XMultiAdapter).multiViewHolderLongClick(this@XMultiAdapter) }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) =
        xMultiBind(holder, getItem(position), getItemViewType(position), position)

    override fun getItemViewType(position: Int): Int = getItem(position).itemType

    override fun getItemCount(): Int = multiData.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) =
        internalOnAttachedToRecyclerView(recyclerView)

    override fun onViewAttachedToWindow(holder: XViewHolder) =
        internalOnViewAttachedToWindow(holder)

    fun getItem(position: Int): T = multiData[position]

    fun setItemLayoutId(action: (itemViewType: Int) -> Int) = also { this.itemLayoutId = action }

    fun setMultiBind(action: (holder: XViewHolder, entity: T, itemViewType: Int, position: Int) -> Unit) =
        also { this.xMultiBind = action }

    fun gridLayoutManagerSpanSize(action: (itemViewType: Int, manager: GridLayoutManager, position: Int) -> Int) =
        also { gridLayoutManagerSpanSize = action }

    fun staggeredGridLayoutManagerFullSpan(action: (itemViewType: Int) -> Boolean) =
        also { staggeredGridLayoutManagerFullSpan = action }

    fun setOnItemClickListener(action: (view: View, position: Int, entity: T) -> Unit) =
        also { onXItemClickListener = action }

    fun setOnItemLongClickListener(action: (view: View, position: Int, entity: T) -> Boolean) =
        also { onXItemLongClickListener = action }

    fun removeAll() = also { multiData.clear() }.notifyDataSetChanged()

    fun remove(position: Int) =
        also { multiData.removeAt(position) }.also { notifyItemRemoved(position) }
            .notifyItemRangeChanged(position, itemCount)

    fun addAll(t: List<T>) = also { multiData.addAll(t) }.notifyDataSetChanged()

    fun add(t: T) = also { multiData.add(t) }.notifyDataSetChanged()

    private fun <T : XMultiCallBack> XViewHolder.multiViewHolderClick(xMultiAdapter: XMultiAdapter<T>): XViewHolder {
        xMultiAdapter.onXItemClickListener?.let { onXItemClickListener ->
            itemView.setOnClickListener {
                if (xMultiAdapter.multiData[layoutPosition].position == XMultiCallBack.NO_CLICK_POSITION) return@setOnClickListener
                onXItemClickListener.invoke(
                    it,
                    layoutPosition,
                    xMultiAdapter.multiData[layoutPosition]
                )
            }
        }
        return this
    }

    private fun <T : XMultiCallBack> XViewHolder.multiViewHolderLongClick(xMultiAdapter: XMultiAdapter<T>) {
        xMultiAdapter.onXItemLongClickListener?.let { onXItemLongClickListener ->
            itemView.setOnLongClickListener {
                if (xMultiAdapter.multiData[layoutPosition].position == XMultiCallBack.NO_CLICK_POSITION) return@setOnLongClickListener false
                onXItemLongClickListener.invoke(
                    itemView,
                    layoutPosition,
                    xMultiAdapter.multiData[layoutPosition]
                )
            }
        }
    }

    private fun internalOnAttachedToRecyclerView(recyclerView: RecyclerView) {
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return gridLayoutManagerSpanSize?.invoke(
                        getItemViewType(position),
                        manager,
                        position
                    )
                        ?: 0
                }
            }
        }
    }

    private fun internalOnViewAttachedToWindow(viewHolder: RecyclerView.ViewHolder) {
        val layoutParams = viewHolder.itemView.layoutParams
        if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            layoutParams.isFullSpan =
                staggeredGridLayoutManagerFullSpan?.invoke(getItemViewType(viewHolder.layoutPosition))
                    ?: false
        }
    }
}
