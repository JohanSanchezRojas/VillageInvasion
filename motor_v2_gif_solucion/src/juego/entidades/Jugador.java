package juego.entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.event.MouseAdapter;
import java.awt.geom.RoundRectangle2D.Double;
import java.util.Vector;

import juego.Assets;
import juego.Conf;
import motor_v1.motor.Entidad;
import motor_v1.motor.GameLoop;
import motor_v1.motor.component.Collider;
import motor_v1.motor.component.Movement;
import motor_v1.motor.component.Sound;
import motor_v1.motor.entidades.Gif;
import motor_v1.motor.entidades.ListaEntidades;
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
	private Sound disparo;
	private Sound dano;
	private int numeroVidas;
	double cronometro = 0;
	double cronometroDisparo = 1000;
	public ListaEntidades listaFlechas;

	public Jugador(Vector2D p, int vidas) {
		super();
		cuerpo = new SpriteMovible("Cuerpo", Assets.jugador, p);
		mira = new Sprite("Mira", Assets.mira);
		disparo = new Sound(Assets.sonidoDisparo);
		disparo.changeVolume(70);
		dano = new Sound(Assets.sonidoDano);
		dano.changeVolume(70);
		this.numeroVidas = vidas;
		listaFlechas = new ListaEntidades();
	}

	@Override
	public void actualizar() {
		disparar();
		movimiento();
		actualizarMira();
		rotacion();
		limite();
		mira.actualizar();
		listaFlechas.actualizar();
		cuerpo.actualizar();
	}

	@Override
	public void dibujar(Graphics g) {
		mira.dibujar(g);
		cuerpo.dibujar(g);
		listaFlechas.dibujar(g);
	}

	@Override
	public void destruir() {
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

	public Sound getDano() {
		return dano;
	}

	public void setDano(Sound dano) {
		this.dano = dano;
	}

	public int getNumeroVidas() {
		return numeroVidas;
	}

	public void setNumeroVidas(int numeroVidas) {
		this.numeroVidas = numeroVidas;
	}

	public Collider getColisiona() {
		return cuerpo.getColisiona();
	}

	private void movimiento() {

		if (InputKeyboard.isDown(motor_v1.motor.input.Key.W)) {
			cuerpo.getMovimiento().setDireccion(Vector2D.UP);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), Conf.JUGADOR_VELOCIDAD * GameLoop.dt);
		} else if (InputKeyboard.isDown(motor_v1.motor.input.Key.A)) {
			cuerpo.getMovimiento().setDireccion(Vector2D.LEFT);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), Conf.JUGADOR_VELOCIDAD * GameLoop.dt);
		} else if (InputKeyboard.isDown(motor_v1.motor.input.Key.S)) {
			cuerpo.getMovimiento().setDireccion(Vector2D.DOWN);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), Conf.JUGADOR_VELOCIDAD * GameLoop.dt);
		} else if (InputKeyboard.isDown(motor_v1.motor.input.Key.D)) {
			cuerpo.getMovimiento().setDireccion(Vector2D.RIGHT);
			cuerpo.getMovimiento().mover(cuerpo.getTransformar(), Conf.JUGADOR_VELOCIDAD * GameLoop.dt);
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

	public void colisionBloque(Bloque bloque) {

		double xJugador = cuerpo.getTransformar().getPosicion().getX();
		double xBloque = bloque.getBloque().getTransformar().getPosicion().getX();

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.RIGHT) && xJugador <= xBloque) {
			cuerpo.getTransformar().getPosicion().setX(xBloque - Conf.JUGADOR_WIDTH);
		}

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.LEFT) && xJugador >= xBloque) {
			cuerpo.getTransformar().getPosicion().setX(xBloque + Conf.WOOD_LADO);
		}

		double yJugador = cuerpo.getTransformar().getPosicion().getY();
		double yB = bloque.getBloque().getTransformar().getPosicion().getY();

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.DOWN) && yJugador <= yB) {
			cuerpo.getTransformar().getPosicion().setY(yB - Conf.JUGADOR_HEIGHT);
		}

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.UP) && yJugador >= yB) {
			cuerpo.getTransformar().getPosicion().setY(yB + Conf.WOOD_LADO);
		}
	}

	private void actualizarMira() {
		mira.getTransformar().setPosicion(new Vector2D(InputMouse.getX(), InputMouse.getY()));
	}

	// Rota al jugador hacia el mouse
	private void rotacion() {
		Vector2D centro = cuerpo.getTransformar().getPosicion();
		Vector2D posicionMouse = new Vector2D(InputMouse.getX(), InputMouse.getY());
		double angulo = centro.getAnguloHacia(posicionMouse);
		double desfase = 90;
		cuerpo.getTransformar().rotarloA(angulo + desfase);
	}

	private void disparar() {
		cronometroDisparo += GameLoop.dt;

		boolean clickDisparo = InputMouse.isClicked();

		// Permite disparar si ha pasado suficiente tiempo entre un disparo y otro
		if (cronometroDisparo > GameLoop.dt * 80 && clickDisparo) {
			cronometroDisparo = 0;
			disparo.play();

			Vector2D centro = cuerpo.getTransformar().getPosicion();
			Vector2D posicionMouse = new Vector2D(InputMouse.getX(), InputMouse.getY());
			double angulo = centro.getAnguloHacia(posicionMouse);
			double desfase = 90;

			Flecha flecha = new Flecha(centro);

			flecha.getFlecha().getTransformar().rotarloA(angulo + desfase);
			flecha.getFlecha().getMovimiento().setDireccion((int) angulo);
			flecha.getFlecha().getTransformar().setPosicion(centro);

			listaFlechas.add("Flecha", flecha);
		}

		// Actualiza las flechas para que no se queden quietas
		for (int i = 0; i < listaFlechas.getLength(); i++) {
			if (listaFlechas.get(i) != null) {
				Flecha flechaAux = (Flecha) listaFlechas.get(i);

				if (flechaAux.getViva()) {
					flechaAux.getFlecha().getMovimiento().mover(flechaAux.getFlecha().getTransformar(),
							1.5 * GameLoop.dt);
					flechaAux.colisionPantalla();
				}
			}
		}
	}
}