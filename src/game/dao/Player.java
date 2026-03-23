package game.dao;

import java.io.Serializable;

public class Player implements Comparable<Player>,Serializable{

	private static final long serialVersionUID = 7166783686477006589L;
	
	public String nombre;
	public int puntuacion;
	/**
	 * @param nombre
	 * @param puntuacion
	 */
	public Player(String nombre, int puntuacion) {
		this.nombre = nombre;
		this.puntuacion = puntuacion;
	}
	@Override
	public String toString() {
		return String.format("%s: %03d", nombre, puntuacion);
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getPuntuacion() {
		return puntuacion;
	}
	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}
	@Override
	public int compareTo(Player o) {
		if(o == null)
			return 0;
		return o.puntuacion - this.puntuacion;
	}
	 
	
}
