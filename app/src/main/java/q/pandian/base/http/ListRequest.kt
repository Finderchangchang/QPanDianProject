package q.pandian.base.http

/**
 * Created by Administrator on 2018/1/4.
 */
class ListRequest<T> {
    var state: Int = 0//请求码0:请求成功。1：失败。2：报错
    var msg: String? = ""//提示的消息
    var data: List<T>? = null
}