package com.mygdx.gamee;

import static com.mygdx.gamee.Game.SCR_HEIGHT;
import static com.mygdx.gamee.Game.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class ScreenSettings implements Screen {
    Game c;

    Texture imgBG;

    TextButton btnSound, btnMusic, btnLanguge, btnExit;
    Sound[] sndTik = new Sound[1];
    public ScreenSettings(Game context) {
        c = context;
        imgBG = new Texture("home.png");
        btnSound = new TextButton(c.fontLarge, "SOUND ON",650);
        btnMusic = new TextButton(c.fontLarge, "MUSIC ON",550);
        btnLanguge = new TextButton(c.fontLarge, "LANGUAGE - EN", 450);
        btnExit = new TextButton(c.fontLarge, "EXIT", 350);
        sndTik[0] = Gdx.audio.newSound(Gdx.files.internal("tik.mp3"));
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
// обработка касаний экрана
        if(Gdx.input.justTouched()) {
            c.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            c.camera.unproject(c.touch);
            if(btnSound.hit(c.touch.x, c.touch.y)) {
                if(c.screenGame.soundOn) sndTik[0].play();
                c.screenGame.soundOn = ! c.screenGame.soundOn;
                if(c.screenGame.soundOn) btnSound.text = "SOUND ON";
                else btnSound.text = "SOUND OFF";
            }
            if(btnMusic.hit(c.touch.x,c.touch.y)){
                if(c.screenGame.soundOn) sndTik[0].play();
                c.screenGame.musicOn = ! c.screenGame.musicOn;
                if(c.screenGame.musicOn) btnMusic.text = "MUSIC ON";
                else btnMusic.text = "MUSIC OFF";
            }
            if(btnExit.hit(c.touch.x, c.touch.y)) {
                if(c.screenGame.soundOn) sndTik[0].play();
                c.setScreen(c.screenIntro);
            }
        }
        // отрисовка всей графики
        c.camera.update();
        c.batch.setProjectionMatrix(c.camera.combined);
        c.batch.begin();
        c.batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        btnSound.font.draw(c.batch, btnSound.text, btnSound.x, btnSound.y);
        btnMusic.font.draw(c.batch, btnMusic.text, btnMusic.x, btnMusic.y);
        btnLanguge.font.draw(c.batch, btnLanguge.text, btnLanguge.x, btnLanguge.y);
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
