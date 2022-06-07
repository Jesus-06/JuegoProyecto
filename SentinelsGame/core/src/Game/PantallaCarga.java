package Game;

import DB.MySQLCLass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.sql.Connection;

public class PantallaCarga extends SettingsScreen {
    //Conexion a base de datos
    MySQLCLass con;
    public Texture fondo, personaje, play;
    public Skin skin;
    public Table table;

    public PantallaCarga(Sentinels sent) {

        super(sent);

        //Conexión base de datos
        con= new MySQLCLass();
        Connection reg = con.getCOnnection();
        con.insertaDatos(1,"","","","","","");
        table = new Table();
        table.setFillParent(true);

        fuente = new BitmapFont(Gdx.files.internal("skin2/estilo1.fnt"),
                Gdx.files.internal("skin2/estilo1.png"),false);


        fondo = new Texture(Gdx.files.internal("fondo_gameLoad.png"));
        personaje = new Texture(Gdx.files.internal("Night/NightEnormeIzq.png"));

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
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
        fuente.draw(spBatch,"PERFILES", 730,725);
        fuente.draw(spBatch,"PARTIDA", 1100,725);
        spBatch.end();

        stage.draw();
        stage.act(delta);

        table.debug();
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
