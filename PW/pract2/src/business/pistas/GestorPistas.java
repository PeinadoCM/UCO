package business.pistas;

import java.util.ArrayList;
import data.dto.pistas.*;
import data.dao.pistas.PistaDAO;


/**
 * Clase `GestorPistas` que gestiona las operaciones relacionadas con las pistas en el sistema.
 * <p>
 * Esta clase implementa el patrón Singleton, asegurando que solo haya una única instancia de 
 * `GestorPistas` en toda la aplicación. Proporciona métodos para crear, modificar, listar y verificar 
 * la disponibilidad de pistas, así como la asignación de materiales a las pistas.
 * </p>
 * <p>
 * Además, se encarga de realizar operaciones relacionadas con los jugadores y las pistas, como 
 * comprobar el número máximo de jugadores por pista y gestionar los materiales disponibles en las pistas.
 * </p>
 */
public class GestorPistas{
	
	private static GestorPistas instance;
	
	/**
	 * Constructor privado de la clase `GestorPistas`.
	 * 
	 * Este constructor está marcado como privado para evitar la creación directa de instancias 
	 * de la clase fuera de la propia clase. Se utiliza dentro del patrón Singleton para garantizar 
	 * que solo se pueda crear una instancia de `GestorPistas` a través del método `getInstance()`.
	 */
	private GestorPistas() {

	}
	
	/**
	 * Obtiene la instancia única del gestor de pistas (patrón Singleton).
	 * 
	 * Este método implementa el patrón de diseño Singleton para asegurarse de que solo exista 
	 * una instancia de la clase `GestorPistas`. Si la instancia no ha sido creada aún, 
	 * la crea y la devuelve. Si ya existe, simplemente la devuelve.
	 * 
	 * @return La instancia única de `GestorPistas`.
	 */
	public static GestorPistas getInstance() {
		if(instance == null) {
			instance = new GestorPistas();
		}
		return instance;
	}
	
	/**
	 * Crea una nueva pista y la guarda en la base de datos.
	 * 
	 * Este método permite crear una nueva pista en la base de datos, especificando su nombre, 
	 * estado (si está disponible o no), tipo (si es interior o exterior), tamaño (tipo de pista 
	 * como minibasket o tres vs tres), y el número máximo de jugadores que puede albergar.
	 * 
	 * @param nombre El nombre de la pista a crear.
	 * @param estado El estado de la pista (true si está disponible, false si no lo está).
	 * @param tipo El tipo de pista (true para pista de exterior, false para interior).
	 * @param tamaño El tamaño de la pista (valor enumerado de tipo `TipoPista`, como ADULTOS, MINIBASKET, etc.).
	 * @param maxjugadores El número máximo de jugadores que puede soportar la pista.
	 * @return Devuelve `true` si la pista se ha creado correctamente, `false` si ocurre algún error al crearla.
	 */
	public boolean crearPistas(String nombre, boolean estado, boolean tipo, TipoPista tamaño, int maxjugadores) {
		PistaDAO crearpista= new PistaDAO();
		return crearpista.createPista(nombre,estado,tipo,tamaño,maxjugadores);
	}

	/**
	 * Crea un nuevo material y lo guarda en la base de datos.
	 * 
	 * Este método permite crear un nuevo material especificando si el material se puede utilizar 
	 * (indicado por el parámetro `uso`), el tipo de material (por ejemplo, pelota, cono, canasta), 
	 * y su estado (nuevo, usado, etc.). El método luego guarda el material en la base de datos a 
	 * través del DAO correspondiente.
	 * 
	 * @param uso Un valor booleano que indica si el material es utilizable en pistas de exterior o interior.
	 * @param tipo El tipo de material que se está creando (por ejemplo, pelota, cono, etc.).
	 * @param estado El estado del material (por ejemplo, nuevo, usado, etc.).
	 * @return Devuelve `true` si el material se ha creado correctamente, `false` en caso contrario.
	 */
	public boolean crearMaterial(boolean uso, Tipo tipo, Estado estado){
		PistaDAO crear_material = new PistaDAO();
		return crear_material.createNewMaterial(uso,tipo,estado);
	}
	
