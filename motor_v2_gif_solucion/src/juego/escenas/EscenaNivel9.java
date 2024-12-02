package juego.escenas;

import java.awt.Graphics;

import juego.Assets;
import juego.Conf;
import juego.entidades.Bloque;
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

public class EscenaNivel9 extends Niveles{
	
	EnemigoProvisional[] bill = new EnemigoProvisional[4];
	
	
	
	
	public EscenaNivel9() {
		super();
		
	}
	
	public void crearJugador() {
		Vector2D p = new Vector2D(new Vector2D(100, (Conf.HEIGHT /2) - (Conf.JUGADOR_HEIGHT / 2) ));
		super.setJugador(new Jugador(p, 10));
	}
	
	public void crearBloques() {
		Bloque bloque;
		
		int y = 0 + Conf.WOOD_LADO / 2;
		int y2 = Conf.HEIGHT - Conf.WOOD_LADO / 2;
		int x =  0 + Conf.WOOD_LADO / 2;
		int x2 =  0 + Conf.WOOD_LADO / 2;
		
		
		for (int i = 0; i < 14; i++) {
			
		}
		
		
	}
	public void crearEnemigoProvisional() {
		
		bill[0] = new EnemigoProvisional(new Vector2D(Conf.WIDTH - 150, 100));
		bill[1] = new EnemigoProvisional(new Vector2D(Conf.WIDTH - 150, (Conf.HEIGHT - 100)));
		bill[2] = new EnemigoProvisional(new Vector2D(100, 100));
		bill[3] = new EnemigoProvisional(new Vector2D(100, (Conf.HEIGHT - 75)));
		
	}
	
	public void siguienteNivel() {
		Scene.cambiarEscena(new EscenaBienvenida());
	}	

	@Override
	public void crearEnemigos() {
		// TODO Auto-generated method stub
		
	}
}
