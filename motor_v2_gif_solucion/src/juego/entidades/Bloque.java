package juego.entidades;
/**

Johan David Sánchez Rojas C17305
Joshua Chacón Alvarez C4E105
Andrew Mora Mejia C05158*/
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.Assets;
import motor_v1.motor.Entidad;
import motor_v1.motor.component.Collider;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.util.Vector2D;

public class Bloque extends Entidad{
	private SpriteMovible bloque;
	
	public Bloque(Vector2D posicion) {
		super();
		bloque = new SpriteMovible("Bloque", Assets.madera);
		bloque.getTransformar().setPosicion(posicion);
	}

	public void actualizar() {
		bloque.actualizar();
	}
	
	public Collider getColisiona() {
		return bloque.getColisiona();
	}

	@Override
	public void destruir() {
	}

	@Override
	public void dibujar(Graphics g) {
		bloque.dibujar(g);
	}

	public SpriteMovible getBloque() {
		return bloque;
	}

	public void setBloque(SpriteMovible bloque) {
		this.bloque = bloque;
	}
	
}