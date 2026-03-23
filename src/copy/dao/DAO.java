package copy.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;

import org.json.JSONObject;
import org.json.JSONTokener;

public class DAO {

	public static Player[] jsonAJava() {
		Player[] aP = new Player[6];
		try {
			JSONObject jO = new JSONObject(new JSONTokener(new FileInputStream("src/copy/guardado/ranking.json")));
			String[] puestos = { "First", "Second", "Third", "Fourth", "Fifth" };
			for (int i = 0; i < puestos.length; i++)
				if (jO.get(puestos[i]) != JSONObject.NULL) {
					JSONObject jOb = (JSONObject) jO.get(puestos[i]);
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
		Player[] campeonesReales = new Player[campeones.length];
		for (int i = 0, c = 0; i < campeones.length; i++)
			if (campeones[i] != null)
				campeonesReales[c++] = campeones[i];
		Arrays.sort(campeonesReales,
				Comparator.nullsLast((a, b) -> Integer.compare(b.getPuntuacion(), a.getPuntuacion())));

		try (PrintStream pS = new PrintStream(new FileOutputStream("src/copy/guardado/ranking.json"))) {
			JSONObject jO = new JSONObject();
			jO.put("First",  (campeonesReales[0] != null) ? campeonesReales[0].asJson() : JSONObject.NULL);
			jO.put("Second", (campeonesReales[1] != null) ? campeonesReales[1].asJson() : JSONObject.NULL);
			jO.put("Third",  (campeonesReales[2] != null) ? campeonesReales[2].asJson() : JSONObject.NULL);
			jO.put("Fourth", (campeonesReales[3] != null) ? campeonesReales[3].asJson() : JSONObject.NULL);
			jO.put("Fifth",  (campeonesReales[4] != null) ? campeonesReales[4].asJson() : JSONObject.NULL);
			pS.print(jO.toString(2));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
