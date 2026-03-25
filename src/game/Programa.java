package game;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.json.JSONWriter;

import game.models.Coordenada;
import game.models.Player;
import game.models.Ranking;
import game.services.RankingService;

//Tengo que revisar el momento en el que chequeo si puede chocar, pq puede "colisionar"
//con la cola que debería desaparecer en el mismo momento... Despues
/*TODO
 * -> Documentar la gran cantidad de cosas que tengo sin documentar
 * -> Alguna funcion de reseteo que borre archivos de guardado y resetee el ranking
 * -> Refactorizar y probablemente sacar a clases
 * -> Quitar la estupidez del min y max en la logica de color
 */ 
/**
 * Contiene la logica principal del programa, y es creando una instancia de esta que puedes
 * jugar usando su función {@link start()}
 */
public class Programa {
	// Es un intento de copia, asi q tengo q intentar adaptarlo y completarlo al
	// modelo q tenia pensado
	/**
	 * Constantes utilizadas para representar direcciones
	 */
	private static final int ARRIBA = 0, ABAJO = 1, IZQUIERDA = 2, DERECHA = 3;
	private static final Random ran = new Random();
	/**
	 * Matriz dentro de la cual se 
	 */
	private JLabel[][] cuadricula;

//	private Coordenada cabeza, cola;
//	private LinkedList<Coordenada> serpiente;
//	private Dato direccion, nuevaDireccion; //Tiene que haber uan mejor forma, pero que mas dará
//	private Dato puntos;
	private int filas,columnas;

