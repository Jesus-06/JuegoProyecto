package PersonajePrincipal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetsNight {

    public static Sprite duck;
    public static Sprite fall;
    public static Sprite idle;
    public static Sprite jump;

    public static Animation<Sprite> walk;

    public static TextureAtlas atlas;

    public static void load(){
        atlas = new TextureAtlas(Gdx.files.internal("Night/PersonajePrincipal.txt"));

        duck = atlas.createSprite("");
        fall = atlas.createSprite("Salto5");
        idle = atlas.createSprite("Quieto1");
        jump = atlas.createSprite("Salto1");

        walk = new Animation<Sprite>(Night.WALK_FRAME_DURATION,
                atlas.createSprite("Correr1"),
                atlas.createSprite("Correr2"),
                atlas.createSprite("Correr3"),
                atlas.createSprite("Correr4"),
                atlas.createSprite("Correr5"),
                atlas.createSprite("Correr6"),
                atlas.createSprite("Correr7"),
                atlas.createSprite("Correr8"),
                atlas.createSprite("Correr9"));

    }

    public static void dispose(){

    }
}
