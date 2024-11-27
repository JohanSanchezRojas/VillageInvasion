package juego.escenas;

import java.awt.Graphics;

import juego.Assets;
import juego.Conf;
import juego.entidades.Bloque;
import juego.entidades.EnemigoBallesta;
import juego.entidades.EnemigoProvisional;
import juego.entidades.Flecha;
import juego.entidades.Jugador;
import motor_v1.motor.GameLoop;
import motor_v1.motor.Scene;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.InputMouse;
import motor_v1.motor.input.Key;
import motor_v1.motor.util.Vector2D;

public class EscenaNivel1 extends Scene {

	private SpriteSolido fondoNivel;
	private EnemigoProvisional bill;
	private Jugador jugador;
	private Bloque[] bloques = new Bloque[5];
	private EnemigoBallesta enemigoB;

	public EscenaNivel1() {
		super();
		Wallpaper();
		crearJugador();
		crearBloques();
		crearEnemigoProvisional();
		crearEnemigos();

	}

	@Override
	public void actualizar() {
		if (InputKeyboard.isKeyPressed(Key.SHIFT)) {
			Scene.cambiarEscena(new EscenaBienvenida());
		}

		bill.actualizar();
		fondoNivel.actualizar();
		jugador.actualizar();
		actualizarBloques();
		colisionBloqueJugador();
		colisionFlechaJugador();
		siguienteNivel();

	}

	@Override
	public void destruir() {

	}

	@Override
	public void dibujar(Graphics arg0) {
		fondoNivel.dibujar(arg0);
		jugador.dibujar(arg0);
		jugador.getMira().dibujar(arg0);
		jugador.getCorazon().dibujar(arg0);
		jugador.getTextoVidas().dibujar(arg0);
		bill.dibujar(arg0);
		dibujarBloques(arg0);
		enemigoB.dibujar(arg0);
	}

	public void Wallpaper() {
		fondoNivel = new SpriteSolido("Fondo", Assets.fondoCesped);

	}

	public void crearJugador() {
		Vector2D p = new Vector2D(150, Conf.HEIGHT / 2);
		jugador = new Jugador(p, 10);
	}

	public void crearEnemigos() {
		enemigoB = new EnemigoBallesta(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT /2-75));

	}

	public void crearBloques() {
		Bloque bloque;
		int y = (Conf.HEIGHT / 2) - 160;

		for (int i = 0; i < bloques.length; i++) {

			Vector2D p = new Vector2D(Conf.WIDTH / 2, y);
			bloque = new Bloque("madera" + i, Assets.madera, p);
			bloque.getTransformar().setPosicion(p.subtract(bloque.getCentroRotacion()));

			bloques[i] = bloque;

			y = y + 80;
		}

	}

	public void colisionBloqueJugador() {
		for (int i = 0; i < bloques.length; i++) {
			if (bloques[i] != null) {
				if (jugador.getColisiona().colisionaCon(bloques[i].getColisiona())) {
					jugador.jugadorColision(bloques[i]);

				}

			}

		}

	}

	public void colisionFlechaJugador() {
		for (int i = 0; i < jugador.flechas.getAll().length; i++) {
			if (jugador.flechas.get(i) != null) {
				Flecha flechaAux = (Flecha) jugador.flechas.get(i);
				for (int j = 0; j < bloques.length; j++) {
					if (bloques[j] != null) {
						if (flechaAux.getColisiona().colisionaCon(bloques[j].getColisiona())) {
							if (flechaAux.getViva()) {
								jugador.flechaColision(bloques[j], flechaAux);
								System.out.println("Bloque");
							}
						}
					}
				}
			}
		}
	}

	public void dibujarBloques(Graphics arg0) {
		for (int i = 0; i < bloques.length; i++) {
			if (bloques[i] != null) {
				bloques[i].dibujar(arg0);

			}

		}

	}

	public void actualizarBloques() {
		for (int i = 0; i < bloques.length; i++) {
			if (bloques[i] != null) {
				bloques[i].actualizar();
				;

			}

		}

	}

	public void crearEnemigoProvisional() {
		bill = new EnemigoProvisional(new Vector2D(Conf.WIDTH - 150, Conf.HEIGHT / 2));

	}

	public void siguienteNivel() {
		if (jugador.getColisiona().colisionaCon(bill.getColisiona())) {
			Scene.cambiarEscena(new EscenaNivel2());
		}

	}

}

