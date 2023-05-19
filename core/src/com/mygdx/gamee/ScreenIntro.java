package com.mygdx.gamee;

import static com.mygdx.gamee.Game.SCR_HEIGHT;
import static com.mygdx.gamee.Game.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class ScreenIntro implements Screen {
    Game c;
    Texture imgBG;
    TextButton btnPlay, btnSettings, btnAbout, btnExit;
    Sound[] sndTik = new Sound[1];
    Sound[] music = new Sound[5];
    boolean gameStart = true;
    int snd;

    public ScreenIntro(Game context) {
        c = context;
        snd = MathUtils.random(0,4);
        imgBG = new Texture("home.png");
        btnPlay = new TextButton(c.fontLarge, "PLAY", 650);
        btnSettings = new TextButton(c.fontLarge, "SETTINGS", 550);
        btnAbout = new TextButton(c.fontLarge, "ABOUT", 450);
        btnExit = new TextButton(c.fontLarge, "EXIT", 350);
        sndTik[0] = Gdx.audio.newSound(Gdx.files.internal("tik.mp3"));
        for (int i = 0; i < music.length; i++) {
            music[i] = Gdx.audio.newSound(Gdx.files.internal("main"+i+".mp3"));
        }

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // обработка касаний экрана
        if (Gdx.input.justTouched()) {
            c.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            c.camera.unproject(c.touch);
            if (btnPlay.hit(c.touch.x, c.touch.y)) {
                gameStart = false;
                c.setScreen(c.screenGame);
                if (c.screenGame.soundOn) sndTik[0].play();
            }
            if (btnSettings.hit(c.touch.x, c.touch.y)) {
                gameStart = false;
                c.setScreen(c.screenSettings);
                if (c.screenGame.soundOn) sndTik[0].play();
            }
            if (btnAbout.hit(c.touch.x, c.touch.y)) {
                gameStart = false;
                c.setScreen(c.screenAbout);
                if (c.screenGame.soundOn) sndTik[0].play();
            }
            if (btnExit.hit(c.touch.x, c.touch.y)) {
                gameStart = false;
                Gdx.app.exit();
                if (c.screenGame.soundOn) sndTik[0].play();
            }
        }
        // отрисовка всей графики
        c.camera.update();
        c.batch.setProjectionMatrix(c.camera.combined);
        c.batch.begin();
        c.batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        btnPlay.font.draw(c.batch, btnPlay.text, btnPlay.x, btnPlay.y);
        btnSettings.font.draw(c.batch, btnSettings.text, btnSettings.x, btnSettings.y);
        btnAbout.font.draw(c.batch, btnAbout.text, btnAbout.x, btnAbout.y);
        btnExit.font.draw(c.batch, btnExit.text, btnExit.x, btnExit.y);
        c.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}