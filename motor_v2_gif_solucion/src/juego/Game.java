package juego;

import java.awt.Graphics;

import juego.escenas.EscenaBienvenida;
import motor_v1.motor.Entidad;
import motor_v1.motor.Lienzo;
import motor_v1.motor.Scene;
/*
 * @version 31-08-2024
 * @author Denis Gonzalez Herrera
 * @description: CONTROLADOR PARA LOS DISTINTOS JUEGOS
 * */

public class Game extends Entidad {

	private Lienzo lienzo;
	private Scene escenaActual;

	public Game() {
		lienzo = new Lienzo(Conf.WIDTH, Conf.HEIGHT, "Village Invasion");
		escenaActual = new EscenaBienvenida();
		Scene.cambiarEscena(escenaActual);
	}
	@Override
	public void actualizar() {
		escenaActual = Scene.getEscenaActual();
		escenaActual.actualizar();
	}
	@Override
	public void dibujar(Graphics g) {
		if (g == null) {
			lienzo.dibujar(escenaActual);
		}
	}
	@Override
	public void destruir() {
		lienzo.dispose();
		System.exit(0);
	}

}
