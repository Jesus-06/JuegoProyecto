package com.sentinels.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MainMenuScreen implements Screen {

	final Sentinels sent;
	Music reproductor;
	Texture texture;
	
	public MainMenuScreen(Sentinels sentinels) {
		
		this.sent = sentinels;
		reproductor = Gdx.audio.newMusic(Gdx.files.internal("Musica/Musica_de_fondo.mp3"));
		reproductor.setLooping(true);
		reproductor.play();
		
	}

	@Override
	public void show() {
		sent.batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("fondo.jpg"));
		
	}

	// Renderizar el fondo
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		sent.batch.begin();
		sent.batch.draw(texture, 0, 0);
		sent.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}

	
}
