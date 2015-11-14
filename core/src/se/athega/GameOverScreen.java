package se.athega;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameOverScreen implements Screen {

    private final InTheShadows game;
    private final OrthographicCamera camera;
    private final Sound batteriesOutSound;
    private final long start;

    public GameOverScreen(final InTheShadows game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        batteriesOutSound = Gdx.audio.newSound(Gdx.files.internal("batteries_out.wav"));
        start = System.currentTimeMillis();
    }

    @Override
    public void show() {
        batteriesOutSound.play();
    }

    @Override
    public void render(float delta) {
        if (System.currentTimeMillis() % 2 == 0 && System.currentTimeMillis() - start <= 1000) {
            Gdx.gl.glClearColor(255f, 0, 0, 1);
        } else {
            Gdx.gl.glClearColor(0, 0, 0, 1);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Oh noes! :( You ran out of battery.",
                Gdx.graphics.getWidth() / 2 - 120f,
                Gdx.graphics.getHeight() / 2 + 30f);
        game.font.draw(game.batch, "Press 'Space' to give it another try.",
                Gdx.graphics.getWidth() / 2 - 120f,
                Gdx.graphics.getHeight() / 2 + 10f);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            dispose();
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
        batteriesOutSound.dispose();
    }
}
