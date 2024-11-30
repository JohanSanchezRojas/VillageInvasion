package juego.escenas;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import juego.Assets;
import juego.Conf;
import juego.entidades.Bloque;
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
	double cronometro = 1200;
	private double cronometroCambioSprite;
	private double cronometroPuntos = 0;
	private boolean cambiandoSprite = false;
	
	private int cantidadEnemigos = 0;
	
	public EscenaNivel1() {
		super();
		romper = new Gif("Romper", Assets.romperFlecha, new Vector2D(0, 0), 100);
		romper.setVisible(false);
		jugador = new Jugador(new Vector2D(150, Conf.HEIGHT / 2), 10);
		listaEnemigos = new ListaEntidades();
		listaBloques = new ListaEntidades();
		puntos = new SpriteText("100", new Color(50), Assets.font_minecraft, false);
		fondoNivel = new SpriteSolido("Fondo", Assets.fondoCesped);
		textoVidas = new SpriteText("x " + jugador.getNumeroVidas(), new Color(250, 250, 250), Assets.font_minecraft, false);
		textoVidas.setPosicion(new Vector2D(55, 35));
		corazon = new SpriteSolido("Corazon", Assets.corazon, new Vector2D(10, 10));
		crearBloques();
		crearEnemigos();
	}

	@Override
	public void actualizar() {
		if (InputKeyboard.isKeyPressed(Key.SHIFT)) {
			Scene.cambiarEscena(new EscenaBienvenida());
		}
		
		fondoNivel.actualizar();
		corazon.actualizar();
		jugador.actualizar();
		listaBloques.actualizar();
		listaEnemigos.actualizar();
		puntos.actualizar();
		textoVidas.actualizar();
		romper.actualizar();
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
	public void dibujar(Graphics g) {
		fondoNivel.dibujar(g);
		jugador.dibujar(g);
		listaBloques.dibujar(g);
		listaEnemigos.dibujar(g);
		textoVidas.dibujar(g);
		puntos.dibujar(g);
		corazon.dibujar(g);
		romper.dibujar(g);
	}

	public void crearEnemigos() {
		EnemigoHechicero enemigo1 = new EnemigoHechicero(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 - 100));
		EnemigoHechicero enemigo2 = new EnemigoHechicero(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 + 100));
		
		listaEnemigos.add("Enemigo", enemigo1);
		listaEnemigos.add("Enemigo", enemigo2);
		
		cantidadEnemigos = 2;
	}

	public void crearBloques() {
		Bloque bloque1 = new Bloque(new Vector2D(Conf.WIDTH /2 - 50, Conf.HEIGHT / 2));
		Bloque bloque2 = new Bloque(new Vector2D(Conf.WIDTH /2 - 50, Conf.HEIGHT / 2 + 80));
		Bloque bloque3 = new Bloque(new Vector2D(Conf.WIDTH /2 - 50, Conf.HEIGHT / 2 - 80));
		Bloque bloque4 = new Bloque(new Vector2D(Conf.WIDTH /2 - 50, Conf.HEIGHT / 2 + 160));
		Bloque bloque5 = new Bloque(new Vector2D(Conf.WIDTH /2 - 50, Conf.HEIGHT / 2 - 160));
		
		listaBloques.add("Bloque", bloque1);
		listaBloques.add("Bloque", bloque2);
		listaBloques.add("Bloque", bloque3);
		listaBloques.add("Bloque", bloque4);
		listaBloques.add("Bloque", bloque5);
	}

	public void comprobarNivelCompletado() {
		int enemigosMuertos = 0;
		for (int i = 0; i < listaEnemigos.getLength(); i++) {
			if (listaEnemigos.get(i).getViva() == false) {
				enemigosMuertos++;
				if (enemigosMuertos == cantidadEnemigos) {
					System.out.println("Ganador");
					siguienteNivel();
				}
			}
			
		}
	}
	
	public void colisionBloqueJugador() {
		for (int i = 0; i < listaBloques.getLength(); i++) {
			if (listaBloques.get(i) != null) {
				Bloque bloque = (Bloque) listaBloques.get(i);
				if (jugador.getColisiona().colisionaCon(bloque.getColisiona())) {
					jugador.jugadorColision(bloque);
				}
			}
		}
	}

	public void colisionJugadorEnemigo() {
		for (int i = 0; i < listaEnemigos.getAll().length; i++) {
			if (listaEnemigos.get(i) != null) {
				EnemigoHechicero enemigoAux = (EnemigoHechicero) listaEnemigos.get(i);
				if (jugador.getColisiona().colisionaCon(enemigoAux.getColisiona()) || jugador.getColisiona().colisionaCon(enemigoAux.getAtaque().getColisiona())) {
					if (enemigoAux.getViva()) {
						cronometro += GameLoop.dt;
						if (cronometro > 1200) {
							// Reducción de vida y activación del estado de invulnerabilidad
							System.out.println("Dano");
							jugador.dano.play();
							jugador.setNumeroVidas(jugador.getNumeroVidas() - 1);
							textoVidas.setMensaje("x " + jugador.getNumeroVidas());

							// Cambio al sprite de invulnerabilidad
							jugador.getCuerpo().setTextura(Assets.jugadorInvencible);

							// Reinicia el cronómetro de invulnerabilidad
							cronometro = 0;

							// Activa el temporizador del cambio de sprite
							cambiandoSprite = true;
							cronometroCambioSprite = 0; // Resetea el temporizador
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
							jugador.dano.play();
							jugador.setNumeroVidas(jugador.getNumeroVidas() - 1);
							textoVidas.setMensaje("x " + jugador.getNumeroVidas());

							// Cambio al sprite de invulnerabilidad
							jugador.getCuerpo().setTextura(Assets.jugadorInvencible);

							// Reinicia el cronómetro de invulnerabilidad
							cronometro = 0;

							// Activa el temporizador del cambio de sprite
							cambiandoSprite = true;
							cronometroCambioSprite = 0; // Resetea el temporizador
							if (jugador.getNumeroVidas() <= 0) {
								Scene.cambiarEscena(new EscenaBienvenida());
							}
						}
					}
				}
			}
		}
		
		if (cambiandoSprite) {
	        cronometroCambioSprite += GameLoop.dt;

	        if (cronometroCambioSprite > 1000) { 
	        	jugador.dano.stop();
	            jugador.getCuerpo().setTextura(Assets.jugador); // Vuelve al sprite normal
	            cambiandoSprite = false; // Desactiva el cambio de sprite
	        }
	    }
	}

	public void colisionBloqueEnemigo() {
		for (int i = 0; i < listaEnemigos.getAll().length; i++) {
			if (listaEnemigos.get(i) != null) {
				for (int j = 0; j < listaBloques.getLength(); j++) {
					if (listaBloques.get(j) != null) {
						Bloque bloque = (Bloque) listaBloques.get(j);
						EnemigoHechicero enemigoAux = (EnemigoHechicero) listaEnemigos.get(i);
						if (enemigoAux.getColisiona().colisionaCon(bloque.getColisiona())) {
							enemigoAux.colisionBloques(bloque);
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
								flechaAux.romper();
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
						EnemigoHechicero enemigoAux = (EnemigoHechicero) listaEnemigos.get(i);
						Flecha flechaAux = (Flecha) jugador.listaFlechas.get(j);
						if (enemigoAux.getColisiona().colisionaCon(flechaAux.getColisiona())) {
							if (enemigoAux.getViva() && flechaAux.getViva()) {
								animacionRomperFlecha(flechaAux);
								puntos.getTransformar().setPosicion(enemigoAux.getCuerpo().getTransformar().getPosicion());
								puntos.setVisible(true);
								enemigoAux.recibirDano();
								flechaAux.destruir();
								flechaAux.setViva(false);
							}
							
							cronometroPuntos += GameLoop.dt;
							
							if (cronometroPuntos > 600) {
								puntos.setVisible(false);
								cronometroPuntos = 0;
							}
							
						}
						
					}
				}
			}
		}
	}

//	public void dibujarBloques(Graphics arg0) {
//		for (int i = 0; i < listaBloques.getLength(); i++) {
//			if (listaBloques.get(i) != null) {
//				Bloque bloque = (Bloque) listaBloques.get(i);
//				bloque.dibujar(arg0);
//
//			}
//
//		}
//
//	}

	public void siguienteNivel() {
			Scene.cambiarEscena(new EscenaNivel2());
	}

}
