package Enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetsEnemigo {

    public static Sprite duck;
    public static Sprite fall;
    public static Sprite defense;
    public static Animation<Sprite> idle;
    public static Animation<Sprite> jump;
    public static Animation<Sprite> walk;
    public static Animation<Sprite> attack;
    public static TextureAtlas atlas;

    public static void load(){

        atlas = new TextureAtlas(Gdx.files.internal("Enemigo1/enemigo1.txt"));

        idle = new Animation<Sprite>(Enemigo.IDLE_FRAME_DURATION,
                atlas.createSprite("idle (1)"),
                atlas.createSprite("idle (2)"),
                atlas.createSprite("idle (3)"),
                atlas.createSprite("idle (4)"),
                atlas.createSprite("idle (5)"),
                atlas.createSprite("idle (6)"),
                atlas.createSprite("idle (7)"),
                atlas.createSprite("idle (8)"),
                atlas.createSprite("idle (9)"),
                atlas.createSprite("idle (10)"),
                atlas.createSprite("idle (11)"));

    }
}
