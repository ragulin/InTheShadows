package se.athega;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InTheShadows extends com.badlogic.gdx.Game {

    public SpriteBatch batch;
    public BitmapFont font;

    private Music music;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        music = Gdx.audio.newMusic(Gdx.files.internal("scary.mp3"));
        music.setLooping(true);
        music.play();
        this.setScreen(new MainMenu(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        music.dispose();
    }
}
