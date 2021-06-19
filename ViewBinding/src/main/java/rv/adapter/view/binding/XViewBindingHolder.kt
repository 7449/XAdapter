package rv.adapter.view.binding

import androidx.viewbinding.ViewBinding
import rv.adapter.view.holder.XViewHolder

class XViewBindingHolder<VB : ViewBinding>(val viewBinding: VB) : XViewHolder(viewBinding.root)