package juego.escenas;
/**

Johan David Sánchez Rojas C17305
Joshua Chacón Alvarez C4E105
Andrew Mora Mejia C05158*/
import java.awt.Graphics;

import juego.Assets;
import juego.Conf;
import juego.entidades.Bloque;
import juego.entidades.EnemigoArquero;
import juego.entidades.EnemigoEspada;
import juego.entidades.Flecha;
import juego.entidades.Jugador;
import motor_v1.motor.Scene;
import motor_v1.motor.entidades.Gif;
import motor_v1.motor.entidades.ListaEntidades;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.Key;
import motor_v1.motor.util.Vector2D;

public class EscenaNivel4 extends Niveles{

	
	public EscenaNivel4() {
		super();
		
	}

	
	public void crearJugador() {
		Vector2D p = new Vector2D(100, Conf.HEIGHT - 200);
		super.setJugador(new Jugador(p, 10));
	}
	
	public void crearBloques() {
		Bloque bloque;
		
		int y = 0 + Conf.WOOD_LADO / 2;
		int y2 = Conf.HEIGHT - Conf.WOOD_LADO / 2;
		int x =  0 + Conf.WOOD_LADO / 2;
		
		for (int i = 0; i < 12; i++) {
			
			if(i <= 3) {
				Vector2D p = new Vector2D(Conf.WIDTH / 1.5, y);
				
				bloque = new Bloque(p);
				bloque.getBloque().getTransformar().setPosicion(p.subtract(bloque.getBloque().getCentroRotacion()));
				super.getListaBloques().add("Bloque", bloque);
				
				y = y + 80;
			}else if(i >= 4 && i <= 7){
				
				Vector2D p = new Vector2D(Conf.WIDTH / 2.5, y2);
				
				bloque = new Bloque(p);
				bloque.getBloque().getTransformar().setPosicion(p.subtract(bloque.getBloque().getCentroRotacion()));
				super.getListaBloques().add("Bloque", bloque);
				
				y2 = y2 - 80;
				
				
			}else if(i >= 8) {
				Vector2D p = new Vector2D(x , Conf.HEIGHT / 3 );
				
				bloque = new Bloque(p);
				bloque.getBloque().getTransformar().setPosicion(p.subtract(bloque.getBloque().getCentroRotacion()));
				super.getListaBloques().add("Bloque", bloque);
				
				x = x + 80;
			}
			
		}
		
	}
	
	
	public void siguienteNivel() {
		Scene.cambiarEscena(new EscenaNivel5());
			
	}


	@Override
	public void crearEnemigos() {
		EnemigoArquero enemigo1 = new EnemigoArquero(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 + 120));
		EnemigoEspada enemigo2 = new EnemigoEspada(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 - 120));
		EnemigoEspada enemigo3 = new EnemigoEspada(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 + 80));
		EnemigoArquero enemigo4 = new EnemigoArquero(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 + 80));
		
		super.getListaEnemigos().add("Enemigo", enemigo1);
		super.getListaEnemigos().add("Enemigo", enemigo2);
		super.getListaEnemigos().add("Enemigo", enemigo3);
		super.getListaEnemigos().add("Enemigo", enemigo4);
		
		super.setCantidadEnemigos(4);
		
	}
}
