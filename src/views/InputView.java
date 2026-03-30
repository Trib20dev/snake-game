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
import javax.swing.SwingConstants;

import controllers.GameController;

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

}
