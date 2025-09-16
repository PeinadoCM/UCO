package ejer3;

import ejer1.Jugador;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.Calendar;

/**
 * Clase que gestiona la creación y administración de usuarios en el sistema.
 * 
 * <p>Esta clase implementa el patrón de diseño Singleton, asegurando que solo 
 * exista una única instancia de {@code GestorUsuarios} en la aplicación. 
 * Proporciona métodos para añadir, modificar, listar y cargar usuarios desde 
 * un archivo de texto. Los usuarios se almacenan en una lista interna, 
 * permitiendo realizar operaciones sobre ellos de manera eficiente.
 * 
 * <p>Las operaciones de la clase incluyen, pero no se limitan a:
 * <ul>
 *     <li>Alta de nuevos usuarios</li>
 *     <li>Modificación de usuarios existentes</li>
 *     <li>Comprobación de requisitos de edad y antigüedad</li>
 *     <li>Carga y guardado de usuarios desde/hacia un archivo</li>
 * </ul>
 * 
 * @see Jugador
 */
public class GestorUsuarios {
	
	private static GestorUsuarios instance;
	
    private List<Jugador> usuarios;
    private final String rutaArchivo = "usuarios.txt";

    /**
     * Constructor privado de la clase {@code GestorUsuarios}.
     * 
     * <p>Este constructor se utiliza para inicializar una nueva instancia de 
     * {@code GestorUsuarios} y para cargar la lista de usuarios desde un archivo 
     * de texto al crear la instancia. Se utiliza como parte de la implementación 
     * del patrón Singleton, asegurando que solo se pueda crear una instancia de 
     * {@code GestorUsuarios} desde dentro de la propia clase.
     * 
     * <p>Inicializa el {@code ArrayList} de usuarios y llama al método 
     * {@code cargarUsuariosDesdeArchivo()} para cargar los usuarios previamente 
     * registrados desde un archivo externo.
     */
    private GestorUsuarios() {
    	usuarios =new ArrayList<>();
        cargarUsuariosDesdeArchivo();
	}
	
    /**
     * Devuelve la instancia única de la clase {@code GestorUsuarios}.
     * 
     * <p>Este método implementa el patrón de diseño Singleton, asegurando que solo 
     * haya una única instancia de {@code GestorUsuarios} en el sistema. 
     * Si la instancia no ha sido creada previamente, se inicializa una nueva 
     * instancia y se devuelve. Si ya existe una instancia, simplemente se 
     * devuelve la instancia existente.
     * 
     * @return La instancia única de {@code GestorUsuarios}.
     */
	public static GestorUsuarios getInstance() {
		if(instance == null) {
			instance = new GestorUsuarios();
		}
		return instance;
	}

	/**
	 * Crea un nuevo usuario en el sistema.
	 * 
	 * <p>Esta función verifica si ya existe un usuario con el mismo correo electrónico. 
	 * Si se encuentra un usuario con ese correo, la función imprime un mensaje informando 
	 * que el usuario ya está registrado y devuelve {@code false}. 
	 * En caso contrario, crea un nuevo objeto {@code Jugador} con los datos proporcionados, 
	 * lo añade a la lista de usuarios y devuelve {@code true}, 
	 * indicando que el usuario se ha creado correctamente.
	 * 
	 * @param nombre El nombre completo del usuario.
	 * @param fechanacimiento La fecha de nacimiento del usuario.
	 * @param correo La dirección de correo electrónico del usuario.
	 * @return {@code true} si el usuario se ha creado correctamente, 
	 *         {@code false} si ya hay un usuario registrado con el mismo correo.
	 */
    public boolean AltaUsuario(String nombre,Date fechanacimiento, String correo){
        for(Jugador it : usuarios){
            if (it.getCorreo().equals(correo)) {
                System.out.println("El usuario ya esta registrado\n");
                return false;
            }
        }
        Jugador nuevoUsuario = new Jugador(nombre,fechanacimiento,correo);
        usuarios.add(nuevoUsuario);
        System.out.println("Usuario añadido con éxito\n");
        return true;   
    }
    
