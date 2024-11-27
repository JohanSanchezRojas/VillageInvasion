package juego.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.Assets;
import motor_v1.motor.GameLoop;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;

public class EnemigoBallesta extends Enemigo{

	public EnemigoBallesta(Vector2D posicion) {
		super("", Assets.enemigoBDown, posicion);
		
		cuerpo = new SpriteMovible("Cuerpo", Assets.enemigoBDown, posicion);
		//cuerpo = new SpriteMovible("Cuerpo", Assets.enemigoBDown);
		//cuerpo = new SpriteMovible("Cuerpo", Assets.enemigoBLeft);
		//cuerpo = new SpriteMovible("Cuerpo", Assets.enemigoBRight);
		//No estoy seguro de que esto vaya de esta manera
		
	}


	public void actulizar() {
		//cuerpo.getTransformar().setEscala(new Vector2D(15,15));
		
		MovimientoEnemigo();
		cuerpo.actualizar();
	}
	
	
	@Override
	public void MovimientoEnemigo() {
		//opciones:
		//1. Un while que condicione que si el enemigo esta vivo se este moviendo al azar
		
		movimientoE = (int) Math.random()*4;
		switch(movimientoE) {
		
		case 1:
			cuerpo.getMovimiento().setDireccion(Vector2D.UP);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
			cuerpo.setTextura(Assets.enemigoBUp);
			break;
		case 2:
			cuerpo.getMovimiento().setDireccion(Vector2D.DOWN);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
			cuerpo.setTextura(Assets.enemigoBDown);
			break;
		case 3:
			cuerpo.getMovimiento().setDireccion(Vector2D.LEFT);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
			cuerpo.setTextura(Assets.enemigoBLeft);
			break;
		case 4:
			cuerpo.getMovimiento().setDireccion(Vector2D.RIGHT);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
			cuerpo.setTextura(Assets.enemigoBRight);
			break;
		}
		
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
