package game.views;

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
import javax.swing.SwingConstants;

import game.controllers.GameController;

public class InputView {
	private GameController gController;

	private JFrame frame;
	private JPanel mainPanel;
	private Container cTitle;
	private JLabel title;
	private Container cTextArea;
	private JLabel textArea;
	private Container cWarning;
	private JLabel warning;
	
	public InputView() {
		frame = new JFrame();
		mainPanel = new JPanel();
		cTitle = new Container();
		title = new JLabel();
		cTextArea = new Container();
		textArea = new JLabel();
		cWarning = new Container();
		warning = new JLabel();
		configurar();
	}

	public void setController(GameController controller) {
		gController = controller;
	}

	private void configurar() {
		configureCTitle();
		configureCTextArea();
		configureWarningMessage();
		configureMainPanel();
		configureFrame();
	}

	private void configureCTitle() {
		title.setText("¿Quien eres?");
		title.setFont(new Font("Arial", Font.BOLD, 30));
		title.setPreferredSize(new Dimension(250, 60));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		cTitle.setLayout(new FlowLayout());
		cTitle.add(title);
	}

	private void configureCTextArea() {
		textArea.setPreferredSize(new Dimension(150, 40));
		textArea.setOpaque(true);
		textArea.setBackground(Color.white);
		textArea.setText(" ");
		textArea.setFont(new Font("Arial", Font.BOLD, 15));
		cTextArea.setLayout(new FlowLayout());
		cTextArea.setPreferredSize(new Dimension(250, 60));
		cTextArea.add(textArea);
	}

	private void configureWarningMessage() {
		warning.setForeground(Color.red);
		warning.setFont(new Font("Arial", Font.BOLD, 20));
		warning.setPreferredSize(new Dimension(250, 30));
		warning.setHorizontalAlignment(SwingConstants.CENTER);
		cWarning.setLayout(new FlowLayout());
		cWarning.add(warning);
	}

	private void configureMainPanel() {
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(cTitle);
		mainPanel.add(cTextArea);
		mainPanel.add(cWarning);
	}
	
	private void configureFrame() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				gController.onNameKeyPressed(e);
			}
		});
		frame.add(mainPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}

	public void setText(String text) {
		textArea.setText(text);
	}

	public String getText() {
		return textArea.getText();
	}

	public void warning(boolean b) {
		warning.setText((b) ? "NOMBRE INVALIDO" : "");
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public void hide() {
		frame.setVisible(false);
	}
	
	public void clear() {
		textArea.setText(" ");
	}
//
//	/**
//	 * Crea una ventana la cual te permite introducir un nombre de hasta 10
//	 * caracteres, el cual solo acepta letras y espacios, para formar el nombre del
//	 * archivo binario que almacenara la puntuación máxima
//	 * 
//	 * @return EL nombre introducida en lower case, sin espacios, y acabado por
//	 *         .bin<br>
//	 *         "Roy Vera" -> "royvera.bin"
//	 */
//	public String obtenerNombre() {
//		JFrame ventana = new JFrame();
//
//		Container cTitulo = new Container();
//		cTitulo.setLayout(new GridLayout(1, 1));
//		cTitulo.setVisible(true);
//		cTitulo.setBounds(0, 0, 250, 60);
//		JLabel titulo = new JLabel();
//		titulo.setText("    ¿Quien eres?");
//		titulo.setFont(new Font("Arial", Font.BOLD, 30));
//		titulo.setVisible(true);
//		titulo.setOpaque(true);
//		cTitulo.add(titulo);
//
//		Container cTextArea = new Container();
//		cTextArea.setLayout(null);
//		cTextArea.setVisible(true);
//		cTextArea.setBounds(0, 60, 250, 60);
//		JLabel textArea = new JLabel();
//		textArea.setBounds(50, 15, 150, 40);
//		textArea.setVisible(true);
//		textArea.setOpaque(true);
//		textArea.setBackground(Color.white);
//		textArea.setText(" ");
//		textArea.setFont(new Font("Arial", Font.BOLD, 15));
//		cTextArea.add(textArea);
//
//		Container cWarning = new Container();
//		cWarning.setLayout(new GridLayout(1, 1));
//		cWarning.setVisible(true);
//		cWarning.setBounds(0, 120, 250, 30);
//		JLabel warning = new JLabel();
//		warning.setVisible(true);
//		warning.setOpaque(true);
//		warning.setForeground(Color.red);
//		warning.setFont(new Font("Arial", Font.BOLD, 20));
//		cWarning.add(warning);
//
//		Dato nombreCreado = new Dato(false);
//		ventana.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
//					if (textArea.getText().length() > 1)
//						textArea.setText(textArea.getText().substring(0, textArea.getText().length() - 1));
//				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//					if (textArea.getText().replace(" ", "").toLowerCase().matches("[a-z]*")) {
//						nombreCreado.valorBooleano = true;
//					} else
//						new Thread(() -> {
//							warning.setText("      NOMBRE INVALIDO");
//							try {
//								Thread.sleep(1000);
//							} catch (InterruptedException e1) {
//							}
//							warning.setText("");
//							textArea.setText(" ");
//						}).start();
//
//				} else if (e.getKeyChar() != KeyEvent.CHAR_UNDEFINED)
//					if (textArea.getText().length() < 11)
//						textArea.setText(textArea.getText() + e.getKeyChar());
//			}
//		});
//		ventana.add(cTitulo);
//		ventana.add(cTextArea);
//		ventana.add(cWarning);
//
//		ventana.setLayout(null);
//		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		ventana.setVisible(true);
//		Insets insets = ventana.getInsets();
//		ventana.setPreferredSize(new Dimension(250 + insets.left + insets.right, 150 + insets.top + insets.bottom));
//		ventana.pack();
//		ventana.setResizable(false);
//
//		new Thread(() -> {
//			while (!nombreCreado.valorBooleano) {
//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e1) {
//				}
//
//			}
//
//		}).run();
//		ventana.setVisible(false);
//		return textArea.getText().replace(" ", "").toLowerCase();
//	}

}
