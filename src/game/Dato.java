package game;
/**
 * Clase creada para poder trabajar con los datos con referencias en funciones<br>
 * Permite trabajar tanto con enteros como con booleanos, teniendo un constructor para
 * ambos casos<br>
 */
public class Dato {
	public Integer valorInt;
	public Boolean valorBooleano;
	/**
	 * Constructor para cuando trabajaras con un entero
	 * @param entero El entero a almacenar
	 */
	public Dato(int entero) {
		this.valorInt = entero;
	}
	/**
	 * Constructor para cuando lo que buscas es almacenar un booleano
	 * @param booleano El booleano a almacenar
	 */
	public Dato(boolean booleano) {
		this.valorBooleano = booleano;
	}
	/**
	 * Constructor vacio por si fuera necesario
	 */
	public Dato() {}
	
}
