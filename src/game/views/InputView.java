package game.views;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

import game.Dato;

public class InputView {
	/**
	 * Crea una ventana la cual te permite introducir un nombre de hasta 10
	 * caracteres, el cual solo acepta letras y espacios, para formar el nombre del
	 * archivo binario que almacenara la puntuación máxima
	 * 
	 * @return EL nombre introducida en lower case, sin espacios, y acabado por
	 *         .bin<br>
	 *         "Roy Vera" -> "royvera.bin"
	 */
	public String obtenerNombre() {
		JFrame ventana = new JFrame();
		
		Container cTitulo = new Container();
		cTitulo.setLayout(new GridLayout(1, 1));
		cTitulo.setVisible(true);
		cTitulo.setBounds(0, 0, 250, 60);
		JLabel titulo = new JLabel();
		titulo.setText("    ¿Quien eres?");
		titulo.setFont(new Font("Arial", Font.BOLD, 30));
		titulo.setVisible(true);
		titulo.setOpaque(true);
		cTitulo.add(titulo);

		Container cTextArea = new Container();
		cTextArea.setLayout(null);
		cTextArea.setVisible(true);
		cTextArea.setBounds(0, 60, 250, 60);
		JLabel textArea = new JLabel();
		textArea.setBounds(50, 15, 150, 40);
		textArea.setVisible(true);
		textArea.setOpaque(true);
		textArea.setBackground(Color.white);
		textArea.setText(" ");
		textArea.setFont(new Font("Arial", Font.BOLD, 15));
		cTextArea.add(textArea);

		Container cWarning = new Container();
		cWarning.setLayout(new GridLayout(1, 1));
		cWarning.setVisible(true);
		cWarning.setBounds(0, 120, 250, 30);
		JLabel warning = new JLabel();
		warning.setVisible(true);
		warning.setOpaque(true);
		warning.setForeground(Color.red);
		warning.setFont(new Font("Arial", Font.BOLD, 20));
		cWarning.add(warning);

		Dato nombreCreado = new Dato(false);
		ventana.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if (textArea.getText().length() > 1)
						textArea.setText(textArea.getText().substring(0, textArea.getText().length() - 1));
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (textArea.getText().replace(" ", "").toLowerCase().matches("[a-z]*")) {
						nombreCreado.valorBooleano = true;
					} else
						new Thread(() -> {
							warning.setText("      NOMBRE INVALIDO");
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
							}
							warning.setText("");
							textArea.setText(" ");
						}).start();

				} else if (e.getKeyChar() != KeyEvent.CHAR_UNDEFINED)
					if (textArea.getText().length() < 11)
						textArea.setText(textArea.getText() + e.getKeyChar());
			}
		});
		ventana.add(cTitulo);
		ventana.add(cTextArea);
		ventana.add(cWarning);

		
		ventana.setLayout(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setVisible(true);
		Insets insets = ventana.getInsets();
		ventana.setPreferredSize(new Dimension(250 + insets.left + insets.right, 150 + insets.top + insets.bottom));
		ventana.pack();
		ventana.setResizable(false);
		
		
		new Thread(() -> {
			while (!nombreCreado.valorBooleano) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
				}

			}

		}).run();
		ventana.setVisible(false);
		return textArea.getText().replace(" ", "").toLowerCase();
	}
	

	
}
