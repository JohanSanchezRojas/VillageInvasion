package juego;
/*
 * @version 08-05-2024
 * @author Denis Gonzalez Herrera
 * */

import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

import motor_v1.motor.util.Loader;

public class Assets {
	/**********************************************************
	 * USO DE LA CLASE:
	 * 	PASO 1 - SE CREAN LAS VARIABLES ESTATICAS DE TODAS LAS IMAGENES  
	 * 	SE PUEDE UTILIZAR VARIABLES O ARREGLOS ESTATICOS EJEMPLO:
	 * -- 	public static BufferedImage[] fondoAnimado = new BufferedImage[26];
	 * -- 	public static BufferedImage jugador;
	 * -- 	public static Clip sonido
	 * -- 	public static Font fuente;
	 * 
	 * 	PASO 2- EN EL METODO inicializar SE CARGAR LAS IMAGENES UTILIZANDO LA RUTA
	 * 	URL EJEMPLO:  
	 * 	
	 * 	for(int i = 0; i < fondoAnimado.length; i++) {
	 *		fondoAnimado[i] = Loader.cargarImagen("/img/fondos/especial/" + (i+1) +".png");
	 *	}	
	 *
	 *	jugador = Loader.cargarImagen("/img/jugador1.png");
	 *
	 *  sonido = Loader.cargarSonido("/sound/space/backgroundMusic2.wav");
	 *  
	 *  fuente = Loader.cargarFuente("/font/futureFont.ttf", 20);
	 **********************************************************/

	// AQUI SE CREAN LAS CONSTANTES PARA LAS IMAGENES DEL JUEGO  
	public static BufferedImage canonTanque;
	public static BufferedImage cuerpoTanque;
	public static BufferedImage billBalazo;
	public static BufferedImage mira;
	public static BufferedImage fondoPuntaje;
	public static BufferedImage fondoMinecraft;
	public static BufferedImage iconosMenu;
	
	
	public static BufferedImage[] explosionColision = new BufferedImage[7];
	public static BufferedImage[] explosionCanon = new BufferedImage[5];
	public static BufferedImage[] humoDisparo = new BufferedImage[10];
	
	// AQUI SE CREAN LAS CONSTANTES PARA LAS FUENTES DEL JUEGO 
	public static Font font_maru;
	public static Font font_minecraft;
	public static Font font_minecraft12;
	
	// AQUI SE CREAN LAS CONSTANTES PARA LOS SONIDOS DEL JUEGO 
	public static Clip background, sonidoDisparo, sonidoExposion;
	
	public static boolean cargados() {
		
		// AQUI SE REALIZA LA CARGA DE LAS IMAGENES DEL JUEGO  
		canonTanque = Loader.cargarImagen("/assets_juego_disparos/canonTanque.png");
		cuerpoTanque = Loader.cargarImagen("/assets_juego_disparos/cuerpoTanque.png");
		billBalazo = Loader.cargarImagen("/assets_juego_disparos/billBalazo.png");
		mira = Loader.cargarImagen("/assets_juego_disparos/mira.png");
		fondoPuntaje = Loader.cargarImagen("/assets_juego_disparos/fondoPuntaje.png");
		fondoMinecraft = Loader.cargarImagen("/assets_juego_disparos/fondoMinecraft.png");
		
		 
		for(int i = 0; i < explosionColision.length; i++) {
			explosionColision[i] = Loader.cargarImagen("/assets_juego_disparos/explosionColision/" + (i) +".png");
		}		 

		for(int i = 0; i < explosionCanon.length; i++) {
			explosionCanon[i] = Loader.cargarImagen("/assets_juego_disparos/explosionCanon/" + (i) +".png");
		}		 

		for(int i = 0; i < humoDisparo.length; i++) {
			humoDisparo[i] = Loader.cargarImagen("/assets_juego_disparos/humoDisparo/" + (i) +".png");
		}		 

		// AQUI SE REALIZA LA CARGA DE LAS FUENTES DEL JUEGO  	
		 font_maru = Loader.cargarFuente("/assets_juego_disparos/fuente/maru.ttf", 25);
		 font_minecraft = Loader.cargarFuente("/assets_juego_disparos/fuente/minecraft_font.ttf", 25);
		 font_minecraft12 = Loader.cargarFuente("/assets_juego_disparos/fuente/minecraft_font.ttf", 12);
		 
		// AQUI SE REALIZA LA CARGA DE LOS SONIDOS DEL JUEGO 
		 background = Loader.cargarSonido("/assets_juego_disparos/sonidos/FinalBattle.wav");
		 sonidoDisparo = Loader.cargarSonido("/assets_juego_disparos/sonidos/playerShoot.wav");
		 sonidoExposion = Loader.cargarSonido("/assets_juego_disparos/sonidos/parry.wav");
		
		return true;
	}
}
