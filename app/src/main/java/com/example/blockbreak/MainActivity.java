package com.example.blockbreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends AppCompatActivity {
    MyView vw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vw = new MyView(this);
        setContentView(vw);

        mHandler.sendEmptyMessage( 0 );

    }
    Handler mHandler = new Handler() {
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0) { // 화면을 갱신해줌
                vw.invalidate();
                mHandler.sendEmptyMessageDelayed( 0, 50 );
            }
        }
    };
}