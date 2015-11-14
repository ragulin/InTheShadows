package se.athega;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen, InputProcessor {

    private final InTheShadows game;

    private static final int NUMBER_OF_OBSTACLES = 10;
    public static final float PIXELS_TO_METERS = 100f;

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private Player player;
    private Array<Obstacle> obstacles = new Array<Obstacle>(NUMBER_OF_OBSTACLES);
    private Exit exit;
    private OrthographicCamera camera;

    private RayHandler rayHandler;

    private boolean debug;
    private boolean isLightOn;
    private Sprite background;
    private long startTime;

    public GameScreen(final InTheShadows game) {
        this.game = game;
        startTime = System.currentTimeMillis();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        world = new World(new Vector2(0f, 0f), true);

        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0f, 0f, 0f, 0.1f);
        rayHandler.setBlurNum(3);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (isPlayerExitContact(contact)) {
                    game.setScreen(new CompletedScreen(game, startTime));
                    dispose();
                }
            }

            private boolean isPlayerExitContact(final Contact contact) {
                return isPlayer(contact) && isExit(contact);
            }

            private boolean isPlayer(final Contact contact) {
                return contact.getFixtureA().getBody() == player.getBody() || contact.getFixtureB().getBody() == player.getBody();
            }

            private boolean isExit(final Contact contact) {
                return contact.getFixtureA().getBody() == exit.getBody() || contact.getFixtureB().getBody() == exit.getBody();
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        createObstacles();
        player = new Player(world, rayHandler);
        exit = new Exit(world);
        background = new Sprite(new Texture("bg.png"));

        box2DDebugRenderer = new Box2DDebugRenderer();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.batch.draw(background, 0 - Gdx.graphics.getWidth()/2, 0 - Gdx.graphics.getHeight() / 2);

        for(int i = 0; i <= NUMBER_OF_OBSTACLES; i++) {
            obstacles.get(i).render(game.batch);
        }
        exit.render(game.batch);
        player.render(game.batch);
        game.font.draw(game.batch, "Battery left: " + player.batteryLeft() + "%",
                -Gdx.graphics.getWidth() / 2 + 10f,
                Gdx.graphics.getHeight() / 2 - 10f);
        game.batch.end();

        player.update();
        if (player.isOutOfBattery()) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
        world.step(1f/60f, 6, 2);

        rayHandler.setCombinedMatrix(camera);
        rayHandler.update();
        rayHandler.render();

        game.batch.begin();
        game.font.draw(game.batch, "Battery left: " + player.batteryLeft() + "%", 10f, Gdx.graphics.getHeight() - 10f);
        game.batch.end();

        if (debug) {
            box2DDebugRenderer.render(world, camera.combined);
        }
    }

    private void createObstacles() {
        for (int i = 0; i <= NUMBER_OF_OBSTACLES; i++) {
            Obstacle currentObstacle = new Obstacle(world);
            obstacles.add(currentObstacle);
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

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                player.forward();
                break;
            case Input.Keys.DOWN:
                player.backward();
                break;
            case Input.Keys.LEFT:
                player.left();
                break;
            case Input.Keys.RIGHT:
                player.right();
                break;
            case Input.Keys.SPACE:
                player.toggleFlashLight();
                break;
            case Input.Keys.F1:
                toggleDebug();
                break;
            case Input.Keys.F2:
                toggleLights();
                break;
        }
        return true;
    }

    private void toggleDebug() {
        this.debug = !this.debug;
    }

    private void toggleLights() {
        if (isLightOn) {
            rayHandler.setAmbientLight(Color.BLACK);
        } else {
            rayHandler.setAmbientLight(Color.LIGHT_GRAY);
        }
        isLightOn = !isLightOn;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
            case Input.Keys.DOWN:
            case Input.Keys.LEFT:
            case Input.Keys.RIGHT:
                player.stop();
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
