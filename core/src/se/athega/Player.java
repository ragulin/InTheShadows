package se.athega;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity {

    private static final float WIDTH = 20f;
    private static final float HEIGHT = 20f;
    private static final int NUMBER_OF_RAYS = 128;
    private static final int POINT_LIGHT_COLOR = 100;
    private static final int FLASH_LIGHT_COLOR = 230;
    private static final float FLASH_LIGHT_DISTANCE = 4f * GameScreen.PIXELS_TO_METERS;
    private static final float FLASH_LIGHT_CONE_DEGREE = 25f;
    private static final long BATTERY_LEVEL = 10 * 100;

    private Vector2 velocity = new Vector2(0, 0);

    private boolean forward;
    private boolean backward;
    private boolean left;
    private boolean right;
    private boolean flashLightOn;
    private long currentBatteryLevel;
    private float speed = 10f;
    private ConeLight flashLight;
    private RayHandler rayHandler;


    public Player(final World world, final RayHandler rayHandler) {
        this.rayHandler = rayHandler;
        this.currentBatteryLevel = BATTERY_LEVEL;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Utils.randomPosition());

        Texture texture = new Texture("player.png");
        sprite = new Sprite(texture);
        sprite.setPosition((bodyDef.position.x) - sprite.getWidth() / 2,
                (bodyDef.position.y) - sprite.getHeight() / 2);

        body = world.createBody(bodyDef);
        body.setTransform(body.getPosition(), MathUtils.random(0, MathUtils.PI2));

        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);

        PolygonShape box = new PolygonShape();
        box.setAsBox(WIDTH, HEIGHT);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        body.createFixture(fixtureDef);

        initLight();
    }

    private void initLight() {
        PointLight pointLight = new PointLight(rayHandler,
                NUMBER_OF_RAYS, null,
                0.5f * GameScreen.PIXELS_TO_METERS, 10f, 10f);
        pointLight.attachToBody(body);
        pointLight.setColor( POINT_LIGHT_COLOR, POINT_LIGHT_COLOR, POINT_LIGHT_COLOR, 1f);

        flashLight = new ConeLight(rayHandler,
                NUMBER_OF_RAYS, null,
                FLASH_LIGHT_DISTANCE, 10f, 10f, FLASH_LIGHT_CONE_DEGREE, 30f);
        flashLight.setColor(Color.BLACK);
        flashLight.attachToBody(body, 10f, 0f);
    }


    public void update() {
        if (left || right) {
            Vector2 position = body.getPosition();
            float angle = body.getAngle();

            if (left) {
                angle += 0.05f;
            }
            if (right) {
                angle -= 0.05f;
            }
            body.setTransform(position, angle );
            sprite.setRotation(MathUtils.radiansToDegrees * angle);
        }

        if (forward || backward) {
            float angle = body.getAngle();
            float x = (float) Math.cos(angle);
            float y = (float) Math.sin(angle);
            speed += 1;
            if (forward) {
                velocity.set(x, y);
            }
            if (backward) {
                velocity.set(-x, -y);
            }
            body.setLinearVelocity(velocity.scl(speed));
            sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                    body.getPosition().y - sprite.getHeight() / 2);
        }

        if (flashLightOn) {
            currentBatteryLevel -= 1;
        }
    }

    public void forward() {
        forward = true;
        backward = false;
    }

    public void backward() {
        backward = true;
        forward = false;
    }

    public void left() {
        left = true;
        right = false;
    }

    public void right() {
        right = true;
        left = false;
    }

    public void stop() {
        body.setLinearVelocity(0, 0);
        left = false;
        right = false;
        forward = false;
        backward = false;
    }

    public void toggleFlashLight() {
        if (flashLightOn) {
            flashLight.setColor(Color.BLACK);
        } else {
            flashLight.setColor(FLASH_LIGHT_COLOR, FLASH_LIGHT_COLOR, FLASH_LIGHT_COLOR, 1f);
        }
        flashLightOn = !flashLightOn;
    }

    public boolean isOutOfBattery() {
        return currentBatteryLevel <= 0;
    }

    public int batteryLeft() {
        return Math.round((float) (currentBatteryLevel * 100) / BATTERY_LEVEL);
    }

}
