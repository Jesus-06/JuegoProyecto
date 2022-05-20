package com.sentinels.game;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class MainMenuScreen implements Screen {

	final Sentinels sent;
	
	Texture texture, texLogo;
	TextureRegion trLogo;
	Stage stage;
	TextButton b1,b2,b3;
	Skin skin;
	Table table;
	Music reproductor, rep2;
	Timer t1;
	Image imgLogo;
	
	public MainMenuScreen(Sentinels sentinels) {
		
		this.sent = sentinels;
		
	}

	// Creamos y cargamos los objeto del menu principal
	@Override
	public void show() {
		t1 = new Timer();
		
		texLogo = new Texture(Gdx.files.internal("logo_transparente.png"));
		trLogo = new TextureRegion(texLogo, 600, 600);
		imgLogo = new Image(trLogo);
		
		sent.batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("fondo.jpg"));
		
		reproductor = Gdx.audio.newMusic(Gdx.files.internal("Musica/Musica_de_fondo.mp3"));
		rep2 = Gdx.audio.newMusic(Gdx.files.internal("Musica/Musica_de_exit.mp3"));
		reproductor.setLooping(true);
		reproductor.play();
		
		stage = new Stage();
		table = new Table();
		table.setFillParent(true);
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		b1 = new TextButton("Nuevo Juego", skin);
		b2 = new TextButton("Cargar Juego", skin);
		b3 = new TextButton("Salir", skin);
		
		b1.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				reproductor.stop();
				sent.setScreen(new PantallaJuego(sent));
				dispose();
				
			}
			
		});
		
		b3.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				reproductor.stop();
				b1.addAction(Actions.parallel(Actions.moveBy(0, 700, 0.5f)));
				b2.addAction(Actions.parallel(Actions.moveBy(0, 700, 0.5f)));
				b3.addAction(Actions.parallel(Actions.moveBy(0, 700, 0.5f)));
				rep2.play();
				
				t1.schedule(new StopTask(), 2*1000);
				
				
			}
			
		});
		
		table.add(b1).padBottom(50).height(40).width(130);
		table.row();
		table.add(b2).height(40).width(130);
		table.row();
		table.add(b3).padTop(50).width(130).height(40);
		
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
		
	}
	
	private void poner(Actor a) {
		
		a.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		stage.addActor(a);
		
	}
	
	// Temporiazador
	class StopTask extends TimerTask {
    	
        public void run() {
            t1.cancel();
            Gdx.app.exit();
        }
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
		
		sent.batch.begin();
		sent.batch.draw(texLogo, 0, 0);
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
		
		texture.dispose();
		texLogo.dispose();
		
	}

	
}
