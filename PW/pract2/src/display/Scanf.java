package display;

import java.util.Scanner;		
import java.text.SimpleDateFormat;
import java.util.Date;
import data.dto.pistas.*;


/**
 * Clase Scanf que proporciona métodos estáticos para leer entradas del usuario.
 * 
 * Esta clase contiene varios métodos estáticos para leer diferentes tipos de datos, como:
 * - Cadenas de texto (String)
 * - Enteros (int)
 * - Fechas (Date)
 * 
 * Los métodos de esta clase incluyen validación básica para asegurar que el usuario ingresa un valor válido.
 */
public class Scanf{
	
	/**
	 * Constructor de la clase Scanf.
	 * 
	 * Este es un constructor vacío que no realiza ninguna operación al ser llamado.
	 */
	public Scanf(){
		
	}
	
	/**
	 * Lee una línea de texto desde la entrada estándar (teclado).
	 * 
	 * Este método solicita al usuario que ingrese una línea de texto a través de la consola 
	 * y devuelve esa línea como un string.
	 * 
	 * @return La línea de texto ingresada por el usuario.
	 */
	public static String Scan_string() {
		String result;
		Scanner sc=new Scanner(System.in);
		
		result=sc.nextLine();
		
		return result;
	}
	
	/**
	 * Lee un número entero desde la entrada estándar (teclado).
	 * 
	 * Este método solicita al usuario que ingrese un número entero. Si el usuario ingresa 
	 * un valor que no es un número entero, el método captura la excepción y solicita al usuario
	 * que vuelva a introducir un valor válido.
	 * 
	 * @return El número entero ingresado por el usuario.
	 */
	public static int Scan_int() {
		int result=0, aux=0;
		Scanner sc=new Scanner(System.in);
		do {
			try {
				result=sc.nextInt();
				aux=1;
			}
			catch(Exception e) {
				System.out.println("Error, no se ha introducido un numero entero, vuelva a introducir el valor");
				sc.nextLine();
			}
		}while(aux==0);
		
		return result;
	}
	
	/**
	 * Lee una fecha desde la entrada estándar (teclado) y la devuelve como un objeto Date.
	 * 
	 * Este método solicita al usuario que ingrese una fecha en formato "dd/MM/yyyy". Si el usuario 
	 * ingresa un formato incorrecto, el método captura la excepción y solicita al usuario que vuelva 
	 * a ingresar una fecha válida en el formato correcto.
	 * 
	 * @return La fecha ingresada por el usuario como un objeto Date.
	 */
	public static Date Scan_fecha() {
		int aux=0;
		Date result=new Date();
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
		Scanner sc=new Scanner(System.in);
		do {
			try {
				result=sdf.parse(sc.nextLine());
				aux=1;
			}
			catch(Exception e) {
				System.out.println("Error, formato de fecha incorrecto, vuelva a introducir la fecha");
			}
		}while(aux==0);
		
		return result;
	}
	
	/**
	 * Lee una fecha y hora desde la entrada estándar (teclado) y la devuelve como un objeto Date.
	 * 
	 * Este método solicita al usuario que ingrese una fecha y hora en el formato "dd/MM/yyyy HH:mm".
	 * Si el usuario ingresa un formato incorrecto, el método captura la excepción y solicita al usuario 
	 * que vuelva a ingresar la fecha y hora correctamente.
	 * 
	 * @return La fecha y hora ingresadas por el usuario como un objeto Date.
	 */
	public static Date Scan_fecha_hora() {
		int aux=0;
		Date result=new Date();
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Scanner sc=new Scanner(System.in);
		do {
			try {
				result=sdf.parse(sc.nextLine());
				aux=1;
			}
			catch(Exception e) {
				System.out.println("Error, formato de fecha incorrecto, vuelva a introducir la fecha");
			}
		}while(aux==0);
		
		return result;
	}
	
	/**
	 * Lee una entrada del usuario para determinar si el tipo es "Exterior" o "Interior".
	 * 
	 * Este método solicita al usuario que ingrese una cadena de texto y valida que la entrada
	 * sea "Exterior" o "Interior", sin importar si la entrada está en mayúsculas o minúsculas.
	 * Si el usuario ingresa un valor no válido, el método seguirá solicitando la entrada
	 * hasta que reciba un valor correcto.
	 * 
	 * @return `true` si el usuario ingresa "Exterior" (sin importar mayúsculas o minúsculas),
	 *         `false` si el usuario ingresa "Interior" (sin importar mayúsculas o minúsculas).
	 */
	public static boolean Scan_Exterior_Interior() {
		String aux;
		do{
			aux=Scanf.Scan_string();
			if(aux.equals("Exterior") || aux.equals("exterior")){
				return true;
			}
			else if(aux.equals("Interior") || aux.equals("interior")){
				return false;
			}
			else{
				System.out.println("Error tipo no valido, vuelva a introducirlo");
			}
		}while(true);
	}
	
