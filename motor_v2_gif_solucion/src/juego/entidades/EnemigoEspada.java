package juego.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.Assets;
import motor_v1.motor.GameLoop;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;

public class EnemigoEspada extends Enemigo{

	private double cronometro;
	public static final int TIEMPO_DE_ESPERA = 1000;
    private int direccionActual; // Dirección que sigue el enemigo actualmente
    public static final int TIEMPO_CAMBIO_DIRECCION = 1000; // Tiempo entre cambios de dirección (en ms)
    
	public EnemigoEspada(Vector2D posicion) {
		super("Enemigo", new SpriteMovible("Enemigo", Assets.enemigoEDown), posicion, null);
		super.setNumeroVidas(2);
		
		direccionActual = MovimientoRandom(); // Dirección inicial aleatoria
	}

	public void actualizar() {
		super.actualizar();
		//movimiento();
	}

	

	@Override
	public void destruir() {
	}

	@Override
	public void dibujar(Graphics g) {
		super.getCuerpo().dibujar(g);

	}

	private int MovimientoRandom() {
        int min = 1; // Mínimo: dirección arriba
        int max = 5; // Máximo: dirección derecha (4 direcciones posibles)
        return (int) (Math.random() * (max - min) + min); // Retorna un número aleatorio entre 1 y 4
    }

	public void movimiento() {
		cronometro += GameLoop.dt;
		
		 // Cambiar dirección si se supera el tiempo especificado
       if (cronometro > TIEMPO_CAMBIO_DIRECCION) {
           direccionActual = MovimientoRandom();
           cronometro = 0;
       }

       // Moverse en la dirección actual
       switch (direccionActual) {
           case 1: // Arriba
               super.getCuerpo().getMovimiento().setDireccion(Vector2D.UP);
               super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(),super.getCuerpo().FACTOR_VELOCIDAD * GameLoop.dt);
               super.getCuerpo().setTextura(Assets.enemigoEUp);
               break;
           case 2: // Abajo
        	   super.getCuerpo().getMovimiento().setDireccion(Vector2D.DOWN);
        	   super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), super.getCuerpo().FACTOR_VELOCIDAD * GameLoop.dt);
        	   super.getCuerpo().setTextura(Assets.enemigoEDown);
               break;
           case 3: // Izquierda
        	   super.getCuerpo().getMovimiento().setDireccion(Vector2D.LEFT);
        	   super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(),super.getCuerpo().FACTOR_VELOCIDAD * GameLoop.dt);
        	   super.getCuerpo().setTextura(Assets.enemigoELeft);
               break;
           case 4: // Derecha
        	   super.getCuerpo().getMovimiento().setDireccion(Vector2D.RIGHT);
        	   super.getCuerpo().getMovimiento().mover(super.getCuerpo().getTransformar(), super.getCuerpo().FACTOR_VELOCIDAD * GameLoop.dt);
        	   super.getCuerpo().setTextura(Assets.enemigoERight);
               break;
       }
		
	}

	@Override
	public void atacar() {
		// TODO Auto-generated method stub
		
	}
}
