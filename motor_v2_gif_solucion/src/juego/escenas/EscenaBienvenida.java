package juego.escenas;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import juego.Assets;
import juego.Conf;
import motor_v1.motor.GameLoop;
import motor_v1.motor.Scene;
import motor_v1.motor.entidades.Gif;
import motor_v1.motor.entidades.ListaEntidades;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.entidades.SpriteText;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.InputMouse;
import motor_v1.motor.input.Key;
import motor_v1.motor.util.Vector2D;

public class EscenaBienvenida extends Scene{
	
	private SpriteText mensajeBienvenida;
	private SpriteText mensajeJuego;
	private SpriteText mensajeInscribirUsuario;
	private SpriteText mensajeSalir;
	private SpriteText joshua;
	private SpriteText andrew;
	private SpriteText johan;
	private Color colorBlanco = new Color(250, 250, 250);
	private SpriteSolido fondo;
	
	
	public EscenaBienvenida() {
		super();
		Wallpaper();
		Mensajes();
		
	}

	@Override
	public void actualizar() {
		if (InputKeyboard.isDown(Key.ENTER)) {
			Scene.cambiarEscena(new EscenaJuego());
		}
		
		if (InputKeyboard.isKeyPressed(Key.ESCAPE)) {
			System.exit(0);
		}
		
		fondo.actualizar();
		mensajeBienvenida.actualizar();
		mensajeJuego.actualizar();
		mensajeInscribirUsuario.actualizar();
		mensajeSalir.actualizar();
		joshua.actualizar();
		andrew.actualizar();
		johan.actualizar();
		
	}
	

	@Override
	public void dibujar(Graphics g) {
		fondo.dibujar(g);
		mensajeBienvenida.dibujar(g);
		mensajeJuego.dibujar(g);
		mensajeInscribirUsuario.dibujar(g);
		mensajeSalir.dibujar(g);
		joshua.dibujar(g);
		andrew.dibujar(g);
		johan.dibujar(g);
		
	}

	@Override
	public void destruir() {
		
	}
	
	//OTROS METODOS
	
	public void Mensajes() {
		
		Vector2D posicionTitulo = new Vector2D((Conf.WIDTH /2), (Conf.HEIGHT / 2) / 2);
		mensajeBienvenida = new SpriteText("Village Invasion", colorBlanco, Assets.font_minecraft, false);
		mensajeBienvenida.setPosicion(posicionTitulo);
		
		Vector2D posicionJugar = new Vector2D(10, (Conf.HEIGHT / 2) - 100);
		mensajeJuego = new SpriteText("Ingresar al Juego (ENTER)", colorBlanco, Assets.font_minecraft, false);
		mensajeJuego.setPosicion(posicionJugar);
		
		Vector2D posicionUsuario = new Vector2D(10, (Conf.HEIGHT / 2));
		mensajeInscribirUsuario = new SpriteText("Inscribir Usuario (ESPACIO)", colorBlanco, Assets.font_minecraft, false);
		mensajeInscribirUsuario.setPosicion(posicionUsuario);
		
		Vector2D posicionSalir = new Vector2D(10, (Conf.HEIGHT / 2) + 100);
		mensajeSalir = new SpriteText("Salir del Juego (ESC)", colorBlanco, Assets.font_minecraft, false);
		mensajeSalir.setPosicion(posicionSalir);
		
		Vector2D posicionJoshua = new Vector2D(10, Conf.HEIGHT - 40);
		joshua = new SpriteText("Joshua Steven Chacon Alvarez", colorBlanco, Assets.font_minecraft12, false);
		joshua.setPosicion(posicionJoshua);
		
		Vector2D posicionAndru = new Vector2D(10, Conf.HEIGHT - 25);
		andrew = new SpriteText("Andrew Mora Mejia", colorBlanco, Assets.font_minecraft12, false);
		andrew.setPosicion(posicionAndru);
		
		Vector2D posicionJohan = new Vector2D(10, Conf.HEIGHT - 10);
		johan = new SpriteText("Johan Sanchez Rojas", colorBlanco, Assets.font_minecraft12, false);
		johan.setPosicion(posicionJohan);
		
		
		
		
		
	}
	
	public void Wallpaper() {
		fondo = new SpriteSolido("minecraft", Assets.fondoMinecraft);
		
	}
	
	
	
	
	

	
	
	

}
