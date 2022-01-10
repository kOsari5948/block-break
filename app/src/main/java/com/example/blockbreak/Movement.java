package com.example.blockbreak;

public class Movement{ // 위치 생성 클래스
    public int xMov;
    public int yMov;
    Movement(double angle){
        xMov=Math.round(Math.round(Math.cos(Math.toRadians(angle)) * 100));
        yMov=Math.round(Math.round(Math.sin(Math.toRadians(angle)) * 100));
    }
}
