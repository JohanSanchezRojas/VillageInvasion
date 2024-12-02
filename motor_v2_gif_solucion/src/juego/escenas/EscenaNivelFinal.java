package juego.escenas;

import java.awt.Graphics;

import juego.Assets;
import juego.Conf;
import motor_v1.motor.Scene;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.InputMouse;
import motor_v1.motor.input.Key;
import motor_v1.motor.util.Vector2D;

public class EscenaNivelFinal extends Scene{

	private SpriteSolido fondo;
	private SpriteSolido mensajeFinal;
	
	public EscenaNivelFinal() {
		Wallpaper();
		crearMensajeFinal();
	}

	public void actualizar() {
		if (InputMouse.isClicked()) {
			Scene.cambiarEscena(new EscenaBienvenida());
		}
		
		fondo.actualizar();
		mensajeFinal.actualizar();
		
	}

	@Override
	public void destruir() {
		// TODO Auto-generated method stub
		
	}

	public void Wallpaper() {
		fondo = new SpriteSolido("minecraft", Assets.fondoMinecraft);
		
	}
	
	@Override
	public void dibujar(Graphics arg0) {
		fondo.dibujar(arg0);
		mensajeFinal.dibujar(arg0);
		
	}

	public void crearMensajeFinal() {
		Vector2D posicionTitulo = new Vector2D((Conf.WIDTH /2), 350);
		mensajeFinal = new SpriteSolido("Village Invasion", Assets.mensajeFinal, posicionTitulo);
		mensajeFinal.getTransformar().setPosicion(posicionTitulo.subtract(mensajeFinal.getCentroRotacion()));
		
	}
}
