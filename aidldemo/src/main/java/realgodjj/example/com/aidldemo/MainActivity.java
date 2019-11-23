package realgodjj.example.com.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import realgodjj.example.com.servicedemo.IMyAidlInterface;
import realgodjj.example.com.servicedemo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btRemoteStartService;
    private Button btRemoteStopService;
    private Button btRemoteBindService;
    private Button btRemoteUnbindService;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btRemoteStartService = findViewById(R.id.bt_remote_start_service);
        btRemoteStopService = findViewById(R.id.bt_remote_stop_service);
        btRemoteBindService = findViewById(R.id.bt_remote_bind_service);
        btRemoteUnbindService = findViewById(R.id.bt_remote_unbind_service);

        btRemoteStartService.setOnClickListener(this);
        btRemoteStopService.setOnClickListener(this);
        btRemoteBindService.setOnClickListener(this);
        btRemoteUnbindService.setOnClickListener(this);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
                try {
                    iMyAidlInterface.showProcess();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_remote_start_service:
                Intent intent1 = new Intent("realgodjj.example.com.myservice");
                intent1.setPackage("realgodjj.example.com.servicedemo");
                startService(intent1);
                break;

            case R.id.bt_remote_stop_service:
                Intent intent2 = new Intent("realgodjj.example.com.myservice");
                intent2.setPackage("realgodjj.example.com.servicedemo");
                stopService(intent2);
                break;

            case R.id.bt_remote_bind_service:
                Intent intent3 = new Intent("realgodjj.example.com.myservice");
                intent3.setPackage("realgodjj.example.com.servicedemo");
                bindService(intent3, serviceConnection, BIND_AUTO_CREATE);
                break;

            case R.id.bt_remote_unbind_service:
                if (serviceConnection != null) {
                    Intent intent4 = new Intent("realgodjj.example.com.myservice");
                    intent4.setPackage("realgodjj.example.com.servicedemo");
                    unbindService(serviceConnection);
                }
                break;
        }
    }
}
