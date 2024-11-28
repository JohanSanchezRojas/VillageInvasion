package juego.entidades;

import java.awt.Color;
import java.awt.image.BufferedImage;

import juego.Assets;
import juego.Conf;
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

	private void limite() {
		double x = cuerpo.getTransformar().getPosicion().getX();
		if (x < 0) {
			cuerpo.getTransformar().getPosicion().setX(0);
		}

		if (x > (Conf.WIDTH - Conf.ENEMIGO_WIDTH)) {
			cuerpo.getTransformar().getPosicion().setX(Conf.WIDTH - Conf.ENEMIGO_WIDTH);
		}

		double y = cuerpo.getTransformar().getPosicion().getY();
		if (y < 0) {
			cuerpo.getTransformar().getPosicion().setY(0);
		}

		if (y > (Conf.HEIGHT - Conf.ENEMIGO_HEIGHT)) {
			cuerpo.getTransformar().getPosicion().setY(Conf.WIDTH - Conf.ENEMIGO_HEIGHT);
		}
	}
	
	public void EnemigoColisionBloques(Bloque b) {

		double xEnemigo = cuerpo.getTransformar().getPosicion().getX();
		double xB = b.getTransformar().getPosicion().getX();

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.RIGHT) && xEnemigo <= xB) {
			cuerpo.getTransformar().getPosicion().setX(xB - Conf.ENEMIGO_WIDTH);
		}

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.LEFT) && xEnemigo >= xB) {
			cuerpo.getTransformar().getPosicion().setX(xB + Conf.WOOD_LADO);
		}

		double yEnemigo = cuerpo.getTransformar().getPosicion().getY();
		double yB = b.getTransformar().getPosicion().getY();

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.DOWN) && yEnemigo <= yB) {
			cuerpo.getTransformar().getPosicion().setY(yB - Conf.ENEMIGO_HEIGHT);
		}

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.UP) && yEnemigo >= yB) {
			cuerpo.getTransformar().getPosicion().setY(yB + Conf.WOOD_LADO);
		}
	}
	
	public void enemigoFlechaColision(Flecha f, Enemigo e) {
		System.out.println("Muere1");
		if (e.getColisiona().colisionaCon(f.getColisiona())) {
			System.out.println("Muere2");
			e.destruir();
			e.setViva(false);
		}
	}
	
	public void actualizar () {
		cuerpo.getColisiona().actualizar();
		limite();
	}
	
	public abstract void MovimientoEnemigo();
	
	public Collider getColisiona() {
		return cuerpo.getColisiona();
	}
}
//{}
