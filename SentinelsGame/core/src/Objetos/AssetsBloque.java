package Objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class AssetsBloque {

    public static Texture block1;
    private static Pixmap p1, p2;

    public static void load(){
        p1 = new Pixmap(Gdx.files.internal("Mundo/bloqueStatico_03.png"));
        p2 = new Pixmap(415,150, p1.getFormat());
        p2.drawPixmap(p1,
                0, 0, p1.getWidth(), p1.getHeight(),
                0, 0, p2.getWidth(), p2.getHeight()
        );
        block1 = new Texture(p2);
        p1.dispose();
        p2.dispose();
    }
}