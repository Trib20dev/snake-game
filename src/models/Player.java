package models;

import org.json.JSONObject;

/**
 * Representa un jugador con un nombre y su puntuación.
 * <p>
 * Proporciona métodos para obtener sus datos, convertirlos a JSON y definir
 * igualdad basada en el nombre del jugador.
 */
public class Player {
	/** Nombre del jugador */
	public String nombre;
	/** Puntuación actual del jugador */
	public int puntuacion;

	/**
	 * Crea un jugador con el nombre y la puntuación indicados.
	 *
	 * @param nombre     Nombre del jugador
	 * @param puntuacion Puntuación inicial del jugador
	 */
	public Player(String nombre, int puntuacion) {
		this.nombre = nombre;
		this.puntuacion = puntuacion;
	}
	
	 /**
     * Devuelve una representación en JSON del jugador.
     *
     * @return JSONObject con las claves "Nombre" y "Puntos"
     */
	public JSONObject asJson() {
		JSONObject jO = new JSONObject();
		jO.put("Nombre", nombre);
		jO.put("Puntos", puntuacion);
		return jO;
	}
	
	
	 /**
     * Devuelve la representación en cadena del jugador.
     * <p>
     * El formato es: Nombre: Puntos (con 3 dígitos, rellenando ceros a la izquierda)
     *
     * @return String con nombre y puntuación formateada
     */
	@Override
	public String toString() {
		return String.format("%s: %03d", nombre, puntuacion);
	}

    /**
     * Compara dos jugadores.
     * <p>
     * Se considera que dos jugadores son equivalentes si tienen el mismo nombre,
     * ignorando la puntuación.
     *
     * @param obj objeto a comparar
     * @return {@code true} si el objeto es un Player con el mismo nombre,
     *         {@code false} en caso contrario
     */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		return ((Player) obj).nombre.equals(nombre);
	}

}
