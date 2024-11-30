package juego.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import juego.Assets;
import juego.escenas.EscenaBienvenida;
import motor_v1.motor.GameLoop;
import motor_v1.motor.Scene;
import motor_v1.motor.component.Sound;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.util.Vector2D;

public class EnemigoHechicero extends Enemigo {

	private double cronometroMovimiento;
	private double cronometroAtaque;
	private double cronometroCancelarAtaque;
	private boolean atacar = true;
	private Sound sonidoHechizo;
	private boolean cancelarAtaque;
	private int direccionActual; // Direccion que sigue el enemigo actualmente
	public static final int TIEMPO_CAMBIO_DIRECCION = 1000; // Tiempo entre cambios de dirección

	public EnemigoHechicero(Vector2D posicion) {
		super("Enemigo", new SpriteMovible("Enemigo", Assets.enemigoHechicero), posicion ,new SpriteMovible("Hechizo", Assets.hechizo));
		super.getAtaque().setVisible(false);
		super.getAtaque().setViva(false);
		super.setNumeroVidas(3);
		sonidoHechizo = new Sound(Assets.sonidoHechizo);
		sonidoHechizo.changeVolume(50);

		direccionActual = MovimientoRandom(); // Dirección inicial aleatoria
	}

	public void actualizar() {
		super.actualizar();
	}
	

	@Override
	public void atacar() {
		if (atacar) {
			cronometroAtaque += GameLoop.dt;

			if (cronometroAtaque > 2000) {
				sonidoHechizo.play();
				super.getAtaque().setViva(true);
				super.getAtaque().setVisible(true);

				cronometroCancelarAtaque = 0;
				atacar = false;
				cancelarAtaque = true;

			}
		}

		if (cancelarAtaque) {
			cronometroCancelarAtaque += GameLoop.dt;

			if (cronometroCancelarAtaque > 500) {
				sonidoHechizo.stop();
				cronometroAtaque = Math.random() * (500 - 1);
				
				cancelarAtaque = false;
				atacar = true;
				super.getAtaque().setViva(false);
				super.getAtaque().setVisible(false);
			}
		}
	}

	public void movimiento() {
		Vector2D posicionHechizo;
		cronometroMovimiento += GameLoop.dt;

		// Cambiar dirección si se supera el tiempo especificado
		if (cronometroMovimiento > TIEMPO_CAMBIO_DIRECCION || super.isColision()) {
			direccionActual = MovimientoRandom();
			cronometroMovimiento = 0;
			super.setColision(false);
		}

		// Moverse en la dirección actual
		switch (direccionActual) {
		case 1: // Arriba
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.UP);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), super.getCuerpo().FACTOR_VELOCIDAD * GameLoop.dt);
			super.getCuerpo().getTransformar().rotarloA(0);
			posicionHechizo = new Vector2D(super.getCuerpo().getTransformar().getPosicion().getX() + 13,
					super.getCuerpo().getTransformar().getPosicion().getY() - 400);
			super.getAtaque().getTransformar().rotarloA(0);
			super.getAtaque().getTransformar().setPosicion(posicionHechizo);
			break;
		case 2: // Abajo
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.DOWN);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), super.getCuerpo().FACTOR_VELOCIDAD * GameLoop.dt);
			super.getCuerpo().getTransformar().rotarloA(180);
			posicionHechizo = new Vector2D(super.getCuerpo().getTransformar().getPosicion().getX() + 10,
					super.getCuerpo().getTransformar().getPosicion().getY() + 40);
			super.getAtaque().getTransformar().rotarloA(0);
			super.getAtaque().getTransformar().setPosicion(posicionHechizo);
			break;
		case 3: // Izquierda
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.LEFT);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), super.getCuerpo().FACTOR_VELOCIDAD * GameLoop.dt);
			super.getCuerpo().getTransformar().rotarloA(270);
			posicionHechizo = new Vector2D(super.getCuerpo().getTransformar().getPosicion().getX() - 210,
					super.getCuerpo().getTransformar().getPosicion().getY() - 175);
			super.getAtaque().getTransformar().rotarloA(90);
			super.getAtaque().getTransformar().setPosicion(posicionHechizo);
			break;
		case 4: // Derecha
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.RIGHT);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), super.getCuerpo().FACTOR_VELOCIDAD * GameLoop.dt);
			super.getCuerpo().getTransformar().rotarloA(90);
			posicionHechizo = new Vector2D(super.getCuerpo().getTransformar().getPosicion().getX() + 230,
					super.getCuerpo().getTransformar().getPosicion().getY() - 175);
			super.getAtaque().getTransformar().rotarloA(90);
			super.getAtaque().getTransformar().setPosicion(posicionHechizo);
			break;
		}

//		if (InputKeyboard.isDown(motor_v1.motor.input.Key.UP)) {
//			cuerpo.getMovimiento().setDireccion(Vector2D.UP);
//			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
//		} else if (InputKeyboard.isDown(motor_v1.motor.input.Key.LEFT)) {
//			cuerpo.getMovimiento().setDireccion(Vector2D.LEFT);
//			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
//		} else if (InputKeyboard.isDown(motor_v1.motor.input.Key.DOWN)) {
//			cuerpo.getMovimiento().setDireccion(Vector2D.DOWN);
//			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
//		} else if (InputKeyboard.isDown(motor_v1.motor.input.Key.RIGHT)) {
//			cuerpo.getMovimiento().setDireccion(Vector2D.RIGHT);
//			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
//		}
	}

	@Override
	public void destruir() {
	}

	@Override
	public void dibujar(Graphics g) {
		super.getCuerpo().dibujar(g);
		super.getAtaque().dibujar(g);
	}

	private int MovimientoRandom() {
		int min = 1; // Mínimo: dirección arriba
		int max = 5; // Máximo: dirección derecha (4 direcciones posibles)
		return (int) (Math.random() * (max - min) + min); // Retorna un número aleatorio entre 1 y 4
	}

}