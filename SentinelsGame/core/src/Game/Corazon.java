package Game;

import PersonajePrincipal.Night;

public class Corazon extends Thread{
    //Construye un nuevo hilo.

    public Corazon(String nombre){
        //super se usa para llamar aj version del constructor de Thread
        super(nombre);

    }
    //Punto de entrada del hilo
    public void run(){
        try  {
            while (!Night.isDead) {
                PantallaJuego.stateTime ++;
                Thread.sleep(70);
                if(PantallaJuego.stateTime==37){
                    PantallaJuego.stateTime=0;
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
