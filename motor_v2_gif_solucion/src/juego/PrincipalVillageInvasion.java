package juego;

/**

Johan David Sánchez Rojas C17305
Joshua Chacón Alvarez C4E105
Andrew Mora Mejia C05158*/

import motor_v1.motor.GameLoop;

public class PrincipalVillageInvasion {

	public static void main(String[] args) {
/**********************************************************
 * NOTAS:
 * 		CARGAR IMAGENES DE PRUEBAS
 * FONDOS
 * JUGADORES 
 * EXPLOCIONES
 * ENEMIGOS
 * DISPAROS
 **********************************************************/
		
		if (Assets.cargados()) {
			Game game = new Game();
			GameLoop gameLoop = new GameLoop(game);
		}
		
	}

}
