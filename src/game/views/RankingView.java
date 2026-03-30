package game.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.models.Player;

public class RankingView {
	private JFrame frame;
	private JPanel mainPanel;
	private JLabel title;
	private JLabel first;
	private JLabel second;
	private JLabel third;
	private JLabel fourth;
	private JLabel fifth;
	private JLabel[] ranks;
	
	public RankingView() {
		frame = new JFrame();
		mainPanel = new JPanel();
		title = new JLabel();
		ranks = new JLabel[5];
		first = new JLabel();
		second = new JLabel();
		third = new JLabel();
		fourth = new JLabel();
		fifth = new JLabel();
		ranks[0] = first;
		ranks[1] = second;
		ranks[2] = third;
		ranks[3] = fourth;
		ranks[4] = fifth;
		configurar();
	}

	private void configurar() {
		configureTitle();
		configureRanks();
		configureMainPanel();
		configureFrame();
	}

	private void configureFrame() {
		frame.add(mainPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	private void configureMainPanel() {
		mainPanel.setLayout(new GridLayout(6,1,10,5));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.add(title);
		for (var label : ranks)
			mainPanel.add(label);
	}

	private void configureTitle() {
		title.setFont(new Font("Arial", Font.BOLD, 30));
		title.setText("RANKING");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setPreferredSize(new Dimension(200, 30));
	}

	private void configureRanks() {
		for (var label : ranks) {
			label.setOpaque(true);
			label.setBackground(Color.white);
			label.setFont(new Font("Arial", Font.BOLD, 20));
			label.setPreferredSize(new Dimension(200, 30));
		}
	}

	public void show() {
		frame.setVisible(true);
	}

	public void hide() {
		frame.setVisible(false);
	}

	public void render(Player[] rankers) {
		for (int i = 0; i < 5; i++)
			if (rankers[i] != null)
				ranks[i].setText(
						String.format("%dº %-10s: %03d", i + 1, rankers[i].getNombre(), rankers[i].getPuntuacion()));
			else 
				ranks[i].setText(String.format("%1d", i + 1));

	}
	
	public boolean isVisible() {
		return frame.isVisible();
	}
	
}
