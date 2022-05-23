package com.sentinels.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;

import PersonajePrincipal.Night;

public class PantallaJuego implements Screen {

	Music reproductor;
	//Tama�o del mundo
	public static final float ANCHO = 1920;
	public static final float ALTO = 1080;

	Sentinels sent;
	private Viewport vista;
	//Administra los trazos sobre la pantalla
	private SpriteBatch batch;
	//Texturas
	private Texture Night_standBy;
	private Texture textura_fondo;
	private Texture HUD;
	private Pixmap p1,p2;

	private Rectangle Night_hitbox;
	
	//Objetos en el juego
	private Night Night;
	
	PantallaJuego(Sentinels sent) {

		this.sent = sent;
		
		reproductor = Gdx.audio.newMusic(Gdx.files.internal("Musica/Musica_de_fondo.mp3"));
		reproductor.setLooping(true);
		reproductor.play();

		this.sent.camara = new OrthographicCamera();
		this.sent.camara.setToOrtho(false, ANCHO, ALTO);
		
		batch = new SpriteBatch();
		
		Night_hitbox= new Rectangle();
		
		Night_hitbox.width = 64;
		Night_hitbox.x = 800 / 2 - Night_hitbox.width / 2;
		Night_hitbox.y = 20;
		Night_hitbox.height = 64;
		

	}
	
	public void render(float delta) {

		p1 = new Pixmap(Gdx.files.internal("Night/Barra_de_vida/1.png"));
		p2 = new Pixmap(510, 350, p1.getFormat());
		p2.drawPixmap(p1,
				0, 0, p1.getWidth(), p1.getHeight(),
				0, 0, p2.getWidth(), p2.getHeight()
		);
		HUD = new Texture(p2);
		p1.dispose();
		p2.dispose();

		p1 = new Pixmap(Gdx.files.internal("fondo_juego.jpg"));
		p2 = new Pixmap(1920, 1080, p1.getFormat());
		p2.drawPixmap(p1,
				0, 0, p1.getWidth(), p1.getHeight(),
				0, 0, p2.getWidth(), p2.getHeight()
		);
		textura_fondo = new Texture(p2);

		p1.dispose();
		p2.dispose();

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		String vidas="X "+Night.Vidas;
		String puntuacion="X "+Night.puntuacion;

		sent.camara.update();
		sent.batch.setProjectionMatrix(sent.camara.combined);

		sent.batch.begin();
		sent.batch.draw(textura_fondo,0,0);
		sent.batch.draw(HUD,0,750);

		sent.fuente.draw(sent.batch, "NIGHT ", 100, 1000);
		sent.fuente.draw(sent.batch,vidas , 140, 900);
		sent.fuente.draw(sent.batch,puntuacion, 250, 900);
		sent.batch.end();


		if (Gdx.input.isKeyPressed(Keys.D)) {
			batch.begin();
			batch.draw(Night_standBy,Night_hitbox.x,Night_hitbox.y);
			batch.end();
			Night_hitbox.x += 400 * Gdx.graphics.getDeltaTime();

		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			Night_hitbox.x -= 400 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			batch.begin();
			batch.draw(Night_standBy,Night_hitbox.x,Night_hitbox.y);
			batch.end();
			Night_hitbox.y += 400 * Gdx.graphics.getDeltaTime();

		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			Night_hitbox.y -= 400 * Gdx.graphics.getDeltaTime();
		}
	
		
	}

	@Override
	public void show() {
		//Night_standBy = new Texture(Gdx.files.internal("Night/standBy.png"));
		//Night = new Night(Night_standBy,ANCHO/2,ALTO/2);


		
	}

	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
