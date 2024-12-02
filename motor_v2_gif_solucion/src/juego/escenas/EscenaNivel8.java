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

public class EscenaNivel8 extends Niveles{
	
	private EnemigoProvisional[] bill = new EnemigoProvisional[4];
	
	
	
	
	public EscenaNivel8() {
		super();
		
	}
	
	public void crearJugador() {
		Vector2D p = new Vector2D(new Vector2D((Conf.WIDTH /2) - (Conf.JUGADOR_WIDTH / 2), (Conf.HEIGHT /2) - (Conf.JUGADOR_HEIGHT / 2) ));
		super.setJugador(new Jugador(p, 10));
	}
	
	public void crearBloques() {
		Bloque bloque;
		
		
		
		
		int y = (Conf.HEIGHT / 2) - 160;
		int x = (Conf.WIDTH / 2 + 150);
		
		
		int x2 = (Conf.WIDTH / 2 - 150);
		int y2 = (Conf.HEIGHT / 2) - 160;
		
		
		
		for (int i = 0; i < 10; i++) {
			
			if(i <= 4 ) {
				Vector2D p = new Vector2D(x, y);
				bloque = new Bloque(p);
				bloque.getBloque().getTransformar().setPosicion(p.subtract(bloque.getBloque().getCentroRotacion()));
				super.getListaBloques().add("Bloque", bloque);
			
				y = y + Conf.WOOD_LADO;
			
			}else if(i >= 5){
				Vector2D p = new Vector2D(x2, y2);
				bloque = new Bloque(p);
				bloque.getBloque().getTransformar().setPosicion(p.subtract(bloque.getBloque().getCentroRotacion()));
				super.getListaBloques().add("Bloque", bloque);
				y2 = y2 +Conf.WOOD_LADO;
				
			}
				
			
			
		}
			
			
			
			
			
		
		
		
		
		
	}
	public void crearEnemigoProvisional() {
		
		bill[0] = new EnemigoProvisional(new Vector2D(Conf.WIDTH - 150, (Conf.HEIGHT /2) + 75));
		bill[1] = new EnemigoProvisional(new Vector2D(Conf.WIDTH - 150, (Conf.HEIGHT /2) - 75));
		bill[2] = new EnemigoProvisional(new Vector2D(100, (Conf.HEIGHT /2) + 75));
		bill[3] = new EnemigoProvisional(new Vector2D(100, (Conf.HEIGHT /2) - 75));
		
	}
	
	public void siguienteNivel() {
		Scene.cambiarEscena(new EscenaNivel9());
			
	}

	@Override
	public void crearEnemigos() {
		// TODO Auto-generated method stub
		
	}
}
