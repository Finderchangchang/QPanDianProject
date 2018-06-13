package q.pandian.control

import android.content.Context
import com.google.gson.reflect.TypeToken
import gd.mmanage.method.Utils
import gd.mmanage.method.Utils.getToken
import q.pandian.base.Url
import q.pandian.base.http.HttpUtils
import q.pandian.base.http.ListRequest
import q.pandian.base.ui.BaseModule
import q.pandian.config.command
import q.pandian.model.GoodModel
import q.pandian.model.UserModel
import q.pandian.ui.ac.DD
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class GoodModule : BaseModule {
    constructor(context: Context?) : super(context)

    fun search_goods() {
        var method = "getpandianlist.aspx"
        var map = HashMap<String, String>()
        map.put("sign", getToken(method))
        map.put("userid", Utils.getCache(Url.u_id))
        map.put("branchcode", Utils.getCache(Url.u_shopid))
        var token = object : TypeToken<ListRequest<GoodModel>>() {}//需要解析的多层类
        HttpUtils<ListRequest<GoodModel>>(this, command.good + 1).post(Url.key + method, map, token)
    }

    /**
     * 扫码操作
     * barcode商品条码，branchcode店铺代码，userid用户编号，userName用户名称
     * */
    fun scan_good(barcode: String) {
        var method = "saoma.aspx"
        var map = HashMap<String, String>()
        map.put("barcode", barcode)
        map.put("sign", getToken(method))
        map.put("branchcode", Utils.getCache(Url.u_shopid))
        map.put("userid", Utils.getCache(Url.u_id))
        map.put("userName", Utils.getCache(Url.u_name))
        var token = object : TypeToken<ListRequest<GoodModel>>() {}//需要解析的多层类
        HttpUtils<ListRequest<GoodModel>>(this, command.good + 2).get(Url.key + method, map, token)
    }

    /**
     * 删除盘点商品
     * userid用户编号，sid商品编号商品列表的id
     * */
    fun delete_good(sid: String) {
        var method = "delpandian.aspx"
        var map = HashMap<String, String>()
        map.put("sid", sid)
        map.put("userid", Utils.getCache(Url.u_id))
        var token = object : TypeToken<ListRequest<GoodModel>>() {}//需要解析的多层类
        HttpUtils<ListRequest<GoodModel>>(this, command.good + 3).post(Url.key + method, map, token)
    }

    /**
     * 保存盘点列表
     * userid用户编号，data商品列表json。盘点列表data数据原样返回。
     * */
    fun save_goods(barcode: String) {
        var method = "savepandian.aspx"
        var map = HashMap<String, String>()
        map.put("data", barcode)
        map.put("userid", Utils.getCache(Url.u_id))
        var token = object : TypeToken<ListRequest<GoodModel>>() {}//需要解析的多层类
        HttpUtils<ListRequest<GoodModel>>(this, command.good + 4).get(Url.key + method, map, token)
    }

    /**
     * 盘点完成
     * userid用户编号，data商品列表json。盘点列表data数据原样返回。
     * */
    fun scan_success(barcode: String) {
        var method = "successpandian.aspx"
        var map = HashMap<String, String>()
        map.put("data", barcode)
        map.put("userid", Utils.getCache(Url.u_id))
        var token = object : TypeToken<ListRequest<GoodModel>>() {}//需要解析的多层类
        HttpUtils<ListRequest<GoodModel>>(this, command.good + 5).get(Url.key + method, map, token)
    }
}