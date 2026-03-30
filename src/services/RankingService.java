package services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;

import org.json.JSONObject;
import org.json.JSONTokener;

import models.Player;
/**
 * Servicio que gestiona el ranking de jugadores.
 * <p>
 * Permite obtener los jugadores almacenados en el archivo {@code ranking.json} 
 * y actualizar el ranking con nuevas puntuaciones.
 */
public class RankingService {
	/** Posiciones del ranking, de primero a quinto */
	private static final String[] POSITIONS = { "First", "Second", "Third", "Fourth", "Fifth" };
	/**
     * Obtiene los jugadores almacenados en {@code ranking.json}.
     * <p>
     * Devuelve un array de tamaño 6, donde los primeros 5 representan el ranking
     * real y el sexto es temporal para operaciones internas (como añadir un nuevo jugador).
     *
     * @return array de jugadores en el ranking, ordenados de mayor a menor puntuación
     */
	public Player[] obtenerPlayers() {
		Player[] aP = new Player[6];
		try {
			JSONObject jO = new JSONObject(new JSONTokener(new FileInputStream("src/data/ranking.json")));
			for (int i = 0; i < POSITIONS.length; i++)
				if (jO.get(POSITIONS[i]) != JSONObject.NULL) {
					JSONObject jOb = (JSONObject) jO.get(POSITIONS[i]);
					aP[i] = new Player(jOb.getString("Nombre"), jOb.getInt("Puntos"));
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		return aP;
	}
	
	/**
     * Actualiza el ranking con un nuevo jugador.
     * <p>
     * Añade el jugador al ranking si su puntuación está entre las 5 mejores,
     * o actualiza su puntuación si ya estaba en el ranking y ha mejorado.
     * El ranking se ordena automáticamente de mayor a menor puntuación.
     *
     * @param player jugador a añadir o actualizar en el ranking
     */
	public void update(Player player) {
		Player[] campeones = obtenerPlayers();
		int i = contiene(campeones, player);
		if(i == -1) // Si no estaba en el ranking, se añade temporalmente
			campeones[5] = player;			
		else if (player.puntuacion > campeones[i].puntuacion) // Si mejora su puntuación
			campeones[i] = player;
		// Ordena de mayor a menor puntuación, dejando nulls al final
		Arrays.sort(campeones, Comparator.nullsLast((a,b) -> b.puntuacion - a.puntuacion)); //Ns si esto hace el truco, pero por probar
		// Guarda los 5 primeros en el archivo JSON
		try (PrintStream pS = new PrintStream(new FileOutputStream("src/data/ranking.json"))) {
			JSONObject jO = new JSONObject();
			for (int j = 0; j < POSITIONS.length; j++)
				jO.put(POSITIONS[j], (campeones[j] != null) ? campeones[j].asJson() : JSONObject.NULL);
			pS.print(jO.toString(2));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
     * Comprueba si un jugador ya está en el ranking.
     *
     * @param ps array de jugadores
     * @param p  jugador a comprobar
     * @return índice del jugador si está en el ranking, -1 si no está
     */
	private int contiene(Player[] ps, Player p) {
		for(int i=0;i<6;i++)
			if(ps[i] != null && ps[i].nombre.equals(p.nombre))
				return i;
		return -1;
	}
	
}
