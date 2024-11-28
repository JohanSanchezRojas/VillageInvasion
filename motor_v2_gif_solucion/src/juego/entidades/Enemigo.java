package juego.entidades;

import java.awt.Color;
import java.awt.image.BufferedImage;

import juego.Assets;
import motor_v1.motor.Entidad;
import motor_v1.motor.component.Collider;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;

public abstract class Enemigo extends Entidad {

	public SpriteMovible cuerpo;
	protected int movimientoE;
	private int numeroVidas;
	//private double crono;
	
	public Enemigo(String nombre, BufferedImage textura, Vector2D posicion) {
		super();
		
	}

	
	public void actualizar () {
		cuerpo.getColisiona().actualizar();
	}
	
	
	public abstract void MovimientoEnemigo();
	
	public Collider getColisiona() {
		return cuerpo.getColisiona();
	}
}
//{}
