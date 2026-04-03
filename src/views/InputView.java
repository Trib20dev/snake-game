package views;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controllers.GameController;
/**
 * Vista de entrada de usuario para que el jugador introduzca su nombre.
 * <p>
 * Esta clase crea una ventana con tres secciones:
 * <ul>
 *     <li>Un título indicando que se debe introducir el nombre.</li>
 *     <li>Un área de texto simulada donde se muestra el nombre ingresado.</li>
 *     <li>Un mensaje de advertencia que indica si el nombre es inválido.</li>
 * </ul>
 * <p>
 * Se comunica con {@link GameController} para recibir eventos de teclado
 * y notificar cuando el usuario ingresa o modifica su nombre.
 * 
 * <p>
 * La vista maneja internamente la configuración de sus componentes
 * y no necesita que el controlador se preocupe por el diseño gráfico.
 */
public class InputView {
	private GameController gController;

	private JFrame frame;
	private JPanel mainPanel;
	private Container cTitle;
	private JLabel title;
	private Container cTextArea;
	private JTextField textArea;
	private Container cWarning;
	private JLabel warning;
	/**
     * Constructor que inicializa todos los componentes de la ventana
     * y los configura correctamente.
     */
	public InputView() {
		frame = new JFrame();
		mainPanel = new JPanel();
		cTitle = new Container();
		title = new JLabel();
		cTextArea = new Container();
		textArea = new JTextField();
		cWarning = new Container();
		warning = new JLabel();
		configurar();
	}
	/**
     * Asigna el controlador que recibirá los eventos de teclado generados
     * por esta vista.
     * 
     * @param controller Instancia de {@link GameController} que maneja los eventos.
     */
	public void setController(GameController controller) {
		gController = controller;
	}

	
	/**
     * Establece el texto que se mostrará en el área de entrada.
     * 
     * @param text Texto a mostrar en la vista.
     */
	public void setText(String text) {
		textArea.setText(text);
	}
	/**
     * Obtiene el texto actual ingresado por el usuario.
     * 
     * @return Texto contenido en el área de entrada.
     */
	public String getText() {
		return textArea.getText();
	}
	/**
	 * Muestra u oculta el mensaje de advertencia indicando que
	 * el nombre ingresado es inválido.
	 * 
	 * @param b true para mostrar la advertencia, false para ocultarla.
	 */
	public void warning(boolean b) {
		warning.setText((b) ? "NOMBRE INVALIDO" : "");
	}
	/**
     * Muestra la ventana de entrada.
     */
	public void show() {
		frame.setVisible(true);
	}
	/**
     * Oculta la ventana de entrada.
     */
	public void hide() {
		frame.setVisible(false);
		gController.onHide();
	}
	/**
     * Limpia el contenido del área de entrada dejando un espacio en blanco.
     */
	public void clear() {
		textArea.setText(" ");
	}
	
	// --- Métodos privados de configuración ---
	/**
     * Agrupa la configuración de todos los contenedores y el frame.
     * Se encarga de llamar a los métodos privados que inicializan cada sección de la ventana.
     */
	private void configurar() {
		configureCTitle();
		configureCTextArea();
		configureWarningMessage();
		configureMainPanel();
		configureFrame();
	}
	/** 
     * Configura el contenedor que muestra el título de la ventana.
     */
	private void configureCTitle() {
		title.setText("¿Quien eres?");
		title.setFont(new Font("Arial", Font.BOLD, 30));
		title.setPreferredSize(new Dimension(250, 60));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		cTitle.setLayout(new FlowLayout());
		cTitle.add(title);
	}
    /** 
     * Configura el área de texto donde el usuario escribe su nombre.
     */
	private void configureCTextArea() {
		textArea.setPreferredSize(new Dimension(150, 40));
//		textArea.setOpaque(true);
//		textArea.setBackground(Color.white);
		textArea.setText(" ");
		textArea.setFont(new Font("Arial", Font.BOLD, 20));
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				gController.onNameKeyPressed(e);
			}
		});
		cTextArea.setLayout(new FlowLayout());
		cTextArea.setPreferredSize(new Dimension(250, 60));
		cTextArea.add(textArea);
		
	}
    /**
     * Configura el contenedor que muestra mensajes de advertencia (por ejemplo, nombre inválido).
     */
	private void configureWarningMessage() {
		warning.setForeground(Color.red);
		warning.setFont(new Font("Arial", Font.BOLD, 20));
		warning.setPreferredSize(new Dimension(250, 30));
		warning.setHorizontalAlignment(SwingConstants.CENTER);
		cWarning.setLayout(new FlowLayout());
		cWarning.add(warning);
	}
	/**
     * Configura el panel principal que agrupa todos los contenedores.
     */
	private void configureMainPanel() {
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(cTitle);
		mainPanel.add(cTextArea);
		mainPanel.add(cWarning);
	}
	/**
     * Configura el JFrame principal y añade un KeyListener para capturar la entrada del usuario.
     */
	private void configureFrame() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(mainPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}
	
	public boolean isVisible() {
		return frame.isVisible();
	}
	
	public void dispose() {
		frame.dispose();
	}
}
