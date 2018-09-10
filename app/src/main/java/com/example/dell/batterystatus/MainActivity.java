package com.example.dell.batterystatus;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    //
    TextView devicedet;
    Button getid;
    TelephonyManager tm;
    String im;
    String id;
    private TextView mTextViewInfo;
    private TextView mTextViewPercentage;
    private ProgressBar mProgressBar;
    private int mProgressStatus = 0;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            mTextViewInfo.setText("Battery Scale : " + scale);

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            mTextViewInfo.setText(mTextViewInfo.getText() + "\nBattery Level : " + level);
            float percentage = level / (float) scale;
            mProgressStatus = (int) ((percentage) * 100);
            mTextViewPercentage.setText("" + mProgressStatus + "%");
            mTextViewInfo.setText(mTextViewInfo.getText() +
                    "\nPercentage : " + mProgressStatus + "%");
            mProgressBar.setProgress(mProgressStatus);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mContext.registerReceiver(mBroadcastReceiver, iFilter);
        mTextViewInfo = (TextView) findViewById(R.id.tv_info);
        mTextViewPercentage = (TextView) findViewById(R.id.tv_percentage);
        mProgressBar = (ProgressBar) findViewById(R.id.pb);
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        devicedet = (TextView) findViewById(R.id.textView);
        getid = (Button) findViewById(R.id.button);
        getid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                    }

                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                im = tm.getDeviceSoftwareVersion();
                id=tm.getDeviceId();
                if(id!=null||im!=null){
                    devicedet.setText("Software Version : "+im+"\nIMEI No : "+id);
                }
                else {
                    devicedet.setText("SOMETHING WENT WRONG");
                }
            }
        });



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
                        im = tm.getDeviceSoftwareVersion();
                        id=tm.getDeviceId();
                        if(id!=null||im!=null){
                            devicedet.setText("Software Version : "+im+"\nIMEI No : "+id);
                        }
                        else {
                            devicedet.setText("SOMETHING WENT WRONG");
                        }

                    }
                } else {
                    Toast.makeText(this, "No Permission granted", Toast.LENGTH_LONG).show();
                }
                return;
        }
//
    }
    private void TestUpdate(){
        int i=1+1;
    }//

}
