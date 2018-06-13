package q.pandian.base.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;
import q.pandian.base.callback.JCallback;
import q.pandian.base.callback.JsonCallback;
import q.pandian.base.ui.BaseModule;

/**
 * Created by Administrator on 2018/2/24.
 */

public class HttpUtils<T> {
    BaseModule module;
    int command;

    public HttpUtils(BaseModule control, int com) {
        module = control;
        command = com;
    }

    public void post(String url, TypeToken<T> type) {
        post(url, null, type);
    }

    public void post(String url, HashMap<String, String> map, TypeToken<T> type) {
        PostRequest ok = OkGo.post(url);

        if (map != null) {
            for (String key : map.keySet()) {
                ok.params(key, map.get(key));
            }
        }
        ok.execute(new JCallback<T>(type) {
            @Override
            public void onSuccess(T typeToken, Call call, Response response) {
                module.callback(command, typeToken);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                module.errorCallback(command,e.getMessage());

            }
        });
    }
    public void get(String url, HashMap<String, String> map, TypeToken<T> type) {
        // PostRequest ok = OkGo.post(url);
        GetRequest ok = OkGo.get(url);
        if (map != null) {
            for (String key : map.keySet()) {
                ok.params(key, map.get(key));
            }
        }
        ok.execute(new JCallback<T>(type) {
            @Override
            public void onSuccess(T typeToken, Call call, Response response) {
                module.callback(command, typeToken);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                module.errorCallback(command,e.getMessage());

            }
        });
    }
}
