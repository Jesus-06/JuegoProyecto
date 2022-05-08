package com.sentinels.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

	//Objetos en el juego
	private Night Night;
	
	private PantallaJuego() {
		
		reproductor = Gdx.audio.newMusic(Gdx.files.internal("Musica_de_fondo"));
		reproductor.setLooping(true);
		reproductor.play();
		
		sent.camara = new OrthographicCamera();
		sent.camara.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();

	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(sent.camara.combined);
		batch.begin();
		batch.draw(Night_standBy,0,0);
		
		
	}

	@Override
	public void show() {
		Night_standBy = new Texture(Gdx.files.internal("Night/standBy"));
		Night = new Night(Night_standBy,ANCHO/2,ALTO/2);
		
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
