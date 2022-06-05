package com.sentinels.game;

import Enemigos.AssetsEnemigo;
import Enemigos.Enemigo;
import PersonajePrincipal.AssetsNight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

import PersonajePrincipal.Night;

public class PantallaJuego extends SettingsScreen {

	Music reproductor;
	private Texture HUD;
	private Pixmap p1,p2;
	Sentinels sent;

	//Objetos en el juego
	Night night;
	Enemigo enemigo;
	public static World world;
	public static BodyDef bdPiso;

	public static int avancePiso = 0;
	Box2DDebugRenderer renderer;
	Array<Body> arrBoddies;

	Texture textura_fondo;

	static float stateTime=0;
	public static Animation<Sprite> fondoAnimado;
	static TextureAtlas atlas;

	public static Vector2 gravity;
	String vidas, puntuacion;

	public PantallaJuego(Sentinels sent) {
		super(sent);

		AssetsNight.load();
		AssetsEnemigo.load();

		gravity = new Vector2(0, -90);

		world = new World(gravity, true);
		renderer = new Box2DDebugRenderer();

		arrBoddies = new Array<>();

		reproductor = Gdx.audio.newMusic(Gdx.files.internal("Musica/Musica_de_fondo.mp3"));

		atlas = new TextureAtlas(Gdx.files.internal("FondosNiveles/fondoNormal.txt"));
		fondoAnimado = new Animation<Sprite>(10,
				atlas.createSprite("Layer_0000"),
				atlas.createSprite("Layer_0001"),
				atlas.createSprite("Layer_0002"),
				atlas.createSprite("Layer_0003"),
				atlas.createSprite("Layer_0004"),
				atlas.createSprite("Layer_0005"),
				atlas.createSprite("Layer_0006"),
				atlas.createSprite("Layer_0007"),
				atlas.createSprite("Layer_0008"),
				atlas.createSprite("Layer_0009"),
				atlas.createSprite("Layer_00010"),
				atlas.createSprite("Layer_00011")
		);

		piso();
		createNight();
		createEnemigo();
	}

	public void loadFondo1(){

		Sprite keyFrame;

		keyFrame = fondoAnimado.getKeyFrame(stateTime,true);

		keyFrame.setPosition(0, 0);
		keyFrame.setSize(2820,2300);
		keyFrame.draw(spBatch);

	}

	private void piso() {
		bdPiso = new BodyDef();
		bdPiso.position.set(0,0.6f);
		bdPiso.type = BodyType.StaticBody;

		EdgeShape shape = new EdgeShape();
		shape.set(0,180, MUNDO_ANCHO, 180);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.friction = .7f;

		Body body = world.createBody(bdPiso);
		body.createFixture(fixDef);
		shape.dispose();

	}

	private void createNight(){
		night = new Night(200, 270);

		BodyDef bd = new BodyDef();
		bd.position.x = night.position.x;
		bd.position.y = night.position.y;
		bd.type = BodyType.DynamicBody;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Night.ANCHO, Night.ALTURA);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 0;
		fixDef.friction = 0;

