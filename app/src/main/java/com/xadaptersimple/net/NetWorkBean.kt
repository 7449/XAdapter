package com.xadaptersimple.net

/**
 * by y on 2016/11/17
 */

class NetWorkBean {

    var title = ""
    var titleImage = ""
    var slug: Int = 0
    lateinit var author: Author

    class Author {
        var profileUrl = ""
        var bio = ""
        var name = ""
    }
}
