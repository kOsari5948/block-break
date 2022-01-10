package com.example.blockbreak;

import android.graphics.Rect;

public class Block{ //블록 생성클래스
    int x;
    int y;
    int width;
    int height; // 블록의 위치와 블록의 크기
    Rect Box_Rect; // 블록의 사각영역
    boolean Box_Exit; // 블록의 존재여부
    // 초기화

    Block( int x, int y, int width, int height, boolean exist ){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        Box_Exit = exist;
        Box_Rect = new Rect( x, y, x + width, y + height ); // 왼쪽, 아래, 오른쪽, 위
    } // Block 생성자 끝

    // 블록이 ball 사각영역과 겹치는 부분이 존재하는지 판별한다.
    int isCrash( Rect ball ){
        // ball의 사각영역과 Box_Rect의 사각영역이 겹치면
        if( Rect.intersects( ball, Box_Rect ) )
        {
            if( Box_Rect.bottom < ball.top ) // 구가 블록의 아래를 때림.
                return 1;
            else if(x+width==ball.left) //구가 블록 왼쪽을 때림

            else // 구가 블록의 위를 때림
                return 2;
        }
        return 0; // 겹치지 않으면 0  리턴
    }

    // 블록을 부순다
    void breakBlock(){
        Box_Exit = false;
    }
}
