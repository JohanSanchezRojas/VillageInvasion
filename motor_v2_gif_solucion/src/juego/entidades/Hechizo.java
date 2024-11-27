package juego.entidades;

import java.awt.image.BufferedImage;

import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;

public class Hechizo extends Proyectil{

	public Hechizo(String nombre, int dano, Vector2D posicionInicial, double angulo, SpriteMovible sprite) {
		super(nombre, dano, posicionInicial, angulo, sprite);
	}


}
