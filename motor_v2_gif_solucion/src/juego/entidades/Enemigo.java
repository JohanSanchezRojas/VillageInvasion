package juego.entidades;

import java.awt.Color;
import java.awt.image.BufferedImage;

import juego.Assets;
import motor_v1.motor.Entidad;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;

public abstract class Enemigo extends Entidad {

	public SpriteMovible cuerpo;
	protected int Movimiento;
	private int numeroVidas;
	
	
	public Enemigo(String nombre, BufferedImage textura, Vector2D posicion) {
		super();
		
	}

	
	public void actualizar () {
		
	}
	
	
	public abstract void MovimientoEnemigo();
}
//{}
