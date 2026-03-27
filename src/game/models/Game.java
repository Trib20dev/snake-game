package game.models;

import java.awt.Dimension;
import java.util.Random;

public class Game {
	private Coordenada manzana;
	private Dimension cuadricula;
	private Snake snake;
	private Score score;
	
	public Snake getSnake() {
		return snake;
	}
	
	public Coordenada getManzana() {
		return manzana;
	}
	
	public Score getScore() {
		return score;
	}
	
	/**
	 * Constructor
	 * @param f filas que tendra la cuadricula
	 * @param c columnas que tendra la cuadricula
	 */
	public Game(int f, int c, int maxPoints) {
		this.snake = new Snake(f,c);
		this.cuadricula = new Dimension(f,c);
		score = new Score();
		score.setMaxPoints(maxPoints);
		crearManzanita();
	}
	
	/**
	 * Metodo para comprobar despues de moverse si la serpiente puede vivir
	 * @return true if it can live and keep going, false if not
	 */
	public boolean isAlive() { //Ns muy bien como llamarla, por ahora queda asi
		Coordenada cabeza = snake.getCabeza();
		if(snake.getSerpiente().stream().filter(e -> e.equals(cabeza)).count()>1)
			return false;
		if(cabeza.f > cuadricula.height-1 || cabeza.c > cuadricula.width-1 || cabeza.f<0 || cabeza.c<0)
			return false;
		return true;
	}

	/**
	 * Aumenta en uno la puntuación 
	 */
	public void aumentarPuntuacion() { //Al final lo q es mas apropiado es q EL CONTROLADOR llame a este metodo
		score.points++;
	}

	/**
	 * Aumenta en uno la puntuación máxima
	 */
	public void aumentarMaxPuntuacion() { //Lo debe llamar el controlador
		score.mPoints++;
	}

	/**
	 * Crea una manzana en una posicion aleatoria que no coincida con la serpiente
	 */
	public void crearManzanita() {
		Random ran = new Random();
		Coordenada c = snake.getCabeza();
		while (snake.getSerpiente().contains(c))
			c = new Coordenada(ran.nextInt(cuadricula.width), ran.nextInt(cuadricula.height));
		manzana = c;
	}

	public boolean comio() {
		return snake.getCabeza().equals(manzana);
	}
	
}
