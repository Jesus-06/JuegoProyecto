package com.sentinels.game;

public class Reloj extends Thread{
    //Construye un nuevo hilo.

    Reloj(String nombre){
        //super se usa para llamar aj versión del constructor de Thread
        super(nombre);

    }
    //Punto de entrada del hilo
    public void run(){
        try  {
            while (true) {
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
