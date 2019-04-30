package com.adapter.example.net

import io.reactivex.Observable
import retrofit2.http.GET

/**
 * by y on 2017/6/16.
 */

object NetApi {

    const val ZL_BASE_API = "https://news-at.zhihu.com/api/4/"

    interface ZLService {
        @GET("news/latest")
        fun getList(): Observable<NetWorkBean>
    }
}
