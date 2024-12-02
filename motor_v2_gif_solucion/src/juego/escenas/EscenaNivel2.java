package juego.escenas;

import java.awt.Color;
import java.awt.Graphics;

import juego.Assets;
import juego.Conf;
import juego.entidades.Bloque;
import juego.entidades.EnemigoHechicero;
import juego.entidades.EnemigoEspada;
import juego.entidades.Jugador;
import juego.entidades.EnemigoProvisional;
import juego.entidades.Flecha;
import motor_v1.motor.Scene;
import motor_v1.motor.entidades.Gif;
import motor_v1.motor.entidades.ListaEntidades;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.entidades.SpriteText;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.Key;
import motor_v1.motor.util.Vector2D;

public class EscenaNivel2 extends Niveles{
	
	private EnemigoProvisional[] bill = new EnemigoProvisional[2];
	
	public EscenaNivel2() {
		super();
		crearEnemigoProvisional();
		
	}
	
	public void crearEnemigos() {
		
		super.setCantidadEnemigos(2);
	}
	
	public void crearJugador() {
		Vector2D p = new Vector2D(150, Conf.HEIGHT /2);
		super.setJugador(new Jugador(p, 10));
	}
	
	public void crearBloques() {
		Bloque bloque;
		int y = (Conf.HEIGHT / 2) - 160;
		
		for (int i = 0; i < 5; i++) {
			
			Vector2D p = new Vector2D(Conf.WIDTH / 2, y);
			bloque = new Bloque(p);
			bloque.getBloque().getTransformar().setPosicion(p.subtract(bloque.getBloque().getCentroRotacion()));
			super.getListaBloques().add("Bloque", bloque);
			
			y = y + 80;
		}
		
		
	}
	
	public void siguienteNivel() {
		
		for (int i = 0; i < bill.length; i++) {
			if(super.getJugador().getColisiona().colisionaCon(bill[i].getColisiona())) {
				Scene.cambiarEscena(new EscenaNivel3());
			}
			
		}
		
	}
	

	public void crearEnemigoProvisional() {
		
		bill[0] = new EnemigoProvisional(new Vector2D(Conf.WIDTH - 150, (Conf.HEIGHT /2) + 75));
		bill[1] = new EnemigoProvisional(new Vector2D(Conf.WIDTH - 150, (Conf.HEIGHT /2) - 75));
		
	}
	
}
