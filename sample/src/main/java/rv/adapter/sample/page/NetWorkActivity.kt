package rv.adapter.sample.page

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import io.reactivex.Observable
import io.reactivex.network.*
import retrofit2.http.GET
import rv.adapter.layout.LayoutStatus
import rv.adapter.recyclerview.*
import rv.adapter.sample.R
import rv.adapter.sample.databinding.ActivityNetworkBinding
import rv.adapter.sample.json.SampleEntity
import rv.adapter.sample.json.StoriesEntity

/**
 * by y on 2016/11/17
 */
class NetWorkActivity :
    BaseActivity<ActivityNetworkBinding>(R.layout.activity_network, "NetWorkSample"),
    RxRequestCallback<StoriesEntity> {

    override fun onCreateViewBinding(rootView: View): ActivityNetworkBinding {
        return ActivityNetworkBinding.bind(rootView)
    }

    interface ZLService {
        @GET("news/latest")
        fun getList(): Observable<StoriesEntity>
    }

    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.include.recyclerView
            .linearLayoutManager()
            .attachXAdapter<SampleEntity>()
            .openLoadingMore()
            .openPullRefresh()
            .setItemLayoutId(R.layout.layout_json_item)
            .setOnBind<SampleEntity> { holder, _, entity ->
                Glide
                    .with(holder.context)
                    .load(entity.image)
                    .into(holder.imageView(R.id.image))
                holder.setText(R.id.title, entity.title)
            }
            .setRefreshListener {
                page = 0
                viewBinding.include.recyclerView.removeAll()
                netWork()
            }
            .setLoadMoreListener {
                if (page < 1) {
                    netWork()
                    ++page
                } else {
                    viewBinding.include.recyclerView.setLoadMoreStatus(LayoutStatus.NO_MORE)
                }
            }
            .refresh(viewBinding.include.recyclerView)
    }

    private fun netWork() {
        ZLService::class.java
            .create()
            .getList()
            .cancel(javaClass.simpleName)
            .request(javaClass.simpleName, this)
    }

    override fun onNetWorkStart() {
        if (page != 0) {
            viewBinding.include.recyclerView.setLoadMoreStatus(LayoutStatus.LOAD)
        }
    }

    override fun onNetWorkError(throwable: Throwable) {
        if (page == 0) {
            viewBinding.include.recyclerView.setRefreshStatus(LayoutStatus.ERROR)
        } else {
            viewBinding.include.recyclerView.setLoadMoreStatus(LayoutStatus.ERROR)
        }
    }

    override fun onNetWorkComplete() {
        if (page == 0) {
            viewBinding.include.recyclerView.setRefreshStatus(LayoutStatus.SUCCESS)
        } else {
            viewBinding.include.recyclerView.setLoadMoreStatus(LayoutStatus.SUCCESS)
        }
    }

    override fun onNetWorkSuccess(data: StoriesEntity) {
        viewBinding.include.recyclerView.addAll(data.stories)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.instance.cancel(javaClass.simpleName)
    }
}
