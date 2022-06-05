package com.sentinels.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Clase principal Juego
public class Sentinels extends Game{
	
	@Override
	public void create() {

		setScreen(new MainMenuScreen(this));	
		
	}

}