	/**
	 * Permite construir un juego de Snake inciable con el método {@link start()}
	 * 
	 * @param filas    Cantidad de filas que tendra la cuafrícula de la serpiente
	 * @param columnas Cantidad de columnas que tendra la cuafrícula de la serpiente
	 */
	public Programa(int filas, int columnas) {
		this.filas = filas;
		this.columnas = columnas;
	}
	/**
	 * Metodo de instancia al que se llama para comenzar la partida
	 */
	public void start() {
		
		String fichero = Ventana.obtenerNombre();
		start(filas,columnas,fichero);
		while(true)
			if(Ventana.volverAJugar()) 
				start(filas,columnas,fichero);
			else {
				fichero = Ventana.obtenerNombre();
				start(filas,columnas,fichero);
				
			}
	}
	/**
	 * Una vez que ya sabes quien va a jugar, se llama a este metodo
	 * para empezar la partida
	 * 
	 * @param filas		Las filas que contendra la cuadricula
	 * @param columnas	Las columnas que contendra la cuadricula
	 * @param fichero	El nombre del fichero que almacena la puntuacion maxima
	 */
	private void start(int filas, int columnas,String fichero) {
		//Declaracion de los componentes y asignación de valores
		LinkedList<Coordenada> serpiente;
		Dato direccion, nuevaDireccion; //Tiene que haber uan mejor forma, pero que mas dará
		Dato puntos, maxPuntos;	
		serpiente = new LinkedList<Coordenada>();
		cuadricula = new JLabel[filas][columnas];
		serpiente.add(new Coordenada(filas / 2, columnas / 2 - 1));
		serpiente.add(new Coordenada(filas / 2, columnas / 2));
		serpiente.add(new Coordenada(filas / 2, columnas / 2 + 1));
		serpiente.add(new Coordenada(filas / 2, columnas / 2 + 2));
		direccion = new Dato(DERECHA);
		nuevaDireccion = new Dato(DERECHA);
		puntos = new Dato(0);
		maxPuntos = new Dato(obtenerPuntuacionMaxima(fichero));
		JFrame frame = new JFrame();
		Container c = new Container();
		JLabel points = new JLabel();
		Container cPoints = new Container();
		JLabel maxPoints = new JLabel();
		Container cMaxPoints = new Container();
		configurarVentana(frame,cMaxPoints,maxPoints, maxPuntos,cPoints, points, puntos, c, cuadricula, serpiente, filas, columnas, direccion, nuevaDireccion);
		
		/**
		 * Es donde opera el bucle del juego
		 */
		new Thread(() -> { // Me va a hacer falta una lista, para saber cual es la nueva cola :/
			boolean vivo = true;
			try {

				Thread.sleep(2000);
				while (vivo) {
					try {
						actualizarCuadrícula(serpiente, cuadricula, nuevaDireccion.valorInt, points, puntos, maxPoints,maxPuntos);
						direccion.valorInt = nuevaDireccion.valorInt; //Evita movimientos imposibles de forma sencilla
					} catch (DeadSnakeException e1) {
						vivo = false;
					}
					Thread.sleep(150);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//Guarda los puntos si hizo mas de los q tiene guardados
			if(obtenerPuntuacionMaxima(fichero) < puntos.valorInt)
				guardarPuntuacionMaxima(fichero,puntos);
			frame.dispose();
			
			//Almacenamos en el ranking la situacion actual -> Ya funciona (falta uan forma de mostrarlo)
			actualizarRanking(fichero, puntos);			
			
		}).run();

	}
	
	//Metodos de configuracion
	
			/**
			 * Configura el frame que contendra la puntuacion y la cuadricula del juego
			 * 
			 * @param frame El frame a configurar
			 */
			public static void configurarFrame(JFrame frame) {
				frame.setLayout(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				Insets insets = frame.getInsets();
				frame.setPreferredSize(new Dimension(400 + insets.left + insets.right, 600 + insets.top + insets.bottom));
				frame.pack();
				frame.setResizable(false);
			}
			/**
			 * Configura el contenedor que contiene la puntuacion a partir del mismo, el albel que contiene, 
			 * y los puntos a mostrar
			 * @param container  Contenedor que contendra el label
			 * @param puntuacion El label que contiene la puntuación
			 * @param puntos     Objeto que contiene los puntos que debe mostrar
			 */
			private static void configurarContenedorPuntuacion(Container container, JLabel puntuacion, Dato puntos) {
				puntuacion.setOpaque(true);
				puntuacion.setVisible(true);
				puntuacion.setText(String.format("POINTS: %03d", puntos.valorInt));// Tiene un medio centrado con espacios por ahora
				puntuacion.setBackground(Color.white);
				puntuacion.setHorizontalTextPosition(SwingConstants.CENTER); // Ns pq pero no va
				puntuacion.setFont(new Font("Puntuacion?", Font.BOLD, 50));// Necesito usar esto pq es lo q encontre para el tamaño del texto
				container.add(puntuacion);
				container.setBounds(0, 0, 400, 100); // Le da la posicion que quiere ocupar tambien, q es encima de la casilla (solo funciona pq el layout del frame lo puse en null)
				container.setLayout(new GridLayout(1, 1));// Forma q encontre pa q se mostrase el cabron
			}

			/**
			 * Basicamente movi aquí toda la lógica encargada de armar la cuadricula en el contenedor,
			 * y de pintar los datos necesarios para empezar la partida en la misma
			 * 
			 * @param container	Contenedor que contendra mis JLabels
			 * @param cuadricula	Matriz de JLabels a colorear
			 * @param serpiente	Conjunto de coordenadas que forman a mi serpiente
			 * @param filas		Numero de filas con las que cuenta la matricula
			 * @param columnas
			 */
			private static void configurarContenedorCuadricula(Container container, JLabel[][] cuadricula, LinkedList<Coordenada> serpiente, int filas, int columnas) {
				container.setLayout(new GridLayout(filas, columnas));

				// Se crean y almacenan como "blancos" los labels
				for (int i = 0; i < filas; i++)
					for (int j = 0; j < columnas; j++) {
						JLabel l = new JLabel();
						l.setOpaque(true);
						l.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
						l.setBackground(Color.white);
						cuadricula[i][j] = l;
					}

				// Ponemos de color a la serpiente
				for (var casilla : serpiente)
					color(cuadricula, casilla, Color.black);
				color(cuadricula, serpiente.getLast(), Color.gray);

				// La forma q encontre, se guarda cada coso de pantalla en un coso de container
				for (int i = 0; i < filas; i++)
					for (int j = 0; j < columnas; j++)
						container.add(cuadricula[i][j]); // Me tomo mi rato averiguar q hacerle a esto
				
				container.setBounds(0, 200, 400, 400); // Pasa también la posición
				crearManzanita(cuadricula);// Añadimos la manzanita
			}
			/**
			 * Configura el contenedor que contiene la maxima puntuacion en la ventana
			 * 
			 * @param cMaxPoints El contenedor en si
			 * @param lPoints    El label en el que se escriben los puntos  
			 * @param maxPuntos  Referencia a los puntos maximos actuales
			 */
			public static void configurarContenedorMaxPoints(Container cMaxPoints, JLabel lPoints, Dato maxPuntos) {
				cMaxPoints.setBounds(0, 100, 400, 100);
				cMaxPoints.setVisible(true);
				lPoints.setText(String.format("M.POINTS: %03d" ,maxPuntos.valorInt));
				cMaxPoints.setLayout(new GridLayout(1, 1));
				lPoints.setFont(new Font("Puntuacion?", Font.BOLD, 50));
				lPoints.setOpaque(true);
				lPoints.setBackground(Color.white);
				cMaxPoints.add(lPoints);
			}
			
			/**
			 * Una agrupacion de todas las configuraciones que debes hacer en la ventana<br>
			 * Este metodo fue creado con el objetivo de facilitar la lectura del codigo
			 */
			public static void configurarVentana(JFrame frame,Container cMaxPoints, JLabel maxPoints,Dato maxPuntos, Container container, JLabel puntuacion, Dato puntos, Container containerPuntuacion, JLabel[][] cuadricula, LinkedList<Coordenada> serpiente, int filas, int columnas, Dato direccion, Dato nuevaDireccion) {
				configurarContenedorCuadricula(container, cuadricula, serpiente, filas, columnas);
				configurarContenedorPuntuacion(containerPuntuacion,puntuacion, puntos);
				configurarContenedorMaxPoints(cMaxPoints, maxPoints, maxPuntos);
				frame.add(containerPuntuacion);
				frame.add(cMaxPoints);
				frame.add(container);
				configurarFrame(frame);// Ns q mas configuraciones necesita este frame, por ahora no creo q mas


				// Vamos a intentar crear un listener y añadirselo al frame -> Funciono :D
				// Crear listener -> Igual lo creo en un metodo, pero necesitaria hacer accesible despues direccion y tal volviendolos Dato
				KeyListener movimientos = new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {// Tengo q con un booleano comprobar si se aplico -> Al final lo adapte con la variable
						// Me gusta como quedo
						switch (e.getKeyCode()) {
						//Lleva esta logica para recordar la direccion actual y evitar suicidios
						case KeyEvent.VK_W, KeyEvent.VK_UP -> {
							if (direccion.valorInt != ABAJO)
								nuevaDireccion.valorInt = ARRIBA;
						}
						case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
							if (direccion.valorInt != ARRIBA)
								nuevaDireccion.valorInt = ABAJO;
						}
						case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
							if (direccion.valorInt != DERECHA)
								nuevaDireccion.valorInt = IZQUIERDA;
						}
						case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
							if (direccion.valorInt != IZQUIERDA)
								nuevaDireccion.valorInt = DERECHA;
						}
						default -> {/*Que fas?*/}
						}

					}
				};
				frame.addKeyListener(movimientos);
			}
			
