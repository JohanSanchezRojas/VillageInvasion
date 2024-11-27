package juego.escenas;

import java.awt.Graphics;

import juego.Assets;
import juego.Conf;
import juego.entidades.Bloque;
import juego.entidades.EnemigoProvisional;
import juego.entidades.Jugador;
import motor_v1.motor.Scene;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.Key;
import motor_v1.motor.util.Vector2D;

public class EscenaNivel3 extends Scene{
	private SpriteSolido fondoNivel;
	private Jugador jugador;
	private Bloque[] bloques = new Bloque[8];
	private EnemigoProvisional[] bill = new EnemigoProvisional[1];

	
	
	
	public EscenaNivel3() {
		super();
		
		
		Wallpaper();
		crearJugador();
		crearBloques();
		crearEnemigoProvisional();		
		
		
	}

	@Override
	public void actualizar() {
		if (InputKeyboard.isKeyPressed(Key.SHIFT)) {
			Scene.cambiarEscena(new EscenaBienvenida());
		}
		fondoNivel.actualizar();
		jugador.actualizar();
		actualizarBloques();
		colisionBloqueJugador();
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
		dibujarBloques(arg0);
		dibujarEnemigosProvisional(arg0);
		
		
		
		
	}
	
	public void Wallpaper() {
		fondoNivel = new SpriteSolido("Fondo", Assets.fondoCesped);
		
	}
	
	public void crearJugador() {
		Vector2D p = new Vector2D(100, Conf.HEIGHT - 200);
		jugador = new Jugador(p, 10);
	}
	
	public void crearBloques() {
		Bloque bloque;
		int y = 0 + Conf.WOOD_LADO / 2;
		
		int y2 = Conf.HEIGHT - Conf.WOOD_LADO / 2;
		
		for (int i = 0; i < bloques.length; i++) {
			
			if(i <= 3) {
				Vector2D p = new Vector2D(Conf.WIDTH / 1.5, y);
				bloque = new Bloque("madera" + i, Assets.madera, p);
				bloque.getTransformar().setPosicion(p.subtract(bloque.getCentroRotacion()));
				bloques[i] = bloque;
				y = y + 80;
			}else if(i >= 4 ){
				Vector2D p = new Vector2D(Conf.WIDTH / 2.5, y2);
				bloque = new Bloque("madera" + i, Assets.madera, p);
				bloque.getTransformar().setPosicion(p.subtract(bloque.getCentroRotacion()));
				bloques[i] = bloque;
				y2 = y2 - 80;
				
				
			}
			
			
			
			
		}
		
		
		
		
	}
	
	public void colisionBloqueJugador() {
		for (int i = 0; i < bloques.length; i++) {
			if(bloques[i] != null) {
				if(jugador.getColisiona().colisionaCon(bloques[i].getColisiona())){
					jugador.jugadorColision(bloques[i]);
					
				}
				
			}
			
		}
		
	}
	
	public void dibujarBloques(Graphics arg0) {
		for (int i = 0; i < bloques.length; i++) {
			if(bloques[i] != null) {
				bloques[i].dibujar(arg0);
				
			}
			
		}
		
	}
	
	public void actualizarBloques() {
		for (int i = 0; i < bloques.length; i++) {
			if(bloques[i] != null) {
				bloques[i].actualizar();;
				
			}
			
		}
		
	}
	
	public void crearEnemigoProvisional() {
		
		bill[0] = new EnemigoProvisional(new Vector2D(Conf.WIDTH - 150, 150));
	}
	
	public void dibujarEnemigosProvisional(Graphics arg0) {
		for (int i = 0; i < bill.length; i++) {
			bill[i].dibujar(arg0);
			
		}
	}
	
	public void siguienteNivel() {
		
		for (int i = 0; i < bill.length; i++) {
			if(jugador.getColisiona().colisionaCon(bill[i].getColisiona())) {
				Scene.cambiarEscena(new EscenaNivel4());
			}
			
		}
		
		
	}
}
