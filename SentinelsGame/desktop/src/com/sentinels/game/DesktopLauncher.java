package com.sentinels.game;

import game.Reloj;
import game.Sentinels;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


public class DesktopLauncher {

	public static void main (String[] arg) {

		Reloj r1 = new Reloj("#1");
		Thread hiloR1 = new Thread(r1);
		hiloR1.start();

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		//config.setWindowedMode(1920, 1080);
		config.setForegroundFPS(400);
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		
		new Lwjgl3Application(new Sentinels(), config);
	}
}
