package juego.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import juego.Assets;
import motor_v1.motor.GameLoop;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.util.Vector2D;

public class EnemigoEspada extends Enemigo {

	private double cronometro;
	public static final int TIEMPO_DE_ESPERA = 1000;
    private int direccionActual; // Dirección que sigue el enemigo actualmente
    public static final int TIEMPO_CAMBIO_DIRECCION = 1000; // Tiempo entre cambios de dirección (en ms)
    
	public EnemigoEspada(Vector2D posicion) {
		super("", Assets.enemigoEDown, posicion);

		cuerpo = new SpriteMovible("Cuerpo", Assets.jugador, posicion);
		direccionActual = MovimientoRandom(); // Dirección inicial aleatoria
	}

	public void actualizar() {
		super.actualizar();
		MovimientoEnemigo();
		cuerpo.actualizar();
	}

	public void MovimientoEnemigo() {
		cronometro += GameLoop.dt;
		
		 // Cambiar dirección si se supera el tiempo especificado
        if (cronometro > TIEMPO_CAMBIO_DIRECCION) {
            direccionActual = MovimientoRandom();
            cronometro = 0;
        }

        // Moverse en la dirección actual
        switch (direccionActual) {
            case 1: // Arriba
                cuerpo.getMovimiento().setDireccion(Vector2D.UP);
                cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
                cuerpo.setTextura(Assets.enemigoEUp);
                break;
            case 2: // Abajo
                cuerpo.getMovimiento().setDireccion(Vector2D.DOWN);
                cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
                cuerpo.setTextura(Assets.enemigoEDown);
                break;
            case 3: // Izquierda
                cuerpo.getMovimiento().setDireccion(Vector2D.LEFT);
                cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
                cuerpo.setTextura(Assets.enemigoELeft);
                break;
            case 4: // Derecha
                cuerpo.getMovimiento().setDireccion(Vector2D.RIGHT);
                cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
                cuerpo.setTextura(Assets.enemigoERight);
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
		cuerpo.dibujar(g);

	}

	private int MovimientoRandom() {
        int min = 1; // Mínimo: dirección arriba
        int max = 5; // Máximo: dirección derecha (4 direcciones posibles)
        return (int) (Math.random() * (max - min) + min); // Retorna un número aleatorio entre 1 y 4
    }

}
