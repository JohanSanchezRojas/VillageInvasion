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
	private SpriteSolido corazon;
	private SpriteText textoVidas;
	private int numeroVidas;
	private Color colorBlanco = new Color(250, 250, 250);
	double cronometro = 0;
	double cronometro2 = 1000;
	private Gif slash;
	private Flecha flecha;
	public ListaEntidades flechas;

	public Jugador(Vector2D p, int vidas) {
		super();
		cuerpo = new SpriteMovible("Cuerpo", Assets.jugador, p);
		mira = new Sprite("Mira", Assets.mira);
		corazon = new SpriteSolido("Corazon", Assets.corazon, new Vector2D(10, 10));
		this.numeroVidas = vidas;
		textoVidas = new SpriteText("x " + numeroVidas, colorBlanco, Assets.font_minecraft, false);
		textoVidas.setPosicion(new Vector2D(55, 35));
		flechas = new ListaEntidades();
		// crearAtaqueEspada();
	}

	private void destruirFlecha(Flecha flecha) {
		if (flecha.getTransformar().getPosicion().getX() > Conf.WIDTH - Conf.FLECHA_HEIGHT) {
			System.out.println("1");
			flecha.destruir();
		} else if (flecha.getTransformar().getPosicion().getX() < 0) {
			System.out.println("2");
			flecha.destruir();
		}

		if (flecha.getTransformar().getPosicion().getY() > Conf.HEIGHT - Conf.FLECHA_WIDTH) {
			System.out.println("3");
			flecha.destruir();
		} else if (flecha.getTransformar().getPosicion().getY() < 0) {
			System.out.println("4");
			flecha.destruir();
		}

	}

	public void flechaColision(Bloque b, Flecha flecha) {

		if (flecha.getColisiona().colisionaCon(b.getColisiona())) {
			flecha.destruir();
		}
	}

	private void disparar() {
		Vector2D centro = cuerpo.getTransformar().getPosicion();
		Vector2D posicionMouse = new Vector2D(InputMouse.getX(), InputMouse.getY());
		double angulo = centro.getAnguloHacia(posicionMouse);
		double desfase = 90;
		
		flecha = new Flecha("Flecha", Assets.flecha);
		
		flechas.add("Flecha", flecha);
		
		for (int i = 0; i < flechas.getAll().length; i++) {
				flecha.getTransformar().rotarloA(angulo + desfase);
				flecha.getMovimiento().setDireccion((int) angulo);
				flecha.getTransformar().setPosicion(centro);
				
				flecha.getMovimiento().mover(flecha.getTransformar(), 1 * GameLoop.dt);
		}
	}

	@Override
	public void actualizar() {
		cronometro2 += GameLoop.dt;
		
		boolean clickDisparo = InputMouse.isClicked();
		
		if (cronometro2 > 1000 && clickDisparo) {
				disparar();
				System.out.println("Disparo");
				cronometro2 = 0;
		}

		for (int i = 0; i < flechas.getAll().length; i++) {
			if (flechas.get(i) != null) {
				Flecha flechaAux = (Flecha) flechas.get(i);

				flechaAux.getMovimiento().mover(flechaAux.getTransformar(), 0.5 * GameLoop.dt);

				if (flechaAux.getViva()) {
					destruirFlecha(flechaAux);
				}
			}
		}
		
		// if (slash != null) {
		// slash.actualizar();
		// }

		// if (InputMouse.isClicked()) {
		// crearAtaqueEspada();
		// destruir();
		// }

		movimiento();
		actualizarMira();
		rotacion();
		limite();

		cuerpo.getColisiona().actualizar();
		cuerpo.actualizar();
		mira.actualizar();
		corazon.actualizar();
		textoVidas.actualizar();
		flechas.actualizar();
		cambiarArma();
		// slash.actualizar();
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

	public void cambiarArma() {

		if (InputKeyboard.isKeyPressed(motor_v1.motor.input.Key.R)) {
			cuerpo.setTextura(Assets.jugador);
		}

		if (InputKeyboard.isKeyPressed(motor_v1.motor.input.Key.F)) {
			cuerpo.setTextura(Assets.jugadorEspada);
		}

	}

	public void crearAtaqueEspada() {
		Vector2D p = new Vector2D(100, 100);
		slash = new Gif(getNombre(), Assets.slash, p, 100);
		Vector2D centro = slash.getTransformar().getPosicion();

		Vector2D posicionMouse = new Vector2D(InputMouse.getX(), InputMouse.getY());
		double angulo = centro.getAnguloHacia(posicionMouse);
		double desfase = 90;
		slash.getTransformar().rotarloA(angulo + desfase);

		slash.setLoop(true);
		slash.setDestruir(true);

	}

	public void ataque() {

		// if (cuerpo.getTextura() == Assets.jugadorEspada && InputMouse.isClicked()){
		// crearAtaqueEspada();
		// destruir();

		// }

	}

	@Override
	public void destruir() {
		// slash.destruir();
		flechas.destruir();
	}

	@Override
	public void dibujar(Graphics g) {
		mira.dibujar(g);
		cuerpo.dibujar(g);
		corazon.dibujar(g);
		textoVidas.dibujar(g);
		flechas.dibujar(g);
//		flecha.dibujar(g);
		// slash.dibujar(g);
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
