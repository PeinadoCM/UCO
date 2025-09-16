package aux;

import java.util.Scanner;		
import java.text.SimpleDateFormat;
import java.sql.Date;


/**
 * Clase que proporciona métodos estáticos para leer entradas del usuario.
 */
public class Scanf{
	
	/**
	 * Constructor de la clase Scanf.
	 */
	public Scanf(){
		
	}
	
	/**
	 * Lee una línea de texto desde la entrada estándar (teclado).
	 *
	 * @return La línea de texto ingresada por el usuario.
	 */
	public static String scanString() {
		String result;
		Scanner sc = new Scanner(System.in);
		
		result = sc.nextLine();
		
		return result;
	}
	
	/**
	 * Lee un número entero desde la entrada estándar (teclado).
	 * 
	 * @return El número entero ingresado por el usuario.
	 */
	public static int scanInt() {
		int result = 0, aux = 0;
		Scanner sc = new Scanner(System.in);
		do {
			try {
				result = sc.nextInt();
				aux = 1;
			}
			catch(Exception e) {
				System.out.println("Error, no se ha introducido un numero entero, vuelva a introducir el valor");
				sc.nextLine();
			}
		}while(aux == 0);
		
		return result;
	}

	/**
	 * Lee un número entero positivo desde la entrada estándar (teclado).
	 * 
	 * @return El número entero positivo ingresado por el usuario.
	 */
	public static int scanIntPositivo() {
		int result = 0;
		do{
			result = scanInt();
			if(result < 0){
				System.out.println("Error, el numero no puede ser negativo");
			}

		} while (result < 0);
		
		return result;
	}

	/**
	 * Lee un número flotante desde la entrada estándar (teclado).
	 * 
	 * @return El número flotante ingresado por el usuario.
	 */
	public static float scanFloat() {
		float result = 0; 
		int aux = 0;
		Scanner sc = new Scanner(System.in);
		do {
			try {
				result = sc.nextFloat();
				aux = 1;
			}
			catch(Exception e) {
				System.out.println("Error, no se ha introducido un numero entero, vuelva a introducir el valor");
				sc.nextLine();
			}
		}while(aux == 0);
		
		return result;
	}

	/**
	 * Lee un número flotante positivo desde la entrada estándar (teclado).
	 * 
	 * @return El número flotante positivo ingresado por el usuario.
	 */
	public static float scanFloatPositivo() {
		float result = 0;
		do{
			result = scanFloat();
			if(result < 0){
				System.out.println("Error, el numero no puede ser negativo");
			}

		} while (result < 0);
		
		return result;
	}

	/**
	 * Lee un número flotante entre 0 y 1 desde la entrada estándar (teclado).
	 * 
	 * @return El número flotante entre 0 y 1 ingresado por el usuario.
	 */
	public static float scanFloatEntre0y1() {
		float result = 0;
		do{
			result = scanFloat();
			if(result < 0 || result > 1){
				System.out.println("Error, el numero no puede ser menor que 0 o mayor que 1");
			}
		} while (result < 0 || result > 1);
		
		return result;
	}

	/**
	 * Lee una cadena con formato de DNI desde la entrada estándar (teclado).
	 * El formato esperado es 8 dígitos seguidos de una letra.
	 * @return El DNI ingresado por el usuario en formato correcto.
	 */
	public static String scanDni() {
		String result = "";
		Boolean valido = true;
		Scanner sc = new Scanner(System.in);
		do{
			result = sc.nextLine();
			valido = true;
			result = result.toUpperCase();

			if(result.matches("[0-9]{8}[A-Z]") == false) {
				System.out.println("Error, el dni no tiene el formato correcto");
				valido = false;
			}
		}while (valido == false);
		
		return result;
	}
	
	/**
	 * Lee una cadena con formato de fecha desde la entrada estándar (teclado).
	 * El formato esperado es "YYYY-MM-DD".
	 * @return La fecha ingresada por el usuario.
	 */
	public static Date scanFecha() {
		int aux = 0;
		String fecha = "";
		Date result = null;
		Scanner sc = new Scanner(System.in);
		do {
			try {
				fecha = sc.nextLine();
				result = Date.valueOf(fecha);
				aux = 1;
			}
			catch(Exception e) {
				System.out.println("Error, formato de fecha incorrecto, vuelva a introducir la fecha");
			}
		}while(aux == 0);
		
		return result;
	}

	/**
	 * Lee una cadena con formato de fecha futura desde la entrada estándar (teclado).
	 * El formato esperado es "YYYY-MM-DD".
	 * @return La fecha futura ingresada por el usuario.
	 */
	public static Date scanFechaFutura(){
		Date fecha = null;
		Boolean valido = false;
		do{
			fecha = scanFecha();
			valido = fecha.after(new Date(System.currentTimeMillis()));
			if(valido == false){
				System.out.println("Error, la fecha no puede ser anterior a la actual");
			}
		}while(valido == false);
		return fecha;
	}
	
}