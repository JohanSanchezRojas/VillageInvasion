package juego.escenas;
/**

Johan David Sánchez Rojas C17305
Joshua Chacón Alvarez C4E105
Andrew Mora Mejia C05158*/
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


import juego.Assets;
import juego.Conf;
import motor_v1.motor.GameLoop;
import motor_v1.motor.Scene;
import motor_v1.motor.component.Sound;
import motor_v1.motor.entidades.Gif;
import motor_v1.motor.entidades.ListaEntidades;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.entidades.SpriteText;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.InputMouse;
import motor_v1.motor.input.Key;
import motor_v1.motor.util.Vector2D;

public class EscenaBienvenida extends Scene{
	
	private SpriteSolido titulo;
	private SpriteText mensajeJuego;
	private SpriteText mensajeInscribirUsuario;
	private SpriteText mensajeSalir;
	private Sound musica;
	private SpriteText joshua;
	private SpriteText andrew;
	private SpriteText johan;
	private Color colorBlanco = new Color(250, 250, 250);
	private SpriteSolido fondo;
	private SpriteSolido instrucciones;
	
	
	public EscenaBienvenida() {
		super();
		musica = new Sound(Assets.sonidoMusica);
		musica.play();
		musica.changeVolume(70);
		musica.loop();
		Wallpaper();
		Mensajes();
		crearInstrucciones();
		crearTitulo();
		
		
	}

	@Override
	public void actualizar() {
		if (InputKeyboard.isDown(Key.ENTER)) {
			Scene.cambiarEscena(new EscenaNivel1());
		}
		
		if (InputKeyboard.isKeyPressed(Key.ESCAPE)) {
			System.exit(0);
		}
		
		fondo.actualizar();
		titulo.actualizar();
		mensajeJuego.actualizar();
		mensajeInscribirUsuario.actualizar();
		mensajeSalir.actualizar();
		joshua.actualizar();
		andrew.actualizar();
		johan.actualizar();
		instrucciones.actualizar();
		
	}
	

	@Override
	public void dibujar(Graphics g) {
		fondo.dibujar(g);
		titulo.dibujar(g);
		mensajeJuego.dibujar(g);
		mensajeInscribirUsuario.dibujar(g);
		mensajeSalir.dibujar(g);
		joshua.dibujar(g);
		andrew.dibujar(g);
		johan.dibujar(g);
		instrucciones.dibujar(g);
		
	}

	@Override
	public void destruir() {
		
	}
	
	//OTROS METODOS
	
	public void Mensajes() {
		
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
	
	public void crearInstrucciones() {
		Vector2D p = new Vector2D((Conf.WIDTH /1.4) , (Conf.HEIGHT / 2));
		instrucciones = new SpriteSolido("instrucciones", Assets.instrucciones, p);
		instrucciones.getTransformar().setPosicion(p.subtract(instrucciones.getCentroRotacion()));
		
	}
	
	public void crearTitulo() {
		Vector2D posicionTitulo = new Vector2D((Conf.WIDTH /2), 100);
		titulo = new SpriteSolido("Village Invasion", Assets.titulo, posicionTitulo);
		titulo.getTransformar().setPosicion(posicionTitulo.subtract(titulo.getCentroRotacion()));
		
	}
	
	
	
	
	
	
	
	
	
	

	
	
	

}