	/**
	 * Lee una opción del usuario y devuelve el tipo de pista correspondiente.
	 * 
	 * Este método solicita al usuario que ingrese un número entero, luego compara la opción
	 * ingresada con las opciones predefinidas para determinar el tipo de pista correspondiente.
	 * Si el número ingresado no es válido, se muestra un mensaje de error y se solicita la entrada nuevamente.
	 * 
	 * Las opciones disponibles son:
	 * 1 - MINIBASKET
	 * 2 - ADULTOS
	 * 3 - TRESVSTRES
	 * 
	 * @return El tipo de pista correspondiente a la opción seleccionada por el usuario.
	 */
	public static TipoPista Scan_TipoPista() {
		int aux;
		do {
			aux=Scanf.Scan_int();
			
			switch(aux) {
				case 1:
					return TipoPista.MINIBASKET;
				case 2:
					return TipoPista.ADULTOS;
				case 3:
					return TipoPista.TRESVSTRES;
				default:
					System.out.println("Opcion introducida no valida, vuelva a seleccionar una opcion");
					break;
			}
		}while(true);
	}
	
	/**
	 * Lee una opción del usuario y devuelve el tipo de material correspondiente.
	 * 
	 * Este método solicita al usuario que ingrese un número entero, luego compara la opción
	 * ingresada con las opciones predefinidas para determinar el tipo de material correspondiente.
	 * Si el número ingresado no es válido, se muestra un mensaje de error y se solicita la entrada nuevamente.
	 * 
	 * Las opciones disponibles son:
	 * 1 - PELOTAS
	 * 2 - CANASTAS
	 * 3 - CONOS
	 * 
	 * @return El tipo de material correspondiente a la opción seleccionada por el usuario.
	 */
	public static Tipo Scan_TipoMaterial() {
		int aux;
		do {
			aux=Scanf.Scan_int();
			
			switch(aux) {
				case 1:
					return Tipo.PELOTAS;
				case 2:
					return Tipo.CANASTAS;
				case 3:
					return Tipo.CONOS;
				default:
					System.out.println("Opcion introducida no valida, vuelva a seleccionar una opcion");
					break;
			}
		}while(true);
	}
	
	/**
	 * Lee la duración de una reserva de una lista de opciones predefinidas (60, 90 o 120 minutos).
	 * 
	 * Este método solicita al usuario que ingrese un número entero correspondiente a la duración de la reserva.
	 * Si el número ingresado no es válido, es decir, no es 60, 90 ni 120, se muestra un mensaje de error y se solicita la entrada nuevamente.
	 * 
	 * Las duraciones válidas son:
	 * 60 minutos
	 * 90 minutos
	 * 120 minutos
	 * 
	 * @return La duración seleccionada por el usuario, que debe ser uno de los valores válidos: 60, 90 o 120.
	 */
	public static int Scan_Duracion() {
		int result;
		do {
			result=Scanf.Scan_int();
			if(result != 60 && result != 90 && result != 120) {
				System.out.println("Opcion introducida no valida, vuelva a introducir la duracion");
			}
		}while(result != 60 && result != 90 && result != 120);
		return result;
	}
	
	/**
	 * Lee el tipo de reserva que el usuario desea seleccionar.
	 * 
	 * Este método solicita al usuario que ingrese un número entero correspondiente al tipo de reserva.
	 * Si el número ingresado no es válido (es decir, no está en el rango de 1 a 3), se muestra un mensaje de error
	 * y se solicita la entrada nuevamente.
	 * 
	 * Los tipos de reserva válidos son:
	 * 1 - Reserva adultos
	 * 2 - Reserva infantil
	 * 3 - Reserva familiar
	 * 
	 * @return El tipo de reserva seleccionado por el usuario, que debe ser uno de los valores válidos: 1, 2 o 3.
	 */
	public static int Scan_TipoReserva() {
		int result;
		do {
			result=Scanf.Scan_int();
			if(result < 1 || result > 3) {
				System.out.println("Opcion introducida no valida, vuelva a seleccionar el tipo de reserva");
			}
		}while(result < 1 || result > 3);
		return result;
	}
	
}