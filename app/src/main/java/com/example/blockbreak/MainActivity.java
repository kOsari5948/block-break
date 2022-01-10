package com.example.blockbreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    MyView vw;
    public static int deviceWidth;
    public static int deviceHeight;
    public static int bottomBarHeight = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics(); //디바이스 높이 너비 구하기
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics); //실제로 구하는 코드

        int resourceIdBottom = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceIdBottom > 0) bottomBarHeight = getResources().getDimensionPixelSize(resourceIdBottom);
        deviceWidth = displayMetrics.widthPixels; //구한거 너비만
        deviceHeight = displayMetrics.heightPixels;//구한거 높이만

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