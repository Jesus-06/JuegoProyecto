package com.sentinels.game;

import PersonajePrincipal.AssetsNight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import PersonajePrincipal.Night;

public class PantallaJuego implements Screen {

	Music reproductor;
	//Tamano del mundo
	public static final float ANCHO = 1200;
	public static final float ALTO = 800;

	private Texture HUD;
	private Pixmap p1,p2;
	Sentinels sent;

	//Administra los trazos sobre la pantalla
	Texture textura_fondo;

	//Objetos en el juego
	Night night;

	World world;
	Box2DDebugRenderer renderer;
	Array<Body> arrBoddies;

	public PantallaJuego(Sentinels sent) {

		this.sent = sent;

		AssetsNight.load();

		Vector2 gravity = new Vector2(0, -80);

		world = new World(gravity, true);
		renderer = new Box2DDebugRenderer();

		arrBoddies = new Array<>();

		piso();
		createNight();

	}

	private void piso() {
		BodyDef bd = new BodyDef();
		bd.position.set(0,0.6f);
		bd.type = BodyType.StaticBody;

		EdgeShape shape = new EdgeShape();
		shape.set(0,268, 2000, 268);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.friction = .7f;

		Body body = world.createBody(bd);
		body.createFixture(fixDef);
		shape.dispose();

	}

	private void createNight(){
		night = new Night(100, 300);

		BodyDef bd = new BodyDef();
		bd.position.x = night.position.x;
		bd.position.y = night.position.y;
		bd.type = BodyType.DynamicBody;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Night.ANCHO, Night.ALTURA);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 15;
		fixDef.friction = 0;

		Body body = world.createBody(bd);
		body.createFixture(fixDef);
		body.setUserData(night);
		shape.dispose();
	}
	@Override
	public void show() {

		textura_fondo = new Texture(Gdx.files.internal("fondo_juego.jpg"));

		reproductor = Gdx.audio.newMusic(Gdx.files.internal("Musica/Musica_de_fondo.mp3"));
		reproductor.setLooping(true);
		reproductor.play();

		this.sent.camara = new OrthographicCamera();
		this.sent.camara.setToOrtho(false, 1920, 1080);


	}

	@Override
	public void render(float delta) {

		String vidas="X "+Night.Vidas;
		String puntuacion="X "+Night.puntuacion;

		p1 = new Pixmap(Gdx.files.internal("Night/Barra_de_vida/1.png"));
		p2 = new Pixmap(510, 350, p1.getFormat());
		p2.drawPixmap(p1,
				0, 0, p1.getWidth(), p1.getHeight(),
				0, 0, p2.getWidth(), p2.getHeight()
		);
		HUD = new Texture(p2);
		p1.dispose();
		p2.dispose();

		p1 = new Pixmap(Gdx.files.internal("fondo_juego.jpg"));
		p2 = new Pixmap(1920, 1080, p1.getFormat());
		p2.drawPixmap(p1,
				0, 0, p1.getWidth(), p1.getHeight(),
				0, 0, p2.getWidth(), p2.getHeight()
		);
		textura_fondo = new Texture(p2);

		p1.dispose();
		p2.dispose();

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float accelX = 0;

		if(Gdx.input.isKeyPressed((Input.Keys.A)))
			accelX = -1;


		else if(Gdx.input.isKeyPressed((Input.Keys.D)))
			accelX = 1;


		if(Gdx.input.isKeyPressed((Input.Keys.S)))
			night.duck();


		if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Keys.SPACE))
			night.jump();


		world.step(delta, 8, 6);
		world.getBodies(arrBoddies);
		for(Body body1 : arrBoddies){
			if(body1.getUserData() instanceof  Night){
				Night night1 = (Night) body1.getUserData();
				night1.update(body1, delta, accelX);
			}
		}

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		this.sent.camara.update();

		sent.batch.setProjectionMatrix(this.sent.camara.combined);
		sent.batch.begin();
		sent.batch.draw(textura_fondo,0,0);
		sent.batch.draw(HUD,0,750);
		sent.fuente.draw(sent.batch, "NIGHT ", 100, 1000);
		sent.fuente.draw(sent.batch,vidas , 140, 900);
		sent.fuente.draw(sent.batch,puntuacion, 250, 900);
		drawNight();
		sent.batch.end();
		//renderer.render(world, sent.camara.combined);
	}

	private void drawNight() {

		Sprite keyFrame = AssetsNight.idle;

		if(night.isJumping){
			keyFrame = AssetsNight.jump;
		} else if(night.isFalling){
			keyFrame = AssetsNight.fall;
		} else if(night.isWalking){
			keyFrame = AssetsNight.walk.getKeyFrame(night.stateTime, true);
		} else if (night.isDucking) {
			keyFrame = AssetsNight.duck;
		}

		keyFrame.setPosition(night.position.x - 100/2, night.position.y - 200/2);
		keyFrame.setSize(100,200);
		keyFrame.draw(sent.batch);

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		AssetsNight.dispose();
		world.dispose();
	}
}