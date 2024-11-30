package juego.escenas;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import juego.Assets;
import juego.Conf;
import juego.entidades.Bloque;
import juego.entidades.EnemigoEspada;
import juego.entidades.EnemigoProvisional;
import juego.entidades.Flecha;
import juego.entidades.Jugador;
import motor_v1.motor.GameLoop;
import motor_v1.motor.Scene;
import motor_v1.motor.component.Sound;
import motor_v1.motor.entidades.ListaEntidades;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.entidades.SpriteText;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.InputMouse;
import motor_v1.motor.input.Key;
import motor_v1.motor.util.Vector2D;

public class EscenaNivel1 extends Scene {

	private SpriteSolido fondoNivel;
	private SpriteText puntos;
	private Jugador jugador;
	private Bloque[] bloques = new Bloque[5];
	private ListaEntidades enemigos;
	private EnemigoEspada enemigoB;
	private EnemigoEspada enemigoB2;
	double cronometro = 1200;
	private double tiempoCambioSprite = 0;
	private boolean cambiandoSprite = false;
	private Sound dano;
	private int cantidadEnemigos = 0;
	
	public EscenaNivel1() {
		super();
		enemigos = new ListaEntidades();
		dano = new Sound(Assets.sonidoDano);
		puntos = new SpriteText("100", new Color(50), Assets.font_minecraft, false);
		Wallpaper();
		crearJugador();
		crearBloques();
		crearEnemigos();
	}

	@Override
	public void actualizar() {
		if (InputKeyboard.isKeyPressed(Key.SHIFT)) {
			Scene.cambiarEscena(new EscenaBienvenida());
		}

		fondoNivel.actualizar();
		jugador.actualizar();
		enemigos.actualizar();
		puntos.actualizar();
		actualizarBloques();
		colisionBloqueJugador();
		colisionFlechaJugador();
		colisionBloqueEnemigo();
		colisionEnemigoFlecha();
		colisionJugadorEnemigo();
		comprobarNivelCompletado();

	}

	@Override
	public void destruir() {

	}

	@Override
	public void dibujar(Graphics arg0) {
		fondoNivel.dibujar(arg0);
		jugador.dibujar(arg0);
		jugador.getMira().dibujar(arg0);
		jugador.getCorazon().dibujar(arg0);
		jugador.getTextoVidas().dibujar(arg0);
		enemigos.dibujar(arg0);
		puntos.dibujar(arg0);
		dibujarBloques(arg0);
	}

	public void Wallpaper() {
		fondoNivel = new SpriteSolido("Fondo", Assets.fondoCesped);

	}

	public void crearJugador() {
		Vector2D p = new Vector2D(150, Conf.HEIGHT / 2);
		jugador = new Jugador(p, 10);
	}

