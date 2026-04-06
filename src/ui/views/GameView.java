package ui.views;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controllers.GameController;
import models.Coordenada;
import models.Game;
import models.Snake;
/**
 * Vista principal del juego Snake.
 * <p>
 * Se encarga de mostrar la cuadrícula del juego, la puntuación actual y la puntuación máxima.
 * Cada celda de la cuadrícula se representa con un JLabel, y se actualiza en cada tick del juego.
 */
@SuppressWarnings("serial")
public class GameView extends JPanel{
	private GameController gController;
//	private JFrame frame;
//	private JPanel mainPanel;
	private Container cCuadricula;
	private JLabel[][] lCuadricula;
	private Container cPuntuacion;
	private JLabel lPuntuacion;
	private Container cMaxPuntuacion;
	private JLabel lMaxPuntuacion;
	/**
     * Constructor que inicializa los componentes gráficos de la ventana del juego.
     */
	public GameView() {
//		frame = new JFrame();
//		mainPanel = new JPanel();
		cCuadricula = new Container();
		cCuadricula = new Container();
		lCuadricula = new JLabel[20][20];
		cPuntuacion = new Container();
		lPuntuacion = new JLabel();
		cMaxPuntuacion = new Container();
		lMaxPuntuacion = new JLabel();
		configurarVentana();
	}
	/**
     * Asigna el controlador que gestionará los eventos del juego.
     *
     * @param gController controlador de la vista
     */
	public void setController(GameController gController) {
		this.gController = gController;
	}

	 // --- Configuración de la ventana y contenedores ---
	/**
     * Configura todos los componentes de la ventana: cuadrícula, puntuaciones y frame.
     */
	public void configurarVentana() {
		configurarCCuadricula();
		configurarContenedorPuntuacion();
		configureCMaxPoints();
		configureMainPanel();
//		configurarFrame();
	}
	/**
     * Configura el panel principal que contiene puntuaciones y cuadrícula.
     */
	private void configureMainPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(cPuntuacion);
		add(cMaxPuntuacion);
		add(cCuadricula);
	}
	
//	/**
//     * Configura el JFrame principal de la ventana del juego, incluyendo el KeyListener para controlar la serpiente.
//     */
//	private void configurarFrame() {
//		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		frame.add(this);
//		frame.pack();
//		frame.setResizable(false);
//		frame.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				gController.onGamePressed(e);
//			}
//		});
//	}

	/**
     * Configura el contenedor que muestra la puntuación actual del jugador.
     */
	private void configurarContenedorPuntuacion() {
		lPuntuacion.setOpaque(true);
		lPuntuacion.setVisible(true);
		lPuntuacion.setText(String.format("POINTS: %03d", 000));
		lPuntuacion.setBackground(Color.white); 
		lPuntuacion.setFont(new Font("Puntuacion?", Font.BOLD, 50));
		lPuntuacion.setPreferredSize(new Dimension(400, 100));
		cPuntuacion.add(lPuntuacion);
		cPuntuacion.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
	}

	/**
     * Crea la cuadrícula del juego como un grid de JLabels y la añade al contenedor principal.
     */
	private void configurarCCuadricula() { // Tiene a tamaño fijo la cuadricula
		cCuadricula.setLayout(new GridLayout(20, 20));

		// Se crean y almacenan como "blancos" los labels
		for (int i = 0; i < 20; i++)
			for (int j = 0; j < 20; j++) {
				JLabel l = new JLabel();
				l.setOpaque(true);
				l.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				l.setBackground(Color.white);
				lCuadricula[i][j] = l;
				cCuadricula.add(lCuadricula[i][j]); // Me tomo mi rato averiguar q hacerle a esto
			}
		cCuadricula.setPreferredSize(new Dimension(400, 400)); // Pasa también la posición
	}

	/**
     * Configura el contenedor que muestra la puntuación máxima alcanzada.
     */
	public void configureCMaxPoints() {
		lMaxPuntuacion.setText(String.format("M.POINTS: %03d", 000));
		lMaxPuntuacion.setFont(new Font("Puntuacion?", Font.BOLD, 50));
		lMaxPuntuacion.setOpaque(true);
		lMaxPuntuacion.setBackground(Color.white);
		lMaxPuntuacion.setPreferredSize(new Dimension(400, 100));
		cMaxPuntuacion.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		cMaxPuntuacion.add(lMaxPuntuacion);
	}

	 // --- Métodos de renderizado y actualización ---
	
	 /**
     * Cambia el color de fondo de una celda específica de la cuadrícula.
     *
     * @param pantalla matriz de labels de la cuadrícula
     * @param posicion coordenada de la celda a colorear
     * @param color    color que se aplicará
     */
	public static void color(JLabel[][] pantalla, Coordenada posicion, Color color) {
		pantalla[posicion.f][posicion.c].setBackground(color);
	}
	/**
     * Renderiza el estado actual del juego en la vista.
     * <p>
     * Se colorea la serpiente, la cabeza y la manzana, y se actualizan las puntuaciones.
     *
     * @param game instancia del juego a renderizar
     */
	public void render(Game game) {
		clear();// Primero limpiamos

		Snake s = game.getSnake();
		for (var coord : s.getSerpiente())
			lCuadricula[coord.f][coord.c].setBackground(Color.black);
		lCuadricula[s.getCabeza().f][s.getCabeza().c].setBackground(Color.gray);
		lCuadricula[game.getManzana().f][game.getManzana().c].setBackground(Color.red);
		lPuntuacion.setText(String.format("POINTS: %03d", game.getScore().points));
		lMaxPuntuacion.setText(String.format("M.POINTS: %03d", game.getScore().mPoints));
	}
	/**
     * Limpia toda la cuadrícula poniendo todas las celdas en blanco.
     */

	private void clear() {
		for (int i = 0; i < 20; i++)
			for (int j = 0; j < 20; j++)
				lCuadricula[i][j].setBackground(Color.white);
	}

	  // --- Métodos de control de visibilidad ---
	
//	/** Muestra la ventana del juego */
//	public void show() {
//		frame.setVisible(true);
//	}
//	
//	/** Oculta la ventana del juego */
//	public void hide() {
//		frame.setVisible(false);
//	}
//
//	/** Libera los recursos de la ventana */
//    public void dispose() {
//        frame.dispose();
//    }
    
}
