package com.sentinels.game;

import Game.Corazon;
import Game.Sentinels;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


public class DesktopLauncher {

	public static void main (String[] arg) {

		Corazon r1 = new Corazon("#1");
		Thread hiloR1 = new Thread(r1);
		hiloR1.start();

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(400);
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		
		new Lwjgl3Application(new Sentinels(), config);

		Gdx.app.exit();
		System.exit(0);
	}
}
