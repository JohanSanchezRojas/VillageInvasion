package juego.entidades;

import java.awt.image.BufferedImage;

import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;

public abstract class Enemigo extends SpriteMovible {

	
	protected int Movimiento;
	
	public Enemigo(String nombre, BufferedImage textura, Vector2D posicion) {
		super(nombre, textura, posicion);
	}

	
	public void actualizar () {
		
	}
	
	
	public abstract void MovimientoEnemigo();
}
//{}