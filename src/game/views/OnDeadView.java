package game.views;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

import game.Dato;

public class OnDeadView {
	/**
	 * Ventana que aparece cuando fallece la serpiente<br>
	 * Esta ventana permite volver a jugar, cambiar el nombre para que juegue otra
	 * persona, y ver el ranking cuando haces click con el mouse en el simbolo que
	 * aparece al lado de la pregunta Utiliza un retorno booleano para controlar las
	 * acciones en base a la eleccion
	 * 
	 * @return true si hace click en repetir y false si hace click en cambiar nombre
	 */
	public boolean volverAJugar() {
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
//		JLabel ranking = new JLabel();
//		ranking.setBounds(200, 10, 40, 40);
//		ranking.setOpaque(true);
//		ranking.setText("֎");
//		ranking.setForeground(Color.red);
//		ranking.setFont(new Font("Arial", Font.BOLD, 34));
//		JFrame ranks = Ventana.crearRanking();
//		ranking.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				ranks.setVisible( (ranks.isVisible())?false:true);
//			}
//		});
		cTitulo.add(titulo);
//		cTitulo.add(ranking);

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
//		ranks.dispose();
		return volveraAJugar.valorBooleano;
	}
}
