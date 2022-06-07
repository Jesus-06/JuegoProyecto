package Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sonidos {

    public static Sound sonido_ataque_night;
    public static Sound sonido_hurt_night;
    public static Sound sonido_jump_night;
    public static Sound sonido_muerte_night;
    public static Sound sonido_spawn_atalanta;
    public static Sound sonido_caminar_night;
    public static Sound sonido_pick_item;


    public static void load(){
        sonido_ataque_night= Gdx.audio.newSound(Gdx.files.internal("Sonidos/espada.mp3"));
        sonido_spawn_atalanta=Gdx.audio.newSound(Gdx.files.internal("Sonidos/AtalantaPrimeraFrase.wav"));
        sonido_hurt_night=Gdx.audio.newSound(Gdx.files.internal("Sonidos/hurt_night.wav"));
        sonido_muerte_night=Gdx.audio.newSound(Gdx.files.internal("Sonidos/muerte.wav"));
        sonido_caminar_night=Gdx.audio.newSound(Gdx.files.internal("Sonidos/caminar.wav"));
        sonido_jump_night=Gdx.audio.newSound(Gdx.files.internal("Sonidos/saltar.wav"));
        sonido_pick_item=Gdx.audio.newSound(Gdx.files.internal("Sonidos/itemDrop.wav"));
    }
}