	/**
	 * Lista todas las pistas que actualmente no están disponibles.
	 * 
	 * Este método consulta las pistas no disponibles a través del DAO y luego imprime la información de cada pista 
	 * que está actualmente marcada como no disponible. Si no se encuentran pistas no disponibles, se imprime un 
	 * mensaje indicando que no hay pistas no disponibles.
	 * 
	 * El estado de disponibilidad de las pistas se obtiene mediante el DAO, el cual filtra las pistas según su estado.
	 */
	public void listarPistasNoDisponibles() {
        PistaDAO pistaDAO = new PistaDAO();
        ArrayList<PistaDTO> pistasNoDisponibles = pistaDAO.requestPistasNoDisponibles();

        if (pistasNoDisponibles.isEmpty()) {
            System.out.println("No hay pistas no disponibles");
        } 
        else {
            System.out.println("Pistas no disponibles:");
            for (PistaDTO pista : pistasNoDisponibles) {   
				System.out.println(pista.toString());
			}
        }
    }

	/**
	 * Lista las pistas disponibles que cumplen con los criterios especificados de número máximo de jugadores 
	 * y tamaño de la pista (tipo de pista).
	 * 
	 * Este método consulta las pistas disponibles a través del DAO, filtra según el número máximo de jugadores 
	 * y el tipo de pista, y luego imprime la información de cada pista que cumple con los criterios especificados.
	 * Si no se encuentran pistas que coincidan con los criterios, se imprime un mensaje indicando que no se 
	 * encontraron pistas.
	 *
	 * @param maxjugadores El número máximo de jugadores que debe admitir la pista.
	 * @param tamaño El tipo de pista que se debe filtrar (por ejemplo, ADULTOS, MINIBASKET, etc.).
	 * 
	 * @see TipoPista
	 */
	public void listarPistasJugadoresTamaño(int maxjugadores, TipoPista tamaño) {
	    PistaDAO pistaDAO = new PistaDAO();
	    ArrayList<PistaDTO> pistas = pistaDAO.requestPistasJugadoresTamaño(maxjugadores,tamaño);
	
	    if (pistas.isEmpty()) {
	        System.out.println("No hay pistas para las opciones seleccioandas");
	    } 
	    else {
	        System.out.println("Pistas:");
	        for (PistaDTO pista : pistas) {   
				System.out.println(pista.toString());
			}
	    }
	}
	
	/**
	 * Obtiene y lista todos los materiales disponibles en el sistema, mostrando información sobre 
	 * cada uno de ellos y devolviendo una lista con los IDs de los materiales disponibles.
	 * 
	 * Este método consulta los materiales disponibles a través del DAO, los imprime en consola y 
	 * devuelve una lista de sus identificadores (IDs).
	 * 
	 * @return Una lista de enteros que contiene los IDs de los materiales disponibles. Si no hay 
	 *         materiales disponibles, se imprime un mensaje en consola indicando que no hay materiales disponibles.
	 *         La lista de IDs estará vacía si no hay materiales disponibles.
	 */
	public ArrayList<Integer> listarMaterialesDisponibles() {
		PistaDAO pistaDAO = new PistaDAO();
		ArrayList<MaterialDTO> materialesDisponibles = pistaDAO.requestMaterialesDisponibles();
		ArrayList<Integer> ids= new ArrayList<>();
		if (materialesDisponibles.isEmpty()) {
	        System.out.println("No hay materiales disponibles");
	    } 
		else {
	        System.out.println("Materiales disponibles:");
	        for (MaterialDTO material : materialesDisponibles) {   
	        	ids.add(material.getId());
				System.out.println(material.toString());
			}
	    }
		return ids;
	}
	
