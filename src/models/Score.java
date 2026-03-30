package models;

public class Score {
	/** Puntuación actual de la partida */
	public int points;
	/** Puntuación máxima alcanzada */
	public int mPoints;

	 /**
     * Constructor que inicializa tanto la puntuación actual como la máxima a 0.
     */
	public Score() {
		points = 0;
		this.mPoints = 0;
	}

	 /**
     * Establece la puntuación máxima.
     *
     * @param mPoints nueva puntuación máxima a almacenar
     */
	public void setMaxPoints(int mPoints) {
		this.mPoints = mPoints;
	}

}
