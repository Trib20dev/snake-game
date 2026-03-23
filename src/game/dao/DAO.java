package game.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class DAO {
	
	public static Player[] jsonAJava() {
		JSONObject jO = null;
		JSONTokener jT;
		try {
			jT = new JSONTokener(new FileInputStream("src/game/guardado/ranking.json"));
			jO = new JSONObject(jT);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

		Player[] pArray = new Player[6];
		if (jO.get("First") != JSONObject.NULL) {
			pArray[0] = new Player(jO.getJSONObject("First").getString("Nombre"),
					jO.getJSONObject("First").getInt("Puntos"));
		}
		if (jO.get("Second") != JSONObject.NULL) {
			pArray[1] = new Player(jO.getJSONObject("Second").getString("Nombre"),
					jO.getJSONObject("Second").getInt("Puntos"));
		}
		if (jO.get("Third") != JSONObject.NULL) {
			pArray[2] = new Player(jO.getJSONObject("Third").getString("Nombre"),
					jO.getJSONObject("Third").getInt("Puntos"));
		}
		if (jO.get("Fourth") != JSONObject.NULL) {
			pArray[3] = new Player(jO.getJSONObject("Fourth").getString("Nombre"),
					jO.getJSONObject("Fourth").getInt("Puntos"));
		}
		if (jO.get("Fifth") != JSONObject.NULL) {
			pArray[4] = new Player(jO.getJSONObject("Fifth").getString("Nombre"),
					jO.getJSONObject("Fifth").getInt("Puntos"));
		}

		return pArray;
	}

	/**
	 * Almacena las 5 mejores runs con mayor puntuacion que le pases
	 * 
	 * Cambio de planes, voy a hacer q implemente comparable y que use sort, q igual es mas facil
	 * Ahora solo hay q añadir a la lista q te da el otro, q es de tamaño 6, el nuevo resultado, 
	 * y el sort lo añade automaticamente si es necesario
	 * 
	 * @param campeones Los jugadores que se almacenaran en el ranking (Solo almacena los primeros 5)
	 */
	public static void javaAJson(Player[] campeones) {
		int c = 0;
		for(var n: campeones)
			if(n != null) c++;
		Player[] campeonesReales = new Player[c];
		c=0;
		for(var n: campeones)
			if(n != null) campeonesReales[c++] = n;
		Arrays.sort(campeonesReales);
		//Puedo escribir el objeto con un new, y luego usar un PrintStream
		try (FileWriter fW = new FileWriter("src/game/guardado/ranking.json")){
			JSONWriter jW = new JSONWriter(fW);
			jW.object();
			jW.key("First");
			//Igual lo modifico pa usar terniarios
			if (campeonesReales.length > 0 && campeonesReales[0] != null)
				jW.object().key("Nombre").value(campeonesReales[0].nombre).key("Puntos").value(campeonesReales[0].puntuacion)
						.endObject();
			else
				jW.value(null);
			jW.key("Second");
			if (campeonesReales.length > 1 && campeonesReales[1] != null)
				jW.object().key("Nombre").value(campeonesReales[1].nombre).key("Puntos").value(campeonesReales[1].puntuacion)
						.endObject();
			else
				jW.value(null);
			jW.key("Third");
			if (campeonesReales.length > 2 && campeonesReales[2] != null)
				jW.object().key("Nombre").value(campeonesReales[2].nombre).key("Puntos").value(campeonesReales[2].puntuacion)
						.endObject();
			else
				jW.value(null);
			jW.key("Fourth");
			if (campeonesReales.length > 3 && campeonesReales[3] != null)
				jW.object().key("Nombre").value(campeonesReales[3].nombre).key("Puntos").value(campeonesReales[3].puntuacion)
						.endObject();
			else
				jW.value(null);	
			jW.key("Fifth");
			if (campeonesReales.length > 4 && campeonesReales[4] != null)
				jW.object().key("Nombre").value(campeonesReales[4].nombre).key("Puntos").value(campeonesReales[4].puntuacion)
						.endObject();
			else
				jW.value(null);
			
			jW.endObject();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
