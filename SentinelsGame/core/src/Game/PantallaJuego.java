package Game;

import Booses.AssetsAtalanta;
import Booses.Atalanta;
import DB.MySQLCLass;
import Enemigos.AssetsEnemigo;
import Enemigos.Enemigo;
import Objetos.AssetsBloque;
import Objetos.AssetsMonedas;
import Objetos.Bloque;
import Objetos.Monedas;
import PersonajePrincipal.AssetsNight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import PersonajePrincipal.Night;

import java.sql.Connection;
import java.sql.ResultSet;

public class PantallaJuego extends SettingsScreen {
	int barradevida = 0;
	private static int NIVEL_ACTUAL = 0;
	int enemigos_generados=0;
	int enemigos_maximo_por_nivel=5;

	Skin skin;
	ImageButtonStyle imgBtn;
	ImageButton imgSave;
	Texture save;
	TextureRegion saveRegion;
	TextureRegionDrawable drawSave;

	Sprite keyFrame_enemigo, keyFrame_night, keyFrame_atalanta, keyFrame_moneda;

	//Plataforma
	Bloque platform;
	Music reproductor;
	public static boolean status = false;

	//Textura
	private Texture keyFrame_block;
	private Texture HUD;
	private Pixmap p1, p2;
	Sentinels sent;

	//Cuerpos de los objetos
	BodyDef bd_night, bd_atalanta;

	//Objetos en el juego
	Night night;
	Enemigo enemigo;
	Atalanta atalanta;
	Monedas moneda;
	public static World world;
	public static BodyDef bdPiso, bdPared1, bdPared2;

	//Piso de avance
	public static int avancePiso = 0;
	Box2DDebugRenderer renderer;
	Array<Body> arrBoddies;

	//Contenedor de enemigos
	Array<Enemigo> arrenemigo;
	//Contenedor de monedas
	Array<Monedas> arrmonedas;
	//Contenedor de bloques
	Array<Bloque> arrbloques;

	//Verificacion de objetos creados
	boolean platformcreated=false;

	//Textura fondo de pantalla
	Texture textura_fondo;

	FixtureDef fixDef_night, fixDef_enemigo, fixDef_atalanta,fixDef_moneda, fixDef_platform;

	static float stateTime = 0;

	//Sprite del fondo animado
	public static Animation<Sprite> fondoAnimado;
	static TextureAtlas atlas;

	public static Vector2 gravity;
	String vidas, puntuacion;

	MySQLCLass con;

