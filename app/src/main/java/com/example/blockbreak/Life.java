package com.example.blockbreak;

import android.graphics.Rect;

public class Life { // 라이프 생성클래스
    int x;
    int y;
    int width;
    int height; // 라이프의 위치와 라이프의 크기
    Rect life_Rect; // 라이프의 사각영역
    boolean life_Exit; // 라이프의 존재여부
    // 초기화

    Life( int x, int y, int width, int height, boolean exist ){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        life_Rect = new Rect( x, y, x + width, y + height ); // 왼쪽, 아래, 오른쪽, 위
        life_Exit = exist;
    } // Life 생성자 끝

    // 라이프 소모
    void breakLife(){ life_Exit = false; }

}

