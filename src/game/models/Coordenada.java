package game.models;
/**
 * Clase utilizada para representar una coordenada, cuenta con una variable {@code fila} 
 * y otra {@code columna} para poder representar su posicion espacial en el ambiente 
 * correspondiente
 */
public class Coordenada {
	int fila, columna;
	/**
	 * Crea una nueva coordenada a partir de las posiciones de fila y
	 * columna introducidas
	 * @param fila Posición en la fila 
	 * @param columna Posición en la columna
	 */
	public Coordenada(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
	}
	/**
	 * Permite crear una nueva coordenada a partir de una coordenada existente,
	 * tomando sus filas y columnas e incrementandolas en el valor indicado en los 
	 * parametros fila y columna
	 * 
	 * @param cord Coordena a clonar 
	 * @param fila Incremento de la fila respecto a la coordenada introducida
	 * @param columna Incremento de la columna respecto a la coordenada introducida
	 */
	public Coordenada(Coordenada cord, int filas, int columnas) {
		this.fila = cord.fila + filas;
		this.columna = cord.columna + columnas;
	}
	
	
	
}
