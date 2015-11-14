package se.athega;

import com.badlogic.gdx.physics.box2d.*;

public class Exit extends Entity {

    private static final float WIDTH = 16f;
    private static final float HEIGHT = 32f;

    public Exit(final World world) {
        body = createStaticBody(world, "door.png", WIDTH, HEIGHT);
    }

}
