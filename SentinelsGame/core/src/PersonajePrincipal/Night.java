package PersonajePrincipal;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sentinels.game.Animation;
import com.sentinels.game.PantallaJuego;

import java.util.Vector;

public class Night {

	static final float ANCHO = .45f;
	static final float ALTURA = .6f;

	static final float ANCHO_DIBUJO = 1.3f;
	static final float ALTURA_DIBUJO = 1.7f;

	static final float WALK_FRAM_DURATION = 0.05f;

	static final float WALK_SPEED = 3;
	static final float JUMP_SPEED = 8;

	static final public int puntuacion=0;
	static final public int Vidas=3;

	boolean isJumping;
	boolean isFalling;
	boolean isWalking;
	boolean isDucking;

	float stateTime = 0;

	Vector2 position;
	Vector2 velocity;

	public Night(float ancho, float alto){
		position = new Vector2(ancho, alto);

	};

	public void update(Body body, float delta, float accelX){

	}

	public void jump(){

	}

	public void duck(){

	}
}
