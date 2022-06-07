package Objetos;

import PersonajePrincipal.Night;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetsMonedas {
    public static Animation<Sprite> idle;
    public static TextureAtlas atlas;

    public static void load(){
        atlas = new TextureAtlas(Gdx.files.internal("Items/monedas.txt"));

        idle = new Animation<Sprite>(Monedas.MONEY_FRAME_DURATION,
                atlas.createSprite("monedas (1)"),
                atlas.createSprite("monedas (2)"),
                atlas.createSprite("monedas (3)"),
                atlas.createSprite("monedas (4)"),
                atlas.createSprite("monedas (5)"),
                atlas.createSprite("monedas (6)")
        );
    }
}
