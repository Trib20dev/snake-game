package controllers;

import java.awt.event.KeyEvent;

import javax.swing.Timer;

import exceptions.DeadSnakeException;
import models.ConfigurableSettings;
import models.Difficulty;
import models.Direccion;
import models.Game;
import models.Player;
import services.DbScoreService;
import ui.frame.MainFrame;
import ui.views.DifficultyView;
import ui.views.GameView;
import ui.views.InputView;
import ui.views.OnDeadView;
import ui.views.RankingView;
/**
 * Controlador principal del juego.
 * <p>
 * Gestiona la interacción entre las vistas y los servicios del juego:
 * <ul>
 *   <li>GameView: vista principal del juego</li>
 *   <li>OnDeadView: vista mostrada al perder</li>
 *   <li>InputView: vista para introducir el nombre del jugador</li>
 *   <li>RankingView: vista del ranking de puntuaciones</li>
 * </ul>
 * <p>
 * Controla el flujo del juego mediante métodos de evento (onSomethingPressed)
 * y un bucle principal {@code startGameLoop()} que actualiza el estado
 * del juego y renderiza las vistas correspondientes.
 * <p>
 * Esta clase se inicializa desde la clase Main y constituye el punto central
 * de la lógica de presentación y control del juego.
 */
public class GameController {
//	private GameView gView;
//	private InputView iView;
//	private OnDeadView dView;
//	private RankingView rView;
//	private DifficultyView diView;
	private MainFrame mFrame;
	
	
	private DbScoreService dService;
	private Game game;
	private ConfigurableSettings cSettings;
	private Difficulty difficulty;
	private int speed;
	
	/*TODO adaptar el player, para que este tenga un score, y modificarlo para
	* que con un metodo save que emplee el DbScoreService se guarde solo, aunq 
	* he de revisar como maneja ese servicio...
	*/
	private String name;

	/**
	 * Inicializa el controlador del juego creando las vistas y servicios necesarios.
	 * <p>
	 * Se crean las siguientes vistas:
	 * <ul>
	 *  <li>GameView (vista del juego)</li>
	 *  <li>OnDeadView (vista del fin de partida)</li>
	 *  <li>InputView (vista de entrada de nombre)</li>
	 *  <li>RankingView (vista de ranking)</li>
	 * </ul>
	 * Ademas, se inicializa el servicio de ranking.
	 * <p>
	 * Cada vista recibe una referencia al controlador para poder comunicarse con el.
	 */
	public GameController(/* int f, int c */) { // Va a no ser adaptable el tamaño de ña cuadrícula :D
		mFrame = new MainFrame();
		mFrame.setControllers(this);
		cSettings = new ConfigurableSettings();
		dService = new DbScoreService();
	}

	/**
	 * Hace visible la interfaz de seleccion de nombre
	 * <p>
	 * Este metodo se llama desde la clase Main para comenzar la aplicacion y
	 * permitir que el jugador ingrese su nombre antes de iniciar la partida
	 */
	public void start() {
		mFrame.switchToIView();
		mFrame.setVisible(true);
	}

	/**
	 * Inicia el bucle principal del juego mediante un temporizador
	 * <p>
	 * Cuenta con un retardo inicial de 2 segundos Ejecuta un tick en el juego de
	 * forma periodica Si la serpiente muere, detiene el temporizador y finaliza la
	 * partida
	 */
	private void startGameLoop() {
		Timer t = new Timer(speed, null);

		t.addActionListener(e -> {
			try {
				tick();
			} catch (DeadSnakeException e1) {
				t.stop();
				onGamesEnd();
			}
		});

		t.setInitialDelay(2000);
		t.start();
	}

	/**
	 * Ejecuta un ciclo de actualización del juego
	 * <p>
	 * En cada tick:
	 * <ul>
	 * <li>Mueve la serpiente</li>
	 * <li>Comprueba si la serpiente sigue viva</li>
	 * <li>Gestiona la ingesta de comida (crecimiento y puntuacion)</li>
	 * <li>Actualiza la dirección de movimiento</li>
	 * <li>Renderiza el nuevo estado del juego</li>
	 * </ul>
	 * 
	 * @throws DeadSnakeException Si la serpiente muere durante el tick
	 */
	public void tick() throws DeadSnakeException {
		game.getSnake().mover();
		if (!game.isAlive())
			throw new DeadSnakeException();
		if (game.come()) { // TODO Separar a un metodo la logica de comida
			game.getSnake().crece(true);
			game.aumentarPuntuacion();
			if (game.getScore().points > game.getScore().mPoints)
				game.aumentarMaxPuntuacion();
			game.crearManzanita();
		} else
			game.getSnake().crece(false);
		game.getSnake().actualizarDireccion();

	 	mFrame.renderGame(game);
	}

