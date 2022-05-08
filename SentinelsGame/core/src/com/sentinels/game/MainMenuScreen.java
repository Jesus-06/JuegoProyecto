package com.sentinels.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class MainMenuScreen implements Screen {

	final Sentinels sent;
	Texture texture;
	Stage stage;
	TextButton b1,b2,b3;
	Skin skin;
	Table table;
	
	public MainMenuScreen(Sentinels sentinels) {
		
		this.sent = sentinels;
		
	}

	@Override
	public void show() {
		sent.batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("fondo.jpg"));
		
		stage = new Stage();
		table = new Table();
		table.setFillParent(true);
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		b1 = new TextButton("Nuevo Juego", skin);
		b2 = new TextButton("Cargar Juego", skin);
		b3 = new TextButton("Salir", skin);
		
		table.add(b1).padBottom(50).height(40).width(130);
		table.row();
		table.add(b2).height(40).width(130);
		table.row();
		table.add(b3).padTop(50).width(130).height(40);
		
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}

	// Renderizar el fondo
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		sent.batch.begin();
		sent.batch.draw(texture, 0, 0);
		sent.batch.end();
		
		stage.draw();
		stage.act(delta);
		
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
