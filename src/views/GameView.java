package views;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.nio.InvalidMarkException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controllers.GameController;
import models.Coordenada;
import models.Game;
import models.Snake;

public class GameView {
	private GameController gController;
	private JFrame frame;
	private JPanel mainPanel;
	private Container cCuadricula;
	private JLabel[][] lCuadricula;
	private Container cPuntuacion;
	private JLabel lPuntuacion;
	private Container cMaxPuntuacion;
	private JLabel lMaxPuntuacion;

	public GameView() {
		frame = new JFrame();
		mainPanel = new JPanel();
		cCuadricula = new Container();
		cCuadricula = new Container();
		lCuadricula = new JLabel[20][20];
		cPuntuacion = new Container();
		lPuntuacion = new JLabel();
		cMaxPuntuacion = new Container();
		lMaxPuntuacion = new JLabel();
		configurarVentana();
	}

	public void setController(GameController gController) {
		this.gController = gController;
	}

	// Metodos de configuracion
	/**
	 * Una agrupacion de todas las configuraciones que debes hacer en la ventana<br>
	 * Este metodo fue creado con el objetivo de facilitar la lectura del codigo
	 */
	public void configurarVentana() {
		configurarCCuadricula();
		configurarContenedorPuntuacion();
		configureCMaxPoints();
		configureMainPanel();
		configurarFrame();
	}

	private void configureMainPanel() {
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(cPuntuacion);
		mainPanel.add(cMaxPuntuacion);
		mainPanel.add(cCuadricula);
	}
	
	/**
	 * Configura el frame que contendra la puntuacion y la cuadricula del juego
	 */
	private void configurarFrame() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(mainPanel);
		frame.pack();
		frame.setResizable(false);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				gController.onGamePressed(e);
			}
		});
	}

	/**
	 * 
	 * @param containerPuntuacion
	 * @param lPuntuacion
	 * @param puntos
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
	 * Crea los cuadradiños y asigna la cuadricula al container
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
	 * Configura el contenedor que contiene la maxima puntuacion en la ventana
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


	/**
	 * Cambia el color del background de la coordenada especificada dentro de la
	 * cuadricula al color indicado
	 * 
	 * @param pantalla Matriz en la que ha de cambiar el color
	 * @param posicion Coordenada concreta
	 * @param color    Color del que quieres que sea la coordenada
	 */
	public static void color(JLabel[][] pantalla, Coordenada posicion, Color color) {
		pantalla[posicion.f][posicion.c].setBackground(color);
	}

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

	private void clear() {
		for (int i = 0; i < 20; i++)
			for (int j = 0; j < 20; j++)
				lCuadricula[i][j].setBackground(Color.white);
	}

	public void show() {
		frame.setVisible(true);
	}

	public void hide() {
		frame.setVisible(false);
	}

	/**
	 * Temporal method
	 */
	public void dispose() {
		frame.dispose();
	}
}
