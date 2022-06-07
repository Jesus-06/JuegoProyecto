package Objetos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Bloque {

    public static final float ANCHO = 200;
    public static final float ALTURA = 50;

    public Vector2 position;
    float random;
    public float stateTime = 0;



    public int state;

    public Bloque(float x, float y){
        position = new Vector2(x,y);
    }

    public void update(Body body,float delta){
        position.x=body.getPosition().x;
        position.y=body.getPosition().y;

        random= (float) Math.toDegrees(body.getAngle());

        stateTime += delta;
    }

}
