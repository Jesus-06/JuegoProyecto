package com.sentinels.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Clase principal Juego
public class Sentinels extends Game{

	OrthographicCamera camara;
	SpriteBatch batch;
	BitmapFont fuente;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		fuente = new BitmapFont();
		setScreen(new MainMenuScreen(this));

	}

	// Renderizamos la aplicación
	@Override
	public void render() {
		super.render();
	}

	// Este método destruimos la interfaz cuando nos salimos (TOTAL) del juego 
	@Override
	public void dispose() {
		batch.dispose();
		fuente.dispose();
	}


}
