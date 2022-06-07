package Game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public abstract class SettingsScreen extends InputAdapter implements Screen {

    public static final float PANTALLA_ANCHO = 1920;
    public static final float PANTALLA_ALTO = 1080;

    public static final float MUNDO_ANCHO = 1920;
    public static final float MUNDO_ALTO = 1080;

    public OrthographicCamera camUI;
    public OrthographicCamera cam2D;

    public SpriteBatch spBatch;

    public BitmapFont fuente;

    public Stage stage;

    Sentinels sent;

    public SettingsScreen (Sentinels sent){

        this.sent = sent;

        stage = new Stage(new StretchViewport(PANTALLA_ANCHO, PANTALLA_ALTO));

        camUI = new OrthographicCamera(PANTALLA_ANCHO, PANTALLA_ALTO);
        camUI.position.set(PANTALLA_ANCHO / 2f, PANTALLA_ALTO / 2f, 0);

        cam2D = new OrthographicCamera(MUNDO_ANCHO, MUNDO_ALTO);
        cam2D.position.set(MUNDO_ANCHO / 2f, MUNDO_ALTO / 2f, 0);

        spBatch = new SpriteBatch();
        fuente = new BitmapFont();

    }


    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

        stage.getViewport().update(width, height, true);
    }
}
