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
        camera.setToOrtho(false, 640, 480);
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
        game.font.draw(game.batch, "In the shadows!", 100, 150);
        game.font.draw(game.batch, "Find the exit, before your flashlight runs out of batteries ", 100, 100);
        game.font.draw(game.batch, "Control the player with the arrow keys, toggle the flashlight with space", 100, 50);
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