	/**
	 * Procesa la entrado de teclado durante la partida y acutaliza la direccion de
	 * la serpiente en consecuencia
	 * <p>
	 * Mapea las teclas WASD y las flechas de direccion a las direcciones
	 * correspondientes del juego
	 * 
	 * @param e El evento generado por la tecla pulsada
	 */
	public void onGamePressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W, KeyEvent.VK_UP -> game.getSnake().declararIntencion(Direccion.ARRIBA);
		case KeyEvent.VK_S, KeyEvent.VK_DOWN -> game.getSnake().declararIntencion(Direccion.ABAJO);
		case KeyEvent.VK_A, KeyEvent.VK_LEFT -> game.getSnake().declararIntencion(Direccion.IZQUIERDA);
		case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> game.getSnake().declararIntencion(Direccion.DERECHA);
		}

	}

	/**
	 * Maneja el momento en que el jugador pierde
	 * <ul>
	 * <li>Oculta la vista del juego (gView)</li>
	 * <li>Actualiza la puntuacion maxima en los archivos locales (sService)</li>
	 * <li>Actualiza el ranking en vase a la nueva puntuacion alcanzada
	 * (rService)</li>
	 * <li>Renderiza la vista de ranking con los nuevos datos (rView)</li>
	 * <li>Muestra la vista de seleccion de replay (dView)</li>
	 * </ul>
	 */
	public void onGamesEnd() {
		int p = game.getScore().mPoints;
		dService.saveOrUpdate(new Player(name, p), difficulty);
		mFrame.renderRanking(dService.getTop5Ranked(difficulty));
		mFrame.switchToTemporal();
	}

//	/**
//	 * Alterna la visibilidad de la vista del ranking
//	 * <p>
//	 * Si la vista esta visible, la oculta; en caso contraio, la muestra
//	 */
//	public void onRankIconPressed() {
//		if (rView.isVisible())
//			rView.hide();
//		else
//			rView.show();
//
//	}

	/**
	 * Maneja el evento de cambio de nombre en la vista de replay
	 * <p>
	 * Realiza las siguientes acciones:
	 * <ul>
	 *  <li>Oculta la vista de replay (dView)</li>
	 *  <li>Oculta la vista de ranking (rView)</li>
	 *  <li>Muestra la vista de seleccion de nombre (iView)</li>
	 * </ul>
	 */
	public void onBNamePressed() {
		mFrame.switchToIView();
	}
	/**
	 * Maneja el evento de "Volver a jugar" en la vista de replay
	 * <p>
	 * Realiza las siguientes acciones:
	 * <ul>
	 *  <li>Reinicia la puntuacion y el estado de la serpiente</li>
	 *  <li>Oculta la vista de replay (dView) y la vista del ranking (rView)</li>
	 *  <li>Renderiza el estado reiniciado del juego en gView</li>
	 *  <li>Muestra la vista del juego</li>
	 *  <li>Inicia el bucle principal del juego mediante {@code startGameLoop()}</li>
	 * </ul>
	 */
	public void onBReplayPressed() {
		game.restart();
		mFrame.renderGame(game);
		mFrame.switchToGView();
		startGameLoop();
	}
	/**
	 * Gestiona el evento de pulsar Enter en la vista de selección de nombre.
	 * <p>
	 * Valida el nombre introducido y, si es correcto, inicializa el juego:
	 * <ul>
	 *   <li>Normaliza el nombre (minúsculas y sin espacios)</li>
	 *   <li>Inicia los servicios y la partida</li>
	 *   <li>Muestra la vista del juego</li>
	 *   <li>Inicia el bucle del juego</li>
	 * </ul>
	 *
	 * @param e evento de teclado que contiene la tecla pulsada
	 */
	public void onNameKeyPressed(KeyEvent e) {
		if (mFrame.getIView().getText().length() >= 10) //Tiene un error un poco raro cuando pasas de 10
			mFrame.getIView().setText(mFrame.getIView().getText().substring(0,10));
        
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (mFrame.getIView().getText().replace(" ", "").toLowerCase().matches("[a-z]*") && mFrame.getIView().getText().length() > 1) {
				name = mFrame.getIView().getText().replace(" ", "").toLowerCase();
				mFrame.switchToDiView();
			} else {
				mFrame.getIView().warning(true);
				Timer t = new Timer(2000, j -> mFrame.getIView().warning(false));
				t.setRepeats(false);
				t.start();
			}
		} 
	}
	
	public void onDifPick(Difficulty dif) {
		difficulty = dif;
		speed = switch (dif) {
		case EASY -> cSettings.getEasySpeed();
		case MEDIUM -> cSettings.getMediumSpeed();
		case HARD -> cSettings.getHardSpeed();
		default -> -1;
		};
		game = new Game(20, 20, dService.getPoints(name,dif));
		mFrame.renderGame(game);
		mFrame.switchToGView();
		startGameLoop(); 
	}
	
}






















