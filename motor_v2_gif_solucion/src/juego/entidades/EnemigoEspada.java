package juego.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.Assets;
import juego.Game;
import motor_v1.motor.GameLoop;
import motor_v1.motor.component.Collider;
import motor_v1.motor.component.Sound;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;

public class EnemigoEspada extends Enemigo {

	private double cronometroEsquivar = TIEMPO_CAMBIO_DIRECCION;
	private double cronometroMovimiento = TIEMPO_CAMBIO_DIRECCION;
	private double cronometroCambioSprite;
	private boolean colisionBloque = false;
	private int direccionActual;
	public static final double TIEMPO_CAMBIO_DIRECCION = GameLoop.dt * 70;
	public static final double VELOCIDAD = GameLoop.dt * 0.15;
	Sound espada;
	Vector2D posicionJugador;
	Bloque[] listaBloques;
	int min;
	int max;
	double diferenciaX;
	double diferenciaY;

	public EnemigoEspada(Vector2D posicion) {
		super(new SpriteMovible("Enemigo", Assets.enemigoEspadaManoIzquierda), posicion);
		posicionJugador = new Vector2D(0, 0);
		listaBloques = new Bloque[0];
		espada = new Sound(Assets.sonidoEspada);
		espada.changeVolume(70);
		super.setNumeroVidas(2);
	}

	@Override
	public void actualizar() {
		super.actualizar();
	}

	@Override
	public void dibujar(Graphics g) {
		super.getCuerpo().dibujar(g);
	}
	
	@Override
	public void destruir() {
	}

	public void movimiento() {
		Vector2D posicionEnemigo = super.getCuerpo().getTransformar().getPosicion();
		double angulo = posicionEnemigo.getAnguloHacia(posicionJugador);
		double desfase = 90;
		super.getCuerpo().getTransformar().rotarloA(angulo - desfase);

		// Si se colisiona contra un bloque utiliza esquivarBloque() para tratar de
		// esquivarlo
		if (colisionBloque) {
			cronometroMovimiento += GameLoop.dt;
			esquivarBloque();

			if (cronometroMovimiento > TIEMPO_CAMBIO_DIRECCION) {
				cronometroMovimiento = 0;
				colisionBloque = false;
			}

		} else {
			moverHaciaJugador(posicionJugador, posicionEnemigo);
			for (int i = 0; i < listaBloques.length; i++) {
				if (listaBloques[i] != null) {
					// Si colisiona contra un bloque decide aleatoriamente entre las dos mejores
					// direcciones para esquivar el bloque en base a su direccion
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

	public void moverHaciaJugador(Vector2D posicionJugador, Vector2D posicion) {

		// Calcula las distancias el jugador y el enemigo
		diferenciaX = posicionJugador.getX() - posicion.getX();
		diferenciaY = posicionJugador.getY() - posicion.getY();

		// Calcula que distancia es mayor
		double TotaldiferenciaX;
		if (diferenciaX >= 0) {
			TotaldiferenciaX = diferenciaX;
		} else {
			TotaldiferenciaX = -diferenciaX;
		}

		double totalDiferenciaY;
		if (diferenciaY >= 0) {
			totalDiferenciaY = diferenciaY;
		} else {
			totalDiferenciaY = -diferenciaY;
		}

		// Decide en que direccion ir en base a que distancia es mas larga
		if (TotaldiferenciaX > totalDiferenciaY) {
			if (diferenciaX > 0) {
				super.getCuerpo().getMovimiento().setDireccion(Vector2D.RIGHT); 
			} else {
				super.getCuerpo().getMovimiento().setDireccion(Vector2D.LEFT); 
			}
		} else {
			if (diferenciaY > 0) {
				super.getCuerpo().getMovimiento().setDireccion(Vector2D.DOWN); 
			} else {
				super.getCuerpo().getMovimiento().setDireccion(Vector2D.UP); 
			}
		}

		atacar(); // El metodo atacar decide si el enemigo debe atacar quieto o moverse
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

	private int MovimientoRandom() {
		return (int) (Math.random() * (max - min) + min);
	}
	
	@Override
	public void atacar() {
		// Calcula que tan lejos estan el enemigo y el jugador
		double distancia = (diferenciaX * diferenciaX) + (diferenciaY * diferenciaY);

		double rangoAtaque = 3500;

		// Si el jugador esta en su rango de ataque el enemigo empieza la animacion de
		// ataque y se queda quieto
		if (distancia < rangoAtaque) {
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), 0);
			if (super.getCuerpo().getTextura().equals(Assets.enemigoEspadaManoIzquierda)
					|| super.getCuerpo().getTextura().equals(Assets.enemigoEspadaManoIzquierdaSlash)) {
				cronometroCambioSprite += GameLoop.dt;
				super.getCuerpo().setTextura(Assets.enemigoEspadaManoDerechaSlash);
				if (cronometroCambioSprite > GameLoop.dt * 30) {
					espada.play();
					super.getCuerpo().setTextura(Assets.enemigoEspadaManoIzquierdaSlash);
					cronometroCambioSprite = 0;
				}

			} else if (super.getCuerpo().getTextura().equals(Assets.enemigoEspadaManoDerecha)
					|| super.getCuerpo().getTextura().equals(Assets.enemigoEspadaManoDerechaSlash)) {
				cronometroCambioSprite += GameLoop.dt;
				super.getCuerpo().setTextura(Assets.enemigoEspadaManoIzquierdaSlash);
				if (cronometroCambioSprite > GameLoop.dt * 30) {
					espada.play();
					super.getCuerpo().setTextura(Assets.enemigoEspadaManoDerechaSlash);
					cronometroCambioSprite = 0;
				}
			}
			// Si el jugador no esta en su rango de ataque detiene su animacion y camina
			// hacia el jugador
		} else {
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), VELOCIDAD);
			if (super.getCuerpo().getTextura().equals(Assets.enemigoEspadaManoIzquierdaSlash)) {
				super.getCuerpo().setTextura(Assets.enemigoEspadaManoIzquierda);
			} else if (super.getCuerpo().getTextura().equals(Assets.enemigoEspadaManoDerechaSlash)) {
				super.getCuerpo().setTextura(Assets.enemigoEspadaManoDerecha);
			}
		}
	}
}