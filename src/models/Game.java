package models;

import java.awt.Dimension;
import java.util.Random;
/**
 * Representa la lógica del juego de la serpiente.
 * <p>
 * Gestiona la serpiente, la manzana, la puntuación y la cuadrícula del juego.
 * Proporciona métodos para avanzar el juego, comprobar si la serpiente sigue viva
 * y generar manzanas aleatorias.
 */
public class Game {
	 /** Posición actual de la manzana en la cuadrícula */
	private Coordenada manzana;
	 /** Dimensiones de la cuadrícula del juego */
	private Dimension cuadricula;
	 /** Serpiente del juego */
	private Snake snake;
	/** Puntuación actual y máxima alcanzada */
	private Score score;
	 /**
     * Devuelve la serpiente actual.
     * 
     * @return objeto Snake que representa la serpiente
     */
	public Snake getSnake() {
		return snake;
	}
	/**
     * Devuelve la posición actual de la manzana.
     * 
     * @return coordenada de la manzana
     */
	public Coordenada getManzana() {
		return manzana;
	}
	/**
     * Devuelve la puntuación del juego.
     * 
     * @return objeto Score con la puntuación actual y máxima
     */
	public Score getScore() {
		return score;
	}
	
	/**
     * Constructor del juego.
     * <p>
     * Inicializa la serpiente, la cuadrícula, la puntuación y genera una manzana
     * aleatoria.
     * 
     * @param f         número de filas de la cuadrícula
     * @param c         número de columnas de la cuadrícula
     * @param maxPoints puntuación máxima inicial del jugador
     */
	public Game(int f, int c, int maxPoints) {
		this.snake = new Snake(f,c);
		this.cuadricula = new Dimension(f,c);
		score = new Score();
		score.setMaxPoints(maxPoints);
		crearManzanita();
	}
	
	 /**
     * Comprueba si la serpiente sigue viva.
     * <p>
     * La serpiente muere si colisiona consigo misma o con los límites de la
     * cuadrícula.
     * 
     * @return {@code true} si la serpiente puede seguir moviéndose,
     *         {@code false} si ha muerto
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
	 * Aumenta en uno la puntuación actual
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
	 * Genera una manzana en una posicion aleatoria que no coincida con la serpiente
	 */
	public void crearManzanita() {
		Random ran = new Random();
		Coordenada c = snake.getCabeza();
		while (snake.getSerpiente().contains(c))
			c = new Coordenada(ran.nextInt(cuadricula.width), ran.nextInt(cuadricula.height));
		manzana = c;
	}
	/**
     * Comprueba si la serpiente ha comido la manzana.
     * 
     * @return {@code true} si la cabeza de la serpiente coincide con la manzana,
     *         {@code false} en caso contrario
     */
	public boolean come() {
		return snake.getCabeza().equals(manzana);
	}
	/**
     * Reinicia el juego, creando una nueva serpiente y reseteando la puntuación.
     */
	public void restart() {
		score.points = 0;
		snake = new Snake(20, 20);
	}
	
}
