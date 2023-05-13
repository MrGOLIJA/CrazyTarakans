package com.mygdx.gamee;

import static com.mygdx.gamee.Game.SCR_HEIGHT;
import static com.mygdx.gamee.Game.SCR_WIDTH;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Tarakan{

    int x,y;
    int width,height;
    public long timeKill;
    float vx,vy;
    boolean isAlive = true;
    public Tarakan(){
        x = MathUtils.random(20,SCR_WIDTH-20);
        y = MathUtils.random(20,SCR_HEIGHT-20);
        vx = MathUtils.random(-5,5f);
        vy = MathUtils.random(-5,5f);
        width = height = MathUtils.random(50,150);

    }

    void move(){
        x += vx;
        y += vy;
    }
    float getX(){return x-width/2;}
    float getY(){
        return y-height/2;
    }
    boolean outOfScreen(){
        return x<-width || x>SCR_WIDTH+width || y<-height || y>SCR_HEIGHT+height;
    }
    boolean outBounds2(){
        if(x<0+width/2 || x>SCR_WIDTH-width/2) vx = -vx;
        if(y<0+height/2 || y>SCR_HEIGHT-height/2) vy = -vy;
        return false;
    }
    boolean hit(float tx, float ty){
        timeKill = TimeUtils.millis();
        if(x-width/2f<tx && tx<x+width/2f && y-height/2f<ty && ty<y+height/2f) {
            isAlive = false;
            vx = vy = 0;
            return true;
        }
        return false;
    }
}