package views;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controllers.GameController;
/**
 * Vista que se muestra cuando la serpiente muere.
 * <p>
 * Presenta un título, un icono de ranking, y dos botones:
 * "Cambiar nombre" y "Reintentar". Los botones y el icono
 * están conectados a un {@link GameController} para manejar
 * las acciones del usuario.
 */
public class OnDeadView {

	private GameController gController;
	private JFrame frame;
	private JPanel mainPanel; // Me informo chati de q me hace falta este para poder trabajar con BoxLayout
	private Container cTitle;
	private JLabel title;
	private JLabel rankIcon;
	private Container cButtons;
	private JLabel bName;
	private JLabel bReplay;
	/**
	 * Constructor que inicializa y configura todos los componentes
	 * de la vista.
	 */
	public OnDeadView() {
		frame = new JFrame();
		mainPanel = new JPanel();
		cTitle = new Container();
		title = new JLabel();
		rankIcon = new JLabel();
		cButtons = new Container();
		bName = new JLabel();
		bReplay = new JLabel();
		configurar();
	}
	/**
	 * Asocia un controlador de juego a la vista.
	 * 
	 * @param gController Controlador que maneja los eventos de la vista.
	 */
	public void setgController(GameController gController) {
		this.gController = gController;
	}
	/**
	 * Configura todos los contenedores y componentes internos
	 * y organiza la jerarquía de la ventana.
	 */
	public void configurar() { // El orden es importante, no hay q añadir hasta que esta plenamente configurado
		configureCTitle();
		configureCButtons();
		configurarMainPanel();
		configureFrame();
	}
	/**
	 * Configura el panel principal con layout vertical y añade
	 * el título y los botones.
	 */
	private void configurarMainPanel() {
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(cTitle);
		mainPanel.add(cButtons);
	}
	/**
	 * Configura el JFrame principal, añadiendo el panel principal,
	 * ajustando tamaño y comportamiento de cierre.
	 */
	private void configureFrame() {
		frame.add(mainPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	/**
	 * Configura el contenedor del título, incluyendo el JLabel
	 * principal y el icono de ranking con su listener.
	 */
	private void configureCTitle() {
		cTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0)); // No le he de poner v-gap, q aumenta de mas
		title.setPreferredSize(new Dimension(200, 60));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setText("¿Que harás?");
		title.setFont(new Font("Arial", Font.BOLD, 30));
		rankIcon.setPreferredSize(new Dimension(40, 40));
		rankIcon.setText("🏆");
		rankIcon.setForeground(Color.orange);
		rankIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 34));
		rankIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gController.onRankIconPressed();
			}
		});
		cTitle.add(title);
		cTitle.add(rankIcon);
	}
	/**
	 * Configura el contenedor de botones, incluyendo "Cambiar nombre"
	 * y "Reintentar" con sus respectivos listeners.
	 */
	private void configureCButtons() {
		cButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		// Configure the change name button
		bName.setPreferredSize(new Dimension(110, 50));
		bName.setHorizontalAlignment(SwingConstants.CENTER);
		bName.setText("Cambiar nombre");
		bName.setOpaque(true);
		bName.setBackground(Color.white);
		bName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gController.onBNamePressed();
			}
		});
		// Configure the replay button
		bReplay.setPreferredSize(new Dimension(110, 50));
		bReplay.setBackground(Color.gray);
		bReplay.setForeground(Color.white);
		bReplay.setOpaque(true);
		bReplay.setHorizontalAlignment(SwingConstants.CENTER);
		bReplay.setText("Reintentar");
		bReplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gController.onBReplayPressed();
			}
		});
		cButtons.add(bName);
		cButtons.add(bReplay);
	}
	/**
	 * Muestra la ventana en pantalla.
	 */
	public void show() {
		frame.setVisible(true);
	}
	/**
	 * Oculta la ventana de la pantalla.
	 */
	public void hide() {
		frame.setVisible(false);
	}
		
}
