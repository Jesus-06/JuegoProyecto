package Game;

import DB.MySQLCLass;
import PersonajePrincipal.Night;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PantallaCarga extends SettingsScreen {
    //Conexion a base de datos
    MySQLCLass con;
    public Texture fondo, personaje;
    public Skin skin;
    TextField perfil;
    Texture texPlay, texDel;
    TextureRegion trPlay, trDel;
    Image imgPlay, imgDel;
    TextureRegionDrawable trdPlay, trdDel;
    ImageButton btPlay, btDel;

    Sentinels sent;

    public PantallaCarga(final Sentinels sent) {

        super(sent);
        this.sent = sent;

        //Conexión base de datos
        con= new MySQLCLass();
        Connection reg = con.getCOnnection();

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        perfil = new TextField("", skin);
        perfil.setSize(250,70);

        fuente = new BitmapFont(Gdx.files.internal("skin2/estilo1.fnt"),
                Gdx.files.internal("skin2/estilo1.png"),false);

        fondo = new Texture(Gdx.files.internal("fondo_gameLoad.png"));
        personaje = new Texture(Gdx.files.internal("Night/NightEnormeIzq.png"));

        poner(perfil);

        texPlay = new Texture(Gdx.files.internal("boton-de-inicio.png"));
        trPlay = new TextureRegion(texPlay, 128, 400);
        imgPlay = new Image(trPlay);
        trdPlay = new TextureRegionDrawable(trPlay);
        btPlay = new ImageButton(trdPlay);

        texDel = new Texture(Gdx.files.internal("delete-button (1).png"));
        trDel = new TextureRegion(texDel, 128, 50);
        imgDel = new Image(trDel);
        trdDel = new TextureRegionDrawable(trDel);
        btDel = new ImageButton(trdDel);

        poner2(btPlay);

        poner3(btDel);

        btPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sent.setScreen(new PantallaCargada(sent, perfil.getText()));
                dispose();
            }
        });

        btDel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                con.eliminarDatos(perfil.getText());
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    private void poner(Actor a) {

        a.setPosition(730, 450);

        stage.addActor(a);

    }
    private void poner2(Actor a) {

        a.setPosition(1100, 200);

        stage.addActor(a);

    }
    private void poner3(Actor a) {

        a.setPosition(1100, 400);

        stage.addActor(a);

    }
    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spBatch.begin();
        spBatch.draw(fondo,500,50);
        spBatch.draw(personaje,150,450);
        fuente.draw(spBatch,"PERFILES", 780,630);
        fuente.draw(spBatch,"PARTIDA", 1100,630);
        spBatch.end();

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

        btPlay.setDisabled(true);
        btDel.setDisabled(true);
        perfil.setDisabled(true);

    }
}
