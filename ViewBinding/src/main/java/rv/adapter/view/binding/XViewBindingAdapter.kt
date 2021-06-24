package rv.adapter.view.binding

import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import rv.adapter.core.XAdapter
import rv.adapter.view.holder.XViewHolder

class XViewBindingAdapter<T, VB : ViewBinding> : XAdapter<T>() {

    companion object {
        private const val viewBindingTag = -11
    }

    private var itemViewBinding: ((parent: ViewGroup) -> VB)? =
        null
    private var viewBindingListener: ((viewBinding: VB, position: Int, entity: T) -> Unit)? =
        null
    private var viewBindingAndHolderListener: ((viewBinding: VB, holder: XViewBindingHolder<VB>, position: Int, entity: T) -> Unit)? =
        null

    override fun createItemViewHolder(
        parent: ViewGroup,
        layoutId: Int,
        click: ((view: View, position: Int, entity: T) -> Unit)?,
        longClick: ((view: View, position: Int, entity: T) -> Boolean)?
    ): XViewHolder {
        val viewBinding = requireNotNull(itemViewBinding?.invoke(parent))
        return XViewBindingHolder(viewBinding).apply {
            itemView.setTag(viewBindingTag, viewBinding)
            setOnClickListener(this, click)
            setOnLongClickListener(this, longClick)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: XViewHolder, position: Int) {
        if (!isItemViewType(position)) {
            return
        }
        val viewBinding = holder.itemView.getTag(viewBindingTag) as VB
        val pos = currentPosition(position)
        viewBindingListener?.invoke(viewBinding, pos, getItem(pos))
        viewBindingAndHolderListener?.invoke(
            viewBinding,
            holder as XViewBindingHolder<VB>,
            pos,
            getItem(pos)
        )
    }

    override fun bindItem(action: (holder: XViewHolder, position: Int, entity: T) -> Unit): XAdapter<T> {
        throw RuntimeException("@see onBindItem")
    }

    override fun setItemLayoutId(layoutId: Int): XAdapter<T> {
        throw RuntimeException("@see onCreateViewBinding")
    }

    fun onCreateViewBinding(action: (parent: ViewGroup) -> VB) = also {
        this.itemViewBinding = action
    }

    fun onBindItem(action: (viewBinding: VB, position: Int, entity: T) -> Unit) =
        also {
            this.viewBindingListener = action
        }

    fun onBindItem(action: (viewBinding: VB, holder: XViewBindingHolder<VB>, position: Int, entity: T) -> Unit) =
        also {
            this.viewBindingAndHolderListener = action
        }

}