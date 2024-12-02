package juego.escenas;

import java.awt.Graphics;

import juego.Assets;
import juego.Conf;
import juego.entidades.Bloque;
import juego.entidades.EnemigoEspada;
import juego.entidades.EnemigoHechicero;
import juego.entidades.EnemigoProvisional;
import juego.entidades.Flecha;
import juego.entidades.Jugador;
import motor_v1.motor.Scene;
import motor_v1.motor.entidades.Gif;
import motor_v1.motor.entidades.ListaEntidades;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.Key;
import motor_v1.motor.util.Vector2D;

public class EscenaNivel3 extends Niveles{
	private EnemigoProvisional[] bill = new EnemigoProvisional[1];

	
	
	
	public EscenaNivel3() {
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
		
		for (int i = 0; i < 8; i++) {
			
			if(i <= 3) {
				Vector2D p = new Vector2D(Conf.WIDTH / 1.5, y);
				
				bloque = new Bloque(p);
				bloque.getBloque().getTransformar().setPosicion(p.subtract(bloque.getBloque().getCentroRotacion()));
				super.getListaBloques().add("Bloque", bloque);
				
				y = y + 80;
			}else if(i >= 4 ){
				Vector2D p = new Vector2D(Conf.WIDTH / 2.5, y2);
				
				bloque = new Bloque(p);
				bloque.getBloque().getTransformar().setPosicion(p.subtract(bloque.getBloque().getCentroRotacion()));
				super.getListaBloques().add("Bloque", bloque);
				
				y2 = y2 - 80;
				
				
			}
			
		}
		
	}
	
	public void crearEnemigoProvisional() {
		
		bill[0] = new EnemigoProvisional(new Vector2D(Conf.WIDTH - 150, 150));
	}
	
	public void siguienteNivel() {
				Scene.cambiarEscena(new EscenaNivel4());		
	}

	@Override
	public void crearEnemigos() {
		EnemigoHechicero enemigo1 = new EnemigoHechicero(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 + 120));
		EnemigoHechicero enemigo2 = new EnemigoHechicero(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 - 120));
		EnemigoHechicero enemigo3 = new EnemigoHechicero(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 + 80));;
		EnemigoHechicero enemigo4 = new EnemigoHechicero(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 - 80));;
		
		super.getListaEnemigos().add("Enemigo", enemigo1);
		super.getListaEnemigos().add("Enemigo", enemigo2);
		super.getListaEnemigos().add("Enemigo", enemigo3);
		super.getListaEnemigos().add("Enemigo", enemigo4);
		
		super.setCantidadEnemigos(4);
	}
}
