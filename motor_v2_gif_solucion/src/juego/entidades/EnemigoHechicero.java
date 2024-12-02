package juego.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import juego.Assets;
import juego.Conf;
import juego.escenas.EscenaBienvenida;
import motor_v1.motor.GameLoop;
import motor_v1.motor.Scene;
import motor_v1.motor.component.Collider;
import motor_v1.motor.component.Sound;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.util.Vector2D;

public class EnemigoHechicero extends Enemigo {

	private double cronometroMovimiento;
	private double cronometroAtaque;
	private double cronometroCancelarAtaque;
	private boolean atacar;
	private Sound sonidoHechizo;
	private boolean cancelarAtaque;
	private int direccionActual; // Direccion que sigue el enemigo actualmente
	public static final double TIEMPO_CAMBIO_DIRECCION = GameLoop.dt * 130; // Tiempo entre cambios de dirección
	private Hechizo hechizo;

	public EnemigoHechicero(Vector2D posicion) {
		super(new SpriteMovible("Enemigo", Assets.enemigoHechicero), posicion);
		super.setNumeroVidas(3);
		hechizo = new Hechizo(new Vector2D(0, 0));
		hechizo.desparecer();
		sonidoHechizo = new Sound(Assets.sonidoHechizo);
		sonidoHechizo.changeVolume(50);
		direccionActual = MovimientoRandom(); // Dirección inicial aleatoria
		atacar = false;
		cancelarAtaque = true;
	}

	public void actualizar() {
		hechizo.actualizar();
		super.actualizar();
	}

	@Override
	public void dibujar(Graphics g) {
		hechizo.dibujar(g);
		super.getCuerpo().dibujar(g);
	}

	@Override
	public void destruir() {
	}
	
	public Hechizo getHechizo() {
		return hechizo;
	}

	public void setHechizo(Hechizo hechizo) {
		this.hechizo = hechizo;
	}

	public Collider getColisiona() {
		return cuerpo.getColisiona();
	}

	// Se mueve en una direccion aleatoria cada cierto tiempo o al chocar contra un
	// bloque o el borde de la pantalla
	public void movimiento() {
		Vector2D posicionHechizo;
		cronometroMovimiento += GameLoop.dt;

		if (cronometroMovimiento > TIEMPO_CAMBIO_DIRECCION || super.isColision()) {
			direccionActual = MovimientoRandom();
			cronometroMovimiento = 0;
			super.setColision(false);
		}

		switch (direccionActual) {
		case 1: // Arriba
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.UP);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(),
					Conf.ENEMIGOH_VELOCIDAD * GameLoop.dt);
			posicionHechizo = new Vector2D(super.getCuerpo().getTransformar().getPosicion().getX() + 13,
					super.getCuerpo().getTransformar().getPosicion().getY() - 400);
			hechizo.getHechizo().getTransformar().rotarloA(0);
			hechizo.getHechizo().getTransformar().setPosicion(posicionHechizo);
			super.getCuerpo().getTransformar().rotarloA(0);
			break;
		case 2: // Abajo
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.DOWN);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(),
					Conf.ENEMIGOH_VELOCIDAD * GameLoop.dt);
			posicionHechizo = new Vector2D(super.getCuerpo().getTransformar().getPosicion().getX() + 10,
					super.getCuerpo().getTransformar().getPosicion().getY() + 40);
			hechizo.getHechizo().getTransformar().rotarloA(0);
			hechizo.getHechizo().getTransformar().setPosicion(posicionHechizo);
			super.getCuerpo().getTransformar().rotarloA(180);
			break;
		case 3: // Izquierda
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.LEFT);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(),
					Conf.ENEMIGOH_VELOCIDAD * GameLoop.dt);
			posicionHechizo = new Vector2D(super.getCuerpo().getTransformar().getPosicion().getX() - 210,
					super.getCuerpo().getTransformar().getPosicion().getY() - 175);
			hechizo.getHechizo().getTransformar().rotarloA(90);
			hechizo.getHechizo().getTransformar().setPosicion(posicionHechizo);
			super.getCuerpo().getTransformar().rotarloA(270);
			break;
		case 4: // Derecha
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.RIGHT);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(),
					Conf.ENEMIGOH_VELOCIDAD * GameLoop.dt);
			posicionHechizo = new Vector2D(super.getCuerpo().getTransformar().getPosicion().getX() + 230,
					super.getCuerpo().getTransformar().getPosicion().getY() - 175);
			hechizo.getHechizo().getTransformar().rotarloA(90);
			hechizo.getHechizo().getTransformar().setPosicion(posicionHechizo);
			super.getCuerpo().getTransformar().rotarloA(90);
			break;
		}
	}

	private int MovimientoRandom() {
		return (int) (Math.random() * (5 - 1) + 1);
	}

	// Lanza un ataque de manera aleatoria cada vez
	@Override
	public void atacar() {
		if (atacar) {
			cronometroAtaque += GameLoop.dt;
			if (cronometroAtaque > GameLoop.dt * 150) {
				
				sonidoHechizo.play();
				cronometroCancelarAtaque = 0;
				atacar = false;
				cancelarAtaque = true;
				hechizo.aparecer();
			}
		}

		if (cancelarAtaque) {
			cronometroCancelarAtaque += GameLoop.dt;
			if (cronometroCancelarAtaque > GameLoop.dt * 50) {
				
				sonidoHechizo.stop();
				cronometroAtaque = GameLoop.dt * (Math.random() * (10 - 1));
				cancelarAtaque = false;
				atacar = true;
				hechizo.desparecer();
				
			}
		}
	}
}