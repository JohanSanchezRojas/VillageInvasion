package juego.escenas;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import juego.Assets;
import juego.Conf;
import juego.entidades.Bloque;
import juego.entidades.Enemigo;
import juego.entidades.EnemigoEspada;
import juego.entidades.EnemigoHechicero;
import juego.entidades.EnemigoProvisional;
import juego.entidades.Flecha;
import juego.entidades.Jugador;
import motor_v1.motor.GameLoop;
import motor_v1.motor.Scene;
import motor_v1.motor.component.Sound;
import motor_v1.motor.entidades.Gif;
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
	private SpriteSolido corazon;
	private SpriteText puntos;
	private SpriteText textoVidas;
	private Jugador jugador;
	private ListaEntidades listaBloques;
	private ListaEntidades listaEnemigos;
	private Gif romper;
	double cronometro = GameLoop.dt * 90;
	private double cronometroCambioSprite;
	private double cronometroPuntos = 0;
	EnemigoEspada enemigo3;
	private int cantidadEnemigos = 0;

	public EscenaNivel1() {
		super();
		romper = new Gif("Romper", Assets.romperFlecha, new Vector2D(0, 0), 100);
		romper.setVisible(false);
		jugador = new Jugador(new Vector2D(150, Conf.HEIGHT / 2), 10000);
		listaEnemigos = new ListaEntidades();
		listaBloques = new ListaEntidades();
		puntos = new SpriteText("100", new Color(50), Assets.font_minecraft, false);
		fondoNivel = new SpriteSolido("Fondo", Assets.fondoCesped);
		textoVidas = new SpriteText("x " + jugador.getNumeroVidas(), new Color(250, 250, 250), Assets.font_minecraft,
				false);
		textoVidas.setPosicion(new Vector2D(55, 35));
		corazon = new SpriteSolido("Corazon", Assets.corazon, new Vector2D(10, 10));
		crearBloques();
		crearEnemigos();
	}

	@Override
	public void actualizar() {
		listaBloques.actualizar();
		listaEnemigos.actualizar();
		jugador.actualizar();
		danoEnemigoAJugador();
		danarJugador();
		movimientoHaciaJugador();
		romper.actualizar();
		puntos.actualizar();
		colisionBloqueJugador();
		colisionFlechaJugador();
		colisionBloqueEnemigo();
		colisionEnemigoFlecha();
		comprobarNivelCompletado();
		if (InputKeyboard.isKeyPressed(Key.SHIFT)) {
			Scene.cambiarEscena(new EscenaBienvenida());
		}
	}

	@Override
	public void destruir() {
	}

	@Override
	public void dibujar(Graphics g) {
		fondoNivel.dibujar(g);
		listaBloques.dibujar(g);
		jugador.dibujar(g);
		listaEnemigos.dibujar(g);
		puntos.dibujar(g);
		romper.dibujar(g);
		textoVidas.dibujar(g);
		corazon.dibujar(g);
	}

	public void crearEnemigos() {
		EnemigoHechicero enemigo1 = new EnemigoHechicero(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 - 100));
		EnemigoHechicero enemigo2 = new EnemigoHechicero(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 + 100));
		EnemigoEspada enemigo3 = new EnemigoEspada(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 + 120));

		listaEnemigos.add("Enemigo", enemigo1);
		listaEnemigos.add("Enemigo", enemigo2);
		listaEnemigos.add("Enemigo", enemigo3);

		cantidadEnemigos = 3;
	}

	public void crearBloques() {
		Bloque bloque1 = new Bloque(new Vector2D(Conf.WIDTH / 2 - 50, Conf.HEIGHT / 2));
		Bloque bloque2 = new Bloque(new Vector2D(Conf.WIDTH / 2 - 50, Conf.HEIGHT / 2 + 80));
		Bloque bloque3 = new Bloque(new Vector2D(Conf.WIDTH / 2 - 50, Conf.HEIGHT / 2 - 80));
		Bloque bloque4 = new Bloque(new Vector2D(Conf.WIDTH / 2 - 50, Conf.HEIGHT / 2 + 160));
		Bloque bloque5 = new Bloque(new Vector2D(Conf.WIDTH / 2 - 50, Conf.HEIGHT / 2 - 160));

		listaBloques.add("Bloque", bloque1);
		listaBloques.add("Bloque", bloque2);
		listaBloques.add("Bloque", bloque3);
		listaBloques.add("Bloque", bloque4);
		listaBloques.add("Bloque", bloque5);
	}

	public void movimientoHaciaJugador() {
		for (int i = 0; i < listaEnemigos.getLength(); i++) {
			if (listaEnemigos.get(i) != null) {
				if (listaEnemigos.get(i) instanceof EnemigoEspada) {
					EnemigoEspada enemigoAux = (EnemigoEspada) listaEnemigos.get(i);
					Bloque[] listaBloquesAux = new Bloque[listaBloques.getSize()];
					for (int j = 0; j < listaBloques.getLength(); j++) {
						if (listaBloques.get(j) != null) {
							Bloque bloqueAux = (Bloque) listaBloques.get(j);
							listaBloquesAux[j] = bloqueAux;
						}
					}
					enemigoAux.setPosicionJugador(jugador.getCuerpo().getTransformar().getPosicion());
					enemigoAux.setListaBloques(listaBloquesAux);
				}
			}

		}
	}

	public void comprobarNivelCompletado() {
		int enemigosMuertos = 0;
		for (int i = 0; i < listaEnemigos.getLength(); i++) {
			if (listaEnemigos.get(i) != null) {
				if (listaEnemigos.get(i).getViva() == false) {
					enemigosMuertos++;
					if (enemigosMuertos == cantidadEnemigos) {
						System.out.println("Ganador");
						siguienteNivel();
					}
				}
			}
		}
	}

	public void colisionBloqueJugador() {
		for (int i = 0; i < listaBloques.getLength(); i++) {
			if (listaBloques.get(i) != null) {
				Bloque bloque = (Bloque) listaBloques.get(i);
				if (jugador.getColisiona().colisionaCon(bloque.getColisiona())) {
					jugador.colisionBloque(bloque);
				}
			}
		}
	}

	public void danoEnemigoAJugador() {
		for (int i = 0; i < listaEnemigos.getAll().length; i++) {
			if (listaEnemigos.get(i) != null && listaEnemigos.get(i) instanceof EnemigoHechicero) {
				EnemigoHechicero enemigoAux = (EnemigoHechicero) listaEnemigos.get(i);
				if (jugador.getColisiona().colisionaCon(enemigoAux.getHechizo().getColisiona()) && enemigoAux.getHechizo().getViva()) {
					cronometro += GameLoop.dt;
					danarJugador();
				}
			} else if (listaEnemigos.get(i) instanceof EnemigoEspada) {
				EnemigoEspada enemigoAux = (EnemigoEspada) listaEnemigos.get(i);
				if (jugador.getColisiona().colisionaCon(enemigoAux.getColisiona()) && enemigoAux.getViva() == true) {
					cronometro += GameLoop.dt;
					danarJugador();
				}
			}
		}

	}

	public void danarJugador() {

		if (cronometro > GameLoop.dt * 100 && jugador.getCuerpo().getTextura().equals(Assets.jugador)) {
			// Reducción de vida y activación del estado de invulnerabilidad
			jugador.getDano().play();
			jugador.setNumeroVidas(jugador.getNumeroVidas() - 1);
			textoVidas.setMensaje("x " + jugador.getNumeroVidas());

			// Cambio al sprite de invulnerabilidad
			jugador.getCuerpo().setTextura(Assets.jugadorInvencible);

			// Reinicia el cronómetro de invulnerabilidad
			cronometro = 0;

			// Activa el temporizador del cambio de sprite
			cronometroCambioSprite = 0; // Resetea el temporizador
			if (jugador.getNumeroVidas() <= 0) {
				Scene.cambiarEscena(new EscenaBienvenida());
			}

		}

		if (jugador.getCuerpo().getTextura().equals(Assets.jugadorInvencible)) {
			cronometroCambioSprite += GameLoop.dt;

			if (cronometroCambioSprite > GameLoop.dt * 100) {
				jugador.getDano().stop();
				jugador.getCuerpo().setTextura(Assets.jugador); // Vuelve al sprite normal
			}
		}

	}

	public void colisionBloqueEnemigo() {
		for (int i = 0; i < listaEnemigos.getAll().length; i++) {
			if (listaEnemigos.get(i) != null) {
				for (int j = 0; j < listaBloques.getLength(); j++) {
					if (listaBloques.get(j) != null) {
						Bloque bloque = (Bloque) listaBloques.get(j);
						Enemigo enemigoAux = (Enemigo) listaEnemigos.get(i);
						if (enemigoAux.getColisiona().colisionaCon(bloque.getColisiona())) {
							enemigoAux.colisionBloque(bloque);
						}
					}
				}
			}
		}
	}

	public void animacionRomperFlecha(Flecha f) {
		romper = new Gif("Romper", Assets.romperFlecha, f.getFlecha().getTransformar().getPosicion(), 200);
		romper.getTransformar().rotarloA(f.getFlecha().getTransformar().getRotacion());
		romper.setLoop(false);
	}

	public void colisionFlechaJugador() {
		for (int i = 0; i < jugador.listaFlechas.getAll().length; i++) {
			if (jugador.listaFlechas.get(i) != null) {
				Flecha flechaAux = (Flecha) jugador.listaFlechas.get(i);
				for (int j = 0; j < listaBloques.getLength(); j++) {
					if (listaBloques.get(j) != null) {
						Bloque bloque = (Bloque) listaBloques.get(j);
						if (flechaAux.getColisiona().colisionaCon(bloque.getColisiona())) {
							if (flechaAux.getViva()) {
								animacionRomperFlecha(flechaAux);
								flechaAux.destruir();
								System.out.println("Bloque");
							}
						}
					}
				}
			}
		}
	}

	public void colisionEnemigoFlecha() {
		for (int i = 0; i < listaEnemigos.getAll().length; i++) {
			if (listaEnemigos.get(i) != null) {
				for (int j = 0; j < jugador.listaFlechas.getAll().length; j++) {
					if (jugador.listaFlechas.get(j) != null) {
						Enemigo enemigoAux = (Enemigo) listaEnemigos.get(i);
						Flecha flechaAux = (Flecha) jugador.listaFlechas.get(j);
						if (enemigoAux.getColisiona().colisionaCon(flechaAux.getColisiona())) {
							if (enemigoAux.getViva() && flechaAux.getViva()) {
								animacionRomperFlecha(flechaAux);
								puntos.getTransformar()
										.setPosicion(enemigoAux.getCuerpo().getTransformar().getPosicion());
								puntos.setVisible(true);
								enemigoAux.recibirDano();
								flechaAux.destruir();
								flechaAux.setViva(false);
							}

							cronometroPuntos += GameLoop.dt;

							if (cronometroPuntos > GameLoop.dt * 35) {
								puntos.setVisible(false);
								cronometroPuntos = 0;
							}
						}
					}
				}
			}
		}
	}

	public void siguienteNivel() {
		Scene.cambiarEscena(new EscenaNivel2());
	}

}
