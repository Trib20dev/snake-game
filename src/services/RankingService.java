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

public class RankingService {
	private static final String[] POSITIONS = { "First", "Second", "Third", "Fourth", "Fifth" };
	/**
	 * TODO Devuelve los players en el ranking.json en formato Player[]
	 * @return
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
	public void update(Player player) {
		Player[] campeones = obtenerPlayers();
		int i = contiene(campeones, player);
		if(i == -1)
			campeones[5] = player;			
		else if (player.puntuacion > campeones[i].puntuacion) 
			campeones[i] = player;
		
		Arrays.sort(campeones, Comparator.nullsLast((a,b) -> b.puntuacion - a.puntuacion)); //Ns si esto hace el truco, pero por probar
		//Almacena los 5 de mayor puntuacion q le pases
		try (PrintStream pS = new PrintStream(new FileOutputStream("src/data/ranking.json"))) {
			JSONObject jO = new JSONObject();
			for (int j = 0; j < POSITIONS.length; j++)
				jO.put(POSITIONS[j], (campeones[j] != null) ? campeones[j].asJson() : JSONObject.NULL);
			pS.print(jO.toString(2));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private int contiene(Player[] ps, Player p) {
		for(int i=0;i<6;i++)
			if(ps[i] != null && ps[i].getNombre().equals(p.getNombre()))
				return i;
		return -1;
	}
	
}
