package q.pandian.control

import android.content.Context
import com.google.gson.reflect.TypeToken
import gd.mmanage.method.Utils.string2MD5
import q.pandian.base.Url
import q.pandian.base.http.HttpUtils
import q.pandian.base.http.ListRequest
import q.pandian.base.ui.BaseModule
import q.pandian.config.command
import q.pandian.model.UserModel
import q.pandian.model.VersionModel
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Administrator on 2018/1/4.
 */
class MainModule : BaseModule {
    constructor(context: Context?) : super(context)

    /**
     * 用户登录
     * @param name 账号
     * @param pwd 密码
     * */
    fun login(name: String, pwd: String) {
        var method="login.aspx"
        var map = HashMap<String, String>()
        map.put("user", name)
        map.put("pwd", pwd)
        var sign=string2MD5(SimpleDateFormat("yyyy-MM-dd").format(Date())+method)
        map.put("sign",sign)
        var token = object : TypeToken<ListRequest<UserModel>>() {}//需要解析的多层类
        HttpUtils<ListRequest<UserModel>>(this, command.login + 1).post(Url.key+method,map, token)
    }

    /**
     * 检查更新
     * */
    fun check_version() {
        var url = "http://gr.rungo.net/?s=index/api/get_provinces"
        var token = object : TypeToken<ListRequest<VersionModel>>() {}
        HttpUtils<ListRequest<VersionModel>>(this, command.login + 2).post(url, token)
    }
}