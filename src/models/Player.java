package models;

import org.json.JSONObject;

public class Player{
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

	public JSONObject asJson() {
		JSONObject jO = new JSONObject();
		jO.put("Nombre", nombre);
		jO.put("Puntos", puntuacion);
		return jO;
	}
	
	public String getNombre() {
		return nombre;
	}
	public int getPuntuacion() {
		return puntuacion;
	}
	
	/**
	 * Se considera que dos jugadores son equivalents cuando cuentan con el mismo nombre
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		return ((Player)obj).nombre.equals(nombre);
	}
	 
	
}
