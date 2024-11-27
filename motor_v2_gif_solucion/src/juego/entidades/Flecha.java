package juego.entidades;

import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.Assets;

public class Flecha extends Proyectil {

	private double velocidad = 10;
	
	public Flecha(String nombre, int dano, Vector2D posicionInicial, double angulo, SpriteMovible sprite) {
		super(nombre, dano, posicionInicial, angulo, sprite);
		// TODO Auto-generated constructor stub
	}

	

  
}
