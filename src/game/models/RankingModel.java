package game.models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;

import org.json.JSONObject;
import org.json.JSONTokener;

import game.dao.Player;

public class RankingModel {
	private static final String[] POSITIONS = { "First", "Second", "Third", "Fourth", "Fifth" };

	public static Player[] jsonAJava() {
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
	public static void javaAJson(Player[] campeones) {
		//Ordena los players
		Arrays.sort(campeones, Comparator.nullsLast((a, b) -> Integer.compare(b.getPuntuacion(), a.getPuntuacion())));
		//ALmacena los 5 de mayor puntuacion
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
