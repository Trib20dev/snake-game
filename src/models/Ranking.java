package models;

import java.util.Arrays;
import java.util.Comparator;
/**
 * Representa un ranking de jugadores.
 * <p>
 * Mantiene un listado de los 5 mejores jugadores según su puntuación.
 * Permite añadir nuevos jugadores y actualizar el ranking automáticamente.
 */
public class Ranking {
	/** Array que almacena los jugadores del ranking (máximo 5) */
	private Player[] players;

	  /**
     * Constructor del ranking.
     * 
     * @param players Array inicial de jugadores. Se recomienda que tenga tamaño 5 o 6,
     *                siendo el último usado temporalmente para añadir un nuevo jugador
     *                antes de ordenar y descartar el que quede fuera.
     */
	public Ranking(Player[] players) {
		this.players = players;
	}
	 /**
     * Devuelve los jugadores del ranking ordenados de mayor a menor puntuación.
     * 
     * @return array de jugadores, donde el primero tiene la mayor puntuación
     */
	public Player[] getPlayers() {
		return players;
	}

    /**
     * Añade un jugador al ranking.
     * <p>
     * Si el jugador ya existe (mismo nombre), actualiza su puntuación solo si
     * es mayor que la anterior. Luego ordena el ranking de mayor a menor
     * puntuación y asegura que solo queden los 5 mejores.
     *
     * @param player jugador a añadir o actualizar en el ranking
     */
	public void añadirPlayer(Player player) {
		int i= contiene(player);
		
		if (i == -1) // Si no esta
			players[5] = player;
		else if (player.puntuacion > players[i].puntuacion) // Si esta y mejora
			players[i] = player;
		
		// Ordena de mayorr a menor
		Arrays.sort(players, Comparator.nullsLast((a, b) -> Integer.compare(b.puntuacion, a.puntuacion)));
		
		// Se asegura de que no queda ningun innecesario al final
		players[5] = null;
	}
	 /**
     * Comprueba si un jugador ya está en el ranking.
     *
     * @param player jugador a comprobar
     * @return índice del jugador si está en el ranking, -1 si no está
     */
	private int contiene(Player player) {
		for (int i = 0; i < players.length; i++) {
			if (player.equals(players[i]))
				return i;
		}
		return -1;
	}

}