		Body body = world.createBody(bd);
		body.createFixture(fixDef);
		body.setUserData(night);
		shape.dispose();
	}

	private void createEnemigo(){
		enemigo = new Enemigo(900, 250);

		BodyDef bd = new BodyDef();
		bd.position.x = enemigo.position.x;
		bd.position.y = enemigo.position.y;
		bd.type = BodyType.StaticBody;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Enemigo.ANCHO, Enemigo.ALTURA);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 0;

		Body body = world.createBody(bd);
		body.createFixture(fixDef);
		body.setUserData(enemigo);
		shape.dispose();
	}

	@Override
	public void show() {

		reproductor.setLooping(true);
		//reproductor.play();

		vidas ="X "+Night.corazones;
		puntuacion ="X "+Night.puntuacion;

		p1 = new Pixmap(Gdx.files.internal("Night/Barra_de_vida/1.png"));
		p2 = new Pixmap(510, 350, p1.getFormat());
		p2.drawPixmap(p1,
				0, 0, p1.getWidth(), p1.getHeight(),
				0, 0, p2.getWidth(), p2.getHeight()
		);
		HUD = new Texture(p2);
		p1.dispose();
		p2.dispose();

		p1 = new Pixmap(Gdx.files.internal("FondosNiveles/Background.png"));
		p2 = new Pixmap(2800, 2300, p1.getFormat());
		p2.drawPixmap(p1,
				0, 0, p1.getWidth(), p1.getHeight(),
				0, 0, p2.getWidth(), p2.getHeight()
		);
		textura_fondo = new Texture(p2);

	}

	@Override
	public void render(float delta) {
		camUI.update();
		spBatch.setProjectionMatrix(camUI.combined);

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		updateCharacters(delta);

		spBatch.begin();
		spBatch.draw(textura_fondo,0,0);
		spBatch.draw(HUD,0,650);
		fuente.draw(spBatch, "NIGHT ", 100, 900);
		fuente.draw(spBatch,vidas , 140, 800);
		fuente.draw(spBatch,puntuacion, 250, 800);
		loadFondo1();
		drawNight();
		drawEnemigo();
		spBatch.end();

		renderer.render(world, camUI.combined);
	}

	private void updateCharacters(float delta){

		float accelX = 0;

		// Creación de los movimientos y cuerpos de los personajes en el mundo

		if(Gdx.input.isKeyPressed((Keys.LEFT)))
			accelX = -1;


		else if(Gdx.input.isKeyPressed((Keys.RIGHT)))
			accelX = 1;

		if(Gdx.input.isKeyPressed((Keys.DOWN)))
			night.duck();

		if (Gdx.input.isKeyPressed(Keys.Z))
			accelX = 2;

		if (Gdx.input.isKeyPressed(Keys.X))
			night.defense();

		if(Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.SPACE))
			night.jump();

		if(Gdx.input.isKeyPressed(Keys.C)) {
			gravity = new Vector2(0, -40);
			world.setGravity(gravity);
			night.jump();
		}

		world.step(delta, 8, 6);
		world.getBodies(arrBoddies);

		for(Body body1 : arrBoddies){
			if(body1.getUserData() instanceof  Night){
				Night night1 = (Night) body1.getUserData();
				night1.update(body1, delta, accelX);
			}
		}

		for(Body body1 : arrBoddies){
			if(body1.getUserData() instanceof  Enemigo){
				Enemigo enemigo1 = (Enemigo) body1.getUserData();
				enemigo1.update(body1, delta);
			}
		}

	}

	private void drawNight() {

		Sprite keyFrame = AssetsNight.idle.getKeyFrame(night.stateTime, true);

		if(night.isJumping){
			keyFrame = AssetsNight.jump.getKeyFrame(night.stateTime, false);
		} else if(night.isFalling){
			keyFrame = AssetsNight.fall;
		} else if(night.isWalking){
			keyFrame = AssetsNight.walk.getKeyFrame(night.stateTime, true);
		} else if (night.isDucking) {
			keyFrame = AssetsNight.duck;
		} else if (night.isAttacking) {
			keyFrame = AssetsNight.attack.getKeyFrame(night.stateTime, true);
		} else if (night.isDefending) {
			keyFrame = AssetsNight.defense;
		}

		keyFrame.setPosition(night.position.x - 100/2, night.position.y - 190/2);
		keyFrame.setSize(90,190);
		keyFrame.draw(spBatch);

	}

	private void drawEnemigo(){

		Sprite keyFrame = AssetsEnemigo.idle.getKeyFrame(enemigo.stateTime, true);

		keyFrame.setPosition(enemigo.position.x - 100/2, enemigo.position.y - 170/2);
		keyFrame.setSize(90,190);
		keyFrame.draw(spBatch);
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