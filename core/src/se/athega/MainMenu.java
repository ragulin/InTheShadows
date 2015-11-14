package se.athega;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenu implements Screen {

    private final InTheShadows game;
    private final OrthographicCamera camera;

    public MainMenu(final InTheShadows game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "In the shadows!",
                Gdx.graphics.getWidth() / 2 - 50f,
                Gdx.graphics.getHeight() - 100f);
        game.font.draw(game.batch, "Find the exit, before your flashlight runs out of batteries.",
                Gdx.graphics.getWidth() / 2 - 180f,
                Gdx.graphics.getHeight() - 240f);
        game.font.draw(game.batch, "Control the player with the arrow keys and toggle the flashlight with space",
                Gdx.graphics.getWidth() / 2 - 220,
                Gdx.graphics.getHeight() - 260f);
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

    }
}
