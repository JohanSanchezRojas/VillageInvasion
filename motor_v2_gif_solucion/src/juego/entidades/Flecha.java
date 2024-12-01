package juego.entidades;

import motor_v1.motor.Entidad;
import motor_v1.motor.GameLoop;
import motor_v1.motor.component.Collider;
import motor_v1.motor.entidades.Gif;
import motor_v1.motor.entidades.GifMovible;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.Assets;
import juego.Conf;

public class Flecha extends Entidad {
	private GifMovible flecha;

	public Flecha(Vector2D posicion) {
		super();
		this.flecha = new GifMovible("Flecha", Assets.flecha, posicion, 100);
	}

	@Override
	public void actualizar() {
		flecha.actualizar();
		colisionPantalla();
	}

	@Override
	public void dibujar(Graphics g) {
		flecha.dibujar(g);
	}

	@Override
	public void destruir() {
		setViva(false);
		flecha.destruir();
	}

	public Collider getColisiona() {
		return flecha.getColisiona();
	}

	public GifMovible getFlecha() {
		return flecha;
	}

	public void setFlecha(GifMovible flecha) {
		this.flecha = flecha;
	}

	public void colisionPantalla() {
		if (flecha.getTransformar().getPosicion().getX() > Conf.WIDTH + 30 - Conf.FLECHA_HEIGHT) {
			System.out.println("1");

			destruir();
		} else if (flecha.getTransformar().getPosicion().getX() < -30) {
			System.out.println("2");
			destruir();
		}

		if (flecha.getTransformar().getPosicion().getY() > Conf.HEIGHT + 30 - Conf.FLECHA_WIDTH) {
			System.out.println("3");
			destruir();
		} else if (flecha.getTransformar().getPosicion().getY() < -30) {
			System.out.println("4");
			destruir();
		}
	}

}
