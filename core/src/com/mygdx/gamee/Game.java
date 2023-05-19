package com.mygdx.gamee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;

public class Game extends com.badlogic.gdx.Game {
    public final static int SCR_WIDTH = 1280,SCR_HEIGHT = 720;//x;y
    BitmapFont font, fontLarge;
    Tarakan tarakan;
    Vector3 touch;
    SpriteBatch batch;
    OrthographicCamera camera;


    ScreenGame screenGame;
    ScreenIntro screenIntro;
    ScreenSettings screenSettings;
    ScreenAbout screenAbout;

    @Override
    public void create() {
        tarakan = new Tarakan();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();
        generateFont();

        screenGame = new ScreenGame(this);
        screenIntro = new ScreenIntro(this);
        screenSettings = new ScreenSettings(this);
        screenAbout = new ScreenAbout(this);
        setScreen(screenIntro);

    }

    @Override
    public void dispose () {
        batch.dispose();
    }
    public void generateFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("mr_insulag.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color = new Color(1, 0.8f, 0.4f, 1);
        parameter.size = 45;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.borderStraight = true;
        parameter.shadowColor = new Color(0.1f, 0.1f, 0.1f, 0.8f);
        parameter.shadowOffsetX = parameter.shadowOffsetY = 3;
        String str = "";
        for (char i = 0x20; i < 0x7B; i++) str += i;
        for (char i = 0x401; i < 0x452; i++) str += i;
        parameter.characters = str;
        //parameter.characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        font = generator.generateFont(parameter);
        parameter.size = 60;
        fontLarge = generator.generateFont(parameter);
        generator.dispose();
    }

}