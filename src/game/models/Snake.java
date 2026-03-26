package game.models;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

public class Snake {
	private LinkedList<Coordenada> serpiente;
	private Direccion direccion, nuevaDireccion;
	private Coordenada cabeza, manzana;// Ns si necesitare cabeza
	private Dimension cuadricula;
	private boolean crece; // Voy a usarlo para la logica de moverse

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
		direccion = Direccion.DERECHA;
		nuevaDireccion = Direccion.DERECHA;
		crece = false;
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

	public Coordenada getManzana() {
		return manzana;
	}

	/**
	 * Crea una manzana en una posicion aleatoria que no coincida con la serpiente
	 * 
	 * @param pantalla
	 */
	public void crearManzanita() {
		Random ran = new Random();
		Coordenada c = cabeza;
		while (serpiente.contains(c))
			c = new Coordenada(ran.nextInt(cuadricula.width), ran.nextInt(cuadricula.height));
		manzana = c;
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
		if (!crece) {// Si no crece
			serpiente.removeFirst();// Fuera cola
			crece = false;
		}

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

}
