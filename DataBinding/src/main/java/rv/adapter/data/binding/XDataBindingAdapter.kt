package rv.adapter.data.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
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

    override fun createItemViewHolder(
        parent: ViewGroup,
        layoutId: Int,
        click: ((view: View, position: Int, entity: T) -> Unit)?,
        longClick: ((view: View, position: Int, entity: T) -> Boolean)?
    ): XViewHolder {
        return XDataBindingHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        ).apply {
            setOnClickListener(this, click)
            setOnLongClickListener(this, longClick)
        }
    }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) {
        if (!isItemViewType(position)) {
            return
        }
        val pos = currentPosition(position)
        holder as XDataBindingHolder
        holder.viewDataBinding.setVariable(variableId, getItem(pos))
        if (executePendingBindings) {
            holder.viewDataBinding.executePendingBindings()
        }
    }
}