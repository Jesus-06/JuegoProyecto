package game;

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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

import PersonajePrincipal.Night;

public class PantallaJuego extends SettingsScreen {


	Sprite keyFrame_enemigo, keyFrame_night;
	Music reproductor;
	private Texture HUD;
	private Pixmap p1,p2;
	Sentinels sent;

	//Cuerpos de los objetos
	BodyDef bd_night,bd_enemigo;

	//Objetos en el juego
	Night night;
	Enemigo enemigo;
	public static World world;
	public static BodyDef bdPiso,bdPared1,bdPared2;

	public static int avancePiso = 0;
	Box2DDebugRenderer renderer;
	Array<Body> arrBoddies;
	//Contenedor de enmigos
	Array<Enemigo> arrenemigo;
	Texture textura_fondo;

	FixtureDef fixDef_night,fixDef_enemigo ;


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
		Enemigo.timeToSpawnEnemy=1.5f;
		world = new World(gravity, true);
		renderer = new Box2DDebugRenderer();

		arrBoddies = new Array<>();
		arrenemigo = new Array<>();

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
		pared1();
		pared2();
		createNight();
		world.setContactListener(new Collision());
	}
	private void addenemy(){

		float x= SettingsScreen.PANTALLA_ANCHO;
		float y= MathUtils.random() *0 + 270;
		addenemy(x,y);

	}
 	private void addenemy(float x, float y){
		enemigo =new Enemigo(x,y);

		BodyDef bd_enemigo = new BodyDef();
		bd_enemigo.position.x = x;
		bd_enemigo.position.y = y;
		bd_enemigo.type = BodyType.DynamicBody;
		Body obody = world.createBody(bd_enemigo);
		obody.setLinearVelocity(Enemigo.WALK_SPEED,0);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Enemigo.ANCHO, Enemigo.ALTURA);

		fixDef_enemigo = new FixtureDef();
		fixDef_enemigo.shape = shape;
		fixDef_enemigo.density = 0;

		Body body = world.createBody(bd_enemigo);
		body.createFixture(fixDef_enemigo);
		body.setUserData(enemigo);
		arrenemigo.add(enemigo);
		shape.dispose();


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
	private void pared1() {
		bdPared1 = new BodyDef();
		bdPared1.position.set(0,0.6f);
		bdPared1.type = BodyType.StaticBody;

		EdgeShape shape = new EdgeShape();
		shape.set(0,180, 0, MUNDO_ALTO);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.friction = .7f;

		Body body = world.createBody(bdPared1);
		body.createFixture(fixDef);
		shape.dispose();

	}
	private void pared2() {
		bdPared2 = new BodyDef();
		bdPared2.position.set(0,0.6f);
		bdPared2.type = BodyType.StaticBody;

		EdgeShape shape = new EdgeShape();
		shape.set(MUNDO_ANCHO,MUNDO_ALTO, MUNDO_ANCHO, 180);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.friction = .7f;

		Body body = world.createBody(bdPared2);
		body.createFixture(fixDef);
		shape.dispose();
	}

	private void createNight(){
		night = new Night(200, 270);

		bd_night = new BodyDef();
		bd_night.position.x = night.position.x;
		bd_night.position.y = night.position.y;
		bd_night.type = BodyType.DynamicBody;
		bd_night.gravityScale=5;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Night.ANCHO, Night.ALTURA);

		fixDef_night = new FixtureDef();
		fixDef_night.shape = shape;
		fixDef_night.density = 0;
		fixDef_night.friction = 0;

		Body body = world.createBody(bd_night);
		body.createFixture(fixDef_night);
		body.setUserData(night);
		shape.dispose();
	}

	@Override
	public void show() {

		reproductor.setLooping(true);
		//reproductor.play();


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
		vidas ="X "+Night.corazones;
		puntuacion ="X "+Night.puntuacion;
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

		for(Enemigo obj : arrenemigo) {
			drawEnemigo(obj);
		}
		spBatch.end();

		renderer.render(world, camUI.combined);
	}

	private void updateCharacters(float delta){

		for(int i = 0; i < arrenemigo.size;i++){
			Enemigo e= arrenemigo.get(i);
			if(e.isRemove){
				arrenemigo.removeIndex(i);
			}
		}
		enemigo.timeToSpawnEnemy +=delta;
		if(enemigo.timeToSpawnEnemy >= enemigo.TIME_TO_SPAWN_ENEMY){
			enemigo.timeToSpawnEnemy -= enemigo.TIME_TO_SPAWN_ENEMY;
			addenemy();
		}
		float accelX = 0;
		
		if(Night.vida <=0&& Night.corazones <=0){


		}else if(Night.vida <=0){
			Night.corazones--;
			Night.vida =100;

		}
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
			if(body1.getUserData() instanceof  Enemigo){
				enemigo = (Enemigo) body1.getUserData();
				if (enemigo.isRemove) {
					arrenemigo.removeValue(enemigo, true);
					world.destroyBody(body1);
				}
			}
		}

		world.getBodies(arrBoddies);
		for(Body body1 : arrBoddies){
			if(body1.getUserData() instanceof  Enemigo){
				enemigo = (Enemigo) body1.getUserData();
				enemigo.update(body1, delta, enemigo.acelx2);

			}
			if(body1.getUserData() instanceof  Night){
				night = (Night) body1.getUserData();
				night.update(body1, delta, accelX);
			}
		}

	}

	private void drawNight() {

		keyFrame_night = AssetsNight.idle.getKeyFrame(night.stateTime, true);


		if(night.isJumping){
			keyFrame_night = AssetsNight.jump.getKeyFrame(night.stateTime, false);
		} else if(night.isFalling){
			keyFrame_night = AssetsNight.fall;
		} else if(night.isWalking){
			keyFrame_night = AssetsNight.walk.getKeyFrame(night.stateTime, true);
		} else if (night.isDucking) {
			keyFrame_night = AssetsNight.duck;
		} else if (night.isAttacking) {
			keyFrame_night = AssetsNight.attack.getKeyFrame(night.stateTime, true);
		} else if (night.isDefending) {
			keyFrame_night = AssetsNight.defense;
		}


		keyFrame_night.setPosition(night.position.x - 100/2, night.position.y - 190/2);
		night.position.x += 10;
		keyFrame_night.setSize(90,190);
		keyFrame_night.draw(spBatch);

	}

	private void drawEnemigo(Enemigo obj){

			if(!enemigo.agresivo) {
				keyFrame_enemigo = AssetsEnemigo.idle.getKeyFrame(obj.stateTime, true);
			}else if(enemigo.agresivo){
				keyFrame_enemigo = AssetsEnemigo.attack.getKeyFrame(obj.stateTime, true);
			}
			keyFrame_enemigo.setPosition(obj.position.x - 100/2, obj.position.y - 170/2);
			keyFrame_enemigo.setSize(90,190);
			keyFrame_enemigo.draw(spBatch);


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

	//COLOSIONES
	class Collision implements ContactListener{

		@Override
		public void beginContact(Contact contact) {
			Body bodyA= contact.getFixtureA().getBody();
			Body bodyB= contact.getFixtureB().getBody();

			if(bodyA.getUserData() instanceof Night && bodyB.getUserData() instanceof Enemigo){
				Night night = (Night) bodyA.getUserData();
				Enemigo enemigo = (Enemigo) bodyB.getUserData();

				enemigo.acelx2=-1;

				if(night.isAttacking){
					System.out.println("Vida de Enemigo: "+ enemigo.vida);
					enemigo.isAttacking=true;
					enemigo.vida-=30;

				} else if(night.isDefending){
					enemigo.isAttacking=true;
					System.out.println("DEFENDIDO");
				} else{
					enemigo.isAttacking=true;
					System.out.println("ME ESTA TOCANDOOOO");
					night.vida-=10;
					System.out.println("Vida de night: "+night.vida);
					enemigo.acelx2=-1;
					enemigo.agresivo=true;
				}

				if (enemigo.vida <= 0){
					enemigo.isRemove = true;
					enemigo.vida = 100;
					System.out.println(enemigo.isRemove);
				}
			}

		}
		private void beginContactNight(Fixture night_2,Fixture fixElse){
		Object somethingElse = fixElse.getBody().getUserData();

			if(somethingElse instanceof Enemigo && night.isDefending){
				enemigo.isAttacking=true;
				System.out.println("DEFENDIDO");
			}
			else if(somethingElse instanceof Enemigo && night.isAttacking){
				((Enemigo) somethingElse).isAttacking=true;
				((Enemigo) somethingElse).vida-=30;
				System.out.println("Vida de Enemigo: "+ ((Enemigo) somethingElse).vida);
			}
			else if(somethingElse instanceof Enemigo){

				enemigo.isAttacking=true;
				System.out.println("ME ESTA TOCANDOOOO");
				night.vida-=10;
				System.out.println("Vida de night: "+night.vida);
				enemigo.acelx2=-1;
				enemigo.agresivo=true;

			}
		}

		@Override
		public void endContact(Contact contact) {

		}

		@Override
		public void preSolve(Contact contact, Manifold oldManifold) {

		}

		@Override
		public void postSolve(Contact contact, ContactImpulse impulse) {

		}
	}
}