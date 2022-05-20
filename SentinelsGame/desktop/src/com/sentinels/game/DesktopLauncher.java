package com.sentinels.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


public class DesktopLauncher {
	
	public static void main (String[] arg) {
		
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		//config.setWindowedMode(1024, 960);
		config.setForegroundFPS(60);
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		
		new Lwjgl3Application(new Sentinels(), config);
	}
}
