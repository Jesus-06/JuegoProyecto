package com.sentinels.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;

import PersonajePrincipal.Night;

public class PantallaJuego implements Screen {

	Music reproductor;
	//Tamaño del mundo
	public static final float ANCHO = 1200;
	public static final float ALTO = 800;
	
	Sentinels sent;
	private Viewport vista;
	//Administra los trazos sobre la pantalla
	private SpriteBatch batch;
	//Texturas
	private Texture Night_standBy;
	private Texture textura_fondo;

	private Rectangle Night_hitbox;
	
	//Objetos en el juego
	private Night Night;
	
	PantallaJuego(Sentinels sent) {
		
		this.sent = sent;
		
		reproductor = Gdx.audio.newMusic(Gdx.files.internal("Musica/Musica_de_fondo.mp3"));
		reproductor.setLooping(true);
		reproductor.play();
		
		this.sent.camara = new OrthographicCamera();
		this.sent.camara.setToOrtho(false, 2000, 2000);
		
		batch = new SpriteBatch();
		
		Night_hitbox= new Rectangle();
		
		Night_hitbox.width = 64;
		Night_hitbox.x = 800 / 2 - Night_hitbox.width / 2;
		Night_hitbox.y = 20;
		Night_hitbox.height = 64;
		

	}
	
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.sent.camara.update();

		batch.setProjectionMatrix(this.sent.camara.combined);
		batch.begin();
		batch.draw(textura_fondo,0,0);
		batch.draw(Night_standBy,Night_hitbox.x,Night_hitbox.y);
		batch.end();
		
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
		Night_standBy = new Texture(Gdx.files.internal("Night/standBy.png"));
		Night = new Night(Night_standBy,ANCHO/2,ALTO/2);
		textura_fondo = new Texture(Gdx.files.internal("fondo_juego.jpg"));
		
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
