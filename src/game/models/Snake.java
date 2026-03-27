package game.models;

import java.awt.Dimension;
import java.util.LinkedList;

public class Snake {
	private LinkedList<Coordenada> serpiente;
	private Direccion direccion, nuevaDireccion;
	private Coordenada cabeza, cola;// Ns si necesitare cabeza
	private Dimension cuadricula;

	/**
	 * Constructor en el que lo unico que debes pasar es el tamaño que tendra la
	 * cuadricula en la que puedo hacer aparecer la manzana, aunq igual lo saco para
	 * afuera, y controlo si crece con una variable o algo
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

	public LinkedList<Coordenada> getSerpiente() {
		return serpiente;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public Direccion getNuevaDireccion() {
		return nuevaDireccion;
	}

	public Coordenada getCabeza() {
		return cabeza;
	}

	/**
	 * Mueve la serpiente y crea una nueva manzana si la come<br>
	 * Puedo revisar posteriormente si muere convirtiendo la lista en un stream y
	 * filtrando por aquellos iguales a cabeza, para despues contar si es uno o
	 * mas<br>
	 * Si es mas de uno la pobre se murio
	 * 
	 */
	public void mover() {// Volvio a mover, pq aca tiene mas sentido
		int f = 0, c = 0;
		switch (nuevaDireccion) {
		case DERECHA -> c++;
		case IZQUIERDA -> c--;
		case ARRIBA -> f--;
		case ABAJO -> f++;
		}
		serpiente.add(new Coordenada(cabeza, f, c));// Se añade la nueva cabeza
		cabeza = serpiente.getLast();
		serpiente.removeFirst();//Fuera cola (El crece() la añade de vuelta si hace falta)

	}

	public void actualizarDireccion() {
		direccion = nuevaDireccion;
	}

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
	 * Hay que llamarlo si o si en cada tick, para que pueda crecer la serpiente
	 * @param b
	 */
	public void crece(boolean b) { //No es eficiente, y necesitaria un rework -_-
		if (b)
			serpiente.addFirst(cola);
		else
			cola = serpiente.getFirst();
	}

}
