package PersonajePrincipal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetsNight {

    public static Sprite duck;
    public static Sprite fall;
    public static Sprite defense;
    public static Sprite die_static;
    public static Animation<Sprite> idle;
    public static Animation<Sprite> jump;
    public static Animation<Sprite> walk;
    public static Animation<Sprite> attack;
    public static Animation<Sprite> die;
    public static TextureAtlas atlas;

    public static void load(){
        atlas = new TextureAtlas(Gdx.files.internal("Night/Night.txt"));

        duck = atlas.createSprite("muerte2");
        fall = atlas.createSprite("salto4");
        defense = atlas.createSprite("defender3");
        die_static = atlas.createSprite("muerte5");

        die = new Animation<Sprite>(Night.DIE_FRAM_DURATION,
                atlas.createSprite("muerte1"),
                atlas.createSprite("muerte2"),
                atlas.createSprite("muerte3"),
                atlas.createSprite("muerte4"),
                atlas.createSprite("muerte5")
                );
        idle = new Animation<Sprite>(Night.IDLE_FRAME_DURATION,
                atlas.createSprite("quieto1"),
                atlas.createSprite("quieto2"),
                atlas.createSprite("quieto3"),
                atlas.createSprite("quieto4"));

        jump = new Animation<Sprite>(Night.JUMP_FRAME_DURATION,
                atlas.createSprite("salto1"),
                atlas.createSprite("salto2"),
                atlas.createSprite("salto3"),
                atlas.createSprite("salto4"));

        walk = new Animation<Sprite>(Night.WALK_FRAME_DURATION,
                atlas.createSprite("correr1"),
                atlas.createSprite("correr2"),
                atlas.createSprite("correr3"),
                atlas.createSprite("correr4"),
                atlas.createSprite("correr5"),
                atlas.createSprite("correr6"),
                atlas.createSprite("correr7"),
                atlas.createSprite("correr8"),
                atlas.createSprite("correr9"),
                atlas.createSprite("correr10"),
                atlas.createSprite("correr11"),
                atlas.createSprite("correr12"),
                atlas.createSprite("correr13"),
                atlas.createSprite("correr14"));

        attack = new Animation<Sprite>(Night.ATTACK_FRAME_DURATION,
                atlas.createSprite("atacar1"),
                atlas.createSprite("atacar2"),
                atlas.createSprite("atacar3"),
                atlas.createSprite("atacar4"),
                atlas.createSprite("atacar5"),
                atlas.createSprite("atacar6"),
                atlas.createSprite("atacar7"));
    }
    public static void dispose(){

        atlas.dispose();

    }
}
