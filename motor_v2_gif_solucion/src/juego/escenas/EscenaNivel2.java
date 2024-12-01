package juego.escenas;

import java.awt.Color;
import java.awt.Graphics;

import juego.Assets;
import juego.Conf;
import juego.entidades.Bloque;
import juego.entidades.EnemigoHechicero;
import juego.entidades.EnemigoEspada;
import juego.entidades.Jugador;
import juego.entidades.EnemigoProvisional;
import juego.entidades.Flecha;
import motor_v1.motor.Scene;
import motor_v1.motor.entidades.Gif;
import motor_v1.motor.entidades.ListaEntidades;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.entidades.SpriteText;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.Key;
import motor_v1.motor.util.Vector2D;

public class EscenaNivel2 extends Scene{
	
	private SpriteSolido fondoNivel;
	private Jugador jugador;
	private EnemigoProvisional[] bill = new EnemigoProvisional[2];
	private SpriteSolido corazon;
	private SpriteText puntos;
	private SpriteText textoVidas;
	private ListaEntidades listaBloques;
	private ListaEntidades listaEnemigos;
	private Gif romper;
	double cronometro = 1200;
	private double cronometroCambioSprite;
	private double cronometroPuntos = 0;
	private boolean cambiandoSprite = false;
	
	private int cantidadEnemigos = 0;
	
	
	public EscenaNivel2() {
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
		
		Wallpaper();
		crearJugador();
		crearBloques();
		crearEnemigoProvisional();		
		
		
	}

	@Override
	public void actualizar() {
		if (InputKeyboard.isKeyPressed(Key.SHIFT)) {
			Scene.cambiarEscena(new EscenaBienvenida());
		}
		fondoNivel.actualizar();
		jugador.actualizar();
		listaBloques.actualizar();
		colisionBloqueJugador();
		siguienteNivel(); 
		colisionFlechaJugador();
		
	}

	@Override
	public void destruir() {
		
	}
	

	@Override
	public void dibujar(Graphics arg0) {
		fondoNivel.dibujar(arg0);
		jugador.dibujar(arg0);
		jugador.getMira().dibujar(arg0);
		listaBloques.dibujar(arg0);
		dibujarEnemigosProvisional(arg0);
		
		
		
		
	}
	public void crearEnemigos() {
		EnemigoEspada enemigoE1 = new EnemigoEspada(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 - 100));
		EnemigoEspada enemigoE2 = new EnemigoEspada(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 + 100));
		
		listaEnemigos.add("Enemigo", enemigoE1);
		listaEnemigos.add("Enemigo", enemigoE2);
		
		cantidadEnemigos = 2;
	}
	
	public void Wallpaper() {
		fondoNivel = new SpriteSolido("Fondo", Assets.fondoCesped);
		
	}
	
	public void crearJugador() {
		Vector2D p = new Vector2D(150, Conf.HEIGHT /2);
		jugador = new Jugador(p, 10);
	}
	
	public void crearBloques() {
		Bloque bloque;
		int y = (Conf.HEIGHT / 2) - 160;
		
		for (int i = 0; i < 5; i++) {
			
			Vector2D p = new Vector2D(Conf.WIDTH / 2, y);
			bloque = new Bloque(p);
			bloque.getBloque().getTransformar().setPosicion(p.subtract(bloque.getBloque().getCentroRotacion()));
			listaBloques.add("Bloque", bloque);
			
			y = y + 80;
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
	
	
	public void crearEnemigoProvisional() {
		
		bill[0] = new EnemigoProvisional(new Vector2D(Conf.WIDTH - 150, (Conf.HEIGHT /2) + 75));
		bill[1] = new EnemigoProvisional(new Vector2D(Conf.WIDTH - 150, (Conf.HEIGHT /2) - 75));
	}
	
	public void dibujarEnemigosProvisional(Graphics arg0) {
		for (int i = 0; i < bill.length; i++) {
			bill[i].dibujar(arg0);
			
		}
	}
	
	public void siguienteNivel() {
		
		for (int i = 0; i < bill.length; i++) {
			if(jugador.getColisiona().colisionaCon(bill[i].getColisiona())) {
				Scene.cambiarEscena(new EscenaNivel3());
			}
			
		}
		
		
	}
		
		
	
}
