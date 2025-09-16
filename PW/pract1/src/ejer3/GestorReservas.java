package ejer3;

import ejer1.Jugador;
import ejer1.Pista;
import ejer1.TipoPista;	
import ejer2.Reserva;
import ejer2.ReservaAdultos;
import ejer2.ReservaFamiliar;
import ejer2.ReservaInfantil;
import ejer3.Bono;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



/**
 * La clase {@code GestorReservas} es responsable de gestionar las reservas 
 * para diferentes tipos de usuarios (adultos, familiares, e infantiles) y 
 * también de manejar los bonos asociados a dichas reservas.
 *
 * <p>Implementa el patrón Singleton, lo que asegura que solo exista una 
 * instancia de esta clase durante la ejecución de la aplicación.</p>
 */
public class GestorReservas{
	
	private static GestorReservas instance;
	
	private ArrayList<ReservaAdultos> reservasAdultos;
	private ArrayList<ReservaFamiliar> reservasFamiliares;
	private ArrayList<ReservaInfantil> reservasInfantiles;
	private ArrayList<Bono> bonos;
	private String rutaArchivoReservasAdultos = "reservas_adultos.txt";
	private String rutaArchivoReservasInfantiles = "reservas_infantiles.txt";
	private String rutaArchivoReservasFamiliares = "reservas_familiares.txt";
	private String rutaArchivoBonos = "bonos.txt";

	/**
	 * Constructor privado para la clase {@link GestorReservas}.
	 *
	 * <p>Inicializa las listas para almacenar las reservas de adultos, 
	 * familiares e infantiles, así como los bonos. También carga las 
	 * reservas y los bonos desde los archivos correspondientes.</p>
	 *
	 * <p>Este constructor es privado para garantizar que la clase 
	 * siga el patrón Singleton, permitiendo la creación de una 
	 * única instancia de {@link GestorReservas}.</p>
	 */
	private GestorReservas() {
		reservasAdultos = new ArrayList<>();
		reservasFamiliares = new ArrayList<>();
		reservasInfantiles = new ArrayList<>();
		bonos= new ArrayList<>();
        cargarReservasAdultosDesdeArchivo();
        cargarReservasInfantilesDesdeArchivo();
        cargarReservasFamiliaresDesdeArchivo();
		cargarBonosDesdeArchivo();

	}

	/**
	 * Obtiene la instancia única de la clase {@link GestorReservas}.
	 *
	 * <p>Este método implementa el patrón Singleton, garantizando que solo 
	 * se cree una única instancia de la clase {@link GestorReservas}. Si la 
	 * instancia no ha sido creada previamente, se inicializa y se devuelve.</p>
	 *
	 * @return la instancia única de {@link GestorReservas}
	 */
	public static GestorReservas getInstance() {
		if(instance == null) {
			instance = new GestorReservas();
		}
		return instance;
	}

	/**
	 * Crea un nuevo bono de reservas para un usuario específico y un tipo de pista.
	 *
	 * <p>Esta función inicializa un objeto de tipo {@link Bono} con el identificador del usuario 
	 * y el tipo de pista especificados. El bono creado puede ser utilizado para realizar reservas 
	 * a un precio reducido o con condiciones especiales según las políticas de la aplicación.</p>
	 *
	 * @param idUsuario el identificador del usuario que solicitará el bono
	 * @param tipo el tipo de pista para el que se crea el bono
	 * @return el objeto {@link Bono} creado
	 */
	public Bono crearBonoReservas(String idUsuario, TipoPista tipo) {
        Bono bono = new Bono(idUsuario, tipo);
        return bono;
    }
	
