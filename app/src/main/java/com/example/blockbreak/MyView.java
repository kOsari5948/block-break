package com.example.blockbreak;

import static android.content.ContentValues.TAG;

import static com.example.blockbreak.MainActivity.bottomBarHeight;
import static com.example.blockbreak.MainActivity.deviceHeight;
import static com.example.blockbreak.MainActivity.deviceWidth;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MyView extends View {
    int xstep; // 공의 x좌표
    int ystep; // 공의 y좌표
    int xMov; // 공의 X 축 증감
    int yMov; // 공의 Y 축 증감

    int angle; // 공의 이동각도
    int size; // 공의 크기

    Rect rect; // 공 사각 영역
    Rect barRect; // 바 사각 영역
    int xBar; // 바의 x위치
    int yBar; // 바의 y 위치
    int barWidth = 500;// 바의 너비
    int barHeight = 40; // 바의 높이
    int blockXpos; // 블록의 시작위치
    int blockYpos;
    int blockXsize;
    int blockYsize;// 블록의 크기

    Block[][] blocks;

    Life[] life;
    int heart = 3;
    int lifeXpos; // 라이프의 위치
    int lifeYpos;
    int lifeXsize;
    int lifeYsize;// 라이프의 크기

    public MyView(Context context) {

        super(context); // 화면안의 랜덤한 위치에 생성

       // angle = new Random().nextInt(360); //90 수직 하강- 270 수직 상승
        angle = 90;
        // 생성된 각도로 x 증감, y 증감 계
        Movement mv = new Movement(angle);
        xMov = mv.xMov;
        yMov = mv.yMov;

        size = 60; // 네모크기 4로 설정

        xBar = 100;
        yBar =  (deviceHeight-bottomBarHeight)*92/100 ;

        xstep =500;
        ystep =yBar - 200; // 임의의 위치에 생성
        //diveheight 디바이스 전체 높이 네비게이션 바 빼고
        //bottombarHeight 네비게이션 바 만큼
        
        //deviceHeight-bottomBarHeight 화면에보이는 영역(네비게이션 바에 살짝 겹침)
        
        //92/100 만 이용해서 튕기는 바 생성

        barRect = new Rect();//바

        barRect.left = xBar-barWidth/2;
        barRect.top = yBar;
        barRect.right = xBar+barWidth/2;
        barRect.bottom = barRect.top + barHeight; // 바의 사각영역 설정

        rect = new Rect(); //공

        rect.left = xstep; // 사각 영역 설정
        rect.top = ystep;
        rect.right = xstep + size;
        rect.bottom = xstep + size;

        blockXpos = 35; // 블록의 시작 위치
        blockYpos = 70;
        blockXsize = 150;
        blockYsize = 150; // 블록의 크기

        // 블록 배열객체 생성 초기
        blocks = new Block[15][10]; //블록 여러개
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                blocks[i][j] = new Block(blockXpos + j * (blockXsize + 300), blockYpos + i * (blockYsize + 300), blockXsize, blockYsize, true);
            }
        } // 블록 생성

        lifeXpos = 10; // 라이프의 위치
        lifeYpos = 10;
        lifeXsize = 50;
        lifeYsize = 50; // 라이프의 크기

        life = new Life[heart];
        for (int i = 0;i<life.length;i++) {
            life[i] = new Life(lifeXpos + i * (lifeXsize + 30), lifeYpos,lifeXsize,lifeYsize,true);
        }
    }
    // 선택한 범위의 각도가 생성된다.
    private void makeAngle(int start, int range) {
        angle = new Random().nextInt(range) + start;
        if(angle>=360) {
            angle -= 360;
        }
    }
    private void remakeAngle(int start, int middle){
        int a;
        a= middle - start;
        angle = (middle + a) + 180;
    }

    //★안드로이드 코드 내부에 있는 거 계속 갱신★
    public void onDraw(Canvas canvas) {
        Paint pnt = new Paint(); // 페인트 객체 생성
        pnt.setColor(Color.BLUE); // 파란색 색깔 선택
        canvas.drawColor(Color.WHITE); // 하얀색 배경

        // 왼쪽 벽에 부딧친 경우(오른쪽 일수도 있음)
        if (xstep < 0) {
            //위로 올라가면서 부딪침
            Log.d("left", angle+"");

            remakeAngle(angle,180);

            xstep = 0;
        }
        // 윗쪽벽에 부딧친 경우
        if (ystep < 0) {

            Log.d("up", angle+"");

            remakeAngle(angle,270);

            ystep = 0;
        }

        // 오른쪽벽 부딧친 경우
        if (xstep + size > getWidth()) { //xstep < 0

            Log.d("right", angle+"");

            remakeAngle(angle,0);

            xstep = getWidth() - size;

        }

        //아래쪽 벽 부딧친 경우
        if (ystep + size > deviceHeight-bottomBarHeight ) {

            Log.d("down", angle+"");
            remakeAngle(angle, 90);
            ystep = getHeight() - size; // 벽에 들어가버리는것 방지
            
            life[(heart-1)].breakLife();
            if (heart>0) {
                heart = heart -1;
            }

        }
        Movement mm = new Movement(angle);
        xMov = mm.xMov;
        yMov = mm.yMov;

        xstep += xMov; // 네모 이
        ystep += yMov; // 오른쪽 벽에 부딧친 경우

        rect.left = xstep;
        rect.top = ystep;
        rect.right = rect.left + size;
        rect.bottom = rect.top + size;

        int n;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j].Box_Exit) {
                    n = blocks[i][j].isCrash(rect);    //벽돌 부수기
                    if (n == 1) { // 블록이 아래 경우 위쪽으로 튀게한다.
                        remakeAngle(angle, 90);
                        blocks[i][j].breakBlock();
                        break;
                    } else if (n == 2) { // 블록이 위의 경우 아래쪽으로 튀게한다.
                        remakeAngle(angle, 270);
                        blocks[i][j].breakBlock();
                        break;
                    }else if(n==3){ //오른쪽 때림
                        remakeAngle(angle, 180);
                        blocks[i][j].breakBlock();
                        break;
                    }else if(n==4){// 왼쪽 때림
                        remakeAngle(angle, 0);
                        blocks[i][j].breakBlock();
                        break;
                    }
                }
            }
        }



        // 바와 공이 겹칠경우 윗쪽으로 튀게 한다
        if (Rect.intersects(barRect, rect)) {
            remakeAngle(angle, 90);
        }

        canvas.drawRect(rect, pnt); // 공 그리기
        canvas.drawRect(xBar - barWidth / 2, yBar, xBar + barWidth / 2, yBar + 10, pnt);
        pnt.setColor(Color.YELLOW);
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j].Box_Exit) {
                    canvas.drawRect(blocks[i][j].Box_Rect, pnt);
                }
            }
        }
        pnt.setColor(Color.RED);
        for (int i = 0; i < life.length; i++) {
            if (life[i].life_Exit) {
                canvas.drawRect(life[i].life_Rect, pnt);
            }
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            return true;

        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {

            xBar = (int)event.getX();

            barRect.left = xBar-barWidth/2;
            barRect.top = yBar;
            barRect.right = xBar+barWidth/2;
            barRect.bottom = barRect.top + barHeight; // 바의 사각영역 설정

            return true;
        }
        return false;
    }
}
