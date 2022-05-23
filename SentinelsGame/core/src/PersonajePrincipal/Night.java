package PersonajePrincipal;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sentinels.game.Animation;
import com.sentinels.game.PantallaJuego;

import java.util.Vector;

public class Night {

	public static final float ANCHO = 50;
	public static final float ALTURA = 80;

	public static final float WALK_FRAME_DURATION = 0.05f;

	public static final float WALK_SPEED = 800;
	public static final float JUMP_SPEED = 3000;
    public static int Vidas=3;
	public static int puntuacion;

	public boolean isJumping;
	public boolean isFalling;
	public boolean isWalking;
	public boolean isDucking;

	public float stateTime = 0;

	public Vector2 position;
	public Vector2 velocity;

	public boolean didDuck;
	public boolean didJump;

	public Night(float ancho, float alto){
		position = new Vector2(ancho, alto);

	}

	public void update(Body body, float delta, float accelX){
		position.x = body.getPosition().x+10;
		position.y = body.getPosition().y+10;

		velocity = body.getLinearVelocity();

		if(didDuck){
			isDucking = true;
			didDuck = false;
			stateTime = 0;
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
				stateTime = 10;
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

	}
}
