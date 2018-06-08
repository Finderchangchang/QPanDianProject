package q.pandian.control

import android.content.Context
import com.google.gson.reflect.TypeToken
import gd.mmanage.method.Utils
import q.pandian.base.Url
import q.pandian.base.http.HttpUtils
import q.pandian.base.http.ListRequest
import q.pandian.base.ui.BaseModule
import q.pandian.config.command
import q.pandian.model.UserModel
import java.text.SimpleDateFormat
import java.util.*

class GoodModule:BaseModule{
    constructor(context: Context?) : super(context)
    fun search_goods() {
        var method="getpandianlist.aspx"
        var map = HashMap<String, String>()
        map.put("branchcode", Utils.getCache(Url.u_shopid))
        map.put("userid", Utils.getCache(Url.u_id))
        var sign= Utils.string2MD5(SimpleDateFormat("yyyy-MM-dd").format(Date()) + method)
        map.put("sign",sign)
        var token = object : TypeToken<ListRequest<UserModel>>() {}//需要解析的多层类
        HttpUtils<ListRequest<UserModel>>(this, command.good + 1).post(Url.key+method,map, token)
    }
}