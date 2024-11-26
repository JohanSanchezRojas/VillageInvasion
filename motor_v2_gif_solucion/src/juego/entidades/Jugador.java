package juego.entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;

import juego.Assets;
import juego.Conf;
import motor_v1.motor.Entidad;
import motor_v1.motor.GameLoop;
import motor_v1.motor.component.Collider;
import motor_v1.motor.entidades.Gif;
import motor_v1.motor.entidades.Sprite;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.entidades.SpriteText;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.InputMouse;
import motor_v1.motor.util.Vector2D;

public class Jugador extends Entidad {
	private SpriteMovible cuerpo;
	private Sprite mira;
	private SpriteSolido corazon;
	private SpriteText textoVidas;
	private int numeroVidas;
	private Color colorBlanco = new Color(250, 250, 250);
	double cronometro = 0;

	public Jugador(int numeroVidas) {
		super();
		cuerpo = new SpriteMovible("Cuerpo", Assets.jugador);
		mira = new Sprite("Mira", Assets.mira);
		corazon = new SpriteSolido("Corazon", Assets.corazon, new Vector2D(10, 10));
		this.numeroVidas = numeroVidas;
		textoVidas = new SpriteText("x "+ numeroVidas, colorBlanco, Assets.font_minecraft, false);
		textoVidas.setPosicion(new Vector2D(55, 35));
	}

	@Override
	public void actualizar() {
		movimiento();
		actualizarMira();
		rotacion();
		limite();

		cuerpo.getColisiona().actualizar();
		cuerpo.actualizar();
		mira.actualizar();
		corazon.actualizar();
		textoVidas.actualizar();
	}

	public void jugadorColision(Bloque b) {

		double xJugador = cuerpo.getTransformar().getPosicion().getX();
		double xB = b.getTransformar().getPosicion().getX();

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.RIGHT) && xJugador <= xB) {
			cuerpo.getTransformar().getPosicion().setX(xB - Conf.JUGADOR_WIDTH);
		}

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.LEFT) && xJugador >= xB) {
			cuerpo.getTransformar().getPosicion().setX(xB + Conf.WOOD_LADO);
		}

		double yJugador = cuerpo.getTransformar().getPosicion().getY();
		double yB = b.getTransformar().getPosicion().getY();

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.DOWN) && yJugador <= yB) {
			cuerpo.getTransformar().getPosicion().setY(yB - Conf.JUGADOR_HEIGHT);
		}

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.UP) && yJugador >= yB) {
			cuerpo.getTransformar().getPosicion().setY(yB + Conf.WOOD_LADO);
		}
	}

	private void limite() {
		double x = cuerpo.getTransformar().getPosicion().getX();
		if (x < 0) {
			cuerpo.getTransformar().getPosicion().setX(0);
		}

		if (x > (Conf.WIDTH - Conf.JUGADOR_WIDTH)) {
			cuerpo.getTransformar().getPosicion().setX(Conf.WIDTH - Conf.JUGADOR_WIDTH);
		}

		double y = cuerpo.getTransformar().getPosicion().getY();
		if (y < 0) {
			cuerpo.getTransformar().getPosicion().setY(0);
		}

		if (y > (Conf.HEIGHT - Conf.JUGADOR_HEIGHT)) {
			cuerpo.getTransformar().getPosicion().setY(Conf.WIDTH - Conf.JUGADOR_HEIGHT);
		}
	}

	public Collider getColisiona() {
		return cuerpo.getColisiona();
	}

	private void rotacion() {
		Vector2D centro = cuerpo.getTransformar().getPosicion();
		Vector2D posicionMouse = new Vector2D(InputMouse.getX(), InputMouse.getY());
		double angulo = centro.getAnguloHacia(posicionMouse);
		double desfase = 90;
		cuerpo.getTransformar().rotarloA(angulo + desfase);

		imprimirDatos(angulo, posicionMouse, centro);
	}

	private void actualizarMira() {
		mira.getTransformar().setPosicion(new Vector2D(InputMouse.getX(), InputMouse.getY()));
	}

	private void movimiento() {

		if (InputKeyboard.isDown(motor_v1.motor.input.Key.W)) {
			cuerpo.getMovimiento().setDireccion(Vector2D.UP);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
		} else if (InputKeyboard.isDown(motor_v1.motor.input.Key.A)) {
			cuerpo.getMovimiento().setDireccion(Vector2D.LEFT);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
		} else if (InputKeyboard.isDown(motor_v1.motor.input.Key.S)) {
			cuerpo.getMovimiento().setDireccion(Vector2D.DOWN);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), cuerpo.FACTOR_VELOCIDAD * GameLoop.dt);
		} else if (InputKeyboard.isDown(motor_v1.motor.input.Key.D)) {
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
		mira.dibujar(g);
		cuerpo.dibujar(g);
		corazon.dibujar(g);
		textoVidas.dibujar(g);
	}

	public void imprimirDatos(double angulo, Vector2D posicionMouse, Vector2D centro) {
		cronometro += GameLoop.dt;

		if (cronometro > 1200) {
			System.out.println("Angulo: " + angulo + " Posicion: " + posicionMouse + " Centro: " + centro);
			cronometro = 0;
		}
	}

	public SpriteMovible getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(SpriteMovible cuerpo) {
		this.cuerpo = cuerpo;
	}

	public Sprite getMira() {
		return mira;
	}

	public void setMira(Sprite mira) {
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

	public Color getColorBlanco() {
		return colorBlanco;
	}

	public void setColorBlanco(Color colorBlanco) {
		this.colorBlanco = colorBlanco;
	}
}
