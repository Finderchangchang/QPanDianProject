package q.pandian.base.callback

import java.io.Serializable

/**
 * data为Object类型
 * Created by Administrator on 2018/1/4.
 */
class ObjectRequest<T> : Serializable {
    var code: Int = 0
    var msg = ""
    var data: T? = null
}