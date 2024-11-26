package juego.entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.Assets;
import juego.Conf;
import motor_v1.motor.Entidad;
import motor_v1.motor.GameLoop;
import motor_v1.motor.component.Collider;
import motor_v1.motor.entidades.Sprite;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.entidades.SpriteText;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.InputMouse;
import motor_v1.motor.util.Vector2D;

public class Jugador extends SpriteMovible{
	
	private int numeroVidas;
	private SpriteMovible mira;
	private SpriteSolido corazon;
	private SpriteText textoVidas;
	private Color colorBlanco = new Color(250, 250, 250);
	
	public Jugador(String nombre, BufferedImage textura, Vector2D posicion, int vidas) {
		super(nombre, textura, posicion);
		this.numeroVidas = vidas;
		crearMira();
		crearVidas();
		
		//getTransformar().setPuntoDeRotacion(new Vector2D(0.5, 0.75));
	}
	
	public void actualizar() {

		
		movimiento();
		limite();
		colisiona.actualizar();
		mira.actualizar();
		actualizarMira();
		corazon.actualizar();
		textoVidas.actualizar();
		//rotarJugador();
		
	}
	
	public Collider getColisiona() {
		return colisiona;
	}
	
	private void limite() {
		double x = transformar.getPosicion().getX();
		if(x < 0) {
			transformar.getPosicion().setX(0);
		}
		
		if(x > (Conf.WIDTH - Conf.JUGADOR_WIDTH)) {
			transformar.getPosicion().setX(Conf.WIDTH - Conf.JUGADOR_WIDTH);
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
		mira.getTransformar().setPosicion(p.subtract(mira.getCentroRotacion()));
		
		
	}
	
	public void actualizarMira() {
		mira.getTransformar().setPosicion( new Vector2D(InputMouse.getX(), InputMouse.getY()));
	}
	/**
	public void rotarJugador() {
		Vector2D p = new Vector2D(InputMouse.getX(), InputMouse.getY());
		getTransformar().rotarloA(getCentroRotacion().getAnguloHacia(p));
		
	}
	**/
	
	public void jugadorColision(Bloque b) {
		
		double xJugador = transformar.getPosicion().getX();
		double xB = b.getTransformar().getPosicion().getX();
			
		if((movimiento.getDireccion() == Vector2D.RIGHT) && xJugador <= xB ) {
			transformar.getPosicion().setX(xB - Conf.JUGADOR_WIDTH);
			
		}
			
		if((movimiento.getDireccion() == Vector2D.LEFT) && xJugador >= xB) {
			transformar.getPosicion().setX(xB + Conf.WOOD_LADO);
			
		}
			
		double yJugador = transformar.getPosicion().getY();
		double yB = b.getTransformar().getPosicion().getY();
			
			
		if((movimiento.getDireccion() == Vector2D.DOWN) && yJugador <= yB) {
			transformar.getPosicion().setY(yB - Conf.JUGADOR_HEIGHT);
			
		}
			
		if((movimiento.getDireccion() == Vector2D.UP) && yJugador >= yB) {
			transformar.getPosicion().setY(yB + Conf.WOOD_LADO);
			
		}
		
			
	}
	
	public void movimiento() {
		
		if(InputKeyboard.isDown(motor_v1.motor.input.Key.D)) {
			movimiento.setDireccion(Vector2D.RIGHT);
			movimiento.mover(transformar, FACTOR_VELOCIDAD * GameLoop.dt);
			
		}else if(InputKeyboard.isDown(motor_v1.motor.input.Key.A)) {
			movimiento.setDireccion(Vector2D.LEFT);
			movimiento.mover(transformar, FACTOR_VELOCIDAD * GameLoop.dt);
			
		}else if(InputKeyboard.isDown(motor_v1.motor.input.Key.W)) {
			movimiento.setDireccion(Vector2D.UP);
			movimiento.mover(transformar, FACTOR_VELOCIDAD * GameLoop.dt);
			
		}else if(InputKeyboard.isDown(motor_v1.motor.input.Key.S)) {
			movimiento.setDireccion(Vector2D.DOWN);
			movimiento.mover(transformar, FACTOR_VELOCIDAD * GameLoop.dt);
			
		}
		
	}
	
	public void crearVidas() {
		Vector2D p = new Vector2D(10, 10);
		corazon = new SpriteSolido("corazones", Assets.corazon, p);
		
		Vector2D pNum = new Vector2D(55, 35);
		textoVidas = new SpriteText("x "+ numeroVidas, colorBlanco, Assets.font_minecraft, false);
		textoVidas.setPosicion(pNum);
		
		
	}

	public int getNumeroVidas() {
		return numeroVidas;
	}

	public void setNumeroVidas(int numeroVidas) {
		this.numeroVidas = numeroVidas;
	}

	public SpriteMovible getMira() {
		return mira;
	}

	public void setMira(SpriteMovible mira) {
		this.mira = mira;
	}

	public SpriteSolido getCorazon() {
		return corazon;
	}

	public void setCorazon(SpriteSolido corazon) {
		this.corazon = corazon;
	}

	public SpriteText getTextoVidas() {
		return textoVidas;
	}

	public void setTextoVidas(SpriteText textoVidas) {
		this.textoVidas = textoVidas;
	}
	
	
	

	
	
	
	
}
