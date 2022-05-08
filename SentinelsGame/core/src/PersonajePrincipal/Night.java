package PersonajePrincipal;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sentinels.game.Animation;
import com.sentinels.game.PantallaJuego;

public class Night extends Animation{

	private float DX = 4; //Incremento en x,define desplazamiento en cada frame
	private float DY=4; //Incremento en y
	
	private final float ALTO_MARGEN=20; //Grosor de la paredes arriba-abajo
	private final float ANCHO_MARGEN =20; //Grosor de la raqueta
	
	public Night(Texture texture, float x, float y) {
		super(texture, x, y);
	}
	
	//Se llama desde el render del juego
	public void dibujar (SpriteBatch batch) {
		sprite.draw(batch);
	}
	//Movimiento del peesonaje
	public void mover() {
		float xp = sprite.getX();
		float yp = sprite.getY();
		
		//Prueba límites DERECHA-IZQUIERDA
		if(xp>=PantallaJuego.ANCHO||xp<=0) {
			DX=-DX; //Invierte el sentido
		}
		//Prueba lmites ARRIBA-ABAJO
		if(yp>=PantallaJuego.ALTO||yp<=0) {
			DY=-DY; 
		}
		
		//Poner la pelota en la nueva posición
		sprite.setX(xp+DX);
		sprite.setY(yp+DY);
	}
}
