package game.models;

import java.util.Arrays;
import java.util.Comparator;

public class RankingModel {
	private PlayerModel[] players;

	/**
	 * Constructor del modelo, se contruye en base a el array que le pases<br>
	 * "Igual deberia usar service directamente en el constructor pa montarlo?"
	 * @param players Los players almacenados en el ranking, obtenidos con el servicio normalmente
	 */
	public RankingModel(PlayerModel[] players) {
		this.players = players;
	}
	/**
	 * TODO Ns si lo hay q documentar pero por si las moscas
	 * @return
	 */
	public PlayerModel[] getPlayers() {
		return players;
	}

	/**
	 * TODO Rehacer la documentacion aca
	 * 
	 * Almacena las 5 mejores runs con mayor puntuacion que le pases
	 * 
	 * Cambio de planes, voy a hacer q implemente comparable y que use sort, q igual
	 * es mas facil Ahora solo hay q añadir a la lista q te da el otro, q es de
	 * tamaño 6, el nuevo resultado, y el sort lo añade automaticamente si es
	 * necesario
	 * 
	 * @param campeones Los jugadores que se almacenaran en el ranking (Solo
	 *                  almacena los primeros 5)
	 */
	public void añadirPlayer(PlayerModel player) {// Igual tengo q meter aca la logica para evitar el duplicado
		// Almacena el nuevo player si hace falta
		int i= contiene(player);
		if (i == -1) // Si no esta
			players[5] = player;
		else if (player.puntuacion > players[i].puntuacion) // Si esta y coincide
			players[i] = player;
		// Ordena los players
		Arrays.sort(players, Comparator.nullsLast((a, b) -> Integer.compare(b.getPuntuacion(), a.getPuntuacion())));
		// Se asegura de que no queda ningun innecesario al final
		players[5] = null;
	}
	/**
	 * TODO Ya le pondre documentacion en algun momento
	 * @param player
	 * @return
	 */
	private int contiene(PlayerModel player) {
		for (int i = 0; i < players.length; i++) {
			if (player.equals(players[i]))//Entiendo q no puede dar problema por nulo
				return i;
		}
		return -1;
	}

}
