package juego.entidades;

import java.awt.image.BufferedImage;

import motor_v1.motor.component.Collider;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.util.Vector2D;

public class Bloque extends SpriteSolido{

	public Bloque(String nombre, BufferedImage textura, Vector2D posicion) {
		super(nombre, textura, posicion);
		
	}
	
	public void actualizar() {
		colisiona.actualizar();
	}
	
	public Collider getColisiona() {
		return colisiona;
	}
	
	
	
	
	

}
