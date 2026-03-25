package game.models;

public class Score {
	public int points;
	public int mPoints;

	/**
	 * Constructor which starts point and max points at 0
	 */
	public Score() {
		points = 0;
		this.mPoints = 0;
	}

	/**
	 * Set the mPoints propertie to the input one
	 * @param mPoints Stored max points
	 */
	public void setMaxPoints(int mPoints) {
		this.mPoints = mPoints;
	}

}
