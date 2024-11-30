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
	public Sound dano;
	private int numeroVidas;
	double cronometro = 0;
	double cronometroDisparo = 1000;
	private Gif slash;
	private Flecha flecha;
	public ListaEntidades listaFlechas;

	public Jugador(Vector2D p, int vidas) {
		super();
		cuerpo = new SpriteMovible("Cuerpo", Assets.jugador, p);
		mira = new Sprite("Mira", Assets.mira);
		disparo = new Sound(Assets.sonidoDisparo);
		dano = new Sound(Assets.sonidoDano);
		this.numeroVidas = vidas;
		
		listaFlechas = new ListaEntidades();
	}


	public void flechaColision(Bloque b, Flecha flecha) {

		if (flecha.getFlecha().getColisiona().colisionaCon(b.getColisiona())) {
			flecha.destruir();
			flecha.setViva(false);
		}
	}

	private void disparar() {
		cronometroDisparo += GameLoop.dt;
		
		boolean clickDisparo = InputMouse.isClicked();
		
		if (cronometroDisparo > 1000 && clickDisparo) {
			cronometroDisparo = 0;
			disparo.play();
			
			Vector2D centro = cuerpo.getTransformar().getPosicion();
			Vector2D posicionMouse = new Vector2D(InputMouse.getX(), InputMouse.getY());
			double angulo = centro.getAnguloHacia(posicionMouse);
			double desfase = 90;
			
			flecha = new Flecha("Flecha", new SpriteMovible("Flecha", Assets.flecha));
			flecha.actualizar();
			listaFlechas.add("Flecha", flecha);
			
			for (int i = 0; i < listaFlechas.getLength(); i++) {
					flecha.getFlecha().getTransformar().rotarloA(angulo + desfase);
					flecha.getFlecha().getMovimiento().setDireccion((int) angulo);
					flecha.getFlecha().getTransformar().setPosicion(centro);
			}
		}
	
		for (int i = 0; i < listaFlechas.getLength(); i++) {
			if (listaFlechas.get(i) != null) {
				Flecha flechaAux = (Flecha) listaFlechas.get(i);

				flechaAux.getFlecha().getMovimiento().mover(flechaAux.getFlecha().getTransformar(), 1.5 * GameLoop.dt);
				
				if (flechaAux.getViva()) {
					flechaAux.colisionPantalla();
				}
			}
		}
	}

	@Override
	public void actualizar() {
		disparar();
		movimiento();
		actualizarMira();
		rotacion();
		limite();

		cuerpo.actualizar();
		mira.actualizar();
		listaFlechas.actualizar();
		cambiarArma();
	}

	public void jugadorColision(Bloque b) {

		double xJugador = cuerpo.getTransformar().getPosicion().getX();
		double xB = b.getBloque().getTransformar().getPosicion().getX();

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.RIGHT) && xJugador <= xB) {
			cuerpo.getTransformar().getPosicion().setX(xB - Conf.JUGADOR_WIDTH);
		}

		if ((cuerpo.getMovimiento().getDireccion() == Vector2D.LEFT) && xJugador >= xB) {
			cuerpo.getTransformar().getPosicion().setX(xB + Conf.WOOD_LADO);
		}

		double yJugador = cuerpo.getTransformar().getPosicion().getY();
		double yB = b.getBloque().getTransformar().getPosicion().getY();

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
		listaFlechas.destruir();
	}

	@Override
	public void dibujar(Graphics g) {
		mira.dibujar(g);
		cuerpo.dibujar(g);
		listaFlechas.dibujar(g);
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


	public int getNumeroVidas() {
		return numeroVidas;
	}

	public void setNumeroVidas(int numeroVidas) {
		this.numeroVidas = numeroVidas;
	}
	
	
}
