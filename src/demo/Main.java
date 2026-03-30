package demo;

import controllers.GameController;
/**
 * Clase principal para iniciar el juego Snake.
 * <p>
 * Crea una instancia del controlador del juego y llama al método start para
 * iniciar la lógica y mostrar la interfaz.
 */
public class Main {	
	public static void main(String[] args) {
		GameController game = new GameController(); 
		game.start();
	}
}	

