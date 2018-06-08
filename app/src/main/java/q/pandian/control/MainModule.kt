package q.pandian.control

import android.content.Context
import com.google.gson.reflect.TypeToken
import q.pandian.base.http.HttpUtils
import q.pandian.base.http.ListRequest
import q.pandian.base.ui.BaseModule
import q.pandian.config.command
import q.pandian.model.VersionModel
import java.util.HashMap

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
        var map = HashMap<String, String>()
        map.put("name", name)
        map.put("pwd", pwd)
        var url = "http://gr.rungo.net/?s=index/api/get_provinces123"
        var token = object : TypeToken<ListRequest<VersionModel>>() {}//需要解析的多层类
        HttpUtils<ListRequest<VersionModel>>(this, command.login + 1).post(url, token)
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