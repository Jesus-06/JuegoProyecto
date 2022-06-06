package Booses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetsAtalanta {

    public static Sprite die_static;
    public static Animation<Sprite> idle;
    public static Animation<Sprite> walk;
    public static Animation<Sprite> attack;
    public static Animation<Sprite> hurt;
    public static Animation<Sprite> die;
    public static TextureAtlas atlas;

    public static void load(){
        atlas = new TextureAtlas(Gdx.files.internal("Demonio/demonio.txt"));

        die_static = atlas.createSprite("muerte (19)");

        hurt = new Animation<Sprite>(Atalanta.HURT_FRAME_DURATION,
                atlas.createSprite("hurt (1)"),
                atlas.createSprite("hurt (2)"),
                atlas.createSprite("hurt (3)"),
                atlas.createSprite("hurt (4)"),
                atlas.createSprite("hurt (5)"));

        die = new Animation<Sprite>(Atalanta.DIE_FRAME_DURATION,
                atlas.createSprite("muerte (1)"),
                atlas.createSprite("muerte (2)"),
                atlas.createSprite("muerte (3)"),
                atlas.createSprite("muerte (4)"),
                atlas.createSprite("muerte (5)"),
                atlas.createSprite("muerte (6)"),
                atlas.createSprite("muerte (7)"),
                atlas.createSprite("muerte (8)"),
                atlas.createSprite("muerte (19)"),
                atlas.createSprite("muerte (10)"),
                atlas.createSprite("muerte (11)"),
                atlas.createSprite("muerte (12)"),
                atlas.createSprite("muerte (13)"),
                atlas.createSprite("muerte (14)"),
                atlas.createSprite("muerte (15)"),
                atlas.createSprite("muerte (16)"),
                atlas.createSprite("muerte (17)"),
                atlas.createSprite("muerte (18)"),
                atlas.createSprite("muerte (19)"));

        idle = new Animation<Sprite>(Atalanta.IDLE_FRAME_DURATION,
                atlas.createSprite("idle (1)"),
                atlas.createSprite("idle (2)"),
                atlas.createSprite("idle (3)"),
                atlas.createSprite("idle (4)"),
                atlas.createSprite("idle (5)"),
                atlas.createSprite("idle (6)"));

        walk = new Animation<Sprite>(Atalanta.WALK_FRAME_DURATION,
                atlas.createSprite("caminar (1)"),
                atlas.createSprite("caminar (2)"),
                atlas.createSprite("caminar (3)"),
                atlas.createSprite("caminar (4)"),
                atlas.createSprite("caminar (5)"),
                atlas.createSprite("caminar (6)"),
                atlas.createSprite("caminar (7)"),
                atlas.createSprite("caminar (8)"),
                atlas.createSprite("caminar (9)"),
                atlas.createSprite("caminar (10)"),
                atlas.createSprite("caminar (11)"),
                atlas.createSprite("caminar (12)"));

        attack = new Animation<Sprite>(Atalanta.ATTACK_FRAME_DURATION,
                atlas.createSprite("ataque (1)"),
                atlas.createSprite("ataque (2)"),
                atlas.createSprite("ataque (3)"),
                atlas.createSprite("ataque (4)"),
                atlas.createSprite("ataque (5)"),
                atlas.createSprite("ataque (6)"),
                atlas.createSprite("ataque (7)"),
                atlas.createSprite("ataque (8)"),
                atlas.createSprite("ataque (9)"),
                atlas.createSprite("ataque (10)"),
                atlas.createSprite("ataque (11)"),
                atlas.createSprite("ataque (12)"),
                atlas.createSprite("ataque (13)"),
                atlas.createSprite("ataque (14)"),
                atlas.createSprite("ataque (15)"));


    }

    public static void dispose(){

    }
}