	/**
	 * Modifica la fecha y hora, así como la duración de una reserva existente.
	 *
	 * <p>Esta función busca en las listas de reservas (adultos, infantiles y familiares) 
	 * una reserva que coincida con el nombre de la pista y la fecha y hora proporcionadas. 
	 * Si se encuentra la reserva, se actualiza con la nueva fecha, duración y se recalcula el precio. 
	 * Si la reserva está asociada a un bono, también se modifica la fecha en el bono correspondiente.</p>
	 *
	 * @param nombre_pista el nombre de la pista de la reserva que se desea modificar
	 * @param fecha_hora la fecha y hora de la reserva que se desea modificar
	 * @param fecha_nueva la nueva fecha y hora que se asignará a la reserva
	 * @param duración la nueva duración que se asignará a la reserva
	 * @return <code>true</code> si la reserva ha sido modificada, 
	 *         <code>false</code> si no se encontró la reserva para modificar
	 */
	public boolean modificarReserva(String nombre_pista, Date fecha_hora, Date fecha_nueva, int duración) {
		
		for (Reserva reserva : reservasAdultos) {
			if (reserva.getNombrePista().equals(nombre_pista) && reserva.getFecha_hora().equals(fecha_hora)) {
				//Comprobamos si es una reserva de un bono para modificar el bono
				if(reserva.getDescuento() == 0.05f) {
					for(Bono bono : bonos) {
						for(int i=0; i < 5-bono.getSesiones(); i++) {
							if(bono.getFecha(i).equals(fecha_hora) && bono.getNombrePista(i).equals(nombre_pista)) {
								bono.setFecha(i, fecha_nueva);
							}
						}
					}
				}
				reserva.setFecha_hora(fecha_nueva);
				reserva.setDuracion(duración);
				reserva.setPrecio(reserva.AsignarPrecio(duración));
				return true;
			}
		}
		for (Reserva reserva : reservasInfantiles) {
			if (reserva.getNombrePista().equals(nombre_pista) && reserva.getFecha_hora().equals(fecha_hora)) {
				//Comprobamos si es una reserva de un bono para modificar el bono
				if(reserva.getDescuento() == 0.05f) {
					for(Bono bono : bonos) {
						for(int i=0; i < 5-bono.getSesiones(); i++) {
							if(bono.getFecha(i).equals(fecha_hora) && bono.getNombrePista(i).equals(nombre_pista)) {
								bono.setFecha(i, fecha_nueva);
							}
						}
					}
				}
				reserva.setFecha_hora(fecha_nueva);
				reserva.setDuracion(duración);
				reserva.setPrecio(reserva.AsignarPrecio(duración));
				return true;
			}
		}
		for (Reserva reserva : reservasFamiliares) {
			if (reserva.getNombrePista().equals(nombre_pista) && reserva.getFecha_hora().equals(fecha_hora)) {
				//Comprobamos si es una reserva de un bono para modificar el bono
				if(reserva.getDescuento() == 0.05f) {
					for(Bono bono : bonos) {
						for(int i=0; i < 5-bono.getSesiones(); i++) {
							if(bono.getFecha(i).equals(fecha_hora) && bono.getNombrePista(i).equals(nombre_pista)) {
								bono.setFecha(i, fecha_nueva);
							}
						}
					}
				}
				reserva.setFecha_hora(fecha_nueva);
				reserva.setDuracion(duración);
				reserva.setPrecio(reserva.AsignarPrecio(duración));
				return true;
			}
		}
		return false;
	}