	public void crearEnemigos() {
		enemigoB = new EnemigoEspada(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 - 100));
		enemigoB2 = new EnemigoEspada(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 + 100));
		
		cantidadEnemigos = 2;
		
		enemigos.add("Enemigo", enemigoB);
		enemigos.add("Enemigo", enemigoB2);
	}

	public void crearBloques() {
		Bloque bloque;
		int y = (Conf.HEIGHT / 2) - 160;

		for (int i = 0; i < bloques.length; i++) {

			Vector2D p = new Vector2D(Conf.WIDTH / 2, y);
			bloque = new Bloque("madera" + i, Assets.madera, p);
			bloque.getTransformar().setPosicion(p.subtract(bloque.getCentroRotacion()));

			bloques[i] = bloque;

			y = y + 80;
		}

	}

	public void comprobarNivelCompletado() {
		int enemigosMuertos = 0;
		for (int i = 0; i < enemigos.getLength(); i++) {
			if (enemigos.get(i).getViva() == false) {
				enemigosMuertos++;
				if (enemigosMuertos == cantidadEnemigos) {
					System.out.println("Ganador");
					siguienteNivel();
				}
			}
			
		}
	}
	
	public void colisionBloqueJugador() {
		for (int i = 0; i < bloques.length; i++) {
			if (bloques[i] != null) {
				if (jugador.getColisiona().colisionaCon(bloques[i].getColisiona())) {
					jugador.jugadorColision(bloques[i]);

				}

			}

		}

	}

	public void colisionJugadorEnemigo() {
		for (int i = 0; i < enemigos.getAll().length; i++) {
			if (enemigos.get(i) != null) {
				EnemigoEspada enemigoAux = (EnemigoEspada) enemigos.get(i);
				if (jugador.getColisiona().colisionaCon(enemigoAux.getColisiona()) || jugador.getColisiona().colisionaCon(enemigoAux.getAtaque().getColisiona())) {
					if (enemigoAux.getViva()) {
						cronometro += GameLoop.dt;
						if (cronometro > 1200) {
							// Reducción de vida y activación del estado de invulnerabilidad
							System.out.println("Dano");
							dano.play();
							jugador.setNumeroVidas(jugador.getNumeroVidas() - 1);
							jugador.getTextoVidas().setMensaje("x " + jugador.getNumeroVidas());

							// Cambio al sprite de invulnerabilidad
							jugador.getCuerpo().setTextura(Assets.jugadorInvencible);

							// Reinicia el cronómetro de invulnerabilidad
							cronometro = 0;

							// Activa el temporizador del cambio de sprite
							cambiandoSprite = true;
							tiempoCambioSprite = 0; // Resetea el temporizador
							if (jugador.getNumeroVidas() <= 0) {
								Scene.cambiarEscena(new EscenaBienvenida());
							}
						}
					}
					
					if (enemigoAux.getAtaque().getViva()) {
						cronometro += GameLoop.dt;
						if (cronometro > 1200) {
							// Reducción de vida y activación del estado de invulnerabilidad
							System.out.println("Dano");
							dano.play();
							jugador.setNumeroVidas(jugador.getNumeroVidas() - 1);
							jugador.getTextoVidas().setMensaje("x " + jugador.getNumeroVidas());

							// Cambio al sprite de invulnerabilidad
							jugador.getCuerpo().setTextura(Assets.jugadorInvencible);

							// Reinicia el cronómetro de invulnerabilidad
							cronometro = 0;

							// Activa el temporizador del cambio de sprite
							cambiandoSprite = true;
							tiempoCambioSprite = 0; // Resetea el temporizador
							if (jugador.getNumeroVidas() <= 0) {
								Scene.cambiarEscena(new EscenaBienvenida());
							}
						}
					}
				}
			}
		}
		
		if (cambiandoSprite) {
	        tiempoCambioSprite += GameLoop.dt;

	        if (tiempoCambioSprite > 1000) { 
	        	dano.stop();
	            jugador.getCuerpo().setTextura(Assets.jugador); // Vuelve al sprite normal
	            cambiandoSprite = false; // Desactiva el cambio de sprite
	        }
	    }
	}

	public void colisionBloqueEnemigo() {
		for (int i = 0; i < enemigos.getAll().length; i++) {
			if (enemigos.get(i) != null) {
				for (int j = 0; j < bloques.length; j++) {
					if (bloques[i] != null) {
						EnemigoEspada enemigoAux = (EnemigoEspada) enemigos.get(i);
						if (enemigoAux.getColisiona().colisionaCon(bloques[j].getColisiona())) {
							enemigoAux.colisionBloques(bloques[i]);
						}
					}
				}
			}
		}
	}

	public void colisionFlechaJugador() {
		for (int i = 0; i < jugador.flechas.getAll().length; i++) {
			if (jugador.flechas.get(i) != null) {
				Flecha flechaAux = (Flecha) jugador.flechas.get(i);
				for (int j = 0; j < bloques.length; j++) {
					if (bloques[j] != null) {
						if (flechaAux.getColisiona().colisionaCon(bloques[j].getColisiona())) {
							if (flechaAux.getViva()) {
								jugador.flechaColision(bloques[j], flechaAux);
								System.out.println("Bloque");
							}
						}
					}
				}
			}
		}
	}

	public void colisionEnemigoFlecha() {
		for (int i = 0; i < enemigos.getAll().length; i++) {
			if (enemigos.get(i) != null) {
				for (int j = 0; j < jugador.flechas.getAll().length; j++) {
					if (jugador.flechas.get(j) != null) {
						EnemigoEspada enemigoAux = (EnemigoEspada) enemigos.get(i);
						Flecha flechaAux = (Flecha) jugador.flechas.get(j);
						if (enemigoAux.getColisiona().colisionaCon(flechaAux.getColisiona())) {
							if (enemigoAux.getViva() && flechaAux.getViva()) {
								puntos.getTransformar().setPosicion(enemigoAux.getCuerpo().getTransformar().getPosicion());
								enemigoAux.enemigoFlechaColision();
								flechaAux.destruir();
								flechaAux.setViva(false);
							}
						}
					}
				}
			}
		}
	}

	public void dibujarBloques(Graphics arg0) {
		for (int i = 0; i < bloques.length; i++) {
			if (bloques[i] != null) {
				bloques[i].dibujar(arg0);

			}

		}

	}

	public void actualizarBloques() {
		for (int i = 0; i < bloques.length; i++) {
			if (bloques[i] != null) {
				bloques[i].actualizar();
				;

			}

		}

	}

	public void siguienteNivel() {
			Scene.cambiarEscena(new EscenaNivel2());
	}

}
