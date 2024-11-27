package juego.entidades;

import motor_v1.motor.component.Collider;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.Assets;

public class Flecha extends SpriteMovible{

	public Flecha(String nombre, BufferedImage textura, Vector2D posicion) {
		super(nombre, textura, posicion);
	}
	
}
