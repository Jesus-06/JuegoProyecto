package Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CrearPerfil extends SettingsScreen{

    public static String PERFIL_USER ;

    Texture personaje;
    public Skin skin;
    TextField perfil;
    Texture texPlay;
    TextureRegion trPlay;
    Image imgPlay;
    TextureRegionDrawable trdPlay;
    ImageButton btPlay;

    public CrearPerfil(final Sentinels sent) {
        super(sent);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        perfil = new TextField("", skin);
        perfil.setSize(250,70);
        poner(perfil);

        personaje = new Texture(Gdx.files.internal("Night/NightEnormeIzq.png"));

        texPlay = new Texture(Gdx.files.internal("boton-de-inicio.png"));
        trPlay = new TextureRegion(texPlay, 128, 400);
        imgPlay = new Image(trPlay);
        trdPlay = new TextureRegionDrawable(trPlay);
        btPlay = new ImageButton(trdPlay);
        poner2(btPlay);

        btPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PERFIL_USER = perfil.getText();
                sent.setScreen(new PantallaJuego(sent));
                dispose();
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

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spBatch.begin();
        spBatch.draw(personaje,250,450);
        fuente.draw(spBatch,"PERFIL", 820,630);
        spBatch.end();

        stage.draw();
        stage.act(delta);

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
        perfil.setDisabled(true);
    }
}
