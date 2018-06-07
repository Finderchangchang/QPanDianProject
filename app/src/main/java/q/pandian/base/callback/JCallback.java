package q.pandian.base.callback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;

import q.pandian.base.http.ListRequest;
import okhttp3.Response;
import q.pandian.base.http.ModelRequest;

/**
 * 数据解析回调类
 * Created by Administrator on 2018/2/24.
 */

public abstract class JCallback<T> extends AbsCallback<T> {
    TypeToken<T> type;

    public JCallback(TypeToken typeToken) {
        type = typeToken;
    }

    @Override
    public T convertSuccess(Response response) throws Exception {
        String s = StringConvert.create().convertSuccess(response);
        try {
            T obj = new Gson().fromJson(s, type.getType());
            response.close();
            return obj;
        } catch (Exception e) {
            TypeToken token = new TypeToken<ModelRequest<String>>() {
            };//需要解析的多层类
            T obj = new Gson().fromJson(s, token.getType());
            response.close();
            return obj;
        }
    }
}