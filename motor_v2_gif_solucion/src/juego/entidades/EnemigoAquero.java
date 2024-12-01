package juego.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.Assets;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;

public class EnemigoAquero extends Enemigo{
	
	Vector2D posicionJugador;
	Bloque[] listaBloques;
	
	public EnemigoAquero(Vector2D posicion) {
		super(new SpriteMovible("Enemigo", Assets.enemigoArquero), posicion);
		posicionJugador = new Vector2D(0, 0);
		listaBloques = new Bloque[0];
	}

	@Override
	public void movimiento() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atacar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destruir() {
		// TODO Auto-generated method stub
		
	}
	
	
}
