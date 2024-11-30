package juego.entidades;

import motor_v1.motor.Entidad;
import motor_v1.motor.component.Collider;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.Assets;
import juego.Conf;

public class Flecha extends Entidad{
	private SpriteMovible flecha;
	
	public Flecha(String nombre, SpriteMovible flecha) {
		super();
		this.flecha = flecha;
	}

	public void colisionPantalla() {
		if (flecha.getTransformar().getPosicion().getX() > Conf.WIDTH - Conf.FLECHA_HEIGHT) {
			System.out.println("1");
			destruir();
			setViva(false);
		} else if (flecha.getTransformar().getPosicion().getX() < 0) {
			System.out.println("2");
			destruir();
			setViva(false);
		}

		if (flecha.getTransformar().getPosicion().getY() > Conf.HEIGHT - Conf.FLECHA_WIDTH) {
			System.out.println("3");
			destruir();
			setViva(false);
		} else if (flecha.getTransformar().getPosicion().getY() < 0) {
			System.out.println("4");
			destruir();
			setViva(false);
		}
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
	}

	public Collider getColisiona() {
		return flecha.getColisiona();
	}
	
	public SpriteMovible getFlecha() {
		return flecha;
	}

	public void setFlecha(SpriteMovible flecha) {
		this.flecha = flecha;
	}
	
	
}
