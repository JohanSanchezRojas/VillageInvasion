package juego.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.Assets;
import motor_v1.motor.Entidad;
import motor_v1.motor.component.Collider;
import motor_v1.motor.entidades.GifMovible;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;

public class Hechizo extends Entidad{

	private SpriteMovible hechizo;
	
	public Hechizo(Vector2D posicion) {
		super();
		this.hechizo = new SpriteMovible("Flecha", Assets.hechizo, posicion);
	}
	
	@Override
	public void actualizar() {
		hechizo.actualizar();
	}

	@Override
	public void destruir() {
		hechizo.setViva(false);
		hechizo.setVisible(false);
	}

	@Override
	public void dibujar(Graphics g) {
		hechizo.dibujar(g);
	}
	
	public void desparecer() {
		hechizo.setVisible(false);
		hechizo.setViva(false);
	}
	
	public void aparecer() {
		hechizo.setVisible(true);
		hechizo.setViva(true);
	}

	public Collider getColisiona() {
		return hechizo.getColisiona();
	}

	public SpriteMovible getHechizo() {
		return hechizo;
	}

	public void setHechizo(SpriteMovible hechizo) {
		this.hechizo = hechizo;
	}
}