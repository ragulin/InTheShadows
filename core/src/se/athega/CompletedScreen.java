package se.athega;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CompletedScreen implements Screen {

    private final InTheShadows game;
    private final OrthographicCamera camera;
    private final Sound foundExitSound;
    private long completedTimeInsSeconds;
    private long start;

    public CompletedScreen(InTheShadows game, final long startTime) {
        this.game = game;
        this.completedTimeInsSeconds = getCompletedTimeInSeconds(startTime);
        foundExitSound = Gdx.audio.newSound(Gdx.files.internal("success.wav"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        start = System.currentTimeMillis();
    }

    @Override
    public void show() {
        foundExitSound.play();
    }

    @Override
    public void render(float delta) {
        if (System.currentTimeMillis() - start <= 1000) {
            Gdx.gl.glClearColor((float)Math.random(), (float)Math.random(), (float)Math.random(), 1);
        } else {
            Gdx.gl.glClearColor(0, 0, 0, 1);
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Hurray!" , 100, 200);
        game.font.draw(game.batch, "You found the exit and completed the level in a smashing " +
                completedTimeInsSeconds + " seconds.", 100, 150);
        game.font.draw(game.batch, "Press 'Space' to play again", 100, 100);
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
        foundExitSound.dispose();
    }

    public long getCompletedTimeInSeconds(final long startTime) {
        return (System.currentTimeMillis() - startTime) / 1000;
    }
}
