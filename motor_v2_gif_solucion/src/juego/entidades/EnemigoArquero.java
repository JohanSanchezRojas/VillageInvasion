package juego.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.Assets;
import juego.Conf;
import motor_v1.motor.GameLoop;
import motor_v1.motor.component.Movement;
import motor_v1.motor.component.Transform;
import motor_v1.motor.entidades.ListaEntidades;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.input.InputMouse;
import motor_v1.motor.util.Vector2D;

public class EnemigoArquero extends Enemigo {

	private double cronometroEsquivar = TIEMPO_CAMBIO_DIRECCION;
	private double cronometroMovimiento = TIEMPO_CAMBIO_DIRECCION;
	double cronometroDisparo = 1000;
	private boolean colisionBloque = false;
	private int direccionActual;
	public static final double TIEMPO_CAMBIO_DIRECCION = GameLoop.dt * 100;
	public static final double VELOCIDAD = GameLoop.dt * 0.15;
    private ListaEntidades listaFlechas;
    private Vector2D posicionJugador;
    Bloque[] listaBloques;
    int min;
	int max;
	double diferenciaX;
	double diferenciaY;

    public EnemigoArquero(Vector2D posicion) {
        super(new SpriteMovible("Arquero", Assets.enemigoArquero, posicion), posicion);
        this.listaFlechas = new ListaEntidades();
        posicionJugador = new Vector2D(0, 0);
        listaBloques = new Bloque[0];
        setNumeroVidas(3); 
    }
    
    public Vector2D getPosicionJugador() {
		return posicionJugador;
	}

	public void setPosicionJugador(Vector2D posicionJugador) {
		this.posicionJugador = posicionJugador;
	}


	

	public ListaEntidades getListaFlechas() {
		return listaFlechas;
	}

	public void setListaFlechas(ListaEntidades listaFlechas) {
		this.listaFlechas = listaFlechas;
	}

	public Bloque[] getListaBloques() {
		return listaBloques;
	}

	public void setListaBloques(Bloque[] listaBloques) {
		this.listaBloques = listaBloques;
	}

	@Override
	public void actualizar() {
		super.actualizar();
		listaFlechas.actualizar();
	}

	@Override
	public void dibujar(Graphics g) {
		super.getCuerpo().dibujar(g);
		listaFlechas.dibujar(g);
	}

    @Override
    public void movimiento() {
    	Vector2D posicionEnemigo = super.getCuerpo().getTransformar().getPosicion();
		double angulo = posicionEnemigo.getAnguloHacia(posicionJugador);
		double desfase = 270;
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

    @Override
    public void atacar() {
    	cronometroDisparo += GameLoop.dt;


		// Permite disparar si ha pasado suficiente tiempo entre un disparo y otro
		if (cronometroDisparo > GameLoop.dt * 80) {
			cronometroDisparo = 0;
//			disparo.play();

			Vector2D centro = super.getCuerpo().getTransformar().getPosicion();
			double angulo = centro.getAnguloHacia(posicionJugador);
			double desfase = 90;

			Flecha flecha = new Flecha(centro);

			flecha.getFlecha().getTransformar().rotarloA(angulo + desfase);
			flecha.getFlecha().getMovimiento().setDireccion((int) angulo);
			flecha.getFlecha().getTransformar().setPosicion(centro);

			listaFlechas.add("Flecha", flecha);
		}

		// Actualiza las flechas para que no se queden quietas
		for (int i = 0; i < listaFlechas.getLength(); i++) {
			if (listaFlechas.get(i) != null) {
				Flecha flechaAux = (Flecha) listaFlechas.get(i);

				if (flechaAux.getViva()) {
					flechaAux.getFlecha().getMovimiento().mover(flechaAux.getFlecha().getTransformar(),
							0.5 * GameLoop.dt);
					flechaAux.colisionPantalla();
				}
			}
		}
    			
    }
    
    public void calcularDistancia() {
    	double distancia = (diferenciaX * diferenciaX) + (diferenciaY * diferenciaY);

		double rangoAtaque = 3500;

		// Si el jugador esta en su rango de ataque el enemigo empieza la animacion de
		// ataque y se queda quieto
		if (distancia < rangoAtaque) {
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), 0);
			
		} else {
			super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), VELOCIDAD);
		}
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

		
		double distancia = (diferenciaX * diferenciaX) + (diferenciaY * diferenciaY);

		double rangoAtaque = 100000;

		// Si el jugador esta en su rango de ataque el enemigo empieza la animacion de
		// ataque y se queda quieto
		if (distancia < rangoAtaque) {
			if (TotaldiferenciaX < totalDiferenciaY) {
				if (diferenciaX < 0) {
					super.getCuerpo().getMovimiento().setDireccion(Vector2D.RIGHT); 
				} else {
					super.getCuerpo().getMovimiento().setDireccion(Vector2D.LEFT); 
				}
			} else {
				if (diferenciaY < 0) {
					super.getCuerpo().getMovimiento().setDireccion(Vector2D.DOWN); 
				} else {
					super.getCuerpo().getMovimiento().setDireccion(Vector2D.UP); 
				}
			}
		} else {
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
		}
		
		// Decide en que direccion ir en base a que distancia es mas larga
		

		calcularDistancia(); // El metodo atacar decide si el enemigo debe atacar quieto o moverse
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
	public void destruir() {
		// TODO Auto-generated method stub
		
	}
}
