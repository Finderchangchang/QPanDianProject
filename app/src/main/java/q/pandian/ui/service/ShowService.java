package q.pandian.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.Timer;

/**
 * Created by Administrator on 2018/3/14.
 */

public class ShowService extends Service {
    static Timer timer = null;


    public void onCreate() {
        Log.e("addNotification", "===========create=======");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public int onStartCommand(final Intent intent, int flags, int startId) {

        new Thread(new MyThread()).start();

        return super.onStartCommand(intent, flags, startId);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 要做的事情
            Log.i("///", "000");
            super.handleMessage(msg);
        }
    };

    class MyThread implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                try {
                    Thread.sleep(1000);// 线程暂停10秒，单位毫秒
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);// 发送消息
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.e("addNotification", "===========destroy=======");
        super.onDestroy();
    }
}
