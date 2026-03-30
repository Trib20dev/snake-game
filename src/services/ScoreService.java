package services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Servicio que gestiona la puntuación máxima de un jugador.
 * <p>
 * Almacena y recupera la puntuación máxima en archivos binarios individuales
 * por jugador. Cada jugador tiene un archivo "{nombre}.bin" en "src/data/bin/".
 */
public class ScoreService {
	/** Ruta del archivo binario que almacena la puntuación máxima del jugador */
	private String rutaFichero;

	/**
	 * Constructor que inicializa la ruta del archivo para el jugador indicado.
	 *
	 * @param nombre nombre del jugador (se usará para generar o acceder el archivo
	 *               binario)
	 */
	public ScoreService(String nombre) {
		rutaFichero = "src/data/bin/" + nombre + ".bin";
	}

	/**
     * Actualiza la puntuación máxima del jugador si la nueva puntuación es mayor
     * que la previamente almacenada.
     *
     * @param puntuacion puntuación actual obtenida por el jugador
     */
	public void actualizarPuntuacionMaxima(int puntuacion) {
		int mPoints = obtenerPuntuacionMaxima();
		if (puntuacion > mPoints)
			try (DataOutputStream out = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(rutaFichero)))) {
				out.writeShort(puntuacion);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
     * Obtiene la puntuación máxima almacenada para el jugador.
     * <p>
     * Si no existe el archivo, devuelve 0 indicando que es la primera partida del
     * jugador.
     *
     * @return la puntuación máxima registrada, o 0 si no existe archivo
     */
	public short obtenerPuntuacionMaxima() {
		try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(rutaFichero)))) {
			return in.readShort();
		} catch (FileNotFoundException e) {
			// Archivo no encontrado: primera vez que juega
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
