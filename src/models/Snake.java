package models;

import java.awt.Dimension;
import java.util.LinkedList;

/**
 * Representa la serpiente del juego.
 * <p>
 * Mantiene la lista de segmentos de la serpiente, su dirección actual y su
 * intención de movimiento, así como métodos para moverla, cambiar dirección y
 * crecer cuando come una manzana.
 */
public class Snake {
	/** Lista de coordenadas que representan los segmentos de la serpiente */
	private LinkedList<Coordenada> serpiente;
	/** Dirección actual de movimiento */
	private Direccion direccion;
	/** Nueva dirección que la serpiente pretende tomar en el siguiente tick */
	private Direccion nuevaDireccion;
	/** Coordenada de la cabeza de la serpiente */
	private Coordenada cabeza;
	/** Coordenada de la cola de la serpiente */
	private Coordenada cola;
	/** Dimensiones de la cuadrícula del juego */
	private Dimension cuadricula;

	/**
	 * Constructor que inicializa la serpiente en el centro de la cuadrícula con una
	 * longitud inicial de 5 segmentos y dirección hacia la derecha.
	 *
	 * @param filas    número de filas de la cuadrícula
	 * @param columnas número de columnas de la cuadrícula
	 */
	public Snake(int filas, int columnas) {
		serpiente = new LinkedList<Coordenada>();
		cuadricula = new Dimension(filas, columnas);
		// Establecer el principio
		for (int i = 0, f = (int) (cuadricula.getWidth() / 2 - 2), c = (int) (cuadricula.getHeight() / 2); i < 5; i++)
			serpiente.add(new Coordenada(f, c++));
		cabeza = serpiente.getLast();
		cola = serpiente.getFirst();
		direccion = Direccion.DERECHA;
		nuevaDireccion = Direccion.DERECHA;
	}

	/** @return lista de segmentos de la serpiente */
	public LinkedList<Coordenada> getSerpiente() {
		return serpiente;
	}

	/** @return dirección actual de la serpiente */
	public Direccion getDireccion() {
		return direccion;
	}

	/**
	 * @return nueva dirección que la serpiente intentará tomar en el siguiente tick
	 */
	public Direccion getNuevaDireccion() {
		return nuevaDireccion;
	}
	/** @return coordenada de la cabeza de la serpiente */
	public Coordenada getCabeza() {
		return cabeza;
	}

	 /**
     * Mueve la serpiente en la dirección de {@code nuevaDireccion}.
     * <p>
     * Agrega una nueva cabeza en la dirección indicada y elimina la cola
     * si la serpiente no debe crecer.
     */
	public void mover() {// Volvio a mover, pq aca tiene mas sentido
		int f = 0, c = 0;
		switch (nuevaDireccion) {
		case DERECHA -> c++;
		case IZQUIERDA -> c--;
		case ARRIBA -> f--;
		case ABAJO -> f++;
		}
		// Se añade la nueva cabeza
		serpiente.add(new Coordenada(cabeza, f, c));
		cabeza = serpiente.getLast();
		 // Elimina la cola (el método crece() la agregará de nuevo si corresponde)
		serpiente.removeFirst();

	}
	/** Actualiza la dirección actual a la nueva dirección declarada */
	public void actualizarDireccion() {
		direccion = nuevaDireccion;
	}
	/**
     * Declara la intención de movimiento de la serpiente.
     * <p>
     * Solo se permite cambiar la dirección si no es opuesta a la actual.
     *
     * @param dir nueva dirección que se desea tomar
     */
	public void declararIntencion(Direccion dir) {
		switch (dir) {
		case ARRIBA -> {
			if (direccion != Direccion.ABAJO)
				nuevaDireccion = dir;
		}
		case ABAJO -> {
			if (direccion != Direccion.ARRIBA)
				nuevaDireccion = dir;
		}
		case DERECHA -> {
			if (direccion != Direccion.IZQUIERDA)
				nuevaDireccion = dir;
		}
		case IZQUIERDA -> {
			if (direccion != Direccion.DERECHA)
				nuevaDireccion = dir;
		}
		}

	}

	/**
     * Controla el crecimiento de la serpiente.
     * <p>
     * Debe llamarse en cada tick; si {@code b} es true, la serpiente crece
     * agregando la cola anterior. Si es false, simplemente actualiza la cola.
     *
     * @param b indica si la serpiente debe crecer
     */
	public void crece(boolean b) { 
		if (b)
			serpiente.addFirst(cola);
		else
			cola = serpiente.getFirst();
	}

}