    /**
     * Muestra por pantalla todos los usuarios registrados en el sistema.
     * 
     * <p>Esta función verifica si hay usuarios en la lista. Si la lista de usuarios está 
     * vacía, se imprime un mensaje informando que no hay usuarios registrados. 
     * De lo contrario, se imprime una lista de los usuarios registrados, 
     * utilizando el método {@code toString()} de la clase {@code Jugador} para mostrar 
     * la información relevante de cada usuario.
     */
    public void listarUsuarios() {                     
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados\n");
        } else {
           System.out.println("Usuarios registrados:\n");
           for (Jugador usuario : usuarios) {
               System.out.println(usuario);
            }
       }
    }

    /**
     * Guarda los jugadores del ArrayList de usuarios en el archivo de texto "usuarios.txt".
     * 
     * <p>Esta función utiliza un {@code BufferedWriter} para escribir los datos de cada jugador 
     * en el archivo especificado. Para cada jugador, se formatean las fechas de nacimiento 
     * y de inscripción utilizando el formato "dd/MM/yyyy", y luego se escriben en una línea 
     * del archivo, separadas por comas. 
     * 
     * <p>En caso de que ocurra un error al intentar escribir en el archivo, se imprime un 
     * mensaje de error que describe el problema.
     * 
     * @throws IOException Si ocurre un error de entrada/salida al intentar guardar los datos en el archivo.
     */
    public void guardarUsuariosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Jugador it : usuarios) {
            	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String fechanacimientoFormateada = formatter.format(it.getFechanacimiento());
                String fechainscripcionFormateada = formatter.format(it.getFechainscripcion());
                writer.write(it.getNombreyapellidos()+","+fechanacimientoFormateada+","+fechainscripcionFormateada+","+it.getCorreo());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    /**
     * Carga los jugadores desde el archivo de texto "usuarios.txt" al ArrayList de usuarios.
     * 
     * <p>Esta función verifica la existencia del archivo especificado. Si el archivo existe, 
     * se lee línea por línea y se intenta crear un objeto {@code Jugador} a partir de cada 
     * línea del archivo. Los jugadores creados se añaden a la lista de usuarios. 
     * Si ocurre un error al parsear la línea (por ejemplo, en el formato de la fecha), 
     * se imprime un mensaje de error específico.
     * 
     * <p>En caso de que no se pueda abrir el archivo o leer su contenido, se informa al 
     * usuario mediante un mensaje de error.
     * 
     * @throws IOException Si ocurre un error de entrada/salida al intentar leer el archivo.
     */
    public void cargarUsuariosDesdeArchivo() {
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    try {
                        Jugador usuario = Jugador.fromTexto(linea);
                        usuarios.add(usuario);

                    } catch (ParseException e) {

                        System.err.println("Error al parsear la fecha en la línea: " + linea);
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al cargar usuarios: " + e.getMessage());
            }
        }
    }

    /**
     * Modifica el nombre y la fecha de nacimiento de un jugador basado en su correo electrónico.
     * 
     * <p>Esta función busca en la lista de usuarios un jugador cuyo correo electrónico coincida 
     * con el proporcionado. Si se encuentra, se actualizan los campos de nombre y fecha de 
     * nacimiento del jugador con los valores especificados. Si el usuario no se encuentra, 
     * se informa que no se ha encontrado.
     * 
     * <p>Devuelve:
     * <ul>
     *   <li>Actualiza el usuario si se encuentra.</li>
     *   <li>Imprime un mensaje indicando si el usuario fue encontrado y modificado o no.</li>
     * </ul>
     * 
     * @param nombre El nuevo nombre y apellidos del jugador.
     * @param fechanacimiento La nueva fecha de nacimiento del jugador.
     * @param correo El correo electrónico del jugador a modificar.
     */
    public void modificarUsuario(String nombre,Date fechanacimiento, String correo){
        
        for (Jugador usuario : usuarios){

            if (usuario.getCorreo().equals(correo)){
                
                usuario.setNombreyapellidos(nombre);
                usuario.setFechanacimiento(fechanacimiento);
                
                System.out.println("Usuario modificado correctamente\n");
                return;
            }
        }
        System.out.println("Usuario no encontrado\n");
    }
    
    /**
     * Verifica si el usuario con el correo proporcionado es mayor de edad.
     * 
     * <p>Esta función recorre la lista de usuarios y comprueba si existe un usuario con el 
     * correo electrónico especificado. Si se encuentra, evalúa si la fecha de nacimiento del 
     * usuario es anterior a la fecha actual menos 18 años. Si es así, se considera que el 
     * usuario es mayor de edad; de lo contrario, es menor de edad.
     * 
     * @param correo El correo electrónico del usuario a comprobar.
     * @return {@code true} si el usuario es mayor de edad, {@code false} si es menor de edad.
     */
    public boolean comprobarMayorEdad(String correo) {
    	
    	for(Jugador usuario: usuarios) {
    		if (usuario.getCorreo().equals(correo)){
    			 Calendar today = Calendar.getInstance();
    		        
    		        // Restar 18 años a la fecha actual
    		        today.add(Calendar.YEAR, -18);
    		        
    		        // Comparar si la fecha de nacimiento es anterior a la fecha actual menos 18 años
    		        if (usuario.getFechanacimiento().before(today.getTime())) {
    		            return true;
    		        } else {
    		            return false;
    		        }
    		}
    	}
    	
    	return false;
    }
    
    /**
     * Verifica si el usuario con el correo proporcionado tiene derecho a un descuento por antigüedad.
     * 
     * <p>Esta función recorre la lista de usuarios y comprueba si existe un usuario con el 
     * correo electrónico especificado. Si se encuentra, evalúa si la fecha de inscripción del 
     * usuario es anterior a dos años desde la fecha actual. Si es así, se aplica un descuento 
     * del 10%. Si no, no se aplica descuento.
     * 
     * @param correo El correo electrónico del usuario a comprobar.
     * @return Un valor de {@code float} que representa el porcentaje de descuento aplicable: 
     *         {@code 0.1f} para un 10% de descuento o {@code 0} si no corresponde.
     */
    public float asginarDescuento(String correo) {
    	for(Jugador usuario: usuarios) {
    		if (usuario.getCorreo().equals(correo)){
    			 Calendar today = Calendar.getInstance();
    		        
    		        today.add(Calendar.YEAR, -2);
    		        
    		        if (usuario.getFechainscripcion().before(today.getTime())) {
    		            return 0.1f;
    		        } else {
    		            return 0;
    		        }
    		}
    	}
    	return 0;
    }
    
    

}