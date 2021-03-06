package q.pandian.base.callback

import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.lzy.okgo.callback.AbsCallback
import com.lzy.okgo.convert.StringConvert
import com.lzy.okgo.request.BaseRequest
import gd.mmanage.method.Utils

import java.lang.reflect.ParameterizedType

import okhttp3.Response
import q.pandian.base.Url
import java.security.MessageDigest
import q.pandian.base.method.Sha1
import q.pandian.config.sp
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Finder丶畅畅 on 2017/5/13 00:00
 * QQ群481606175
 */

abstract class JsonCallback<T> : AbsCallback<T> {

    constructor(type: TypeToken<T>) {
        tokee = type;
    }

    var tokee: TypeToken<T>? = null
    override fun onBefore(request: BaseRequest<*>?) {
        super.onBefore(request)
        var token = Utils.getCache(Url.u_shopid)
//        if (!TextUtils.isEmpty(token)) {
//            request!!.params("branchcode", Utils.getCache(Url.u_shopid))
//            request!!.params("userid", Utils.getCache(Url.u_id))
//            request!!.params("userName", Utils.getCache(Url.u_name))
//        }
    }


    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     * <pre>
     * OkGo.get(Urls.URL_METHOD)//
     * .tag(this)//
     * .execute(new DialogCallback<LzyResponse></LzyResponse><ServerModel>>(this) {
     * @Override
     * *          public void onSuccess(LzyResponse<ServerModel> responseData, Call call, Response response) {
     * *              handleResponse(responseData.data, call, response);
     * *          }
     * *     });
     * * </ServerModel></ServerModel></pre>
     */
    @Throws(Exception::class)
    override fun convertSuccess(response: Response): T {
        val genType = javaClass.genericSuperclass
        //从上述的类中取出真实的泛型参数，有些类可能有多个泛型，所以是数值
        //Utils.putCache(KEY_SESSIONID,"");
        // var sess=Utils.getCache("PHPSESSID");
        //if(Utils.getCache(KEY_SESSIONID).equals("")) {


        // Utils.putCache("PHPSESSID", headers[0].split(":").get(1).replace("\""))
        //}
        val params = (genType as ParameterizedType).actualTypeArguments
        val s = StringConvert.create().convertSuccess(response)

        val jsonReader = JsonReader(response.body().charStream())

        //我们的示例代码中，只有一个泛型，所以取出第一个，得到如下结果
        //com.lzy.demo.model.LzyResponse<com.lzy.demo.model.ServerModel>
        val type = params[0] as? ParameterizedType ?: throw IllegalStateException("没有填写泛型参数")

        // 这里这么写的原因是，我们需要保证上面我解析到的type泛型，仍然还具有一层参数化的泛型，也就是两层泛型
        // 如果你不喜欢这么写，不喜欢传递两层泛型，那么以下两行代码不用写，并且javabean按照
        // https://github.com/jeasonlzy/okhttp-OkGo/blob/master/README_JSONCALLBACK.md 这里的第一种方式定义就可以实现
        //如果确实还有泛型，那么我们需要取出真实的泛型，得到如下结果
        //class com.lzy.demo.model.LzyResponse
        //此时，rawType的类型实际上是 class，但 Class 实现了 Type 接口，所以我们用 Type 接收没有问题
        val rawType = type.rawType
        //这里获取最终内部泛型的类型 com.lzy.demo.model.ServerModel
        val typeArgument = type.actualTypeArguments[0]

        //这里我们既然都已经拿到了泛型的真实类型，即对应的 class ，那么当然可以开始解析数据了，我们采用 Gson 解析
        //以下代码是根据泛型解析数据，返回对象，返回的对象自动以参数的形式传递到 onSuccess 中，可以直接使用
        if (typeArgument === Void::class.java) {
            //无数据类型,表示没有data数据的情况（以  new DialogCallback<LzyResponse<Void>>(this)  以这种形式传递的泛型)
            val simpleResponse = Convert.fromJson<SimpleResponse>(jsonReader, SimpleResponse::class.java)
            response.close()

            return simpleResponse.toLzyResponse() as T
        } else if (rawType === ListRequest::class.java) {
            //有数据类型，表示有data
            val lzyResponse = Convert.fromJson<ListRequest<*>>(jsonReader, type)
            response.close()
            val code = lzyResponse.code
            //这里的0是以下意思
            //一般来说服务器会和客户端约定一个数表示成功，其余的表示失败，这里根据实际情况修改
            if (code == 0) {
                return lzyResponse as T
            } else if (code == 104) {
                //比如：用户授权信息无效，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw IllegalStateException("用户授权信息无效")
            } else if (code == 105) {
                //比如：用户收取信息已过期，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw IllegalStateException("用户收取信息已过期")
            } else if (code == 106) {
                //比如：用户账户被禁用，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw IllegalStateException("用户账户被禁用")
            } else if (code == 300) {
                //比如：其他乱七八糟的等，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw IllegalStateException("其他乱七八糟的等")
            } else {
                throw IllegalStateException(lzyResponse.msg)
            }
        } else {
            response.close()
            throw IllegalStateException("基类错误无法解析!")
        }
    }
}