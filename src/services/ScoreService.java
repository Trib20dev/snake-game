package services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScoreService {
	private String rutaFichero;

	/**
	 * Constructor de la clase
	 * 
	 * @param nombre Nombre de la persona en cuestion a revisar sus puntos,<br>
	 *        utilizado para localizar o formar su archivo binario
	 */
	public ScoreService(String nombre) {
		rutaFichero = "src/data/bin/" + nombre + ".bin";
	}

	/**
	 * Almacena en un archivo binario la puntuación maxima alcanzada si esta es
	 * superior a la máxima original
	 * 
	 * @param puntuacion Puntuación que ha de guardar
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
	 * Obtiene la puntuación máxima almacenada en el archivo binario, y si ocurriese
	 * un FileNotFoundException devuelve 0, ya que implica que es la primera vez que
	 * juega
	 * 
	 * @param Nombre del archivo en el que ha de buscar la puntuacion maxima
	 * @return La puntuacion máxima almacenada, o 0 si es la primera vez que juega
	 */
	public short obtenerPuntuacionMaxima() {
		try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(rutaFichero)))) {
			return in.readShort();
		} catch (FileNotFoundException e) {
			// Entra por aca cuando es la primera vez que juega la persona
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