	/**
	 * Cancela una reserva en función del nombre de la pista y la fecha y hora de la reserva.
	 *
	 * <p>Esta función busca en las listas de reservas (adultos, infantiles y familiares) 
	 * una reserva que coincida con el nombre de la pista y la fecha y hora proporcionadas. 
	 * Si encuentra la reserva, la elimina de la lista correspondiente y, si es una reserva asociada a un bono, 
	 * también cancela la reserva en el bono relacionado. </p>
	 *
	 * @param nombre_pista el nombre de la pista de la reserva que se desea cancelar
	 * @param fecha_hora la fecha y hora de la reserva que se desea cancelar
	 * @return <code>true</code> si la reserva ha sido cancelada, 
	 *         <code>false</code> si no se encontró la reserva
	 */
	public boolean cancelarReserva(String nombre_pista, Date fecha_hora) {
		
		for (Reserva reserva : reservasAdultos) {
			if (reserva.getNombrePista().equals(nombre_pista) && reserva.getFecha_hora().equals(fecha_hora)) {
				//Comprobamos si es una reserva de un bono para modificar el bono
				if(reserva.getDescuento() == 0.05f) {
					for(Bono bono : bonos) {
						for(int i=0; i < 5-bono.getSesiones(); i++) {
							if(bono.getFecha(i).equals(fecha_hora) && bono.getNombrePista(i).equals(nombre_pista)) {
								bono.cancelarReserva(i);
							}
						}
					}
				}
				reservasAdultos.remove(reserva);
				return true;
			}
		}
		for (Reserva reserva : reservasInfantiles) {
			if (reserva.getNombrePista().equals(nombre_pista) && reserva.getFecha_hora().equals(fecha_hora)) {
				//Comprobamos si es una reserva de un bono para modificar el bono
				if(reserva.getDescuento() == 0.05f) {
					for(Bono bono : bonos) {
						for(int i=0; i < 5-bono.getSesiones(); i++) {
							if(bono.getFecha(i).equals(fecha_hora) && bono.getNombrePista(i).equals(nombre_pista)) {
								bono.cancelarReserva(i);
							}
						}
					}
				}
				reservasInfantiles.remove(reserva);
				return true;
			}
		}
		for (Reserva reserva : reservasFamiliares) {
			if (reserva.getNombrePista().equals(nombre_pista) && reserva.getFecha_hora().equals(fecha_hora)) {
				//Comprobamos si es una reserva de un bono para modificar el bono
				if(reserva.getDescuento() == 0.05f) {
					for(Bono bono : bonos) {
						for(int i=0; i < 5-bono.getSesiones(); i++) {
							if(bono.getFecha(i).equals(fecha_hora) && bono.getNombrePista(i).equals(nombre_pista)) {
								bono.cancelarReserva(i);
							}
						}
					}
				}
				reservasFamiliares.remove(reserva);
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Muestra por pantalla el número de reservas de cada tipo (adultos, infantiles y familiares) 
	 * para una fecha y pista específica.
	 *
	 * <p>Esta función itera a través de las reservas de adultos, infantiles y familiares, 
	 * y cuenta cuántas de ellas están programadas para la fecha y pista especificadas. 
	 * Al finalizar, imprime el número de reservas de cada tipo en la consola. 
	 * Si no hay reservas de un tipo específico para la fecha y pista indicadas, 
	 * se informa que no hay reservas.</p>
	 *
	 * @param fecha la fecha para la que se desea consultar las reservas
	 * @param nombre el nombre de la pista para la que se desean consultar las reservas
	 */
	public void mostrarReservas(Date fecha, String nombre) {
		int contAdulto=0, contInfantil=0, contFamiliar=0;
		Calendar calFecha = Calendar.getInstance();
		calFecha.setTime(fecha);
		for(ReservaAdultos reserva: reservasAdultos) {

			Calendar calReserva = Calendar.getInstance();
			calReserva.setTime(reserva.getFecha_hora());

			if(calReserva.get(Calendar.DAY_OF_MONTH) == calFecha.get(Calendar.DAY_OF_MONTH)
                && calReserva.get(Calendar.MONTH) == calFecha.get(Calendar.MONTH)
                && calReserva.get(Calendar.YEAR) == calFecha.get(Calendar.YEAR)
                && reserva.getNombrePista().equals(nombre)){
				contAdulto++;
			}
		}
		if(contAdulto == 0) {
			System.out.println("No hay reservas de adultos en la pista "+nombre+" en la fecha " + calFecha.get(Calendar.DAY_OF_MONTH) + "/" + (calFecha.get(Calendar.MONTH) + 1) + "/" 
            + calFecha.get(Calendar.YEAR));
		}
		else {
			System.out.println("Hay "+contAdulto+" reservas de adultos en la pista "+nombre+" en la fecha "+ calFecha.get(Calendar.DAY_OF_MONTH) + "/" + (calFecha.get(Calendar.MONTH) + 1) + "/" 
            + calFecha.get(Calendar.YEAR));
		}

		for(ReservaInfantil reserva: reservasInfantiles) {

			Calendar calReserva = Calendar.getInstance();
			calReserva.setTime(reserva.getFecha_hora());

			if(calReserva.get(Calendar.DAY_OF_MONTH) == calFecha.get(Calendar.DAY_OF_MONTH)
                && calReserva.get(Calendar.MONTH) == calFecha.get(Calendar.MONTH)
                && calReserva.get(Calendar.YEAR) == calFecha.get(Calendar.YEAR)
                && reserva.getNombrePista().equals(nombre)){
				contInfantil++;
			}
		}
		if(contInfantil == 0) {
			System.out.println("No hay reservas infantiles en la pista "+nombre+" en la fecha " + calFecha.get(Calendar.DAY_OF_MONTH) + "/" + (calFecha.get(Calendar.MONTH) + 1) + "/" 
            + calFecha.get(Calendar.YEAR));
		}
		else {
			System.out.println("Hay "+contInfantil+" reservas infantiles en la pista "+nombre+" en la fecha "+ calFecha.get(Calendar.DAY_OF_MONTH) + "/" + (calFecha.get(Calendar.MONTH) + 1) + "/" 
            + calFecha.get(Calendar.YEAR));
		}

		for(ReservaFamiliar reserva: reservasFamiliares) {

			Calendar calReserva = Calendar.getInstance();
			calReserva.setTime(reserva.getFecha_hora());

			if(calReserva.get(Calendar.DAY_OF_MONTH) == calFecha.get(Calendar.DAY_OF_MONTH)
                && calReserva.get(Calendar.MONTH) == calFecha.get(Calendar.MONTH)
                && calReserva.get(Calendar.YEAR) == calFecha.get(Calendar.YEAR)
                && reserva.getNombrePista().equals(nombre)){
				contFamiliar++;
			}
		}
		if(contFamiliar == 0) {
			System.out.println("No hay reservas familiares en la pista "+nombre+" en la fecha " + calFecha.get(Calendar.DAY_OF_MONTH) + "/" + (calFecha.get(Calendar.MONTH) + 1) + "/" 
            + calFecha.get(Calendar.YEAR));
		}
		else {
			System.out.println("Hay "+contFamiliar+" reservas familiares en la pista "+nombre+" en la fecha "+ calFecha.get(Calendar.DAY_OF_MONTH) + "/" + (calFecha.get(Calendar.MONTH) + 1) + "/" 
            + calFecha.get(Calendar.YEAR));
		}
	}
	
	/**
	 * Muestra el número de reservas futuras de cada tipo (adultos, infantiles y familiares).
	 *
	 * <p>Esta función itera a través de las reservas de adultos, infantiles y familiares, 
	 * y cuenta cuántas de ellas están programadas para una fecha y hora futura. 
	 * Al finalizar, imprime el número de reservas futuras de cada tipo en la consola. 
	 * Si no hay reservas futuras de un tipo específico, se indica que no hay reservas 
	 * futuras para ese tipo.</p>
	 */
	public void mostrarReservasFuturas() {
		int contAdulto=0, contInfantil=0, contFamiliar=0;
		Calendar calActual = Calendar.getInstance();
		calActual.setTime(new Date());

		for(ReservaAdultos reserva: reservasAdultos) {

			Calendar calReserva = Calendar.getInstance();
			calReserva.setTime(reserva.getFecha_hora());

			if(calReserva.after(calActual)) {
				contAdulto++;
			}
		}
		if(contAdulto == 0) {
			System.out.println("No hay reservas de adultos futuras");
		}
		else {
			System.out.println("Hay "+contAdulto+" reservas de adultos futuras");
		}

		for(ReservaInfantil reserva: reservasInfantiles) {

			Calendar calReserva = Calendar.getInstance();
			calReserva.setTime(reserva.getFecha_hora());

			if(calReserva.after(calActual)) {
				contInfantil++;
			}
		}
		if(contInfantil == 0) {
			System.out.println("No hay reservas infantiles futuras");
		}
		else {
			System.out.println("Hay "+contInfantil+" reservas infantiles futuras");
		}

		for(ReservaFamiliar reserva: reservasFamiliares) {

			Calendar calReserva = Calendar.getInstance();
			calReserva.setTime(reserva.getFecha_hora());

			if(calReserva.after(calActual)) {
				contFamiliar++;
			}
		}
		if(contFamiliar == 0) {
			System.out.println("No hay reservas familiares futuras");
		}
		else {
			System.out.println("Hay "+contFamiliar+" reservas familiares futuras");
		}
	}
	
	/**
	 * Guarda las reservas de adultos del ArrayList de reservas de adultos en 
	 * el archivo de texto "reservas_adultos.txt".
	 *
	 * <p>Esta función itera sobre todas las reservas de adultos almacenadas en el 
	 * ArrayList y escribe cada una de ellas en el archivo especificado. La fecha y 
	 * hora de la reserva se formatea en el formato "dd/MM/yyyy HH:mm". Cada línea 
	 * escrita en el archivo contiene información sobre el usuario, la fecha y hora, 
	 * la duración, el nombre de la pista, el precio, el descuento y la cantidad de 
	 * adultos. Si ocurre un error durante el proceso de escritura, se imprime un 
	 * mensaje de error en la consola.</p>
	 *
	 * @throws IOException si hay un error al escribir en el archivo.
	 */
	public void guardarReservasAdultosEnArchivo() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivoReservasAdultos))) {
			for (ReservaAdultos it : getAllReservasAdultos()) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	            String fecha_horaFormateada = formatter.format(it.getFecha_hora());
				writer.write(it.getId_user() + "," + fecha_horaFormateada + "," + it.getDuracion() + "," + it.getNombrePista() + "," + it.getPrecio() + "," + it.getDescuento()+ ","+ it.getAdultos());
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error al guardar reservas de adultos: " + e.getMessage());
		}
	}

	/**
	 * Guarda las reservas infantiles del ArrayList de reservas infantiles en 
	 * el archivo de texto "reservas_infantiles.txt".
	 *
	 * <p>Esta función itera sobre todas las reservas infantiles almacenadas en el 
	 * ArrayList y escribe cada una de ellas en el archivo especificado. La fecha y 
	 * hora de la reserva se formatea en el formato "dd/MM/yyyy HH:mm". Cada línea 
	 * escrita en el archivo contiene información sobre el usuario, la fecha y hora, 
	 * la duración, el nombre de la pista, el precio, el descuento y la cantidad de 
	 * niños. Si ocurre un error durante el proceso de escritura, se imprime un 
	 * mensaje de error en la consola.</p>
	 *
	 * @throws IOException si hay un error al escribir en el archivo.
	 */
	public void guardarReservasInfantilesEnArchivo() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivoReservasInfantiles))) {
			for (ReservaInfantil it : getAllReservasInfantiles()) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	            String fecha_horaFormateada = formatter.format(it.getFecha_hora());
				writer.write(it.getId_user() + "," + fecha_horaFormateada + "," + it.getDuracion() + "," + it.getNombrePista() + "," + it.getPrecio() + "," + it.getDescuento()+ ","+ it.getNinos());
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error al guardar reservas infantiles: " + e.getMessage());
		}
	}
	
	/**
	 * Guarda las reservas familiares del ArrayList de reservas familiares en 
	 * el archivo de texto "reservas_familiares.txt".
	 *
	 * <p>Esta función itera sobre todas las reservas familiares almacenadas en el 
	 * ArrayList y escribe cada una de ellas en el archivo especificado. La fecha y 
	 * hora de la reserva se formatea en el formato "dd/MM/yyyy HH:mm". Cada línea 
	 * escrita en el archivo contiene información sobre el usuario, la fecha y hora, 
	 * la duración, el nombre de la pista, el precio, el descuento, la cantidad de 
	 * adultos y la cantidad de niños. Si ocurre un error durante el proceso de 
	 * escritura, se imprime un mensaje de error en la consola.</p>
	 *
	 * @throws IOException si hay un error al escribir en el archivo.
	 */
	public void guardarReservasFamiliaresEnArchivo() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivoReservasFamiliares))) {
			for (ReservaFamiliar it : getAllReservasFamiliares()) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	            String fecha_horaFormateada = formatter.format(it.getFecha_hora());
				writer.write(it.getId_user() + "," + fecha_horaFormateada + "," + it.getDuracion() + "," + it.getNombrePista() + "," + it.getPrecio() + "," + it.getDescuento()+ ","+ it.getAdultos()+ ","+ it.getNinos());
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error al guardar reservas familiares: " + e.getMessage());
		}
	}

	/**
	 * Carga las reservas de adultos desde el archivo de texto "reservas_adultos.txt"
	 * al ArrayList de reservas de adultos.
	 *
	 * <p>Esta función verifica si el archivo existe y, si es así, lee cada línea 
	 * del archivo. Cada línea se convierte en un objeto de tipo ReservaAdultos 
	 * utilizando el método estático {@link ReservaAdultos#fromTexto(String)} y 
	 * se añade al ArrayList de reservas de adultos. Si ocurre un error al parsear 
	 * una línea, se muestra un mensaje de error en la consola. Si hay un error 
	 * al intentar abrir o leer el archivo, se imprime un mensaje correspondiente.</p>
	 *
	 * @throws IOException si hay un error al leer el archivo.
	 */
	public void cargarReservasAdultosDesdeArchivo() {
		File archivo = new File(rutaArchivoReservasAdultos);
		if (archivo.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivoReservasAdultos))) {
				String linea;
				while ((linea = reader.readLine()) != null) {
					try {

						ReservaAdultos reserva = ReservaAdultos.fromTexto(linea);
						reservasAdultos.add(reserva);

					} catch (ParseException e) {
						System.err.println("Error al parsear las reservas de adultos en la línea: " + linea);
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				System.out.println("Error al cargar reservas de adultos: " + e.getMessage());
			}
		}
	}
	
	/**
	 * Carga las reservas infantiles desde el archivo de texto "reservas_infantiles.txt"
	 * al ArrayList de reservas infantiles.
	 *
	 * <p>Esta función verifica si el archivo existe y, si es así, lee cada línea 
	 * del archivo. Cada línea se convierte en un objeto de tipo ReservaInfantil 
	 * utilizando el método estático {@link ReservaInfantil#fromTexto(String)} y 
	 * se añade al ArrayList de reservas infantiles. Si ocurre un error al parsear 
	 * una línea, se muestra un mensaje de error en la consola. Si hay un error 
	 * al intentar abrir o leer el archivo, se imprime un mensaje correspondiente.</p>
	 *
	 * @throws IOException si hay un error al leer el archivo.
	 */
	public void cargarReservasInfantilesDesdeArchivo() {
		File archivo = new File(rutaArchivoReservasInfantiles);
		if (archivo.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivoReservasInfantiles))) {
				String linea;
				while ((linea = reader.readLine()) != null) {
					try {

						ReservaInfantil reserva = ReservaInfantil.fromTexto(linea);
						reservasInfantiles.add(reserva);
						
					} catch (ParseException e) {
						System.err.println("Error al parsear las reservas en la línea: " + linea);
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				System.out.println("Error al cargar reservas: " + e.getMessage());
			}
		}
	}

	/**
	 * Carga las reservas familiares desde el archivo de texto "reservas_familiares.txt"
	 * al ArrayList de reservas familiares.
	 *
	 * <p>Esta función verifica si el archivo existe y, si es así, lee cada línea 
	 * del archivo. Cada línea se convierte en un objeto de tipo ReservaFamiliar 
	 * utilizando el método estático {@link ReservaFamiliar#fromTexto(String)} y 
	 * se añade al ArrayList de reservas familiares. Si ocurre un error al parsear 
	 * una línea, se muestra un mensaje de error en la consola. Si hay un error 
	 * al intentar abrir o leer el archivo, se imprime un mensaje correspondiente.</p>
	 *
	 * @throws IOException si hay un error al leer el archivo.
	 */
	public void cargarReservasFamiliaresDesdeArchivo() {
		File archivo = new File(rutaArchivoReservasFamiliares);
		if (archivo.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivoReservasFamiliares))) {
				String linea;
				while ((linea = reader.readLine()) != null) {
					try {

						ReservaFamiliar reserva = ReservaFamiliar.fromTexto(linea);
						reservasFamiliares.add(reserva);
						
					} catch (ParseException e) {
						System.err.println("Error al parsear las reservas en la línea: " + linea);
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				System.out.println("Error al cargar reservas: " + e.getMessage());
			}
		}
	}

	/**
	 * Guarda los bonos almacenados en el ArrayList de bonos en el archivo de 
	 * texto "bonos.txt".
	 *
	 * <p>Esta función itera a través de cada bono y escribe los datos del bono, 
	 * incluyendo el ID del usuario, el tipo de bono y la fecha de caducidad, 
	 * en el archivo. Si el bono tiene reservas, también se guardan los 
	 * nombres de las pistas y las fechas de las reservas. En caso de que 
	 * ocurra un error al intentar escribir en el archivo, se mostrará un 
	 * mensaje de error en la consola.</p>
	 *
	 * @throws IOException si hay un error al escribir en el archivo.
	 */
	 public void guardarBonosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivoBonos))) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            for (Bono bono : bonos) {
                String fechaCaducidadFormateada = formatter.format(bono.getFechaCaducidad());
                writer.write(bono.getIdUsuario() + "," + bono.getTipo() + "," + fechaCaducidadFormateada);
                for (int i = 0; i < 5; i++) {
                    if (bono.getNombrePista(i) != null) {
                        String fechaReservaFormateada = formatter.format(bono.getFecha(i));
                        writer.write("," + bono.getNombrePista(i) + "," + fechaReservaFormateada);
                    }
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los bonos: " + e.getMessage());
        }
    }

	
	 /**
	  * Carga los bonos desde el archivo de texto "bonos.txt" y los agrega al 
	  * ArrayList de bonos.
	  *
	  * <p>Esta función verifica si el archivo existe y, si es así, lee cada 
	  * línea del archivo, parsea los datos correspondientes a cada bono y 
	  * los almacena en la lista de bonos. Si ocurre un error al parsear 
	  * alguna línea o al leer el archivo, se mostrará un mensaje de error en 
	  * la consola.</p>
	  *
	  * @throws IOException si hay un error al leer el archivo.
	  */
    public void cargarBonosDesdeArchivo() {
        File archivo = new File(rutaArchivoBonos);
        if (archivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivoBonos))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    try {
                        String[] datos = linea.split(",");
                        String idUsuario = datos[0];
                        TipoPista tipo = TipoPista.valueOf(datos[1]);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        Date fechaCaducidad = formatter.parse(datos[2]);
                        Bono bono = new Bono(idUsuario, tipo);
                        int index = 3;
                        while (index < datos.length) {
                            String nombrePista = datos[index++];
                            Date fechaReserva = formatter.parse(datos[index++]);
                            bono.añadirReserva(nombrePista, fechaReserva);
                        }
                        bonos.add(bono);
                    } catch (ParseException e) {
                        System.err.println("Error al parsear el bono en la línea: " + linea);
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al cargar los bonos: " + e.getMessage());
            }
        }
    }
	
    /**
     * Devuelve una lista de todas las reservas.
     *
     * <p>Esta función crea un nuevo ArrayList que contiene todas las 
     * reservas de diferentes tipos: adultos, familiares e infantiles. 
     * Se utiliza para acceder a todas las reservas registradas en el 
     * sistema, permitiendo una visualización completa.</p>
     *
     * @return Un {@link ArrayList} de objetos {@link Reserva} que 
     *         representa todas las reservas registradas.
     */
	public ArrayList<Reserva> getAllReservas() {
		ArrayList<Reserva> allReservas = new ArrayList<>();
		allReservas.addAll(reservasAdultos);
		allReservas.addAll(reservasFamiliares);
		allReservas.addAll(reservasInfantiles);
		return allReservas;
	}

	/**
	 * Devuelve una lista de todas las reservas de adultos.
	 *
	 * <p>Esta función crea un nuevo ArrayList que contiene todas las 
	 * reservas de adultos almacenadas en el sistema. Se utiliza para 
	 * acceder a las reservas de adultos y puede ser útil para 
	 * visualizaciones o informes.</p>
	 *
	 * @return Un {@link ArrayList} de objetos {@link ReservaAdultos} 
	 *         que representa todas las reservas de adultos registradas.
	 */
	public ArrayList<ReservaAdultos> getAllReservasAdultos() {
		ArrayList<ReservaAdultos> allReservas = new ArrayList<>();
		allReservas.addAll(reservasAdultos);
		return allReservas;
	}

	/**
	 * Devuelve una lista de todas las reservas infantiles.
	 *
	 * <p>Esta función crea un nuevo ArrayList que contiene todas las 
	 * reservas infantiles almacenadas en el sistema. Se utiliza para 
	 * acceder a las reservas infantiles y puede ser útil para 
	 * visualizaciones o informes.</p>
	 *
	 * @return Un {@link ArrayList} de objetos {@link ReservaInfantil} 
	 *         que representa todas las reservas infantiles registradas.
	 */
	public ArrayList<ReservaInfantil> getAllReservasInfantiles() {
		ArrayList<ReservaInfantil> allReservas = new ArrayList<>();
		allReservas.addAll(reservasInfantiles);
		return allReservas;
	}

	/**
	 * Devuelve una lista de todas las reservas familiares.
	 * 
	 * <p>Esta función crea un nuevo ArrayList que contiene todas las 
	 * reservas familiares almacenadas en el sistema. Se utiliza para 
	 * acceder a las reservas familiares y puede ser útil para 
	 * visualizaciones o informes.</p>
	 *
	 * @return Un {@link ArrayList} de objetos {@link ReservaFamiliar} 
	 *         que representa todas las reservas familiares registradas.
	 */
	public ArrayList<ReservaFamiliar> getAllReservasFamiliares() {
		ArrayList<ReservaFamiliar> allReservas = new ArrayList<>();
		allReservas.addAll(reservasFamiliares);
		return allReservas;
	}
	
	/**
	 * Añade una reserva de adultos al listado de reservas para adultos.
	 * 
	 * <p>Esta función toma un objeto {@link ReservaAdultos} como 
	 * parámetro y lo agrega al ArrayList de reservas para adultos. 
	 * Es útil para gestionar las reservas específicas para adultos.</p>
	 *
	 * @param reserva El objeto {@link ReservaAdultos} que se desea 
	 *                añadir al ArrayList de reservas para adultos.
	 */
	public void añadirReservaAdultos(ReservaAdultos reserva) {
		reservasAdultos.add(reserva);
	}
	
	/**
	 * Añade una reserva infantil al listado de reservas infantiles.
	 * 
	 * <p>Esta función toma un objeto {@link ReservaInfantil} como 
	 * parámetro y lo agrega al ArrayList de reservas infantiles. 
	 * Es útil para gestionar las reservas específicas para niños.</p>
	 *
	 * @param reserva El objeto {@link ReservaInfantil} que se desea 
	 *                añadir al ArrayList de reservas infantiles.
	 */
	public void añadirReservaInfantil(ReservaInfantil reserva) {
		reservasInfantiles.add(reserva);
	}
	
	/**
	 * Añade una reserva familiar al listado de reservas familiares.
	 * 
	 * <p>Esta función toma un objeto {@link ReservaFamiliar} como 
	 * parámetro y lo agrega al ArrayList de reservas familiares. 
	 * Es útil para gestionar las reservas específicas para familias.</p>
	 *
	 * @param reserva El objeto {@link ReservaFamiliar} que se desea 
	 *                añadir al ArrayList de reservas familiares.
	 */
	public void añadirReservaFamiliar(ReservaFamiliar reserva) {
		reservasFamiliares.add(reserva);
	}
	
	/**
	 * Añade un bono al listado de bonos disponibles.
	 * 
	 * <p>Esta función toma un objeto {@link Bono} como parámetro y lo 
	 * agrega al ArrayList de bonos. Es útil para gestionar los bonos 
	 * de los usuarios y realizar un seguimiento de ellos.</p>
	 *
	 * @param bono El objeto {@link Bono} que se desea añadir al 
	 *             ArrayList de bonos.
	 */
	public void añadirBono(Bono bono) {
		bonos.add(bono);
	}
	
	/**
	 * Comprueba si la pista especificada está disponible para la reserva en 
	 * la fecha y hora dadas.
	 * 
	 * <p>Esta función verifica si hay alguna reserva existente para la pista 
	 * con el nombre proporcionado en la fecha y hora especificadas. Si hay 
	 * una reserva en conflicto, la función devuelve {@code false}; de lo 
	 * contrario, devuelve {@code true}.</p>
	 *
	 * @param nombre_pista El nombre de la pista que se desea comprobar.
	 * @param fecha_hora   La fecha y hora para la que se desea realizar la 
	 *                     reserva.
	 * @return {@code true} si la pista está disponible en la fecha y hora 
	 *         especificadas; {@code false} si ya hay una reserva en esa 
	 *         pista y fecha.
	 */
	public boolean comprobarPistayFechaReserva(String nombre_pista, Date fecha_hora) {
		for (Reserva reserva : getAllReservas()) {
			if (reserva.getNombrePista().equals(nombre_pista)) {
				Calendar calendar = Calendar.getInstance();
			    calendar.setTime(reserva.getFecha_hora());
			    calendar.add(Calendar.MINUTE, reserva.getDuracion());
			    Date fechaFin = calendar.getTime();
			    if ((fecha_hora.after(reserva.getFecha_hora()) && fecha_hora.before(fechaFin)) || reserva.getFecha_hora().equals(fecha_hora)) {
	                return false;
	            }
			}
		}
		
		return true;
	}
	
	/**
	 * Comprueba que la fecha proporcionada sea, como mínimo, 24 horas en el 
	 * futuro con respecto a la fecha actual.
	 * 
	 * <p>Esta función verifica si la fecha y hora especificadas son al menos 
	 * 24 horas posteriores a la fecha y hora actuales. Esto es útil para 
	 * validar reservas que requieren un aviso previo.</p>
	 *
	 * @param fecha_hora La fecha y hora que se desea comprobar para la 
	 *                   reserva.
	 * @return {@code true} si la fecha es, como mínimo, 24 horas en el futuro; 
	 *         {@code false} en caso contrario.
	 */
	public boolean comprobarFechaReserva(Date fecha_hora){
		Calendar today = Calendar.getInstance();
		
		today.add(Calendar.DAY_OF_MONTH, 1);
		
		if (fecha_hora.before(today.getTime())) {
			return false;
		} 
		else {
			return true;
		}
	}
	
	/**
	 * Comprueba si el usuario tiene algún bono que admita el tipo de pista 
	 * especificado.
	 * 
	 * <p>Esta función verifica si el bono del usuario, identificado por su 
	 * correo electrónico, permite el uso del tipo de pista que se pasa como 
	 * parámetro. Además, comprueba que el bono tenga sesiones disponibles.</p>
	 *
	 * @param correo El correo electrónico del usuario cuyo bono se desea 
	 *               comprobar.
	 * @param tipo   El tipo de pista que se desea comprobar, representado 
	 *               como un valor de la enumeración {@link TipoPista}.
	 * @return {@code true} si el usuario tiene un bono que admite el tipo de 
	 *         pista especificado y tiene sesiones disponibles; {@code false} en 
	 *         caso contrario.
	 */
	public boolean comprobarBonoPista(String correo, TipoPista tipo) {
		
		for(Bono bono : bonos) {
			if(bono.getIdUsuario().equals(correo) && bono.getTipo().equals(tipo) && bono.getSesiones() != 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Comprueba si el usuario tiene algún bono que admita el tipo de reserva 
	 * especificado.
	 * 
	 * <p>Esta función verifica si el bono del usuario, identificado por su 
	 * correo electrónico, permite realizar reservas del tipo especificado. 
	 * Los tipos de reserva se definen como sigue:
	 * <ul>
	 *     <li>1 -> Reserva para adultos</li>
	 *     <li>2 -> Reserva infantil</li>
	 *     <li>3 -> Reserva familiar</li>
	 * </ul>
	 * 
	 * @param correo      El correo electrónico del usuario cuyo bono se 
	 *                    desea comprobar.
	 * @param tipo_reserva El tipo de reserva que se desea comprobar. 
	 *                     Puede ser 1 (adultos), 2 (infantil) o 3 (familiar).
	 * @return {@code true} si el usuario tiene un bono que admite el tipo de 
	 *         reserva especificado; {@code false} en caso contrario.
	 */
	public boolean comprobarBonoTipoReserva(String correo, int tipo_reserva) {
			
		//Tipo reserva=1 -> Reserva adultos
		//Tipo reserva=2 -> Reserva infantil
		//Tipo reserva=3 -> Reserva familiar
		switch(tipo_reserva){
			case 1:
				for(Bono bono : bonos){
					if(bono.getIdUsuario().equals(correo) && bono.getTipo().equals(TipoPista.ADULTOS)){
						return true;
					}
				}
			break;
			case 2:
				for(Bono bono : bonos){
					if(bono.getIdUsuario().equals(correo) && bono.getTipo().equals(TipoPista.MINIBASKET)){
						return true;
					}
				}
			break;
			case 3:
				for(Bono bono : bonos){
					if(bono.getIdUsuario().equals(correo) && (bono.getTipo().equals(TipoPista.MINIBASKET) || bono.getTipo().equals(TipoPista.TRESVSTRES))){
						return true;
					}
				}
			break;
		}

		return false;
	}
	
	/**
	 * Añade una reserva a un bono existente del usuario especificado.
	 * 
	 * <p>Esta función busca un bono asociado al correo electrónico del usuario y 
	 * al tipo de pista proporcionado. Si se encuentra un bono correspondiente, 
	 * se añade la reserva con los detalles especificados.
	 * 
	 * @param correo     El correo electrónico del usuario cuyo bono se desea 
	 *                   añadir la reserva.
	 * @param tipo      El tipo de pista correspondiente al bono al que se 
	 *                  desea añadir la reserva.
	 * @param nombre_pista El nombre de la pista que se está reservando.
	 * @param fecha_hora La fecha y hora de la reserva que se está añadiendo.
	 */
	public void añadirReservaABono(String correo, TipoPista tipo,String nombre_pista, Date fecha_hora) {
		for(Bono bono : bonos) {
			if(bono.getIdUsuario().equals(correo) && bono.getTipo().equals(tipo)) {
				bono.añadirReserva(nombre_pista, fecha_hora);
			}
		}
	}
	
	/**
	 * Comprueba si el usuario especificado tiene un bono con sesiones disponibles.
	 * 
	 * <p>Esta función itera a través de la lista de bonos y verifica si existe 
	 * un bono asociado al correo electrónico del usuario que tenga sesiones 
	 * disponibles. 
	 * 
	 * @param correo El correo electrónico del usuario cuyo bono se desea verificar.
	 * @return {@code true} si el usuario tiene un bono con sesiones disponibles; 
	 *         {@code false} en caso contrario.
	 */
	public boolean comprobarBonoUsuario(String correo) {
		for(Bono bono : bonos) {
			if(bono.getIdUsuario().equals(correo) && bono.getSesiones() != 0) {
				return true;
			}
		}
		return false;
	}
	

}
