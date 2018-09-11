package com.xadaptersimple.net

/**
 * by y on 2016/11/17
 */

public class NetWorkBean {

    lateinit var title: String
    var titleImage: String? = null
    var slug: Int = 0
    var author: Author? = null

    class Author {
        var profileUrl: String? = null
        var bio: String? = null
        var name: String? = null
    }
}
