package com.example.connectphone;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView iptxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iptxt = findViewById(R.id.textviewip);

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        iptxt.setText(ip);

        connect();
    }

    private void connect() {
        try{
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            outputStream.writeBytes("setprop service.adb.tcp.port 5555");
//            outputStream.writeBytes("stop adbd");
//            outputStream.writeBytes("start adbd");
            outputStream.flush();

            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            try {
                su.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }catch(IOException e){
            e.printStackTrace();
            }
        }

}