package copy.dao;

import java.io.Serializable;

import org.json.JSONObject;

public class Player implements Serializable{

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
	
	 
	
}
