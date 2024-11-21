package juego.escenas;

import java.awt.Graphics;
import java.util.Random;

import juego.Assets;
import juego.Conf;
import motor_v1.motor.GameLoop;
import motor_v1.motor.Scene;
import motor_v1.motor.entidades.Gif;
import motor_v1.motor.entidades.ListaEntidades;
import motor_v1.motor.input.InputMouse;
import motor_v1.motor.util.Vector2D;

public class EscenaInicio extends Scene{

	private Gif explosionColision;
	private double cronometro;
	private ListaEntidades listaExplosiones;
	
	public static final int DURACION_EXPLOSION = 100; // 1000 MILISEGUNDOS ES UN SEGUNDO
	public static final int TIEMPO_ESPERA = 500; // 1000 MILISEGUNDOS ES UN SEGUNDO
	
	public EscenaInicio() {
		super();
		listaExplosiones = new ListaEntidades();
	}

	@Override
	public void actualizar() {

		//CONTROLAR UNA SOLA INSTANCIA
		/*if (explosionColision!= null ) {
			explosionColision.actualizar();
		}*/
		
		listaExplosiones.actualizar();

		//CONTROLAR UNA EXPLOSION CADA VEZ QUE SE PRESIONA EL CLICK
		/*if (InputMouse.isClicked()) {
			crearExplosiones();
		}*/
		
		crearExplosiones();
		
		destruir(); 
	}
	

	@Override
	public void dibujar(Graphics g) {
		//DIBUJAR UNA SOLA INSTANCIA
		/*if (explosionColision!= null ) {
			explosionColision.dibujar(g);
		}*/
		
		listaExplosiones.dibujar(g);
	}

	@Override
	public void destruir() {
		listaExplosiones.destruir();
	}
	
// OTROS METODOS
	
	private void crearExplosiones() {
		cronometro += GameLoop.dt; // ACUMULADOR QUE FUNCIONA COMO UN CRONOMETRO
		if (cronometro > TIEMPO_ESPERA) {
			cronometro = 0;
			Vector2D posicion = new Vector2D(posicionAzar(0, Conf.WIDTH), posicionAzar(0, Conf.WIDTH));
			explosionColision = new Gif("explosion", Assets.explosionColision, posicion, DURACION_EXPLOSION );
			explosionColision.setLoop(false); // EL GIF SOLO SE REPITE UNA SOLA VEZ
			explosionColision.setDestruir(true); // EL GIF SE REPITE UNA SOLA VEZ Y LUEGO SE DESTRUYE
			
			listaExplosiones.add("explosion", explosionColision);
		}
	}

	//RANDOM ENTRE UNA VALOR MINIMO Y UN VALOR MAXIMO PARA CREAR POSICIONES X Y Y 
	private int posicionAzar(int minimo, int maximo) {
		Random random = new Random();
		return minimo + random.nextInt(maximo);
	}
	
	

}