	/**
	 * Asocia un material a una pista específica, verificando las restricciones relacionadas con
	 * la cantidad máxima de materiales por tipo y la compatibilidad del material con el tipo de pista.
	 * 
	 * @param nombre_pista El nombre de la pista a la que se desea asociar el material.
	 * @param id_material El ID del material que se desea asociar a la pista.
	 * 
	 * @return Un código entero que indica el resultado de la operación:
	 *         <ul>
	 *         <li>0 -> El material fue añadido con éxito.</li>
	 *         <li>1 -> La pista no existe.</li>
	 *         <li>2 -> No se pueden añadir más materiales de ese tipo a la pista seleccionada (límite alcanzado).</li>
	 *         <li>3 -> El material seleccionado no se puede añadir a la pista seleccionada debido a restricciones de uso (exterior/interior).</li>
	 *         <li>4 -> Error al intentar asociar el material a la pista.</li>
	 *         </ul>
	 * 
	 * Este método verifica que la pista exista, si el material se puede añadir en función de
	 * la cantidad de materiales ya asignados, y si el material es adecuado para el tipo de pista
	 * (por ejemplo, si es de uso exterior o interior).
	 */
	public int asignarMaterialAPista(String nombre_pista, int id_material) {
		PistaDAO pistaDAO = new PistaDAO();
		PistaDTO pista=pistaDAO.requestPista(nombre_pista);
		//Comprueba que la pista exista
		if(pista == null) {
			return 1;
		}
		//El material non hace falta comprobar si existe porque solo se le deja seleccionar materiales disponibles
		MaterialDTO material=pistaDAO.requestMaterial(id_material);
		//Comprueba el numero de materiales de ese tipo que hay asociado a la pista
		int num_material=pistaDAO.requestContTipoMaterial(nombre_pista, material.getTipo());
		if((material.getTipo() == Tipo.PELOTAS && num_material == 12) || (material.getTipo() == Tipo.CANASTAS && num_material == 2) || (material.getTipo() == Tipo.CONOS && num_material == 20)) {
			return 2;
		}
		//Comprueba que el material se puede añadir a la pista por si es de exterior o interior
		if(pista.getTipo() == true && material.getUso() == false) {
			return 3;
		}
		//Asocia el material a la pista
		if(pistaDAO.assignNewMaterialToPista(nombre_pista, id_material) == true) {
			return 0;
		}
		return 4;
	}
	
	/**
	 * Comprueba si el número de jugadores es compatible con el máximo permitido en una pista.
	 * 
	 * @param nombre_pista El nombre de la pista en la que se quiere realizar la comprobación.
	 * @param num_jugadores El número de jugadores que se desean agregar a la pista.
	 * 
	 * @return {@code true} si el número de jugadores es menor o igual al máximo permitido en la pista,
	 *         {@code false} si el número de jugadores excede el límite permitido.
	 *         
	 * Este método realiza una comprobación sobre la existencia de la pista solicitada y luego
	 * verifica si el número de jugadores es menor o igual al máximo permitido para esa pista.
	 */
	public boolean comprobarMaxJugadores(String nombre_pista, int num_jugadores){
		PistaDAO pistaDAO = new PistaDAO();
		PistaDTO pista=pistaDAO.requestPista(nombre_pista);
		//Comprueba que la pista exista
		if(pista == null) {
			return false;
		}
		if(pista.getMaxJugadores() >= num_jugadores) {
			return true;
		}
		return false;
	}
	
	/**
	 * Comprueba si una pista está disponible para un tipo de reserva específico.
	 * 
	 * @param nombre_pista El nombre de la pista que se desea comprobar.
	 * @param tipo_reserva El tipo de reserva que se quiere hacer. 
	 *                     Los valores válidos son:
	 *                     - 1: Reserva para adultos.
	 *                     - 2: Reserva infantil.
	 *                     - 3: Reserva familiar.
	 * 
	 * @return {@code true} si la pista es adecuada para el tipo de reserva especificado,
	 *         {@code false} en caso contrario.
	 *         
	 * Este método realiza una comprobación sobre la existencia de la pista solicitada, y 
	 * luego verifica si el tamaño de la pista coincide con el tipo de reserva:
	 * - Para tipo 1 (adultos), la pista debe ser de tamaño ADULTOS.
	 * - Para tipo 2 (infantil), la pista debe ser de tamaño MINIBASKET.
	 * - Para tipo 3 (familiar), la pista debe ser de tamaño MINIBASKET o TRESVSTRES.
	 */
	public boolean comprobarPistaReserva(String nombre_pista, int tipo_reserva){
		PistaDAO pistaDAO = new PistaDAO();
		PistaDTO pista=pistaDAO.requestPista(nombre_pista);
		//Comprueba que la pista exista
		if(pista == null) {
			return false;
		}
		//Tipo reserva=1 -> Reserva adultos
		//Tipo reserva=2 -> Reserva infantil
		//Tipo reserva=3 -> Reserva familiar
		switch(tipo_reserva){
			case 1:
				if(pista.getNombre().equals(nombre_pista) && pista.getTamaño().equals(TipoPista.ADULTOS)){
					return true;
				}
			break;
			case 2:
				if(pista.getNombre().equals(nombre_pista) && pista.getTamaño().equals(TipoPista.MINIBASKET)){
					return true;
				}
			break;
			case 3:
				if(pista.getNombre().equals(nombre_pista) && (pista.getTamaño().equals(TipoPista.MINIBASKET) || pista.getTamaño().equals(TipoPista.TRESVSTRES))){
					return true;
				}
			break;
		}

		return false;

	}
	
}