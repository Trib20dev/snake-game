package game.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;

import game.models.Player;
import game.models.Ranking;
import game.services.RankingService;

public class RankingView {
	
	
	
	/**
	 * Genera un ranking a partir del archivo ranking.json el cual muestra a los 5
	 * jugadores con mas puntos y sus puntuaciones en el formato: <o>
	 * <li>1º Nombre: puntos</li>
	 * <li>2º Nombre: puntos</li>
	 * <li>3º</li>
	 * </ol>
	 * Y asi hasta el quinto Para poder utilizar esta ventana hay que poner la
	 * propiedad visible del frame retornado a true, ya que por defecto esta en
	 * false
	 * 
	 * @return El frame ya configurado para poder mostrarlo u ocultarlo con la
	 *         propiedad visible a gusto
	 */
	private static JFrame crearRanking() {
		JFrame vRank = new JFrame();
		RankingService ser = new RankingService();//Definitivamente tengo q revisar como trabajo esto
		Ranking modelo = new Ranking(ser.obtenerPlayers());
		Player players[] = modelo.getPlayers();
		vRank.setLayout(null);
		vRank.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		vRank.setVisible(false); Creo recordar q se genera en false automaticamente
		Insets insets = vRank.getInsets();
		vRank.setPreferredSize(new Dimension(250 + insets.left + insets.right, 300 + insets.top + insets.bottom));
		vRank.pack();
		vRank.setResizable(false);

		JLabel titulo = new JLabel();
		titulo.setFont(new Font("Arial", Font.BOLD, 30));
		titulo.setText("RANKING");
		titulo.setBounds(50, 10, 200, 30);

		JLabel first = new JLabel();
		first.setBounds(40, 50, 170, 30);
		first.setFont(new Font("Arial", Font.BOLD, 20));
		String sFirst = (players[0] != null) ? String.format("1º %-10s: %03d", players[0].nombre, players[0].puntuacion)
				: "";
		first.setText(sFirst);
		first.setOpaque(true);
		first.setBackground(Color.white);

		JLabel second = new JLabel();
		second.setBounds(40, 90, 170, 30);
		second.setFont(new Font("Arial", Font.BOLD, 20));
		String sSecond = (players[1] != null)
				? String.format("2º %-10s: %03d", players[1].nombre, players[1].puntuacion)
				: "";
		second.setText(sSecond);
		second.setOpaque(true);
		second.setBackground(Color.white);

		JLabel third = new JLabel();
		third.setBounds(40, 130, 170, 30);
		third.setFont(new Font("Arial", Font.BOLD, 20));
		String sThird = (players[2] != null) ? String.format("3º %-10s: %03d", players[2].nombre, players[2].puntuacion)
				: "";
		third.setText(sThird);
		third.setOpaque(true);
		third.setBackground(Color.white);

		JLabel fourth = new JLabel();
		fourth.setBounds(40, 170, 170, 30);
		fourth.setFont(new Font("Arial", Font.BOLD, 20));
		String sFourth = (players[3] != null)
				? String.format("4º %-10s: %03d", players[3].nombre, players[3].puntuacion)
				: "";
		fourth.setText(sFourth);
		fourth.setOpaque(true);
		fourth.setBackground(Color.white);

		JLabel fifth = new JLabel();
		fifth.setBounds(40, 210, 170, 30);
		fifth.setFont(new Font("Arial", Font.BOLD, 20));
		String sFifth = (players[4] != null) ? String.format("5º %-10s: %03d", players[4].nombre, players[4].puntuacion)
				: "";
		fifth.setText(sFifth);
		fifth.setOpaque(true);
		fifth.setBackground(Color.white);

		vRank.add(titulo);
		vRank.add(first);
		vRank.add(second);
		vRank.add(third);
		vRank.add(fourth);
		vRank.add(fifth);

		return vRank;
	}
}
