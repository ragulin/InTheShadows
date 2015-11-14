package se.athega;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Entity {

    protected Sprite sprite;
    protected Body body;

    public void render(final SpriteBatch spriteBatch) {
        spriteBatch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(),
                sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
    }

    public Body getBody() {
        return body;
    }

    public Body createStaticBody(final World world, final String image, final float width, final float height) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(Utils.randomPosition());


        body = world.createBody(bodyDef);

        Texture texture = new Texture(image);
        sprite = new Sprite(texture);
        sprite.setPosition((bodyDef.position.x) - sprite.getWidth() / 2,
                (bodyDef.position.y) - sprite.getHeight() / 2);

        PolygonShape box = new PolygonShape();
        box.setAsBox(width, height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;

        body.createFixture(fixtureDef);
        return body;
    }
}
