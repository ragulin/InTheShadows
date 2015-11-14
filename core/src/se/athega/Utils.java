package se.athega;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class Utils {

    public static Vector2 randomPosition() {
        float x = MathUtils.random(0, Gdx.graphics.getWidth());
        float y = MathUtils.random(0, Gdx.graphics.getHeight());
//        System.out.println("x" + x);
//        System.out.println("y " + y);

        return new Vector2(x, y);
    }

}
