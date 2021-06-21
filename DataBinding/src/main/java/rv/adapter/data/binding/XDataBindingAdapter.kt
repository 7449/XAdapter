package rv.adapter.data.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import rv.adapter.core.ItemTypes
import rv.adapter.core.XAdapter
import rv.adapter.view.holder.XViewHolder

/**
 * @author y
 * @create 2018/12/25
 */
open class XDataBindingAdapter<T>(
    private val variableId: Int,
    private val executePendingBindings: Boolean = true
) : XAdapter<T>() {

    private val mData: ObservableArrayList<T> = ObservableArrayList()

    override var dataContainer: MutableList<T>
        get() = mData
        set(value) {
            mData.addAll(value)
        }

    override fun itemViewHolder(parent: ViewGroup): XViewHolder {
        return XDataBindingHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        ).viewHolderClick().viewHolderLongClick()
    }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) {
        if (getItemViewType(position) != ItemTypes.ITEM.type) {
            return
        }
        val pos = currentItemPosition(position)
        holder as XDataBindingHolder
        holder.viewDataBinding.setVariable(variableId, mData[pos])
        if (executePendingBindings) {
            holder.viewDataBinding.executePendingBindings()
        }
    }
}