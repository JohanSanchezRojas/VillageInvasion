package juego.escenas;

import java.awt.Graphics;

import juego.Assets;
import juego.Conf;
import juego.entidades.Bloque;
import juego.entidades.EnemigoProvisional;
import juego.entidades.Flecha;
import juego.entidades.Jugador;
import motor_v1.motor.Scene;
import motor_v1.motor.entidades.Gif;
import motor_v1.motor.entidades.ListaEntidades;
import motor_v1.motor.entidades.SpriteSolido;
import motor_v1.motor.input.InputKeyboard;
import motor_v1.motor.input.Key;
import motor_v1.motor.util.Vector2D;

public class EscenaNivel8 extends Scene{
	private SpriteSolido fondoNivel;
	private Jugador jugador;
	private EnemigoProvisional[] bill = new EnemigoProvisional[4];
	private ListaEntidades listaBloques;
	private Gif romper;
	
	
	
	public EscenaNivel8() {
		super();
		romper = new Gif("Romper", Assets.romperFlecha, new Vector2D(0, 0), 100);
		romper.setVisible(false);
		listaBloques = new ListaEntidades();
		
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
		listaBloques.actualizar();
		colisionFlechaJugador();
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
		dibujarEnemigosProvisional(arg0);
		listaBloques.dibujar(arg0);
		
		
		
	}
	
	public void Wallpaper() {
		fondoNivel = new SpriteSolido("Fondo", Assets.fondoCesped);
		
	}
	
	public void crearJugador() {
		Vector2D p = new Vector2D(new Vector2D((Conf.WIDTH /2) - (Conf.JUGADOR_WIDTH / 2), (Conf.HEIGHT /2) - (Conf.JUGADOR_HEIGHT / 2) ));
		jugador = new Jugador(p, 10);
	}
	
	public void crearBloques() {
		Bloque bloque;
		
		
		
		
		int y = (Conf.HEIGHT / 2) - 160;
		int x = (Conf.WIDTH / 2 + 150);
		
		
		int x2 = (Conf.WIDTH / 2 - 150);
		int y2 = (Conf.HEIGHT / 2) - 160;
		
		
		
		for (int i = 0; i < 10; i++) {
			
			if(i <= 4 ) {
				Vector2D p = new Vector2D(x, y);
				bloque = new Bloque(p);
				bloque.getBloque().getTransformar().setPosicion(p.subtract(bloque.getBloque().getCentroRotacion()));
				listaBloques.add("Bloque", bloque);
			
				y = y + Conf.WOOD_LADO;
			
			}else if(i >= 5){
				Vector2D p = new Vector2D(x2, y2);
				bloque = new Bloque(p);
				bloque.getBloque().getTransformar().setPosicion(p.subtract(bloque.getBloque().getCentroRotacion()));
				listaBloques.add("Bloque", bloque);
				y2 = y2 +Conf.WOOD_LADO;
				
			}
				
			
			
		}
			
			
			
			
			
		
		
		
		
		
	}
	
	public void colisionBloqueJugador() {
		for (int i = 0; i < listaBloques.getLength(); i++) {
			if (listaBloques.get(i) != null) {
				Bloque bloque = (Bloque) listaBloques.get(i);
				if (jugador.getColisiona().colisionaCon(bloque.getColisiona())) {
					jugador.colisionBloque(bloque);
				}
			}
		}
	}
	

	public void animacionRomperFlecha(Flecha f) {
		romper = new Gif("Romper", Assets.romperFlecha, f.getFlecha().getTransformar().getPosicion(), 200);
		romper.getTransformar().rotarloA(f.getFlecha().getTransformar().getRotacion());
		romper.setLoop(false);
	}
	
	public void colisionFlechaJugador() {
		for (int i = 0; i < jugador.listaFlechas.getAll().length; i++) {
			if (jugador.listaFlechas.get(i) != null) {
				Flecha flechaAux = (Flecha) jugador.listaFlechas.get(i);
				for (int j = 0; j < listaBloques.getLength(); j++) {
					if (listaBloques.get(j) != null) {
						Bloque bloque = (Bloque) listaBloques.get(j);
						if (flechaAux.getColisiona().colisionaCon(bloque.getColisiona())) {
							if (flechaAux.getViva()) {
								animacionRomperFlecha(flechaAux);
								flechaAux.destruir();
								System.out.println("Bloque");
							}
						}
					}
				}
			}
		}
	}
	
	
	
	public void crearEnemigoProvisional() {
		
		bill[0] = new EnemigoProvisional(new Vector2D(Conf.WIDTH - 150, (Conf.HEIGHT /2) + 75));
		bill[1] = new EnemigoProvisional(new Vector2D(Conf.WIDTH - 150, (Conf.HEIGHT /2) - 75));
		bill[2] = new EnemigoProvisional(new Vector2D(100, (Conf.HEIGHT /2) + 75));
		bill[3] = new EnemigoProvisional(new Vector2D(100, (Conf.HEIGHT /2) - 75));
		
	}
	
	public void dibujarEnemigosProvisional(Graphics arg0) {
		for (int i = 0; i < bill.length; i++) {
			bill[i].dibujar(arg0);
			
		}
	}
	
	public void siguienteNivel() {
		
		for (int i = 0; i < bill.length; i++) {
			if(jugador.getColisiona().colisionaCon(bill[i].getColisiona())) {
				Scene.cambiarEscena(new EscenaNivel9());
			}
			
		}
		
		
	}
}