//			/**
//			 * Crea una manzana en una posicion aleatoria que
//			 * no coincida con la serpiente
//			 * 
//			 * @param pantalla Cuadrícula en la que ha de crear la manzana
//			 */
//			public static void crearManzanita(JLabel[][] pantalla) {
//				Coordenada tempCoor = new Coordenada(ran.nextInt(pantalla.length), ran.nextInt(pantalla[0].length));
//				while (pantalla[tempCoor.fila][tempCoor.columna].getBackground() != Color.white)// Si no es blanco busca otro
//																								// lugar
//					tempCoor = new Coordenada(ran.nextInt(pantalla.length), ran.nextInt(pantalla[0].length));
//				color(pantalla, tempCoor, Color.red);// Crea mi manzanita
//			}
			/**
			 * Cambia el color del background de la coordenada especificada dentro de la cuadricula 
			 * al color indicado
			 * @param pantalla	Matriz en la que ha de cambiar el color
			 * @param posicion	Coordenada concreta
			 * @param color		Color del que quieres que sea la coordenada
			 */
			public static void color(JLabel[][] pantalla, Coordenada posicion, Color color) {
				pantalla[posicion.fila][posicion.columna].setBackground(color);
			}
	
	
	//Metodos de apoyo/abstracción
	


//	/**
//	 * Se encarga de desplazar la serpiente y actualizar la cuadrícula en base a la
//	 * dirección actual de la misma
//	 * 
//	 * @param serp     Lista enlazada con las coordenadas de la serpiente
//	 * @param pantalla Cuadrícula sobre la que se muestra la serpiente
//	 * @param dir      Dirección o sentido actual de la serpiente
//	 * @throws DeadSnakeException Cuando muere la serpiente en la cuadrícula a causa
//	 *                            del movimiento
//	 */
//	public static void actualizarCuadrícula(LinkedList<Coordenada> serp, JLabel[][] pantalla, int dir, JLabel puntuacion, Dato puntos,JLabel maxPoints, Dato maxPuntos) //Inicialmente lo había llamado mover, pero tambien me vale
//			throws DeadSnakeException {
//		int f = 0, c = 0;
//		Coordenada cabeza = serp.getLast();
//		// Falta ver si puede cambiar la direccion, pero eso, como sea
//		// ira pa el evento
//
//		switch (dir) {
//		case DERECHA -> c++;
//		case IZQUIERDA -> c--;
//		case ARRIBA -> f--;
//		case ABAJO -> f++;
//		}
//
//		Coordenada nuevaCabeza = new Coordenada(cabeza, f, c);
//
//		// Miro si se come una manzanita
//		if (pantalla[(Math.min(pantalla.length - 1, (Math.max(0,nuevaCabeza.fila))))][(Math.min(pantalla.length - 1, (Math.max(0,nuevaCabeza.columna))))].getBackground() != Color.red) { //Voy a usar un min y max para q no salga de los limites el valor
//			color(pantalla, serp.getFirst(), Color.white); // Antigua cola, en blanco
//			serp.removeFirst();// Ya es blanca, deja de existir
//		} else {
//			crearManzanita(pantalla);
//			aumentarPuntuacion(puntuacion, puntos);
//			if(puntos.valorInt > maxPuntos.valorInt)
//				aumentarMaxPuntuacion(maxPoints, maxPuntos);
//			
//		}
//		if (!cabezaValida(pantalla, nuevaCabeza))// Si la cabeza toma una posicición inválida lanza una excepción a capturar
//			throw new DeadSnakeException();
//
//		color(pantalla, serp.getLast(), Color.black);// Antigua cabeza, en negro
//		serp.add(nuevaCabeza);// Añadimos la nueva cabeza
//
//		color(pantalla, nuevaCabeza, Color.gray);// La ponemos en gris
//
//		// Actualizamos la direccion a la nueva direccion
////		direccion == nuevaDireccion Tuve q mover esta tonteria pa fuera pq no es accesible
//
//	}

	/**
	 * Función empleada para comprobar la validez de una posicion espacial para la
	 * nueva cabeza de la serpiente
	 * 
	 * @param pantalla Cuadricula {@code JLabel[][]} en la que debe comprobar la
	 *                 validez de la nueva cabeza
	 * @param cabeza   Coordenada que pretende ocupar la cabeza dentro de la
	 *                 cuadricula
	 * @return true si esta cabeza es posible, false si no lo es
	 */
	public static boolean cabezaValida(JLabel[][] pantalla, Coordenada cabeza) {
		return !(cabeza.fila > pantalla.length - 1 || cabeza.columna > pantalla[0].length - 1 || cabeza.fila < 0
				|| cabeza.columna < 0 || pantalla[cabeza.fila][cabeza.columna].getBackground() == Color.black);
	}

	
	/**
	 * Aumenta en uno la puntuación y lo actualiza en el label correspondiente
	 * 
	 * @param puntitos Label que muestra los puntos
	 * @param puntos   Referencia a los puntos
	 */
	public static void aumentarPuntuacion(JLabel puntitos, Dato puntos) {
		puntitos.setText(String.format("POINTS: %03d", puntos.valorInt++ + 1));
	}
	/**
	 * Aumenta en uno la puntuación máxima y lo actualiza en el label correspondiente
	 * 
	 * @param puntitos Label que muestra los puntos máximos 
	 * @param puntos   Referencia a los puntos maximos
	 */
	public static void aumentarMaxPuntuacion(JLabel puntitos, Dato puntos) {
		puntitos.setText(String.format("M.POINTS: %03d", puntos.valorInt++ + 1));
	}
	//Metodos que trabajan con la puntuacion maxima en el archivo binario 
	
	/**
	 * Almacena en un archivo binario la puntuación maxima alcanzada
	 * 
	 * @param nombre del archivo en el que ha de guardar la puntuacion
	 * @param puntuacion Puntuación que ha de guardar
	 */
	private static void guardarPuntuacionMaxima(String fichero, Dato puntuacion) {
		try(DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("src/game/guardado/" + fichero)))  ){
			out.writeShort(puntuacion.valorInt);
		} catch (IOException e ) {
			e.printStackTrace();
		}
	}
	/**
	 * Obtiene la puntuación máxima almacenada en el archivo binario, y si ocurriese un 
	 * FileNotFoundException devuelve 0, ya que implica que es la primera vez que juega
	 * 
	 * @param  Nombre del archivo en el que ha de buscar la puntuacion maxima
	 * @return La puntuacion máxima almacenada, o 0 si es la primera vez que juega
	 */
	private static short obtenerPuntuacionMaxima(String fichero) {
		try(DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("src/game/guardado/" + fichero)))  ){
			return in.readShort();
		} catch (FileNotFoundException e ) {
//			e.printStackTrace();
//			System.out.println("Primera vez jugando e :D");
			//Entra por aca cuando es la primera vez que juega la persona
		} catch (IOException e){
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * Se encarga de actualizar el ranking.json para poder mostrarlo graficamente
	 * a posteriori
	 * 
	 * @param fichero Utilizado para guardar el nombre del actual jugador
	 * @param puntos Los puntos que obtuvo en esta partida
	 */
	public static void actualizarRanking(String fichero, Dato puntos) {
		//Creo q funciona? Ojala y lo haga, pq vaya quebradero de cabeza
		RankingService servicio = new RankingService();
		Ranking modelo = new Ranking(servicio.obtenerPlayers());
		modelo.añadirPlayer(new Player(fichero.replaceAll("([a-z]*).bin", "$1"), puntos.valorInt));
		servicio.guardar(modelo.getPlayers());
	}
	/**
	 * Elimina los archivos binarios de guardado y "limpia" el archivo json
	 */
	public static void reset() {
		//Busca todos los hijos q atraviesen el filtro y los borra
		for(File file: new File("src/game/guardado").listFiles(e -> (e.getName().matches("[a-z]*\\.bin"))))
			file.delete();
		//Ahora hay solo que rehacer el json		
		try(FileWriter f = new FileWriter("src/game/guardado/ranking.json")){
			JSONWriter jW = new JSONWriter(f);
			jW.object();
			jW.key("First").value(null)
			  .key("Second").value(null)
			  .key("Third").value(null)
			  .key("Fourth").value(null)
			  .key("Fifth").value(null)
			  .endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}