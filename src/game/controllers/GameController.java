package game.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import game.DeadSnakeException;
import game.models.Direccion;
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

		startGameLoop(); // Aqui si entraria a lo que formaria el bucle
	}

	public String obtenerNombre() {
		return iView.obtenerNombre();
	}

	private void startGameLoop() { //Igual me veo obligado a volverlo un thread, o lo trabajo con evento el fin, no se
		new Timer(200, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					tick();
				} catch (DeadSnakeException e1) {
					((Timer) e.getSource()).stop(); // Voy entendiendo como hacer esto, creo
				}

			}
		}).start();
		System.out.println("Cachis, perdiste");
	}

	public void tick() throws DeadSnakeException {
		game.getSnake().mover();
		if (!game.isAlive()) {
			gView.hide();
//			gView.gameOver(); //Esto fue una recomendacion q recibi de chati, igual le hago algo aca, ns
			throw new DeadSnakeException();
		}
		if (game.comio()) {
			game.aumentarPuntuacion();
//			gView.aumentarPuntuacion();No deberia hacer falra
			if (game.getScore().points > game.getScore().mPoints) {
				game.aumentarMaxPuntuacion();
//				gView.aumentarMaxPuntuacion(); Mas de lo mismo
			}
			game.crearManzanita();
		}
		game.getSnake().actualizarDireccion();
		// No soy consciente de si le falta algo... me gustaría pensar que no
		gView.render(game);
	}
	/**
	 * TODO ns si mover pa aqui la logiva respecto a la direccion o no...
	 * @param e
	 */
	public void onGamePressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W, KeyEvent.VK_UP ->   game.getSnake().declararIntencion(Direccion.ARRIBA);
		case KeyEvent.VK_S, KeyEvent.VK_DOWN -> game.getSnake().declararIntencion(Direccion.ABAJO); 
		case KeyEvent.VK_A, KeyEvent.VK_LEFT -> game.getSnake().declararIntencion(Direccion.IZQUIERDA);
		case KeyEvent.VK_D, KeyEvent.VK_RIGHT-> game.getSnake().declararIntencion(Direccion.DERECHA);
		}

	}

}
