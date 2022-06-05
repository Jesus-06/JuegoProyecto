package game;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class MainMenuScreen extends SettingsScreen {
	
	Texture texture, texLogo;
	TextureRegion trLogo;
	TextButton b1,b2,b3;
	Skin skin;
	Table table;
	Music reproductor, rep2;
	Timer t1;
	Image imgLogo;
	Pixmap p1, p2;
	TextureRegionDrawable trdLogo;
	ImageButton btLogo;
	
	public MainMenuScreen(Sentinels sentinels) {
		super(sentinels);

		t1 = new Timer();

		p1 = new Pixmap(Gdx.files.internal("logo_transparente.png"));
		p2 = new Pixmap(550, 500, p1.getFormat());
		p2.drawPixmap(p1,
				0, 0, p1.getWidth(), p1.getHeight(),
				0, 0, p2.getWidth(), p2.getHeight()
		);
		texLogo = new Texture(p2);
		p1.dispose();
		p2.dispose();
		trLogo = new TextureRegion(texLogo, 500, 400);
		imgLogo = new Image(trLogo);
		trdLogo = new TextureRegionDrawable(trLogo);
		btLogo = new ImageButton(trdLogo);


		texture = new Texture(Gdx.files.internal("fondo.jpg"));

		reproductor = Gdx.audio.newMusic(Gdx.files.internal("Musica/Musica_de_fondo.mp3"));
		rep2 = Gdx.audio.newMusic(Gdx.files.internal("Musica/Musica_de_exit.mp3"));
		reproductor.setLooping(true);
		reproductor.play();

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

		b2.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {

				reproductor.stop();
				sent.setScreen(new PantallaCarga(sent));
				dispose();

			}

		});

		b3.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {

				reproductor.stop();
				b1.addAction(Actions.parallel(Actions.moveBy(0, 700, 1.5f)));
				b2.addAction(Actions.parallel(Actions.moveBy(0, 700, 1.5f)));
				b3.addAction(Actions.parallel(Actions.moveBy(0, 700, 1.5f)));
				btLogo.addAction(Actions.parallel(Actions.moveBy(0, 700, 1.5f)));
				rep2.play();

				t1.schedule(new StopTask(), (long) (1.5*1000));


			}

		});

		table.add(b1).padBottom(50).height(40).width(130);
		table.row();
		table.add(b2).height(40).width(130);
		table.row();
		table.add(b3).padTop(50).width(130).height(40);

		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);

		poner(btLogo);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){

			reproductor.stop();
			b1.addAction(Actions.parallel(Actions.moveBy(0, 700, 7f)));
			b2.addAction(Actions.parallel(Actions.moveBy(0, 700, 7f)));
			b3.addAction(Actions.parallel(Actions.moveBy(0, 700, 7f)));
			btLogo.addAction(Actions.parallel(Actions.moveBy(0, 700, 7f)));
			rep2.play();

			t1.schedule(new StopTask(), (long) (1.5*1000));
		}

		spBatch.begin();
		spBatch.draw(texture, 0, 0 );
		spBatch.end();

		stage.draw();
		stage.act(delta);
	}

	private void poner(Actor a) {
		
		a.setPosition(690, 700);
		
		stage.addActor(a);
		
	}
	
	// Temporiazador
	 class StopTask extends TimerTask {
        public void run() {
            t1.cancel();
            Gdx.app.exit();
        }
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
		table.clear();
	}

	
}
