package Enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetsEnemigo {

    public static Animation<Sprite> hurt;
    public static Animation<Sprite> idle;
    public static Animation<Sprite> die;
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
        walk = new Animation<Sprite>(Enemigo.WALK_FRAME_DURATION,
                atlas.createSprite("walk (1)"),
                atlas.createSprite("walk (2)"),
                atlas.createSprite("walk (3)"),
                atlas.createSprite("walk (4)"),
                atlas.createSprite("walk (5)"),
                atlas.createSprite("walk (6)"),
                atlas.createSprite("walk (7)"),
                atlas.createSprite("walk (8)"),
                atlas.createSprite("walk (9)"),
                atlas.createSprite("walk (10)"),
                atlas.createSprite("walk (11)"),
                atlas.createSprite("walk (12)"),
                atlas.createSprite("walk (13)"),
                atlas.createSprite("walk (14)"),
                atlas.createSprite("walk (15)"),
                atlas.createSprite("walk (16)"),
                atlas.createSprite("walk (17)"),
                atlas.createSprite("walk (18)"),
                atlas.createSprite("walk (19)"),
                atlas.createSprite("walk (20)")
                );
        attack = new Animation<Sprite>(Enemigo.ATTACK_FRAME_DURATION,
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
                atlas.createSprite("ataque (15)"),
                atlas.createSprite("ataque (16)"),
                atlas.createSprite("ataque (17)"),
                atlas.createSprite("ataque (18)"),
                atlas.createSprite("ataque (19)"),
                atlas.createSprite("ataque (20)")
                );
        die = new Animation<Sprite>(7,
                atlas.createSprite("die (1)"),
                atlas.createSprite("die (2)"),
                atlas.createSprite("die (3)"),
                atlas.createSprite("die (4)"),
                atlas.createSprite("die (5)"),
                atlas.createSprite("die (6)"),
                atlas.createSprite("die (7)"),
                atlas.createSprite("die (8)"),
                atlas.createSprite("die (9)"),
                atlas.createSprite("die (10)"),
                atlas.createSprite("die (11)"),
                atlas.createSprite("die (12)"),
                atlas.createSprite("die (13)"),
                atlas.createSprite("die (14)"),
                atlas.createSprite("die (15)"),
                atlas.createSprite("die (16)"),
                atlas.createSprite("die (17)"),
                atlas.createSprite("die (18)"),
                atlas.createSprite("die (19)"),
                atlas.createSprite("die (20)")
                );
        hurt = new Animation<Sprite>(7,
                atlas.createSprite("hurt (1)"),
                atlas.createSprite("hurt (2)"),
                atlas.createSprite("hurt (3)"),
                atlas.createSprite("hurt (4)"),
                atlas.createSprite("hurt (5)"),
                atlas.createSprite("hurt (6)"),
                atlas.createSprite("hurt (7)"),
                atlas.createSprite("hurt (8)"),
                atlas.createSprite("hurt (9)"),
                atlas.createSprite("hurt (10)"),
                atlas.createSprite("hurt (11)"),
                atlas.createSprite("hurt (12)"),
                atlas.createSprite("hurt (13)"),
                atlas.createSprite("hurt (14)"),
                atlas.createSprite("hurt (15)"),
                atlas.createSprite("hurt (16)"),
                atlas.createSprite("hurt (17)"),
                atlas.createSprite("hurt (18)"),
                atlas.createSprite("hurt (19)"),
                atlas.createSprite("hurt (20)")
        );
    }
}
