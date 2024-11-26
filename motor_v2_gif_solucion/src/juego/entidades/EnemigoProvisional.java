package juego.entidades;

import java.awt.image.BufferedImage;

import juego.Assets;
import motor_v1.motor.component.Collider;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.util.Vector2D;

public class EnemigoProvisional extends SpriteSolido{

	public EnemigoProvisional(Vector2D posicion) {
		super("Enemigo provisional", Assets.billBalazo, posicion);
		
	}
	
	public void actualizar() {
		colisiona.actualizar();
	}
	
	public Collider getColisiona() {
		return colisiona;
	}
	
	
	
	
	

}
