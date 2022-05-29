package Booses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetsAtalanta {

    public static Sprite duck;
    public static Sprite fall;
    public static Sprite idle;
    public static Sprite jump;

    public static Animation<Sprite> walk;

    static TextureAtlas atlas;

    public static void load(){
        atlas = new TextureAtlas(Gdx.files.internal("Atalanta/atalantaFaseUno.txt"));

        duck = atlas.createSprite("");
        fall = atlas.createSprite("atalanta_fase_uno1");
        idle = atlas.createSprite("atalanta_fase_uno1");
        jump = atlas.createSprite("atalanta_fase_uno1");

        walk = new Animation<Sprite>(Atalanta.WALK_FRAME_DURATION,
                atlas.createSprite("atalanta_fase_uno1"),
                atlas.createSprite("atalanta_fase_uno2"),
                atlas.createSprite("atalanta_fase_uno3"),
                atlas.createSprite("atalanta_fase_uno4"),
                atlas.createSprite("atalanta_fase_uno5"),
                atlas.createSprite("atalanta_fase_uno6"));
    }
    public static void dispose(){

    }
}