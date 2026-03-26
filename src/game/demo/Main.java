package game.demo;

import game.controllers.GameController;

public class Main {
	public static void main(String[] args) {
		GameController game = new GameController(); //Ahora mismo no le puedes decir el tamaño q quieres
		game.start();
	}
}	

