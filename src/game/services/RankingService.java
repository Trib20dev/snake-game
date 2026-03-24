package game.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.json.JSONObject;
import org.json.JSONTokener;

import game.dao.Player;

public class RankingService {
	private static final String[] POSITIONS = { "First", "Second", "Third", "Fourth", "Fifth" };
	/**
	 * TODO Devuelve los players en el ranking.json en formato Player[]
	 * @return
	 */
	public Player[] obtenerPlayers() {
		Player[] aP = new Player[6];
		try {
			JSONObject jO = new JSONObject(new JSONTokener(new FileInputStream("src/game/guardado/ranking.json")));
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
	public void guardar(Player[] campeones) {
		//Almacena los 5 de mayor puntuacion q le pases
		try (PrintStream pS = new PrintStream(new FileOutputStream("src/game/guardado/ranking.json"))) {
			JSONObject jO = new JSONObject();
			for (int i = 0; i < POSITIONS.length; i++)
				jO.put(POSITIONS[i], (campeones[i] != null) ? campeones[i].asJson() : JSONObject.NULL);
			pS.print(jO.toString(2));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
