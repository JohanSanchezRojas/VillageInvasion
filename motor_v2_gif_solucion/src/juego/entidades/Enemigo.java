package juego.entidades;

import java.awt.Color;
import java.awt.image.BufferedImage;

import juego.Assets;
import juego.Conf;
import motor_v1.motor.Entidad;
import motor_v1.motor.GameLoop;
import motor_v1.motor.component.Collider;
import motor_v1.motor.component.Sound;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.entidades.SpriteText;
import motor_v1.motor.util.Vector2D;

public abstract class Enemigo extends Entidad {

	public SpriteMovible cuerpo;
	private SpriteMovible ataque;
	private Sound blanco;
	protected int movimientoE;
	private int numeroVidas;
	private boolean colision;
	// private double crono;

	public Enemigo(String nombre, SpriteMovible cuerpo, Vector2D posicion, SpriteMovible ataque) {
		super();
		this.cuerpo = cuerpo;
		this.ataque = ataque;
		this.cuerpo.getTransformar().setPosicion(posicion);
		blanco = new Sound(Assets.sonidoBlanco);
		blanco.changeVolume(70);
	}

	public void colisionPantalla() {

		double x = cuerpo.getTransformar().getPosicion().getX();
		if (x < 0) {
			cuerpo.getTransformar().getPosicion().setX(0);
			colision = true;
		}

		if (x > (Conf.WIDTH - Conf.ENEMIGO_WIDTH)) {
			cuerpo.getTransformar().getPosicion().setX(Conf.WIDTH - Conf.ENEMIGO_WIDTH);
			colision = true;
		}

		double y = cuerpo.getTransformar().getPosicion().getY();
		if (y < 0) {
			cuerpo.getTransformar().getPosicion().setY(0);
			colision = true;
		}

		if (y > (Conf.HEIGHT - Conf.ENEMIGO_HEIGHT)) {
			cuerpo.getTransformar().getPosicion().setY(Conf.WIDTH - Conf.ENEMIGO_HEIGHT);
			colision = true;
		}
	}

	public void colisionBloques(Bloque b) {

		double xEnemigo = cuerpo.getTransformar().getPosicion().getX();
		double xB = b.getBloque().getTransformar().getPosicion().getX();

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.RIGHT) && xEnemigo <= xB) {
			cuerpo.getTransformar().getPosicion().setX(xB - Conf.ENEMIGO_WIDTH);
			colision = true;
		}

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.LEFT) && xEnemigo >= xB) {
			cuerpo.getTransformar().getPosicion().setX(xB + Conf.WOOD_LADO);
			colision = true;
		}

		double yEnemigo = cuerpo.getTransformar().getPosicion().getY();
		double yB = b.getBloque().getTransformar().getPosicion().getY();

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.DOWN) && yEnemigo <= yB) {
			cuerpo.getTransformar().getPosicion().setY(yB - Conf.ENEMIGO_HEIGHT);
			colision = true;
		}

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.UP) && yEnemigo >= yB) {
			cuerpo.getTransformar().getPosicion().setY(yB + Conf.WOOD_LADO);
			colision = true;
		}
	}

	public void recibirDano() {
		blanco.play();
		numeroVidas--;
	}

	public void actualizar() {
		cuerpo.getColisiona().actualizar();
		ataque.getColisiona().actualizar();
		atacar();
		movimiento();
		colisionPantalla();
		morir();
	}

	public abstract void movimiento();

	public abstract void atacar();

	public void morir() {
		if (numeroVidas == 0) {
			destruir();
			setViva(false);
			ataque.setViva(false);
		}
	}

	public SpriteMovible getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(SpriteMovible cuerpo) {
		this.cuerpo = cuerpo;
	}

	public Collider getColisiona() {
		return cuerpo.getColisiona();
	}

	public SpriteMovible getAtaque() {
		return ataque;
	}

	public void setAtaque(SpriteMovible ataque) {
		this.ataque = ataque;
	}

	public boolean isColision() {
		return colision;
	}

	public void setColision(boolean colision) {
		this.colision = colision;
	}

	public int getNumeroVidas() {
		return numeroVidas;
	}

	public void setNumeroVidas(int numeroVidas) {
		this.numeroVidas = numeroVidas;
	}

}
//{}
