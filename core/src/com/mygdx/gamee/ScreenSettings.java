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
    Texture imgT,imgMr,imgZh;

    TextButton btnSound, btnMusic, btnLanguge, btnExit,btnLevel,btnSkin;
    Sound[] sndTik = new Sound[1];
    int whatSkin, whatLevel;
    public ScreenSettings(Game context) {
        c = context;
        imgBG = new Texture("home.png");
        imgT = new Texture("tarakan.png");
        imgMr = new Texture("muravei.png");
        imgZh = new Texture("zhuk.png");
        btnSound = new TextButton(c.fontLarge, "SOUND ON",650);
        btnMusic = new TextButton(c.fontLarge, "MUSIC ON",550);
        btnLevel = new TextButton(c.fontLarge,"LEVEL:HARD",350);
        btnLanguge = new TextButton(c.fontLarge, "LANGUAGE - EN", 450);
        btnSkin = new TextButton(c.fontLarge,"SKIN: TARAKAN",250);
        btnExit = new TextButton(c.fontLarge, "EXIT", 150);
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
            if(btnLevel.hit(c.touch.x,c.touch.y)){
                if(c.screenGame.soundOn) sndTik[0].play();
                whatLevel ++;
                if (whatLevel == ScreenGame.TARAKAN) btnLevel.text = "LEVEL: HARD";
                if (whatLevel == ScreenGame.MURAVEI) btnLevel.text = "LEVEL: MEDIUM";
                if (whatLevel == ScreenGame.ZHUK) btnLevel.text = "LEVEL: LOW";
                if (whatLevel>2) {
                    whatLevel = 0;
                    btnLevel.text = "LEVEL: HARD";
                }
            }
            if(btnSkin.hit(c.touch.x,c.touch.y)){
                if(c.screenGame.soundOn) sndTik[0].play();
                whatSkin++;
                if (whatSkin == ScreenGame.TARAKAN) btnSkin.text = "SKIN: TARAKAN";
                if (whatSkin == ScreenGame.MURAVEI) btnSkin.text = "SKIN: MURAVEI";
                if (whatSkin == ScreenGame.ZHUK) btnSkin.text = "SKIN: ZHUK";
                if (whatSkin>2) {
                    whatSkin = 0;
                    btnSkin.text = "SKIN: TARAKAN";
                }
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
        btnLevel.font.draw(c.batch,btnLevel.text,btnLevel.x,btnLevel.y);
        btnSkin.font.draw(c.batch,btnSkin.text,btnSkin.x,btnSkin.y);
        if (whatSkin == ScreenGame.TARAKAN) c.batch.draw(imgT,btnSkin.x+550,btnSkin.y-100,150,150);
        if (whatSkin == ScreenGame.MURAVEI) c.batch.draw(imgMr,btnSkin.x+550,btnSkin.y-100,150,150);
        if (whatSkin == ScreenGame.ZHUK) c.batch.draw(imgZh,btnSkin.x+550,btnSkin.y-100,150,150);
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
