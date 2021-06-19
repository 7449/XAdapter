package rv.adapter.sample.page

import android.os.Bundle
import android.view.View
import android.widget.Toast
import rv.adapter.multiple.XMultiAdapter
import rv.adapter.recyclerview.convertMultiAdapter
import rv.adapter.recyclerview.linearLayoutManager
import rv.adapter.sample.R
import rv.adapter.sample.databinding.ActivityMultipleBinding
import rv.adapter.sample.json.JsonUtils

/**
 * by y on 2017/1/12.
 */
class MultipleActivity :
    BaseActivity<ActivityMultipleBinding>(R.layout.activity_multiple, "MultipleAdapterSample") {

    override fun onCreateViewBinding(rootView: View): ActivityMultipleBinding {
        return ActivityMultipleBinding.bind(rootView)
    }

    companion object {
        const val TYPE_LINE = 1
        const val TYPE_ITEM = -11
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding.include.recyclerView
            .linearLayoutManager()
            .convertMultiAdapter(XMultiAdapter(JsonUtils.multipleList))
            .setItemLayoutId { viewType ->
                when (viewType) {
                    TYPE_LINE -> R.layout.item_line_adapter
                    else -> R.layout.item_multi_adapter
                }
            }
            .setMultiBind { holder, entity, itemViewType, _ ->
                when (itemViewType) {
                    TYPE_ITEM -> {
                        holder.setText(R.id.tv_message, entity.message)
                        holder.imageView(R.id.iv_icon).setImageResource(entity.icon)
                    }
                }
            }
            .gridLayoutManagerSpanSize { itemViewType, manager, _ ->
                if (itemViewType != TYPE_ITEM) {
                    manager.spanCount
                } else {
                    1
                }
            }
            .staggeredGridLayoutManagerFullSpan {
                it != TYPE_ITEM
            }
            .setOnItemClickListener { view, _, entity ->
                Toast.makeText(
                    view.context,
                    "当前 position:  " + entity.itemMultiPosition + "  " + entity.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setOnItemLongClickListener { view, _, entity ->
                Toast.makeText(view.context, "当前内容  = " + entity.message, Toast.LENGTH_SHORT).show()
                true
            }
    }
}
