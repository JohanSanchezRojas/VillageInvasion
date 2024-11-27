package juego.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import motor_v1.motor.Entidad;
import motor_v1.motor.entidades.SpriteMovible;
import motor_v1.motor.util.Vector2D;

public class Proyectil extends Entidad{

	private String nombre;
	private int dano;
	private Vector2D posicionInicial;
	private double angulo;
	private SpriteMovible sprite;
	
	public Proyectil(String nombre, int dano, Vector2D posicionInicial, double angulo, SpriteMovible sprite) {
		super();
		this.nombre = nombre;
		this.dano = dano;
		this.posicionInicial = posicionInicial;
		this.angulo = angulo;
		this.sprite = sprite;
	}

	@Override
	public void actualizar() {
		actualizar();
	}

	@Override
	public void destruir() {
		destruir();
	}

	@Override
	public void dibujar(Graphics g) {
		dibujar(g);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		this.dano = dano;
	}

	public Vector2D getPosicionInicial() {
		return posicionInicial;
	}

	public void setPosicionInicial(Vector2D posicionInicial) {
		this.posicionInicial = posicionInicial;
	}

	public double getAngulo() {
		return angulo;
	}

	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}

	public SpriteMovible getSprite() {
		return sprite;
	}

	public void setSprite(SpriteMovible sprite) {
		this.sprite = sprite;
	}

}