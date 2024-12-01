package juego.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.Assets;
import juego.Game;
import motor_v1.motor.GameLoop;
import motor_v1.motor.component.Collider;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;

public class EnemigoEspada extends Enemigo {

	private double cronometroEsquivar = TIEMPO_CAMBIO_DIRECCION;
	private double cronometroMovimiento = TIEMPO_CAMBIO_DIRECCION;
	private double cronometroCambioSprite;
	private boolean colisionBloque = false;
	private int direccionActual;
	public static final int TIEMPO_CAMBIO_DIRECCION = 1500;
	public static final double VELOCIDAD = 2.5;
	Vector2D posicionJugador; 
	Bloque[] listaBloques;
	int min;
	int max;
	double diferenciaX;
	double diferenciaY;

	public EnemigoEspada(Vector2D posicion) {
		super("Enemigo", new SpriteMovible("Enemigo", Assets.enemigoEspadaManoIzquierda), posicion,
				new SpriteMovible("Hechizo", Assets.hechizo));
		posicionJugador = new Vector2D(0, 0);
		listaBloques = new Bloque[0];
		super.getAtaque().setVisible(false);
		super.getAtaque().setViva(false);
		super.setNumeroVidas(2);
	}

	public void actualizar() {
		super.actualizar();
	}

	@Override
	public void destruir() {
	}

	@Override
	public void dibujar(Graphics g) {
		super.getCuerpo().dibujar(g);
	}

