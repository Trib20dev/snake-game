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

	public void setgController(GameController gController) {
		this.gController = gController;
	}

	public void configurar() { // El orden es importante, no hay q añadir hasta que esta plenamente configurado
		configureCTitle();
		configureCButtons();
		configurarMainPanel();
		configureFrame();
	}

	private void configurarMainPanel() {
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(cTitle);
		mainPanel.add(cButtons);
	}

	private void configureFrame() {
		frame.add(mainPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

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

	public void show() {
		frame.setVisible(true);
	}

	public void hide() {
		frame.setVisible(false);
	}

}
