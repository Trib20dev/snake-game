package models;

/**
 * Clase utilizada para representar una coordenada, cuenta con una variable
 * {@code fila} y otra {@code columna} para poder representar su posicion
 * espacial en el ambiente correspondiente
 */
public class Coordenada {
	/** Fila de la coordenada */
	public int f;
	/** Columna de la coordenada */
	public int c;

	/**
	 * Crea una nueva coordenada a partir de las posiciones de fila y columna
	 * introducidas
	 * 
	 * @param fila    Posición en la fila
	 * @param columna Posición en la columna
	 */
	public Coordenada(int fila, int columna) {
		this.f = fila;
		this.c = columna;
	}

	/**
	 * Permite crear una nueva coordenada a partir de una coordenada existente,
	 * tomando sus filas y columnas e incrementandolas en el valor indicado en los
	 * parametros fila y columna
	 * 
	 * @param cord    Coordenada a clonar
	 * @param filas    Incremento de la fila respecto a la coordenada introducida
	 * @param columnas Incremento de la columna respecto a la coordenada introducida
	 */
	public Coordenada(Coordenada cord, int filas, int columnas) {
		f = cord.f + filas;
		c = cord.c + columnas;
	}
	/**
	 * Comprueba si dos coordenadas son iguales comparando fila y columna.
	 *
	 * @param obj Objeto a comparar
	 * @return {@code true} si la fila y columna coinciden, {@code false} en caso contrario
	 */
	@Override
	public boolean equals(Object obj) { // Por lo que encontre es la manera tipica de escribirlo
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		Coordenada cord = (Coordenada)obj;
		return f==cord.f && c==cord.c;
	}

}
