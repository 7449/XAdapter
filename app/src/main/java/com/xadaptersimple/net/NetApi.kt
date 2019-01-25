package com.xadaptersimple.net

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * by y on 2017/6/16.
 */

object NetApi {
    const val ZL_BASE_API = "https://zhuanlan.zhihu.com/api/"

    interface ZLService {
        @GET("columns/" + "{suffix}/posts")
        fun getList(@Path("suffix") suffix: String,
                    @Query("limit") limit: Int,
                    @Query("offset") offset: Int): Observable<NetWorkBean>
    }
}
