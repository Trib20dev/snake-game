package game.views;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import game.Dato;
import game.models.Player;
import game.models.Ranking;
import game.services.RankingService;

/**
 * Varias de las configuraciones y creaciones de ventanas
 */
public class Ventana {

	/**
	 * Crea una ventana la cual te permite introducir un nombre de hasta 10
	 * caracteres, el cual solo acepta letras y espacios, para formar el nombre del
	 * archivo binario que almacenara la puntuación máxima
	 * 
	 * @return EL nombre introducida en lower case, sin espacios, y acabado por
	 *         .bin<br>
	 *         "Roy Vera" -> "royvera.bin"
	 */
	public static String obtenerNombre() {
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

		ventana.dispose();
		return textArea.getText().replace(" ", "").toLowerCase() + ".bin";
	}

	/**
	 * Ventana que aparece cuando fallece la serpiente<br>
	 * Esta ventana permite volver a jugar, cambiar el nombre para que juegue otra
	 * persona, y ver el ranking cuando haces click con el mouse en el simbolo que
	 * aparece al lado de la pregunta Utiliza un retorno booleano para controlar las
	 * acciones en base a la eleccion
	 * 
	 * @return true si hace click en repetir y false si hace click en cambiar nombre
	 */
	public static boolean volverAJugar() {
		Dato volveraAJugar = new Dato();

		JFrame ventana = new JFrame();

		Container cTitulo = new Container();
		cTitulo.setLayout(null);
		cTitulo.setVisible(true);
		cTitulo.setBounds(0, 0, 250, 60);
		JLabel titulo = new JLabel();
		titulo.setText(" ¿Que harás?");
		titulo.setFont(new Font("Arial", Font.BOLD, 30));
		titulo.setVisible(true);
		titulo.setOpaque(true);
		titulo.setBounds(0, 0, 200, 60);
		;
		JLabel ranking = new JLabel();
		ranking.setBounds(200, 10, 40, 40);
		ranking.setOpaque(true);
		ranking.setText("֎");
		ranking.setForeground(Color.red);
		ranking.setFont(new Font("Arial", Font.BOLD, 34));
		JFrame ranks = Ventana.crearRanking();
		ranking.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ranks.setVisible( (ranks.isVisible())?false:true);
			}
		});
		cTitulo.add(titulo);
		cTitulo.add(ranking);

		JLabel nombre = new JLabel();
		nombre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				volveraAJugar.valorBooleano = false;
			}
		});
		nombre.setBounds(15, 70, 110, 50);
		nombre.setBackground(Color.white);
		nombre.setOpaque(true);
		nombre.setText("  Cambiar nombre");

		JLabel intento = new JLabel();
		intento.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				volveraAJugar.valorBooleano = true;
			}
		});
		intento.setBounds(130, 70, 110, 50);
		intento.setBackground(Color.gray);
		intento.setForeground(Color.white);
		intento.setOpaque(true);
		intento.setText("       Reintentar");

		ventana.add(cTitulo);
		ventana.add(nombre);
		ventana.add(intento);

		// Tuve q cambiar la configuracion al final por consejo de IA
		// pq si no por alguna razon colapsaba y no le añadia correctamente el contenido
		ventana.setLayout(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setVisible(true);
		Insets insets = ventana.getInsets();
		ventana.setPreferredSize(new Dimension(250 + insets.left + insets.right, 150 + insets.top + insets.bottom));
		ventana.pack();
		ventana.setResizable(false);
		
		//Vamos a añadirle que lo reinicie tambien cuando hagas click en espacio o enter
		ventana.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER)
					volveraAJugar.valorBooleano=true;
				super.keyPressed(e);
			}
		});
		
		

		new Thread(() -> {
			while (volveraAJugar.valorBooleano == null) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
				}

			}

		}).run();
		ventana.dispose();
		ranks.dispose();
		return volveraAJugar.valorBooleano;
	}

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
