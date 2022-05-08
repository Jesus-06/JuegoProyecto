package com.sentinels.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import PersonajePrincipal.Night;

public class PantallaJuego {
	
	Music reproductor;
	//Tamaño del mundo
	public static final float ANCHO = 1200;
	public static final float ALTO = 800;
	//Camara
	private OrthographicCamera camara;
	private Viewport vista;
	//Administra los trazos sobre la pantalla
	private SpriteBatch batch;
	//Texturas
	private Texture Night_standBy;

	//Objetos en el juego
	private Night Night;
	
	private PantallaJuego() {
		
		reproductor =Gdx.audio.newMusic(Gdx.files.internal("Musica_de_fondo"));
		reproductor.setLooping(true);
		reproductor.play();
		
		camara = new OrthographicCamera();
		camara.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();

	}
	
	private void crearObjetos() {
		Night_standBy = new Texture(Gdx.files.internal("Night/standBy"));
		Night = new Night(Night_standBy,ANCHO/2,ALTO/2);
		
	}
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camara.combined);
		batch.begin();
		batch.draw(Night_standBy,0,0);
		
		
	}
}
