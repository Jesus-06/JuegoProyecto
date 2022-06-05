package Enemigos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Enemigo {
    public static final float ANCHO = 30;
    public static final float ALTURA = 70;

    public static final float WALK_FRAME_DURATION = 0.045f;
    public static final float JUMP_FRAME_DURATION = .55f;
    public static final float IDLE_FRAME_DURATION = .085f;
    public static final float ATTACK_FRAME_DURATION = .085f;

    public static final float WALK_SPEED = 9000;
    public static final float JUMP_SPEED = 3000;
    public static int vida = 100;
    public boolean isJumping;
    public boolean isFalling;
    public boolean isWalking;
    public boolean isDucking;
    public boolean isAttacking;
    public boolean isDefending;

    public float stateTime = 0;

    public Vector2 position;
    public Vector2 velocity;

    public boolean didDuck;
    public boolean didJump;
    public boolean didDefense;

    public Enemigo(float ancho, float alto){

        position = new Vector2(ancho, alto);

    }

    public void update(Body body, float delta, float accelX){
        position.x = body.getPosition().x;
        position.y = body.getPosition().y;

        velocity = body.getLinearVelocity();

        if(didDuck){
            isDucking = true;
            didDuck = false;
            stateTime = 0;
        }else {
            isDucking = false;
        }

        if(didJump){
            didJump = false;
            isJumping = true;
            stateTime = 0;
            velocity.y = JUMP_SPEED;
        }

        if(accelX == -1){
            velocity.x += -WALK_SPEED;
            isWalking = !isJumping && !isFalling;
        } else if(accelX == 1){
            velocity.x += WALK_SPEED;
            isWalking = !isJumping && !isFalling;
        } else {
            velocity.x = 0;
            isWalking = false;
        }

        if(isJumping){
            if(velocity.y <= 0){
                isJumping = false;
                isFalling = true;
                stateTime = 0;
            }
        }else if(isFalling){
            if(velocity.y >= 0){
                isFalling = false;
                stateTime = 0;
            }
        }

        body.setLinearVelocity(velocity);
        stateTime += delta;

    }

    public void jump(){
        if(!isJumping && !isFalling){
            didJump = true;
        }
    }

    public void duck(){
        if(!isJumping && !isFalling){
            didDuck = true;
        }

    }

    public void defense(){
        if(!isJumping && !isFalling){
            didDefense = true;
        }

    }
}
