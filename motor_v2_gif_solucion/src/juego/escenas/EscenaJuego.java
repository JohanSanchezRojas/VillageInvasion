package juego.escenas;

import java.awt.Graphics;

import motor_v1.motor.Scene;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.Key;

public class EscenaJuego extends Scene{

	@Override
	public void actualizar() {
		if (InputKeyboard.isKeyPressed(Key.SHIFT)) {
			Scene.cambiarEscena(new EscenaBienvenida());
		}
	}

	@Override
	public void destruir() {
		
	}

	@Override
	public void dibujar(Graphics arg0) {
		
	}

}