	//Constructor
	public PantallaJuego(Sentinels sent) {
		super(sent);
		this.sent=sent;
		atalanta=new Atalanta(-100,0);
		status = true;
		//Conexión base de datos
		con= new MySQLCLass();
		final Connection reg = con.getCOnnection();

		//Cargar assets de los objetos
		AssetsBloque.load();
		AssetsNight.load();
		AssetsEnemigo.load();
		AssetsAtalanta.load();
		AssetsMonedas.load();
		Sonidos.load();

		//Crear mundo y su gravedad
		gravity = new Vector2(0, -90);
		enemigo.timeToSpawnEnemy = 1.5f;
		world = new World(gravity, true);
		renderer = new Box2DDebugRenderer();

		//Array de los enemigos
		arrBoddies = new Array<>();
		arrenemigo = new Array<>();
		arrmonedas = new Array<>();
		arrbloques = new Array<>();

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

		save = new Texture(Gdx.files.internal("save-game (1).png"));
		saveRegion = new TextureRegion(save, 100, 100);
		drawSave = new TextureRegionDrawable(saveRegion);

		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		imgBtn = new ImageButtonStyle(skin.get(Button.ButtonStyle.class));
		imgBtn.imageUp = drawSave;
		imgSave = new ImageButton(imgBtn);
		poner(imgSave);

		imgSave.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				con.insertaDatos(CrearPerfil.PERFIL_USER, night.position.x, night.position.y, barradevida, NIVEL_ACTUAL, night.corazones, night.puntuacion);
			}
		});

		Gdx.input.setInputProcessor(stage);
	}

	private void poner(Actor a) {

		a.setPosition(1700, 940);

		stage.addActor(a);

	}

	private void createNight() {
		night = new Night(Night.POSICION_INICIAL_X, Night.POSICION_INICIAL_Y);

		bd_night = new BodyDef();
		bd_night.position.x = night.position.x;
		bd_night.position.y = night.position.y;
		bd_night.type = BodyType.DynamicBody;
		bd_night.gravityScale = 5;

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

	private void createAtalanta() {
		atalanta = new Atalanta(1200, 420);

		bd_atalanta = new BodyDef();
		bd_atalanta.position.x = atalanta.position.x;
		bd_atalanta.position.y = atalanta.position.y;
		bd_atalanta.type = BodyType.DynamicBody;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Atalanta.ANCHO, Atalanta.ALTURA);

		fixDef_atalanta = new FixtureDef();
		fixDef_atalanta.shape = shape;
		fixDef_atalanta.density = -10;


		Body body = world.createBody(bd_atalanta);
		body.createFixture(fixDef_atalanta);
		body.setUserData(atalanta);
		shape.dispose();
	}
	private void addMoneda(){
		float x = MathUtils.random() * SettingsScreen.PANTALLA_ANCHO;
		float y = SettingsScreen.PANTALLA_ALTO + 270;
		addMoneda(x, y);
	}
	private void addMoneda(float x, float y){
		moneda= new Monedas(x,y);

		BodyDef bd_moneda = new BodyDef();
		bd_moneda.position.x = x;
		bd_moneda.position.y = y;
		bd_moneda.type = BodyType.DynamicBody;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(moneda.ANCHO, moneda.ALTURA);

		fixDef_moneda = new FixtureDef();
		fixDef_moneda.shape = shape;
		fixDef_moneda.density = 0;

		Body body = world.createBody(bd_moneda);
		body.createFixture(fixDef_moneda);
		body.setUserData(moneda);

		arrmonedas.add(moneda);

		shape.dispose();

	}
	private void addPlatform(float x, float y){
		platform = new Bloque(x, y);

		BodyDef bd_block = new BodyDef();
		bd_block.position.x = x;
		bd_block.position.y = y;
		bd_block.type = BodyType.StaticBody;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(platform.ANCHO, platform.ALTURA);

		fixDef_platform = new FixtureDef();
		fixDef_platform.shape = shape;
		fixDef_platform.density = 0;

		Body body = world.createBody(bd_block);
		body.createFixture(fixDef_platform);
		body.setUserData(platform);

		arrbloques.add(platform);

		shape.dispose();

	}
	private void addenemy() {
		float x = 0;
		if(PantallaJuego.stateTime>15){
			 x = 0;
		}else if(PantallaJuego.stateTime<=15){
			 x = SettingsScreen.PANTALLA_ANCHO;
		}
		
		float y = MathUtils.random() * 0 + 270;
		System.out.println(x + PantallaJuego.stateTime);
		addenemy(x, y);

	}
	private void addenemy(float x, float y) {
		enemigo = new Enemigo(x, y);

		BodyDef bd_enemigo = new BodyDef();
		bd_enemigo.position.x = x;
		bd_enemigo.position.y = y;
		bd_enemigo.type = BodyType.DynamicBody;
		Body obody = world.createBody(bd_enemigo);
		obody.setLinearVelocity(Enemigo.WALK_SPEED, 0);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(enemigo.ANCHO, enemigo.ALTURA);

		fixDef_enemigo = new FixtureDef();
		fixDef_enemigo.shape = shape;
		fixDef_enemigo.density = 0;

		Body body = world.createBody(bd_enemigo);
		body.createFixture(fixDef_enemigo);
		body.setUserData(enemigo);

		arrenemigo.add(enemigo);

		shape.dispose();


	}
	public void loadFondo1() {

		Sprite keyFrame;
		keyFrame = fondoAnimado.getKeyFrame(stateTime, true);

		keyFrame.setPosition(0, 0);
		keyFrame.setSize(2820, 2300);
		keyFrame.draw(spBatch);

	}
	private void piso() {
		bdPiso = new BodyDef();
		bdPiso.position.set(0, 0.6f);
		bdPiso.type = BodyType.StaticBody;

		EdgeShape shape = new EdgeShape();
		shape.set(0, 180, MUNDO_ANCHO, 180);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.friction = .7f;

		Body body = world.createBody(bdPiso);
		body.createFixture(fixDef);
		shape.dispose();

	}

	private void pared1() {
		bdPared1 = new BodyDef();
		bdPared1.position.set(0, 0.6f);
		bdPared1.type = BodyType.StaticBody;

		EdgeShape shape = new EdgeShape();
		shape.set(0, 180, 0, MUNDO_ALTO);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.friction = .7f;

		Body body = world.createBody(bdPared1);
		body.createFixture(fixDef);
		shape.dispose();

	}

	private void pared2() {
		bdPared2 = new BodyDef();
		bdPared2.position.set(0, 0.6f);
		bdPared2.type = BodyType.StaticBody;

		EdgeShape shape = new EdgeShape();
		shape.set(MUNDO_ANCHO, MUNDO_ALTO, MUNDO_ANCHO, 180);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.friction = .7f;

		Body body = world.createBody(bdPared2);
		body.createFixture(fixDef);
		shape.dispose();
	}

	@Override
	public void show() {

		reproductor.setLooping(true);
		reproductor.play();

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

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)){
			reproductor.stop();
			sent.setScreen(new MainMenuScreen(sent));
		}

		vidas = "X " + Night.corazones;
		puntuacion = "X " + Night.puntuacion;

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		updateCharacters(delta);

		spBatch.begin();
		spBatch.draw(textura_fondo, 0, 0);

		loadFondo1();
		for(Monedas obj : arrmonedas){
			drawMoneda(obj);
		}
		fuente.draw(spBatch, "NIGHT ", 100, 900);
		fuente.draw(spBatch, vidas, 140, 800);
		fuente.draw(spBatch, puntuacion, 250, 800);
		for (Bloque obj : arrbloques) {
			drawBlock(obj);
		}
		drawNight();
		if(!atalanta.isDead) {
			drawAtalanta();
		}

		for (Enemigo obj : arrenemigo) {
			drawEnemigo(obj);
		}
		spBatch.draw(HUD, 0, 650);
		if(arrenemigo.isEmpty()&&avancePiso==1) {
			NIVEL_ACTUAL++;
			fuente.draw(spBatch, "SIGUIENTE NIVEL", 840, 570);
			fuente.draw(spBatch, "NIVEL: "+NIVEL_ACTUAL, 860, 550);
			Sonidos.sonido_spawn_atalanta.play();
		}
		spBatch.end();

		stage.draw();
		stage.act(delta);

		//renderer.render(world, camUI.combined);
	}

	private void updateCharacters(float delta) {
		if(NIVEL_ACTUAL==10){
			enemigos_maximo_por_nivel=0;
			reproductor.stop();
			reproductor= Gdx.audio.newMusic(Gdx.files.internal("Musica/Blue_Blood.mp3"));
			reproductor.setLooping(true);
			reproductor.play();
			createAtalanta();
			atalanta.isAlive=true;
			NIVEL_ACTUAL=0;
		}
		if(PantallaJuego.stateTime>=Math.floor(Math.random()*(18-0+1)+0)) {
			atalanta.acelx2 = 1;
		}
		if(PantallaJuego.stateTime<Math.floor(Math.random()*(19-0+36)+0)) {
			atalanta.acelx2 = -1;
		}

		for (int i = 0; i < arrenemigo.size; i++) {
			Enemigo e = arrenemigo.get(i);
			if(PantallaJuego.stateTime==Math.floor(Math.random()*(36-0+1)+0)) {
				e.acelx2 = 1;
			}
			if(PantallaJuego.stateTime==Math.floor(Math.random()*(36-0+1)+0)) {
				e.acelx2 = -1;
			}
			if (e.isRemove) {
				arrenemigo.removeIndex(i);
			}
		}
		for (int i = 0; i < arrmonedas.size; i++) {
			Monedas e = arrmonedas.get(i);
			if (e.state==Monedas.STATE_RECOGIDO) {
				arrmonedas.removeIndex(i);
			}
		}
		if(!platformcreated) {
			addPlatform(230, 500);
			addPlatform(900, 700);
			addPlatform(1670, 500);

			platformcreated=true;
		}
		enemigo.timeToSpawnEnemy += delta;
		if (enemigo.timeToSpawnEnemy >= enemigo.TIME_TO_SPAWN_ENEMY) {
			enemigo.timeToSpawnEnemy -= enemigo.TIME_TO_SPAWN_ENEMY;

			if(enemigos_generados<enemigos_maximo_por_nivel){
				if(!atalanta.isAlive) {
					enemigos_generados++;
					addenemy();
				}
				if(enemigos_generados==enemigos_maximo_por_nivel){
					avancePiso=1;
				}
			}
		}
		if(arrenemigo.isEmpty()&&avancePiso==1){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			enemigos_maximo_por_nivel+=2;
			avancePiso=0;
			enemigos_generados=0;
			arrenemigo.clear();
		}
		moneda.timeToSpawnMoneda +=delta;
		if (moneda.timeToSpawnMoneda >= moneda.TIME_TO_SPAWN_MONEDA) {
			moneda.timeToSpawnMoneda -= moneda.TIME_TO_SPAWN_MONEDA;
			addMoneda();
		}

		float accelX = 0;

		if (Night.vida <= 0 && Night.corazones <= 0) {

			night.isDying=true;

		} else if (Night.vida <= 0) {
			Sonidos.sonido_muerte_night.play();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			night.corazones--;
			night.vida = 100;

		}

		// Creacion de los movimientos y cuerpos de los personajes en el mundo

		if (Gdx.input.isKeyPressed((Keys.LEFT))&& !night.isDead) {
			accelX = -1;
		}
		else if (Gdx.input.isKeyPressed((Keys.RIGHT))&& !night.isDead) {
			accelX = 1;
		}
		if (Gdx.input.isKeyPressed((Keys.DOWN))&& !night.isDead)
			night.duck();

		if (Gdx.input.isKeyPressed(Keys.Z)&& !night.isDead) {
			accelX = 2;
			Sonidos.sonido_ataque_night.play();
		}

		if (Gdx.input.isKeyPressed(Keys.X)&& !night.isDead)
			night.defense();

		if (Gdx.input.isKeyPressed(Keys.UP)&& !night.isDead || Gdx.input.isKeyPressed(Keys.SPACE)&& !night.isDead) {
			night.jump();
			Sonidos.sonido_jump_night.play();
		}
		if (Gdx.input.isKeyPressed(Keys.C)&& !night.isDead) {
			gravity = new Vector2(0, -18);
			world.setGravity(gravity);
			night.jump();
			Sonidos.sonido_jump_night.play();
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)&& !night.isDead) {
			gravity = new Vector2(0, -1000);
			world.setGravity(gravity);
		}


		world.step(delta, 8, 6);
		world.getBodies(arrBoddies);

		world.getBodies(arrBoddies);

		for (Body body1 : arrBoddies) {
			if (body1.getUserData() instanceof Enemigo) {
				enemigo = (Enemigo) body1.getUserData();
				if (enemigo.isRemove) {
					Night.puntuacion+=10;
					arrenemigo.removeValue(enemigo, true);
					world.destroyBody(body1);
				}
			}
			if (body1.getUserData() instanceof Atalanta) {
				atalanta = (Atalanta) body1.getUserData();
				if (atalanta.vida<=1) {

					world.destroyBody(body1);
				}
			}
			if (body1.getUserData() instanceof Monedas) {
				moneda= (Monedas) body1.getUserData();
				if (moneda.state==1) {
					world.destroyBody(body1);
				}
			}
		}

		world.getBodies(arrBoddies);
		for (Body body1 : arrBoddies) {
			if (body1.getUserData() instanceof Enemigo) {
				enemigo = (Enemigo) body1.getUserData();
				enemigo.update(body1, delta, enemigo.acelx2);
			}
			if (body1.getUserData() instanceof Night) {
				night = (Night) body1.getUserData();
				night.update(body1, delta, accelX);
			}
			if (body1.getUserData() instanceof Atalanta) {
				atalanta = (Atalanta) body1.getUserData();
				atalanta.update(body1, delta);
			}
			if (body1.getUserData() instanceof Monedas) {
				moneda = (Monedas) body1.getUserData();
				moneda.update(body1,delta);
			}
		}
	}

	private void drawNight() {

		keyFrame_night = AssetsNight.idle.getKeyFrame(night.stateTime, true);


		if (night.isJumping) {
			keyFrame_night = AssetsNight.jump.getKeyFrame(night.stateTime, false);
		} else if (night.isFalling) {
			keyFrame_night = AssetsNight.fall;
		} else if (night.isWalking) {
			keyFrame_night = AssetsNight.walk.getKeyFrame(night.stateTime, true);
		} else if (night.isDucking) {
			keyFrame_night = AssetsNight.duck;
		} else if (night.isAttacking) {
			keyFrame_night = AssetsNight.attack.getKeyFrame(night.stateTime, true);
		} else if (night.isDefending) {
			keyFrame_night = AssetsNight.defense;
		} else if(night.isDying){
			keyFrame_night= AssetsNight.die.getKeyFrame(night.stateTime,false);
			night.isDead=true;
		} else if(night.isDead){
			keyFrame_night= AssetsNight.die_static;
		}


		keyFrame_night.setPosition(night.position.x - 100 / 2, night.position.y - 190 / 2);
		keyFrame_night.setSize(90, 190);
		keyFrame_night.draw(spBatch);

	}
	private void drawMoneda(Monedas obj){

		keyFrame_moneda = AssetsMonedas.idle.getKeyFrame(obj.stateTime, true);

		keyFrame_moneda.setPosition(obj.position.x - 83 / 2, obj.position.y - 90 / 2);
		keyFrame_moneda.setSize(90, 90);
		keyFrame_moneda.draw(spBatch);


	}
	private void drawBlock(Bloque obj){

		keyFrame_block = AssetsBloque.block1;

		spBatch.draw(keyFrame_block, obj.position.x- 410 / 2, obj.position.y + -130 / 2);

	}

	private void drawEnemigo(Enemigo obj) {
		if(PantallaJuego.stateTime==1){
			enemigo.agresivo=false;
			enemigo.ishurt=false;
		}
		if(enemigo.ishurt){
			keyFrame_enemigo = AssetsEnemigo.hurt.getKeyFrame(obj.stateTime, true);
		}
		else if (!enemigo.agresivo) {
			keyFrame_enemigo = AssetsEnemigo.idle.getKeyFrame(obj.stateTime, true);

		} else if (enemigo.agresivo) {
			keyFrame_enemigo = AssetsEnemigo.attack.getKeyFrame(obj.stateTime, true);
		}
		keyFrame_enemigo.setPosition(obj.position.x - 100 / 2, obj.position.y - 170 / 2);
		keyFrame_enemigo.setSize(90, 190);
		keyFrame_enemigo.draw(spBatch);

	}

	private void drawAtalanta() {

		if(atalanta.vida<=0){
			keyFrame_atalanta= AssetsAtalanta.die.getKeyFrame(atalanta.stateTime, true);
			atalanta.isDead=true;
		}
		else if(atalanta.isAgressive){
			keyFrame_atalanta = AssetsAtalanta.attack.getKeyFrame(atalanta.stateTime, true);
			if(PantallaJuego.stateTime==1){
				atalanta.isAgressive=false;
			}
		}else if(!atalanta.isAgressive) {
			keyFrame_atalanta = AssetsAtalanta.idle.getKeyFrame(atalanta.stateTime, true);
		}


		keyFrame_atalanta.setPosition(atalanta.position.x - 700 / 2, atalanta.position.y - 489 / 2);
		keyFrame_atalanta.setSize(700, 750);
		keyFrame_atalanta.draw(spBatch);




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
	class Collision implements ContactListener {

		@Override
		public void beginContact(Contact contact) {
			Body bodyA = contact.getFixtureA().getBody();
			Body bodyB = contact.getFixtureB().getBody();

			if(bodyA.getUserData() instanceof Night && bodyB.getUserData() instanceof Monedas){
				Monedas moneda = (Monedas) bodyB.getUserData();
				Night.puntuacion+=10;
				moneda.state=Monedas.STATE_RECOGIDO;
				Sonidos.sonido_pick_item.play();
			}
			if (bodyA.getUserData() instanceof Night && bodyB.getUserData() instanceof Enemigo) {
				Night night = (Night) bodyA.getUserData();
				Enemigo enemigo = (Enemigo) bodyB.getUserData();

				if (night.isAttacking) {
					enemigo.vida -= 100;
					enemigo.ishurt=true;
				} else if (night.isDefending) {
					enemigo.isAttacking = true;
					System.out.println("DEFENDIDO");
				} else {
					enemigo.ishurt = true;
					enemigo.agresivo = true;
					night.vida -= 10;
					if(barradevida==10&&!night.isDead){
						barradevida=0;
					}
					barradevida++;
					if(barradevida<=10) {

						p1 = new Pixmap(Gdx.files.internal("Night/Barra_de_vida/" + barradevida + ".png"));
						p2 = new Pixmap(510, 350, p1.getFormat());
						p2.drawPixmap(p1,
								0, 0, p1.getWidth(), p1.getHeight(),
								0, 0, p2.getWidth(), p2.getHeight()
						);
						HUD = new Texture(p2);
						p1.dispose();
						p2.dispose();
					}
					Sonidos.sonido_hurt_night.play();
				}
				if (enemigo.vida <= 0) {
					enemigo.isRemove = true;
					enemigo.vida = 100;
				}

			}
			if (bodyA.getUserData() instanceof Night && bodyB.getUserData() instanceof Atalanta) {

				Night night = (Night) bodyA.getUserData();
				Atalanta atalanta = (Atalanta) bodyB.getUserData();

				if (night.isAttacking) {
					System.out.println("Vida de Enemigo: " + atalanta.vida);
					enemigo.isAttacking = true;
					atalanta.vida -= 1000;

				} else if (night.isDefending) {
					enemigo.isAttacking = true;
					System.out.println("DEFENDIDO");
				} else {
					if(barradevida==10&&!night.isDead){
						barradevida=0;
					}
					barradevida++;
					if(barradevida<=10) {

						p1 = new Pixmap(Gdx.files.internal("Night/Barra_de_vida/" + barradevida + ".png"));
						p2 = new Pixmap(510, 350, p1.getFormat());
						p2.drawPixmap(p1,
								0, 0, p1.getWidth(), p1.getHeight(),
								0, 0, p2.getWidth(), p2.getHeight()
						);
						HUD = new Texture(p2);
						p1.dispose();
						p2.dispose();
					}
					System.out.println("ME ESTA TOCANDOOOO");
					night.vida -= 10;
					Sonidos.sonido_hurt_night.play();
					System.out.println("Vida de night: " + night.vida);
					atalanta.isAgressive = true;
				}
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