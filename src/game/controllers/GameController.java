package game.controllers;

import java.awt.event.KeyEvent;

import javax.swing.Timer;

import game.models.DeadSnakeException;
import game.models.Direccion;
import game.models.Game;
import game.models.Player;
import game.services.RankingService;
import game.services.ScoreService;
import game.views.GameView;
import game.views.InputView;
import game.views.OnDeadView;
import game.views.RankingView;

public class GameController {
	private GameView gView;
	private InputView iView;
	private OnDeadView dView;
	private RankingView rView;
	private ScoreService sService;
	private RankingService rService;
	private Game game;

	private String name;

	public GameController(/* int f, int c */) { // Va a no ser adaptable el tamaño de ña cuadrícula :D
		gView = new GameView();
		dView = new OnDeadView();
		iView = new InputView();
		rView = new RankingView();

		gView.setController(this);
		dView.setgController(this);
		iView.setController(this);

		rService = new RankingService();
	}

	public void start() {
		iView.show();
	}

//	public String obtenerNombre() {
//		return iView.obtenerNombre();
//	}

	private void startGameLoop() {
		Timer t = new Timer(150, e -> {
			try {
				tick();
			} catch (DeadSnakeException e1) {
				((Timer) e.getSource()).stop();
	            onGamesEnd();
			}
		});
		t.setInitialDelay(2000);
		t.start();
	}

	/**
	 * Representa un tick del juego
	 * 
	 * @throws DeadSnakeException Señaliza la finalizacion del juego por muerte de
	 *                            la serpiente
	 */
	public void tick() throws DeadSnakeException {
		game.getSnake().mover();
		if (!game.isAlive())
			throw new DeadSnakeException();
		if (game.come()) { // Si comio
			game.getSnake().crece(true);// Ns como poner la logica para este crece
			game.aumentarPuntuacion();
			if (game.getScore().points > game.getScore().mPoints)
				game.aumentarMaxPuntuacion();
			game.crearManzanita();
		} else // Si no comio
			game.getSnake().crece(false);
		game.getSnake().actualizarDireccion(); // Necesario pa poder giraraa
		// No soy consciente de si le falta algo... me gustaría pensar que no
		gView.render(game);
	}

	/**
	 * TODO ns si mover pa aqui la logiva respecto a la direccion o no...
	 * 
	 * @param e
	 */
	public void onGamePressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W, KeyEvent.VK_UP -> game.getSnake().declararIntencion(Direccion.ARRIBA);
		case KeyEvent.VK_S, KeyEvent.VK_DOWN -> game.getSnake().declararIntencion(Direccion.ABAJO);
		case KeyEvent.VK_A, KeyEvent.VK_LEFT -> game.getSnake().declararIntencion(Direccion.IZQUIERDA);
		case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> game.getSnake().declararIntencion(Direccion.DERECHA);
		}

	}

	public void onGamesEnd() {
		int p = game.getScore().mPoints;
		gView.hide();
		sService.actualizarPuntuacionMaxima(p);
		rService.update(new Player(name, p));
		rView.render(rService.obtenerPlayers());
		dView.show();
	}

	public void onRankIconPressed() {
		if (rView.isVisible())
			rView.hide();
		else
			rView.show();

	}

	public void onBNamePressed() { // TODO no funciona por ahora, a ver si despues del rewoek va bien
		dView.hide();
		rView.hide();
		iView.show();
	}

	public void onBReplayPressed() {
		game.restart();
		dView.hide();
		gView.render(game);
		gView.show();
		startGameLoop();
	}

	public void onNameKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if (iView.getText().length() > 1)
				iView.setText(iView.getText().substring(0, iView.getText().length() - 1));
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (iView.getText().replace(" ", "").toLowerCase().matches("[a-z]*") && iView.getText().length() > 1) {
				name = iView.getText().replace(" ", "").toLowerCase();
				iView.hide();
				iView.clear();
				sService = new ScoreService(name);
				game = new Game(20, 20, sService.obtenerPuntuacionMaxima()); // Debería crear bien el game
				gView.render(game);
				gView.show();
				startGameLoop(); // Aqui si entraria a lo que formaria el bucle
			} else {
				iView.warning(true);
				Timer t = new Timer(2000, j -> iView.warning(false));
				t.setRepeats(false);
				t.start();
			}

		} else if (e.getKeyChar() != KeyEvent.CHAR_UNDEFINED)
			if (iView.getText().length() < 11)
				iView.setText(iView.getText() + e.getKeyChar());
		
	}

}
