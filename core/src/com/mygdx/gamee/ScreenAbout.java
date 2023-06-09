package com.mygdx.gamee;

import static com.mygdx.gamee.Game.SCR_HEIGHT;
import static com.mygdx.gamee.Game.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class ScreenAbout implements Screen {
Game c;
Texture imgBG;
TextButton btnBack;
Sound[] sndTik = new Sound[1];
String textAbout =
        "Суть игры в том, чтобы\n" +
        "уничтожить всех тараканов.\n"+
        "На каждом уровне сложности\n" +
        "разное кол-во тараканов.\n"+
        "Игра была создана для изучения\n" +
        "основ программирования.\n";
    public ScreenAbout(Game game){
        c = game;
        imgBG = new Texture("home.png");
        btnBack = new TextButton(c.fontLarge, "НАЗАД", 100);
        sndTik[0] = Gdx.audio.newSound(Gdx.files.internal("tik.mp3"));
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            c.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            c.camera.unproject(c.touch);

            if(btnBack.hit(c.touch.x, c.touch.y)) {
                if(c.screenGame.soundOn) sndTik[0].play();
                c.setScreen(c.screenIntro);
            }
        }

        // события

        // отрисовка всей графики
        c.camera.update();
        c.batch.setProjectionMatrix(c.camera.combined);
        c.batch.begin();
        c.batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        c.font.draw(c.batch, textAbout, 100, 700);
        btnBack.font.draw(c.batch, btnBack.text, btnBack.x, btnBack.y);
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
