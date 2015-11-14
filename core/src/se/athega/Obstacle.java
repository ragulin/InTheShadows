package se.athega;

import com.badlogic.gdx.physics.box2d.*;

public class Obstacle extends Entity {

    private static final float WIDTH = 40f;
    private static final float HEIGHT = 20f;

    public Obstacle(final World world) {
        body = createStaticBody(world, "boulder.png", WIDTH, HEIGHT);
    }

    public int getX() {
        return (int) (getBody().getPosition().x - WIDTH / 2 );
    }

    public int getY() {
        return (int) (getBody().getPosition().y - HEIGHT / 2);
    }

    public boolean overlaps(final Obstacle other) {
        return rangeIntersect(getX(), getX() + (int)WIDTH, other.getX(), other.getX() + (int)WIDTH) &&
                rangeIntersect(getY(), getY() - (int)HEIGHT, other.getY(), other.getY() - (int)HEIGHT);
    }


    private boolean rangeIntersect(int min0, int max0, int min1, int max1)  {
        return Math.max(min0, max0) >= Math.min(min1, max1) && Math.min(min0, max0) <= Math.max(min1, max1);

    }

}
