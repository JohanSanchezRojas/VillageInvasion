package juego.escenas;
/**
Johan David Sánchez Rojas C17305
Joshua Chacón Alvarez C4E105
Andrew Mora Mejia C05158*/
import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import juego.Assets;
import juego.Conf;
import juego.entidades.Bloque;
import juego.entidades.Enemigo;
import juego.entidades.EnemigoArquero;
import juego.entidades.EnemigoEspada;
import juego.entidades.EnemigoHechicero;
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

public class EscenaNivel1 extends Nivel {
	public EscenaNivel1() {
		super();
	}

	public void crearEnemigos() {

		EnemigoEspada enemigo1 = new EnemigoEspada(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 + 120));
		EnemigoEspada enemigo2 = new EnemigoEspada(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2 - 120));
		
		super.getListaEnemigos().add("Enemigo", enemigo1);
		super.getListaEnemigos().add("Enemigo", enemigo2);
;

		super.setCantidadEnemigos(2);
	}

	public void crearBloques() {
		Bloque bloque1 = new Bloque(new Vector2D(Conf.WIDTH / 2 - 50, Conf.HEIGHT / 2));
		Bloque bloque2 = new Bloque(new Vector2D(Conf.WIDTH / 2 - 50, Conf.HEIGHT / 2 + 80));
		Bloque bloque3 = new Bloque(new Vector2D(Conf.WIDTH / 2 - 50, Conf.HEIGHT / 2 - 80));
		Bloque bloque4 = new Bloque(new Vector2D(Conf.WIDTH / 2 - 50, Conf.HEIGHT / 2 + 160));
		Bloque bloque5 = new Bloque(new Vector2D(Conf.WIDTH / 2 - 50, Conf.HEIGHT / 2 - 160));

		super.getListaBloques().add("Bloque", bloque1);
		super.getListaBloques().add("Bloque", bloque2);
		super.getListaBloques().add("Bloque", bloque3);
		super.getListaBloques().add("Bloque", bloque4);
		super.getListaBloques().add("Bloque", bloque5);
	}
	
	public void siguienteNivel() {
		Scene.cambiarEscena(new EscenaNivel2());
	}

	@Override
	public void crearJugador() {
		super.setJugador(new Jugador(new Vector2D(150, Conf.HEIGHT / 2), 10));
		
	}

}