	public void esquivarBloque() {
		double velocidadEsquive = VELOCIDAD * 2;
		double velocidadRetroceso = VELOCIDAD / 5;
		
		cronometroEsquivar += GameLoop.dt;

		if (cronometroEsquivar > TIEMPO_CAMBIO_DIRECCION) {
			direccionActual = MovimientoRandom();
			cronometroEsquivar = 0;
		}

		switch (direccionActual) {
		// Colision izquierda
		case 1:
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.UP);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadEsquive);
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.RIGHT);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadRetroceso);
			break;

		case 2:
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.DOWN);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadEsquive);
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.RIGHT);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadRetroceso);
			break;

		// Colision derecha
		case 3:
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.UP);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadEsquive);
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.LEFT);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadRetroceso);
			break;

		case 4:
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.DOWN);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadEsquive);
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.LEFT);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadRetroceso);
			break;

		// Colision arriba
		case 5:
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.LEFT);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadEsquive);
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.DOWN);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadRetroceso);
			break;

		case 6:
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.RIGHT);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadEsquive);
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.DOWN);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadRetroceso);
			break;

		// Colision abajo
		case 7:
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.LEFT);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadEsquive);
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.UP);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadRetroceso);
			break;

		case 8:
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.RIGHT);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadEsquive);
			super.getCuerpo().getMovimiento().setDireccion(Vector2D.UP);
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), velocidadRetroceso);
			break;
		}
	}

	public void moverEnemigo() {

		// Obtén la posición actual del enemigo
		Vector2D posicionEnemigo = super.getCuerpo().getTransformar().getPosicion();

		// Calculamos el ángulo hacia el jugador
		double angulo = posicionEnemigo.getAnguloHacia(posicionJugador);
		double desfase = 90;

		// Ajustamos la rotación del enemigo para que siempre mire hacia el jugador
		super.getCuerpo().getTransformar().rotarloA(angulo - desfase);

		if (colisionBloque) {
			cronometroMovimiento += GameLoop.dt;
			esquivarBloque();

			if (cronometroMovimiento > TIEMPO_CAMBIO_DIRECCION) {
				cronometroMovimiento = 0;
				colisionBloque = false;
			}

		} else {
			moverHaciaJugador(posicionJugador, listaBloques, posicionEnemigo);
			for (int i = 0; i < listaBloques.length; i++) {
				if (listaBloques[i] != null) {
					if (super.getCuerpo().getColisiona().colisionaCon(listaBloques[i].getColisiona())) {
						colisionBloque = true;

						if (super.getCuerpo().getMovimiento().getDireccion() == Vector2D.LEFT) {
							min = 1;
							max = 3;
						} else if (super.getCuerpo().getMovimiento().getDireccion() == Vector2D.RIGHT) {
							min = 3;
							max = 5;
						} else if (super.getCuerpo().getMovimiento().getDireccion() == Vector2D.UP) {
							min = 5;
							max = 7;
						} else if (super.getCuerpo().getMovimiento().getDireccion() == Vector2D.DOWN) {
							min = 7;
							max = 9;
						}
					}
				}
			}
			
		}
	}

	public void moverHaciaJugador(Vector2D posicionJugador, Bloque[] bloque, Vector2D posicion) {

		// Calcula las diferencias entre las posiciones del jugador y del enemigo
		diferenciaX = posicionJugador.getX() - posicion.getX();
		diferenciaY = posicionJugador.getY() - posicion.getY();

		// Calculamos la diferencia absoluta para determinar el eje dominante
		double absDiferenciaX;
		if (diferenciaX >= 0) {
			absDiferenciaX = diferenciaX;
		} else {
			absDiferenciaX = -diferenciaX;
		}

		double absDiferenciaY;
		if (diferenciaY >= 0) {
			absDiferenciaY = diferenciaY;
		} else {
			absDiferenciaY = -diferenciaY;
		}

		// Determina la dirección del movimiento basado en el eje de mayor diferencia
		if (absDiferenciaX > absDiferenciaY) {
			// Movimiento en el eje X
			if (diferenciaX > 0) {
				super.getCuerpo().getMovimiento().setDireccion(Vector2D.RIGHT); // Mover hacia la derecha
			} else {
				super.getCuerpo().getMovimiento().setDireccion(Vector2D.LEFT); // Mover hacia la izquierda
			}
		} else {
			// Movimiento en el eje Y
			if (diferenciaY > 0) {
				super.getCuerpo().getMovimiento().setDireccion(Vector2D.DOWN); // Mover hacia abajo
			} else {
				super.getCuerpo().getMovimiento().setDireccion(Vector2D.UP); // Mover hacia arriba
			}
		}

		atacar(); // El metodo atacar decidi si el enemigo debe atacar quieto o moverse
	}

	private int MovimientoRandom() {
		return (int) (Math.random() * (max - min) + min); // Retorna un número aleatorio entre 1 y 4
	}

	public void movimiento() {
		moverEnemigo();
	}

	@Override
	public void atacar() {
		double distancia = diferenciaX * (diferenciaX) + (diferenciaY * diferenciaY);

		double rangoAtaque = 4000;

		if (distancia < rangoAtaque) {
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), 0);
			if (super.getCuerpo().getTextura().equals(Assets.enemigoEspadaManoIzquierda) || super.getCuerpo().getTextura().equals(Assets.enemigoEspadaManoIzquierdaSlash)) {
				cronometroCambioSprite += GameLoop.dt;
				super.getCuerpo().setTextura(Assets.enemigoEspadaManoIzquierdaSlash);
				if (cronometroCambioSprite > 500) {
					super.getCuerpo().setTextura(Assets.enemigoEspadaManoDerecha);
					cronometroCambioSprite = 0;
				}
				
			} else if (super.getCuerpo().getTextura().equals(Assets.enemigoEspadaManoDerecha) || super.getCuerpo().getTextura().equals(Assets.enemigoEspadaManoDerechaSlash)) {
				cronometroCambioSprite += GameLoop.dt;
				super.getCuerpo().setTextura(Assets.enemigoEspadaManoDerechaSlash);
				if (cronometroCambioSprite > 500) {
					super.getCuerpo().setTextura(Assets.enemigoEspadaManoIzquierda);
					cronometroCambioSprite = 0;
				}
			}
		} else {
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), VELOCIDAD);
			if (super.getCuerpo().getTextura().equals(Assets.enemigoEspadaManoIzquierdaSlash)) {
				super.getCuerpo().setTextura(Assets.enemigoEspadaManoIzquierda);
			} else if (super.getCuerpo().getTextura().equals(Assets.enemigoEspadaManoDerechaSlash)) {
				super.getCuerpo().setTextura(Assets.enemigoEspadaManoDerecha);
			}
		}
	}

	public Vector2D getPosicionJugador() {
		return posicionJugador;
	}

	public void setPosicionJugador(Vector2D posicionJugador) {
		this.posicionJugador = posicionJugador;
	}

	public Bloque[] getListaBloques() {
		return listaBloques;
	}

	public void setListaBloques(Bloque[] listaBloque) {
		this.listaBloques = listaBloque;
	}
}
