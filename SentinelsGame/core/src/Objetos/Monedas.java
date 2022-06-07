package Objetos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Monedas {

    public static final float ANCHO = 20;
    public static final float ALTURA = 20;
    public static final float TIME_TO_SPAWN_MONEDA = 10;
    public static final float MONEY_FRAME_DURATION = 0.1f;
    public static final int STATE_RECOGIDO = 1;
    public static final int STATE_NORMAL=0;
    public static float timeToSpawnMoneda;

    public Vector2 position;
    float random;
    public float stateTime = 0;



    public int state;

    public Monedas(float x, float y){
        position = new Vector2(x,y);
        state=STATE_NORMAL;
    }

    public void update(Body body,float delta){
        position.x=body.getPosition().x;
        position.y=body.getPosition().y;

        random= (float) Math.toDegrees(body.getAngle());


        stateTime += delta;
    }

    public void takeMoney(){
        state= STATE_RECOGIDO;
    }
}
