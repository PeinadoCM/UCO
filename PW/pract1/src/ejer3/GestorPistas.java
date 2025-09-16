package ejer3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

import ejer1.Pista;
import ejer1.Tipo;
import ejer1.TipoPista;
import ejer1.Estado;
import ejer1.Jugador;
import ejer1.Material;

/**
 * Clase que gestiona las pistas y los materiales asociados en la aplicación.
 * 
 * <p>La clase {@code GestorPistas} es responsable de manejar la creación, gestión y 
 * almacenamiento de objetos {@code Pista} y {@code Material}. Implementa el patrón 
 * Singleton, garantizando que solo exista una instancia de esta clase en la aplicación.
 * 
 * <p>Entre sus funcionalidades, permite cargar pistas y materiales desde archivos, 
 * comprobar la disponibilidad de nombres, asignar materiales a pistas, y listar 
 * pistas y materiales según diferentes criterios.
 * 
 * <p>Utiliza estructuras de datos como {@code ArrayList} para almacenar las pistas 
 * y materiales, y ofrece métodos para interactuar con estas colecciones.
 */
public class GestorPistas{
	
	private static GestorPistas instance;
	
	private ArrayList<Pista> pistas;
	private ArrayList<Material> materiales;
	private String rutaArchivoPistas= "pistas.txt";
	private String rutaArchivoMateriales= "materiales.txt";
	
	/**
	 * Constructor privado de {@code GestorPistas}.
	 * 
	 * <p>Inicializa una nueva instancia de {@code GestorPistas} creando listas vacías para las 
	 * pistas y los materiales. Además, carga las pistas y los materiales desde sus respectivos 
	 * archivos al momento de crear la instancia.
	 * 
	 * <p>Este constructor es privado para evitar la creación de instancias adicionales, 
	 * siguiendo el patrón Singleton de esta clase.
	 */
	private GestorPistas() {
		pistas =new ArrayList<>();
		materiales=new ArrayList<>();
        cargarPistasDesdeArchivo();
        cargarMaterialesDesdeArchivo();
	}
	
	/**
	 * Devuelve la instancia única de {@code GestorPistas} (patrón Singleton).
	 * 
	 * <p>Esta función implementa el patrón de diseño Singleton, garantizando que solo haya una 
	 * instancia de {@code GestorPistas} en toda la aplicación. Si la instancia no ha sido creada 
	 * aún, se crea una nueva. En caso contrario, se devuelve la instancia existente.
	 * 
	 * @return La instancia única de {@code GestorPistas}.
	 */
	public static GestorPistas getInstance() {
		if(instance == null) {
			instance = new GestorPistas();
		}
		return instance;
	}
	
	/**
	 * Devuelve el número total de materiales en la lista de materiales.
	 * 
	 * <p>Esta función retorna la cantidad de objetos {@code Material} almacenados en el 
	 * {@code ArrayList} de materiales. Es útil para conocer cuántos materiales han sido 
	 * añadidos hasta el momento.
	 * 
	 * @return El número de materiales en la lista.
	 */
	public int materialesSize(){
		return materiales.size();
	}
	
