package com.mygdx.gamee;

import static com.mygdx.gamee.Game.SCR_HEIGHT;
import static com.mygdx.gamee.Game.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Muha extends Tarakan{
    float x,y;
    int width, height;
    float vx,vy;
    boolean isAlive = true;
    public Muha(){
        x = MathUtils.random(100,SCR_WIDTH-100);
        y = MathUtils.random(100,SCR_HEIGHT-100);
        vx = MathUtils.random(-3,2f);
        vy = MathUtils.random(-2,3f);
        width = height = MathUtils.random(50,150);
    }
    void move(){
        x += vx;
        y += vy;
    }
    float getX() {
        return x - width / 2;
    }

    float getY() {
        return y - height / 2;
    }

    boolean hit(float tx, float ty){
        if(x-width/2f<tx && tx<x+width/2f && y-height/2f<ty && ty<y+height/2f) {
            isAlive = false;
            vx = vy = 0;
            return true;
        }
        return false;
    }
}
