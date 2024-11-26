package juego.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.Assets;
import juego.Conf;
import motor_v1.motor.Entidad;
import motor_v1.motor.GameLoop;
import motor_v1.motor.component.Collider;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.InputMouse;
import motor_v1.motor.util.Vector2D;

public class Jugador extends SpriteMovible{
	


	public SpriteMovible mira;
	
	public Jugador(String nombre, BufferedImage textura, Vector2D posicion) {
		super(nombre, textura, posicion);
		crearMira();
		getTransformar().setPuntoDeRotacion(new Vector2D(0.39, 0.62));
	}
	
	public void actualizar() {
		if(InputKeyboard.isDown(motor_v1.motor.input.Key.D)) {
			movimiento.setDireccion(Vector2D.RIGHT);
			movimiento.mover(transformar, FACTOR_VELOCIDAD * GameLoop.dt);
			
		}if(InputKeyboard.isDown(motor_v1.motor.input.Key.A)) {
			movimiento.setDireccion(Vector2D.LEFT);
			movimiento.mover(transformar, FACTOR_VELOCIDAD * GameLoop.dt);
			
		}if(InputKeyboard.isDown(motor_v1.motor.input.Key.Q)) {
			movimiento.setDireccion(Vector2D.UP);
			movimiento.mover(transformar, FACTOR_VELOCIDAD * GameLoop.dt);
			
		}if(InputKeyboard.isDown(motor_v1.motor.input.Key.S)) {
			movimiento.setDireccion(Vector2D.DOWN);
			movimiento.mover(transformar, FACTOR_VELOCIDAD * GameLoop.dt);
			
		}
		
		limite();
		colisiona.actualizar();
		mira.actualizar();
		actualizarMira();
		rotarJugador();
		
	}
	
	public Collider getColisiona() {
		return colisiona;
	}
	
	private void limite() {
		double x = transformar.getPosicion().getX();
		if(x < 0) {
			transformar.getPosicion().setX(0);
		}
		
		if(x > (Conf.WIDTH - Conf.jugador_WIDTH)) {
			transformar.getPosicion().setX(Conf.WIDTH - Conf.jugador_WIDTH);
		}
		
		double y = transformar.getPosicion().getY();
		if(y < 0) {
			transformar.getPosicion().setY(0);
		}
		
		if(y > (Conf.HEIGHT - Conf.JUGADOR_HEIGHT)) {
			transformar.getPosicion().setY(Conf.WIDTH - Conf.JUGADOR_HEIGHT);
		}
		
		
	}
	
	public void crearMira() {
		Vector2D p = new Vector2D(InputMouse.getX(), InputMouse.getY());
		mira = new SpriteMovible("mira", Assets.mira, p);
		
	}
	
	public void actualizarMira() {
		mira.getTransformar().setPosicion( new Vector2D(InputMouse.getX(), InputMouse.getY()));
	}
	
	public void rotarJugador() {
		Vector2D p = new Vector2D(InputMouse.getX(), InputMouse.getY());
		getTransformar().rotarloA(getCentroRotacion().getAnguloHacia(p));
		//(getTransformar().getPuntoDeRotacion().getAnguloHacia((p));
		
	}

	
	
	
	
}
