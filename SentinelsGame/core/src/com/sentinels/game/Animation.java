package com.sentinels.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Animation {
	protected Sprite sprite;
	
	//Crea el objeto con cierta textura en la posicion x, y
	public Animation(Texture texture,float x, float y) {
		sprite= new Sprite(texture);
		sprite.setPosition(x, y);
	}
}
