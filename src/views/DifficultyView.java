package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controllers.GameController;
import models.Difficulty;

public class DifficultyView {
	private JFrame frame;
	private JPanel pButtons;
	private JButton bEasy;
	private JButton bMedium;
	private JButton bHard;
	private GameController gController;
	
	public static void main(String[] args) {
		DifficultyView dView = new DifficultyView();
		dView.show();
	}
	
	public void setController(GameController con) {
		gController = con;
	}
	
	public DifficultyView() {
		frame = new JFrame();
		pButtons = new JPanel();
		bEasy = new JButton();
		bMedium = new JButton();
		bHard = new JButton();
		configurar();
	}
//	25 50 25 50 25 50 25

	private void configurar() {
		configureBEasy();
		configureBMedium();
		configureBHard();
		configurePButtons();
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
		bMedium.addActionListener(e -> gController.onDifPick(Difficulty.EASY));
	}
	private void configureBHard() {
		bHard.setPreferredSize(new Dimension(50,50));
		bHard.setBackground(Color.red);
		bHard.addActionListener(e -> gController.onDifPick(Difficulty.EASY));
	}
	private void configurePButtons() {
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER,25,10));
		pButtons.setPreferredSize(new Dimension(250,70));
		pButtons.add(bEasy);
		pButtons.add(bMedium);
		pButtons.add(bHard);
	}
	private void configureFrame() {
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.add(pButtons);
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















































