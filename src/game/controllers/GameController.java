package game.controllers;

import game.DeadSnakeException;
import game.models.Game;
import game.services.ScoreService;
import game.views.GameView;
import game.views.InputView;

public class GameController {
	private GameView gView;
	private InputView iView;
	private ScoreService sService;
	private Game game;

	public GameController(/* int f, int c */) { // Va a no ser adaptable el tamaño de ña cuadrícula :D
		gView = new GameView();
		iView = new InputView();

		gView.setController(this);

		;
	}

	public void start() {
		String name = iView.obtenerNombre();// Ns si cambiar el name de aca o no
		sService = new ScoreService(name);
		game = new Game(20, 20, sService.obtenerPuntuacionMaxima()); // Debería crear bien el game

		gView.render(game);
		gView.show();
		
		startGameLoop(); //Aqui si entraria a lo que formaria el bucle
	}

	public String obtenerNombre() {
		return iView.obtenerNombre();
	}

	public void startGameLoop() {
		
	}
	
	public void tick() throws DeadSnakeException{
		game.getSnake().mover();
		if(!game.isAlive()) {
			gView.hide();
			gView.gameOver();
			throw new DeadSnakeException();
		}
		if(game.comio()) {
			game.aumentarPuntuacion();
			gView.aumentarPuntuacion();
			if(game.getScore().points > game.getScore().mPoints) {
				game.aumentarMaxPuntuacion();
				gView.aumentarMaxPuntuacion();
			}
			game.crearManzanita();
		}
		game.getSnake().actualizarDireccion();
		//No soy consciente de si le falta algo... me gustaría pensar que no
		
		
	}

}
