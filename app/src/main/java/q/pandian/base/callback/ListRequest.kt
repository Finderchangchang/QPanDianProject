package q.pandian.base.callback

import java.io.Serializable

/**
 * data为List类型
 * Created by Finder丶畅畅 on 2017/5/13 00:04
 */
class ListRequest<T> : Serializable {

    var code: Int = 0
    var Success: Boolean = false

    var msg: String? = null
    var Data: ArrayList<T>? = ArrayList()
}
