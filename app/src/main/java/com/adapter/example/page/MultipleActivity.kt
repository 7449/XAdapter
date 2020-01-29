package com.adapter.example.page

import android.os.Bundle
import android.widget.Toast
import com.adapter.example.R
import com.adapter.example.json.JsonUtils
import com.xadapter.multi.SimpleXMultiItem
import com.xadapter.multi.XMultiAdapter
import com.xadapter.recyclerview.*
import com.xadapter.vh.getImageView
import com.xadapter.vh.setText
import kotlinx.android.synthetic.main.layout_recyclerview.*

/**
 * by y on 2017/1/12.
 */
class MultipleActivity : BaseActivity(R.layout.activity_multiple, "MultipleAdapterSample") {

    companion object {
        const val TYPE_LINE = 1
        const val TYPE_ITEM = -11
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView
                .linearLayoutManager()
                .attachMultiAdapter(XMultiAdapter(JsonUtils.multipleList))
                .multiSetItemLayoutId { viewType ->
                    when (viewType) {
                        TYPE_LINE -> R.layout.item_line_adapter
                        else -> R.layout.item_multi_adapter
                    }
                }
                .multiSetBind<SimpleXMultiItem> { holder, entity, itemViewType, _ ->
                    when (itemViewType) {
                        TYPE_ITEM -> {
                            holder.setText(R.id.tv_message, entity.message)
                            holder.getImageView(R.id.iv_icon).setImageResource(entity.icon)
                        }
                    }
                }
                .multiGridLayoutManagerSpanSize { itemViewType, manager, _ ->
                    if (itemViewType != TYPE_ITEM) {
                        manager.spanCount
                    } else {
                        1
                    }
                }
                .multiStaggeredGridLayoutManagerFullSpan {
                    it != TYPE_ITEM
                }
                .multiSetOnItemClickListener<SimpleXMultiItem> { view, _, entity ->
                    Toast.makeText(view.context, "当前 position:  " + entity.itemMultiPosition + "  " + entity.message, Toast.LENGTH_SHORT).show()
                }
                .multiSetOnItemLongClickListener<SimpleXMultiItem> { view, _, entity ->
                    Toast.makeText(view.context, "当前内容  = " + entity.message, Toast.LENGTH_SHORT).show()
                    true
                }
    }
}
