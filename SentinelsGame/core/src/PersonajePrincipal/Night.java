package PersonajePrincipal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import game.PantallaJuego;

public class Night {
	
	public static final float ANCHO = 45;
	public static final float ALTURA = 95;

	public static final float WALK_FRAME_DURATION = 0.045f;
	public static final float JUMP_FRAME_DURATION = .55f;
	public static final float IDLE_FRAME_DURATION = .35f;
	public static final float ATTACK_FRAME_DURATION = .085f;
	public static final float DIE_FRAM_DURATION = .085f;

	public static final float WALK_SPEED = 9000;
	public static final float JUMP_SPEED = 3000;

    public static int corazones =3;

	public static int vida = 100;
	public static int puntuacion;

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
	public boolean isDead;
	public boolean isDying;

	public Night(float ancho, float alto){

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

		if(didDefense){
			isDefending = true;
			didDefense = false;
			stateTime = 0;
		}else {
			isDefending = false;
		}
		if(accelX == 2){
			isAttacking = true;
			isWalking =true;

		}else {
			isAttacking = false;
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
				isWalking = false;
			}
		}else if(isFalling){
			if(velocity.y >= 0){
				isFalling = false;
				stateTime = 0;
				PantallaJuego.gravity.set(0,-90);
				PantallaJuego.world.setGravity(PantallaJuego.gravity);
				isWalking = false;
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