	/**
	 * Comprueba si no existe ninguna pista con el nombre especificado.
	 * 
	 * <p>Esta función recorre el {@code ArrayList} de pistas y verifica si alguna de ellas tiene 
	 * el mismo nombre que el proporcionado como parámetro. 
	 * 
	 * @param nombre El nombre de la pista a comprobar.
	 * @return {@code true} si el nombre está disponible, {@code false} si ya existe una pista con ese nombre.
	 */
	public boolean comprobarNombre(String nombre){
		for(Pista pista: pistas){
			if(pista.getNombre().equals(nombre)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Crea una nueva pista y la agrega a la lista de pistas.
	 * 
	 * <p>Esta función recibe los parámetros necesarios para instanciar un objeto de tipo {@code Pista},
	 * que incluye el nombre, el estado, el tipo, el tamaño y el número máximo de jugadores de la pista. 
	 * Una vez creada, la pista se añade al {@code ArrayList} de pistas.
	 * 
	 * @param nombre El nombre de la pista.
	 * @param estado {@code true} si la pista está disponible, {@code false} si no lo está.
	 * @param tipo {@code true} si la pista es de tipo exterior, {@code false} si es de tipo interior.
	 * @param tamaño El tamaño de la pista, representado por el enum {@code TipoPista}.
	 * @param maxjugadores El número máximo de jugadores que puede admitir la pista.
	 */
	public void crearPistas(String nombre, boolean estado, boolean tipo, TipoPista tamaño, int maxjugadores) {
		Pista pista=new Pista(nombre, estado, tipo, tamaño, maxjugadores);
		pistas.add(pista);
	}
	
	/**
	 * Crea un nuevo objeto {@code Material} y lo agrega a la lista de materiales.
	 * 
	 * <p>Esta función recibe los parámetros necesarios para instanciar un objeto de tipo {@code Material},
	 * que incluye el identificador, el uso, el tipo y el estado del material. Una vez creado, el material
	 * se añade al {@code ArrayList} de materiales.
	 * 
	 * @param id El identificador único del material.
	 * @param uso {@code true} si el material es para uso exterior, {@code false} si es para uso interior.
	 * @param tipo El tipo de material a crear, representado por el enum {@code Tipo}.
	 * @param estado El estado del material, representado por el enum {@code Estado}.
	 */
	public void crearMaterial(int id, boolean uso, Tipo tipo, Estado estado) {
		Material material=new Material(id, uso, tipo, estado);
		materiales.add(material);
	}
	
	/**
	 * Asocia un material a la pista especificada si el material está disponible.
	 * 
	 * <p>Esta función toma el nombre de una pista y un objeto {@code Material} como parámetros.
	 * Primero, verifica si el estado del material es {@code Estado.DISPONIBLE}. Si es así, 
	 * recorre el {@code ArrayList} de pistas en busca de la pista con el nombre proporcionado 
	 * que también esté disponible (estado {@code true}). Si encuentra la pista correspondiente, 
	 * asocia el material a esa pista.
	 * 
	 * @param nombre_pista El nombre de la pista a la que se desea asociar el material.
	 * @param material El objeto {@code Material} que se quiere asociar a la pista.
	 */
	public void asignarMaterialAPista(String nombre_pista, Material material) {
		if(material.getEstado() == Estado.DISPONIBLE) {
			for(Pista pista: pistas) {
				if(pista.getNombre().equals(nombre_pista) && pista.getEstado() == true) {
					pista.asociarMaterialAPista(material);
				}
			}
		}
		
	}
	
	/**
	 * Muestra por pantalla todas las pistas que no están disponibles.
	 * 
	 * <p>Esta función recorre el {@code ArrayList} de pistas y verifica el estado de cada una. 
	 * Si una pista no está disponible (estado {@code false}), se imprime su nombre en la consola.
	 * 
	 * <p>Si al menos una pista no está disponible, se muestra un encabezado "Pistas no disponibles:".
	 * Si no hay pistas no disponibles, se imprime el mensaje "No hay pistas no disponibles".
	 */
	public void listarPistasNoDisponibles () {
		boolean first= true;
		for(Pista pista : pistas) {
			if(pista.getEstado() == false) {
				if(first) {
					System.out.println("Pistas no disponibles:");
					first=false;
				}
				System.out.println(pista.getNombre());
			}
		}
		if(first) {
			System.out.println("No hay pistas no disponibles");
		}
	}
	
	/**
	 * Devuelve una lista de pistas disponibles que coinciden con el tipo y número de jugadores especificados.
	 * 
	 * <p>Esta función recorre el {@code ArrayList} de pistas y selecciona aquellas que cumplen con los
	 * siguientes criterios:
	 * <ul>
	 *   <li>Tienen el mismo tamaño especificado por el parámetro {@code tipo}.</li>
	 *   <li>Están disponibles ({@code estado} es {@code true}).</li>
	 *   <li>Admiten un número de jugadores mayor o igual al especificado en {@code num_jugadores}.</li>
	 * </ul>
	 * 
	 * <p>Las pistas que cumplen con todos estos requisitos se añaden a un nuevo {@code ArrayList} que
	 * se devuelve al final.
	 * 
	 * @param num_jugadores El número mínimo de jugadores que debe admitir la pista.
	 * @param tipo El tipo de pista ({@code TipoPista}) que se desea buscar.
	 * @return Un {@code ArrayList} de objetos {@code Pista} que cumplen con los criterios de disponibilidad,
	 *         tipo y número mínimo de jugadores.
	 */
	public ArrayList<Pista> pistasLibresConJugadores(int num_jugadores, TipoPista tipo){
		ArrayList<Pista> pistas_disponibles= new ArrayList<>();
		for(Pista pista : pistas) {
			if(pista.getTamaño() == tipo && pista.getEstado() == true && pista.getMaxJugadores() >= num_jugadores) {
				pistas_disponibles.add(pista);
			}
		}
		return pistas_disponibles;
	}
	
	/**
	 * Muestra por pantalla todos los materiales disponibles en el {@code ArrayList} de materiales.
	 * 
	 * <p>Esta función recorre el {@code ArrayList} de materiales y verifica el estado de cada material.
	 * Si el estado de un material es {@code Estado.DISPONIBLE}, imprime su información en la consola.
	 * Dependiendo del uso del material, se indica si es para "Exterior" o "Interior".
	 * 
	 * <p>El formato de salida de cada material es:
	 * <pre>{@code
	 * id.- tipo (Exterior/Interior)
	 * }</pre>
	 * 
	 * <p>Ejemplo de salida:
	 * <pre>{@code
	 * 1.- Balón Exterior
	 * 2.- Red Interior
	 * }</pre>
	 */
	public void listarMaterialesDisponibles() {
		for(Material material: materiales) {
			if(material.getEstado() == Estado.DISPONIBLE) {
				if(material.getUso() == true) {
					System.out.println(material.getId()+".- "+material.getTipo()+" Exterior");
				}
				else {
					System.out.println(material.getId()+".- "+material.getTipo()+" Interior");
				}
				
			}
		}
	}
	
	/**
	 * Devuelve el material en la posición especificada del {@code ArrayList} de materiales.
	 * 
	 * <p>Esta función toma un índice {@code pos} y devuelve el objeto {@code Material} correspondiente
	 * a esa posición en el {@code ArrayList} de materiales.
	 * 
	 * @param pos La posición del material en el {@code ArrayList} de materiales que se desea obtener.
	 * @return El objeto {@code Material} en la posición especificada.
	 * @throws IndexOutOfBoundsException si el índice especificado está fuera del rango del {@code ArrayList}.
	 */
	public Material devolverMaterial(int pos) {
		return materiales.get(pos);
	}
	
	/**
	 * Guarda las pistas del {@code ArrayList} de pistas en el archivo de texto "pistas.txt".
	 * 
	 * <p>Esta función recorre el {@code ArrayList} de pistas y escribe cada pista en una línea 
	 * del archivo especificado en {@code rutaArchivoPistas}. Cada pista se representa en formato
	 * de texto, con sus atributos (nombre, estado, tipo, tamaño, número máximo de jugadores y lista
	 * de materiales) separados por comas.
	 * 
	 * <p>Si ocurre un error de E/S al escribir en el archivo, el error se registra en la consola.
	 * 
	 * @throws IOException si ocurre un error al intentar escribir en el archivo de pistas.
	 */
	public void guardarPistasEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivoPistas))) {
            for (Pista it : pistas) {
                writer.write(it.getNombre()+","+it.getEstado()+","+it.getTipo()+","+it.getTamaño()+","+it.getMaxJugadores()+","+it.getListaMateriales());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar pistas: " + e.getMessage());
        }
    }

