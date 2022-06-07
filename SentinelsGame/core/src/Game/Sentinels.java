package Game;

import com.badlogic.gdx.Game;

// Clase principal Juego
public class Sentinels extends Game{
	
	@Override
	public void create() {

		setScreen(new MainMenuScreen(this));	
		
	}

}
