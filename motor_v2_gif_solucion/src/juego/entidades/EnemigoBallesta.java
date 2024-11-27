package juego.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.Assets;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;

public class EnemigoBallesta extends Enemigo{

	public EnemigoBallesta() {
		super("", null, null);
		cuerpo = new SpriteMovible("Cuerpo", Assets.enemigoBUp);
		//cuerpo = new SpriteMovible("Cuerpo", Assets.enemigoBDown);
		//cuerpo = new SpriteMovible("Cuerpo", Assets.enemigoBLeft);
		//cuerpo = new SpriteMovible("Cuerpo", Assets.enemigoBRight);
		//No estoy seguro de que esto vaya de esta manera

	}

	

	public void actulizar() {
		cuerpo.getTransformar().setEscala(new Vector2D(15,15));
	}
	
	
	@Override
	public void MovimientoEnemigo() {
		
		
	}

	@Override
	public void destruir() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dibujar(Graphics g) {
		cuerpo.dibujar(g);
		
	}

}