	/**
	 * Carga las pistas desde el archivo de texto "pistas.txt" en el {@code ArrayList} de pistas.
	 * 
	 * <p>Esta función lee cada línea del archivo especificado en {@code rutaArchivoPistas}, donde
	 * cada línea representa una pista en formato de texto. Cada línea se convierte a un objeto de
	 * tipo {@code Pista} mediante el método estático {@code Pista.fromTexto(String)} y se añade
	 * al {@code ArrayList} de pistas.
	 * 
	 * <p>Si se produce un error al leer o procesar el archivo, se registrará el error en la consola.
	 * Los posibles errores incluyen:
	 * <ul>
	 *   <li>{@code ParseException}: cuando una línea no puede convertirse en un objeto {@code Pista}.</li>
	 *   <li>{@code IOException}: cuando ocurre un problema al leer el archivo.</li>
	 * </ul>
	 * 
	 * <p>En caso de error de parseo en una línea específica, la función continúa procesando las siguientes líneas.
	 * 
	 * @throws IOException si ocurre un error al intentar leer el archivo de pistas.
	 */
	public void cargarPistasDesdeArchivo(){

		File archivo = new File(rutaArchivoPistas);
        if (archivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivoPistas))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    try {
                        Pista pista = Pista.fromTexto(linea);
                        pistas.add(pista);

                    } catch (ParseException e) {

                        System.err.println("Error al parsear los materiales en la línea: " + linea);
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al cargar pistas: " + e.getMessage());
            }
        }
	}
	
	/**
	 * Guarda los materiales del {@code ArrayList} de materiales en el archivo de texto "materiales.txt".
	 * 
	 * <p>Esta función recorre el {@code ArrayList} de materiales y escribe cada material en una línea 
	 * del archivo especificado en {@code rutaArchivoMateriales}. Cada material se representa en formato de 
	 * texto, con los atributos del material (ID, uso, tipo y estado) separados por comas.
	 * 
	 * <p>Si ocurre un error de E/S al escribir en el archivo, el error se registra en la consola.
	 * 
	 * @throws IOException si ocurre un error al intentar escribir en el archivo de materiales.
	 */
	public void guardarMaterialesEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivoMateriales))) {
            for (Material it : materiales) {
                writer.write(it.getId()+","+it.getUso()+","+it.getTipo()+","+it.getEstado());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar materiales: " + e.getMessage());
        }
    }

	/**
	 * Carga los materiales desde el archivo de texto "materiales.txt" en el {@code ArrayList} de materiales.
	 * 
	 * <p>Esta función lee cada línea del archivo especificado en {@code rutaArchivoMateriales}, donde
	 * cada línea representa un material en formato de texto. Cada línea se convierte a un objeto de
	 * tipo {@code Material} mediante el método estático {@code Material.fromTexto(String)} y se añade
	 * al {@code ArrayList} de materiales.
	 * 
	 * <p>Si se produce un error al leer o procesar el archivo, se registrará el error en la consola.
	 * Los posibles errores incluyen:
	 * <ul>
	 *   <li>{@code ParseException}: cuando una línea no puede convertirse en un objeto {@code Material}.
	 *   <li>{@code IOException}: cuando ocurre un problema al leer el archivo.
	 * </ul>
	 * 
	 * <p>En caso de error de parseo en una línea específica, se continúa con la siguiente línea.
	 * 
	 * @throws IOException si ocurre un error al intentar leer el archivo de materiales.
	 */
	public void cargarMaterialesDesdeArchivo(){
		
		File archivo = new File(rutaArchivoMateriales);
        if (archivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivoMateriales))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    try {
                        Material material = Material.fromTexto(linea);
                        materiales.add(material);

                    } catch (ParseException e) {

                        System.err.println("Error al parsear los materiales en la línea: " + linea);
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al cargar materiales: " + e.getMessage());
            }
        }
	}
	
	/**
	 * Verifica si el tamaño de una pista específica coincide con el tipo de reserva solicitado.
	 * 
	 * <p>Dependiendo del tipo de reserva especificado en el parámetro {@code tipo_reserva}, busca 
	 * en la lista de pistas una pista cuyo nombre coincida con {@code nombre_pista} y cuyo tamaño 
	 * sea adecuado para el tipo de reserva solicitado:
	 * <ul>
	 *   <li>Tipo de reserva 1: Se espera que el tamaño de la pista sea {@code TipoPista.ADULTOS}.</li>
	 *   <li>Tipo de reserva 2: Se espera que el tamaño de la pista sea {@code TipoPista.MINIBASKET}.</li>
	 *   <li>Tipo de reserva 3: Se permite que el tamaño de la pista sea {@code TipoPista.MINIBASKET} o {@code TipoPista.TRESVSTRES}.</li>
	 * </ul>
	 * 
	 * <p>Devuelve {@code true} si se encuentra una pista cuyo tamaño corresponde con el tipo de reserva 
	 * especificado; en caso contrario, devuelve {@code false}.
	 * 
	 * @param nombre_pista El nombre de la pista a comprobar.
	 * @param tipo_reserva El tipo de reserva para el cual se quiere verificar el tamaño de la pista.
	 *        <ul>
	 *          <li>1: Reserva para adultos</li>
	 *          <li>2: Reserva infantil</li>
	 *          <li>3: Reserva familiar</li>
	 *        </ul>
	 * @return {@code true} si el tamaño de la pista coincide con el tipo de reserva, {@code false} en caso contrario.
	 */
	public boolean comprobarPistaReserva(String nombre_pista, int tipo_reserva){
		//Tipo reserva=1 -> Reserva adultos
		//Tipo reserva=2 -> Reserva infantil
		//Tipo reserva=3 -> Reserva familiar
		switch(tipo_reserva){
			case 1:
				for(Pista pista : pistas){
					if(pista.getNombre().equals(nombre_pista) && pista.getTamaño().equals(TipoPista.ADULTOS)){
						return true;
					}
				}
			break;
			case 2:
				for(Pista pista : pistas){
					if(pista.getNombre().equals(nombre_pista) && pista.getTamaño().equals(TipoPista.MINIBASKET)){
						return true;
					}
				}
			break;
			case 3:
				for(Pista pista : pistas){
					if(pista.getNombre().equals(nombre_pista) && (pista.getTamaño().equals(TipoPista.MINIBASKET) || pista.getTamaño().equals(TipoPista.TRESVSTRES))){
						return true;
					}
				}
			break;
		}

		return false;

	}
	
	/**
	 * Comprueba si el número máximo de jugadores permitido en una pista específica es mayor o igual 
	 * al número de jugadores proporcionado.
	 * 
	 * <p>Recorre una lista de objetos de tipo {@code Pista} buscando el nombre de pista especificado 
	 * en el parámetro {@code nombre_pista}. Si encuentra la pista con dicho nombre, verifica si 
	 * el número máximo de jugadores permitido en la pista es mayor o igual al número proporcionado
	 * en el parámetro {@code num_jugadores}.
	 * 
	 * <p>Devuelve {@code true} si el número de jugadores permitido en la pista es mayor o igual al
	 * número de jugadores especificado. Devuelve {@code false} si el número máximo de jugadores en 
	 * la pista es menor que el número de jugadores especificado o si no se encuentra una pista 
	 * con el nombre dado.
	 * 
	 * @param nombre_pista El nombre de la pista en la que se quiere realizar la comprobación.
	 * @param num_jugadores El número de jugadores que se desea validar contra el máximo permitido en la pista.
	 * @return {@code true} si el número de jugadores que admite la pista es mayor o igual al 
	 *         número de jugadores pasados como parámetro; {@code false} en caso contrario o si no 
	 *         se encuentra la pista.
	 */
	public boolean comprobarMaxJugadores(String nombre_pista, int num_jugadores){
		for(Pista pista : pistas){
			if(pista.getNombre().equals(nombre_pista)){
				if(pista.getMaxJugadores() >= num_jugadores){
					return true;
				}
				else{
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * Obtiene el tipo de pista basado en el nombre de la pista proporcionado.
	 * 
	 * <p>Recorre una lista de objetos de tipo {@code Pista} y compara el nombre de cada
	 * pista con el nombre especificado en el parámetro {@code nombre_pista}. Si encuentra
	 * una coincidencia, devuelve el tamaño de la pista, representado por un valor del
	 * tipo enumerado {@code TipoPista}.
	 * 
	 * <p>En caso de que no se encuentre ninguna pista con el nombre especificado, la
	 * función devuelve por defecto {@code TipoPista.ADULTOS}.
	 * 
	 * @param nombre_pista El nombre de la pista que se desea buscar.
	 * @return El tipo de la pista ({@code TipoPista}) correspondiente al nombre dado.
	 *         Si no se encuentra ninguna pista con el nombre especificado, devuelve
	 *         {@code TipoPista.ADULTOS}.
	 */
	public TipoPista getTipoPista(String nombre_pista) {
		for(Pista pista : pistas) {
			if(pista.getNombre().equals(nombre_pista)) {
				return pista.getTamaño();
			}
		}
		return TipoPista.ADULTOS;
	}


}