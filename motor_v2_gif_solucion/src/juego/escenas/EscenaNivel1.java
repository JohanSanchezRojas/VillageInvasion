package juego.escenas;

import java.awt.Graphics;

import juego.Assets;
import juego.entidades.Jugador;
import motor_v1.motor.Scene;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.InputMouse;
import motor_v1.motor.input.Key;
import motor_v1.motor.util.Vector2D;

public class EscenaNivel1 extends Scene{
	
	private SpriteSolido fondoNivel;
	private Jugador jugador;
	
	
	public EscenaNivel1() {
		super();
		Wallpaper();
		crearJugador();
		
		
	}

	@Override
	public void actualizar() {
		if (InputKeyboard.isKeyPressed(Key.SHIFT)) {
			Scene.cambiarEscena(new EscenaBienvenida());
		}
		fondoNivel.actualizar();
		jugador.actualizar();
		
		
		
	}

	@Override
	public void destruir() {
		
		
	}

	@Override
	public void dibujar(Graphics arg0) {
		fondoNivel.dibujar(arg0);
		jugador.dibujar(arg0);
		jugador.mira.dibujar(arg0);
		
	}
	
	public void Wallpaper() {
		fondoNivel = new SpriteSolido("Fondo", Assets.fondoCesped);
		
	}
	
	public void crearJugador() {
		Vector2D p = new Vector2D(0, 0);
		jugador = new Jugador("Jugador", Assets.jugador, p);
		
	}
	
	


}
