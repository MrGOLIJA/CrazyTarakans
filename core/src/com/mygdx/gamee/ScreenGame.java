package com.mygdx.gamee;

import static com.mygdx.gamee.Game.SCR_HEIGHT;
import static com.mygdx.gamee.Game.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class ScreenGame implements Screen {
    Game c;
    InputKeyboard inputKeyboard;
    Texture imgT, imgMr, imgZh;
    Texture imgB;
    Texture imgBG;
    Texture imgM;
    Sound[] sndWin = new Sound[1];
    Sound[] sndLose = new Sound[1];
    Sound[] sndHit = new Sound[3];
    Sound[] music = new Sound[5];

    ArrayList<Tarakan> tarakans = new ArrayList<>();
    ArrayList<Muha> muhas = new ArrayList<>();
    Player[] players = new Player[6];
    Player player;
    int frags;

    int snd = MathUtils.random(0, 4);
    int sizeOfTarakan;
    int delaySpawnMuha, timeRemoveT = 1000;
    long timeStart, timeCurrent, time, timeSpawn = TimeUtils.millis();
    public static final int PLAY_GAME = 0, ENTER_NAME = 1, SHOW_TABLE = 2;
    public static final int HARD = 0, MEDIUM = 1, LOW = 2;
    int condition = PLAY_GAME;
    TextButton btnExit;
    boolean soundOn = true;
    boolean musicOn = true;
    boolean isLose, isWin;
    public static final int TARAKAN = 0, MURAVEI = 1, ZHUK = 2;
    float live;

    public ScreenGame(Game context) {
        c = context;
        inputKeyboard = new InputKeyboard(SCR_WIDTH, SCR_HEIGHT, 10);
        // создание изображений
        imgT = new Texture("tarakan.png");
        imgMr = new Texture("muravei.png");
        imgZh = new Texture("zhuk.png");
        imgB = new Texture("blood.png");
        imgM = new Texture("muha.png");
        imgBG = new Texture("floor.png");
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player("Никто", 0);
        }
        for (int i = 0; i < sndHit.length; i++) {
            sndHit[i] = Gdx.audio.newSound(Gdx.files.internal("hit" + i + ".mp3"));
        }
        player = new Player("Gamer", 0);
        btnExit = new TextButton(c.font, "Exit", 200);
        sndWin[0] = Gdx.audio.newSound(Gdx.files.internal("win.mp3"));
        sndLose[0] = Gdx.audio.newSound(Gdx.files.internal("lose.mp3"));
    }

    @Override
    public void show() {
        gameStart();
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            c.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            c.camera.unproject(c.touch);
            if (condition == SHOW_TABLE) {
                if (btnExit.hit(c.touch.x, c.touch.y)){
                    c.setScreen(c.screenIntro);
                }
                else gameStart();
            }
            if (condition == PLAY_GAME) {
                for (int i = 0; i < tarakans.size(); i++) {
                    if (tarakans.get(i).isAlive && tarakans.get(i).hit(c.touch.x, c.touch.y)) {
                        frags++;
                        if (soundOn) sndHit[MathUtils.random(0, 2)].play();
                        tarakans.remove(i);
                    }
                }
                for (int i = 0; i < muhas.size(); i++) {
                    if (muhas.get(i).hit(c.touch.x, c.touch.y)) {
                        live++;
                        muhas.remove(i);
                    }
                }
            }
            if (condition == ENTER_NAME) {
                if (inputKeyboard.endOfEdit(c.touch.x, c.touch.y)) {
                    player.name = inputKeyboard.getText();
                    players[players.length - 1].frags = player.frags;
                    players[players.length - 1].name = player.name;
                    sortPlayers();
                    saveTableOfRecords();
                    condition = SHOW_TABLE;
                }
            }
        }
        if(condition == PLAY_GAME){
            if(c.screenSettings.whatLevel == LOW || c.screenSettings.whatLevel == MEDIUM) {
                if (frags > 10){
                    gameOver();
                }
            }
            if (live < 0) {
                gameOver();
            }
        }
        for (int i = 0; i < tarakans.size(); i++) {
            if (tarakans.get(i).outOfScreen()) {
                live--;
                tarakans.remove(i);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            c.setScreen(c.screenIntro);
        }
        // события игры
        for (Tarakan t : tarakans) t.move();
        for (Muha m : muhas) m.move();
        if (condition == PLAY_GAME) {
            timeCurrent = TimeUtils.millis() - timeStart;
        }
        if(c.screenSettings.whatLevel == LOW || c.screenSettings.whatLevel == MEDIUM){
            if (frags == 10 && soundOn){
                isWin = true;
            }
        }
        if (live < 0 && soundOn) {
            isLose = true;
        }
        // отрисовка всей графики
        c.camera.update();
        c.batch.setProjectionMatrix(c.camera.combined);
        c.batch.begin();
        c.batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        if (condition != ENTER_NAME && condition != SHOW_TABLE) {
            SpawnTarakan();
            SpawnMuha();
            for (int i = 1; i <= live; i++) {
                if(c.screenSettings.whatSkin == TARAKAN){
                    c.batch.draw(imgT, 60 * i, 20, 70, 70);
                }
                if(c.screenSettings.whatSkin == MURAVEI){
                    c.batch.draw(imgMr, 60 * i, 20, 70, 70);
                }
                if(c.screenSettings.whatSkin == ZHUK){
                    c.batch.draw(imgZh, 60 * i, 20, 70, 70);
                }
            }
            for (Tarakan t : tarakans) {
                if(c.screenSettings.whatSkin == TARAKAN) {
                    c.batch.draw(imgT, t.getX(), t.getY(), t.width, t.height);
                }
                if(c.screenSettings.whatSkin == MURAVEI) {
                    c.batch.draw(imgMr, t.getX(), t.getY(), t.width, t.height);
                }
                if(c.screenSettings.whatSkin == ZHUK){
                    c.batch.draw(imgZh, t.getX(), t.getY(), t.width, t.height);
                }
            }
            for (Muha m : muhas) {
                c.batch.draw(imgM, m.x, m.y, m.width, m.height);
            }
        }
        if (live < 0) {
            c.font.draw(c.batch, "YOU LOSE!", 0, SCR_HEIGHT / 4f * 3 + 100, SCR_WIDTH, Align.center, true);
        }
        if(c.screenSettings.whatLevel == LOW || c.screenSettings.whatLevel == MEDIUM){
            if (frags > 10 && live > 0) {
                c.font.draw(c.batch, "YOU WIN!", 0, SCR_HEIGHT / 4f * 3 + 100, SCR_WIDTH, Align.center, true);
            }
        }

        c.font.draw(c.batch, "KILLS: " + frags, 10, SCR_HEIGHT - 10);
        c.font.draw(c.batch, timeToString(timeCurrent), SCR_WIDTH - 250, SCR_HEIGHT - 10);
        if (condition == ENTER_NAME) {
            inputKeyboard.draw(c.batch);
        }
        if (condition == SHOW_TABLE) {
            c.font.draw(c.batch, tableOfRecordsToString(), 0, SCR_HEIGHT / 4f * 3, SCR_WIDTH, Align.center, true);
            btnExit.font.draw(c.batch, btnExit.text, btnExit.x, btnExit.y);
        }
        c.batch.end();
    }

    String tableOfRecordsToString() {
        String s = "";
        for (int i = 0; i < players.length - 1; i++) {
            s += players[i].name + points(players[i].name, 13) + fragsToString(players[i].frags) + " FRAGS" + "\n";
        }
        return s;
    }

    void gameOver() {
        condition = ENTER_NAME;
        player.frags = frags;
        if (isLose) {
            sndLose[0].play();
        }
        if (isWin) {
            sndWin[0].play();
        }
        music[snd].dispose();
    }

    void loadTableOfRecords() {
        try {
            Preferences pref = Gdx.app.getPreferences("12");
            for (int i = 0; i < players.length; i++) {
                if (pref.contains("name" + i)) {
                    players[i].name = pref.getString("name" + i, "null");
                }
                if (pref.contains("frags" + i)){
                    players[i].frags = pref.getInteger("frags" + i, 0);
                }
            }
        } catch (Exception e) {
        }
    }

    String fragsToString(int frags) {
        String frag = "";
        frag += frags;
        return frag;
    }

    String timeToString(long time) {
        return time / 1000 / 60 / 60 + ":" + time / 1000 / 60 % 60 / 10 + time / 1000 / 60 % 60 % 10 + ":" + time / 1000 % 60 / 10 + time / 1000 % 60 % 10;
    }

    private void gameStart() {
        for (int i = 0; i < music.length; i++) {
            music[i] = Gdx.audio.newSound(Gdx.files.internal("main" + i + ".mp3"));
        }
        if (c.screenGame.musicOn) {
            music[snd].play();
        }
        condition = PLAY_GAME;
        frags = 0;
        timeStart = TimeUtils.millis();
        loadTableOfRecords();
        if(c.screenSettings.whatLevel == HARD) {
            delaySpawnMuha = 5000;
            sizeOfTarakan = 10;
        }
        if(c.screenSettings.whatLevel == MEDIUM) {
            delaySpawnMuha = 2500;
            sizeOfTarakan = 7;
        }
        if(c.screenSettings.whatLevel == LOW){
            delaySpawnMuha = 1000;
            sizeOfTarakan = 5;
        }
        live = 5;
    }

    String points(String name, int length) {
        int n = length - name.length();
        String s = "";
        for (int i = 0; i < n; i++) s += ".";
        return s;
    }

    void saveTableOfRecords() {
        try {
            Preferences pref = Gdx.app.getPreferences("12");
            for (int i = 0; i < players.length; i++) {
                pref.putString("name" + i, players[i].name);
                pref.putInteger("frags" + i, players[i].frags);
            }
            pref.flush();
        } catch (Exception e) {
        }
    }

    void sortPlayers() {
        for (int j = 0; j < players.length; j++) {
            for (int i = 0; i < players.length - 1; i++) {
                if (players[i].frags < players[i + 1].frags) {
                    Player c = players[i];
                    players[i] = players[i + 1];
                    players[i + 1] = c;
                }
            }
        }
    }

    void SpawnTarakan() {
        if (tarakans.size() < sizeOfTarakan && TimeUtils.millis() > time + 500) {
            tarakans.add(new Tarakan());
        }
    }

    void SpawnMuha() {
        if (TimeUtils.millis() > timeSpawn + delaySpawnMuha) {
            muhas.add(new Muha());
            timeSpawn = TimeUtils.millis();
        }
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
