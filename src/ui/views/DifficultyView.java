package ui.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controllers.GameController;
import models.Difficulty;

@SuppressWarnings("serial")
public class DifficultyView extends JPanel{
	private JFrame frame;
	private JPanel mainPanel;
	private JLabel title;
	private JPanel pButtons;
	private JButton bEasy;
	private JButton bMedium;
	private JButton bHard;
	private GameController gController;
	
	public void setController(GameController con) {
		gController = con;
	}
	
	public DifficultyView() {
		frame = new JFrame();
		mainPanel = new JPanel();
		title = new JLabel();
		pButtons = new JPanel();
		bEasy = new JButton();
		bMedium = new JButton();
		bHard = new JButton();
		configurar();
	}

	private void configurar() {
		configureBEasy();
		configureBMedium();
		configureBHard();
		configurePButtons();
		configureTitle();
		configureMainPanel();
		configureFrame();
	}

	private void configureBEasy() {
		bEasy.setPreferredSize(new Dimension(50,50));
		bEasy.setBackground(Color.green);
		bEasy.addActionListener(e -> gController.onDifPick(Difficulty.EASY));
	}
	private void configureBMedium() {
		bMedium.setPreferredSize(new Dimension(50,50));
		bMedium.setBackground(Color.yellow);
		bMedium.addActionListener(e -> gController.onDifPick(Difficulty.MEDIUM));
	}
	private void configureBHard() {
		bHard.setPreferredSize(new Dimension(50,50));
		bHard.setBackground(Color.red);
		bHard.addActionListener(e -> gController.onDifPick(Difficulty.HARD));
	}
	private void configurePButtons() {
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER,25,10));
		pButtons.setPreferredSize(new Dimension(250,70));
		pButtons.add(bEasy);
		pButtons.add(bMedium);
		pButtons.add(bHard);
		
		pButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
		
	}
	
	private void configureTitle() {
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setText("Dificultad");
		title.setFont(new Font("Arial", Font.BOLD, 30));
		title.setPreferredSize(new Dimension(250, 50));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
	}
	
	private void configureMainPanel() {
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(title);
		mainPanel.add(pButtons);
	}
	
	private void configureFrame() {
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.add(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public void hide() {
		frame.setVisible(false);
	}
	
}















































