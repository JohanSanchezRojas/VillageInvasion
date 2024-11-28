package juego.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import juego.Assets;
import motor_v1.motor.GameLoop;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.util.Vector2D;

public class EnemigoBallesta extends Enemigo{

	private double crono;
	public static final int TIEMPO_DE_ESPERA = 1000;
	
	public EnemigoBallesta(Vector2D posicion) {
		super("", Assets.enemigoBDown, posicion);
		
		cuerpo = new SpriteMovible("Cuerpo", Assets.enemigoBDown, posicion);
		//cuerpo = new SpriteMovible("Cuerpo", Assets.enemigoBUp, posicion);
		//cuerpo = new SpriteMovible("Cuerpo", Assets.enemigoBLeft, posicion);
		//cuerpo = new SpriteMovible("Cuerpo", Assets.enemigoBRight, posicion);
		//No estoy seguro de que esto vaya de esta manera
		
	}


	public void actulizar() {
		//cuerpo.getTransformar().setEscala(new Vector2D(15,15));
		MovimientoEnemigo();
		cuerpo.actualizar();
	}
	
	
	public void MovimientoEnemigo() {

		
		//movimientoE = (int) Math.random()*4;
		/*
		MovimientoRandom();
		switch(MovimientoRandom()) {
		
		case 1:
			cuerpo.getMovimiento().setDireccion(Vector2D.UP);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
			//cuerpo.setTextura(Assets.enemigoBUp);
			break;
		case 2:
			cuerpo.getMovimiento().setDireccion(Vector2D.DOWN);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
			//cuerpo.setTextura(Assets.enemigoBDown);
			break;
		case 3:
			cuerpo.getMovimiento().setDireccion(Vector2D.LEFT);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
			//cuerpo.setTextura(Assets.enemigoBLeft);
			break;
		case 4:
			cuerpo.getMovimiento().setDireccion(Vector2D.RIGHT);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
			//cuerpo.setTextura(Assets.enemigoBRight);
			break;
		}
		*/
		
		if (InputKeyboard.isDown(motor_v1.motor.input.Key.UP)) {
			cuerpo.getMovimiento().setDireccion(Vector2D.UP);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
		} else if (InputKeyboard.isDown(motor_v1.motor.input.Key.LEFT)) {
			cuerpo.getMovimiento().setDireccion(Vector2D.LEFT);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
		} else if (InputKeyboard.isDown(motor_v1.motor.input.Key.DOWN)) {
			cuerpo.getMovimiento().setDireccion(Vector2D.DOWN);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
		} else if (InputKeyboard.isDown(motor_v1.motor.input.Key.RIGHT)) {
			cuerpo.getMovimiento().setDireccion(Vector2D.RIGHT);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
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
	
	private int MovimientoRandom() {
		
		int numero = 0;
		crono += GameLoop.dt;
		if (crono > TIEMPO_DE_ESPERA) {
			Random random = new Random();
			int min = 1;
			int max = 4;
			numero = min + random.nextInt(max);
			crono = 0;
		}
		System.out.println(numero);
		return numero;
		
		
	}
}
