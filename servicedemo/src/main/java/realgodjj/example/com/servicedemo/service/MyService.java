package realgodjj.example.com.servicedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import realgodjj.example.com.servicedemo.IMyAidlInterface;

public class MyService extends Service {
    public static final String TAG = "RealgodJJ";
    private int i = 0;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Create service!");
        new Thread() {
            @Override
            public void run() {
                for (; i <= 100; i++) {
                    try {
                        sleep(1000);
//                        TimeUnit.SECONDS.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Start service!");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Destroy service!");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG, "onBind: Bind Service!");
        return new IMyAidlInterface.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public void showProcess() throws RemoteException {
                Log.d(TAG, "showProcess: " + i);
            }
        };
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: Unbind Service!");
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {
        //可以设计自己的方法，实现进度监控
        public int getProcess() {
            return i;
        }
    }
}
