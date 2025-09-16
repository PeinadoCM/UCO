package display;
		
import java.util.ArrayList;
import java.util.Date;
import data.dto.pistas.*;
import business.pistas.GestorPistas;
import business.jugadores.GestorUsuarios;
import business.reservas.GestorReservas;


public class MainProgram {

	public static void main(String[] args) {
		int opc_gestor;
		do {
			System.out.println("\nSelecciona la opcion que deseas:\n"
					+ "1.- Gestor de Usuarios\n"
					+ "2.- Gestor de Pistas\n"
					+ "3.- Gestor de Reservas\n"
					+ "4.- Salir\n");
			opc_gestor=Scanf.Scan_int();
			switch(opc_gestor) {
				case 1:
					MainProgram.gestor_usuarios();
					break;
				case 2:
					MainProgram.gestor_pistas();
					break;
				case 3:
					MainProgram.gestor_reservas();
					break;
				case 4:
					System.out.println("Saliendo del programa");
					break;
				default:
					System.out.println("Error, la opcion seleccionada no es valida");
					break;
			}
		}while(opc_gestor != 4);
	}
	
	public static void gestor_usuarios() {
		System.out.println("Bienvenido al gestor de usuarios");
		int opc;
		String nombre_usuario,correo;
		Date fecha;
		GestorUsuarios g_usus=GestorUsuarios.getInstance();
		do {
			System.out.println("\nSelecciona la opcion que deseas:\n"
								+ "1.- Dar de alta a un usuario\n"
								+ "2.- Modificar la informacion de un usuario\n"
								+ "3.- Listar usuarios registrados\n"
								+ "4.- Salir\n");
			opc=Scanf.Scan_int();
			switch(opc){
				case 1:
					System.out.println("Introduce el nombre del usuario");
					nombre_usuario=Scanf.Scan_string();

					System.out.println("Introduce la fecha de nacimiento(dd/MM/yyyy):");
					fecha=Scanf.Scan_fecha();
		
					System.out.println("Introduce el correo del usuario");
					correo=Scanf.Scan_string();

					if(g_usus.AltaUsuario(nombre_usuario, fecha, correo) == true) {
						System.out.println("Usuario añadido con exito");
					}
					else {
						System.out.println("Error, ya existe un usuario con ese correo");
					}
					break;
				case 2:
					System.out.println("Introduce el nuevo nombre:");
					nombre_usuario=Scanf.Scan_string();
		
					System.out.println("Introduce la nueva fecha de nacimiento(dd/MM/yyyy):");
					fecha=Scanf.Scan_fecha();
								
					System.out.println("Introduce el correo del usuario");
					correo=Scanf.Scan_string();

					if(g_usus.modificarUsuario(nombre_usuario,fecha, correo)){
						System.out.println("Usuario modificado con exito");
					}
					else {
						System.out.println("Error, no existe un usuario con ese correo");
					}
					break;
				case 3:
					g_usus.listarUsuarios();
					break;
				case 4:
					System.out.println("Saliendo del gestor de usuarios");
					break;
				default:
					System.out.println("Error, la opcion seleccionada no es valida");
					break;
			}
		}while(opc != 4);
	}
	
	public static void gestor_pistas() {
		System.out.println("Bienvenido al gestor de pistas");
		int opc, num_usuarios, id_material;
		String nombre_pista;
		boolean tipo_pista,estado_pista;
		GestorPistas g_pist=GestorPistas.getInstance();
		TipoPista tamaño;
		boolean uso;
		Tipo tipo_material;
		Estado estado_material;
		ArrayList<Integer> ids=new ArrayList<>();
		do{
			System.out.println("\nSelecciona la opcion que deseas:\n"
								+ "1.- Crear pista\n"
								+ "2.- Crear materiales\n"
								+ "3.- Asociar material a pista\n"
								+ "4.- Listar pistas no disponibles\n"
								+ "5.- Buscar pista por numero de usuarios y tipo de pista\n"
								+ "6.- Salir\n");
			opc=Scanf.Scan_int();
			switch(opc){
				case 1:
					
					System.out.println("Introduzca el nombre de la pista");
					nombre_pista=Scanf.Scan_string();
					
					System.out.println("Introduzca si quiere que la pista sea de interior o exterior");
					tipo_pista=Scanf.Scan_Exterior_Interior();
					
					estado_pista=true;
		
					System.out.println("Selecciona el tipo de pista:\n"
								+ "1.- Minibasket\n"
								+ "2.- Adultos\n"
								+ "3.- 3VS3\n");
					tamaño=Scanf.Scan_TipoPista();
					
					System.out.println("Introduzca el maximo de jugadores");
					num_usuarios=Scanf.Scan_int();
					
					
					if(g_pist.crearPistas(nombre_pista,estado_pista,tipo_pista,tamaño,num_usuarios) == true){
						System.out.println("Pista creada con exito");
					}
					else{
						System.out.println("Ya existe una pista con ese nombre");
					}
					break;
				case 2:
					
					System.out.println("Introduzca el uso del material(Exterior o interior)");
					uso=Scanf.Scan_Exterior_Interior();
	
					System.out.println("Selecciona el tipo de material:\n"
											+ "1.- Pelota\n"
											+ "2.- Canasta\n"
											+ "3.- Cono\n");
					tipo_material=Scanf.Scan_TipoMaterial();

					estado_material=Estado.DISPONIBLE;

					if(g_pist.crearMaterial(uso,tipo_material,estado_material)){
						System.out.println("Material creado con exito");
					}
					else{
						System.out.println("No se ha podido crear el material");
					}
								
					break;
				case 3:
					System.out.println("Introduzca el nombre de la pista");
					nombre_pista=Scanf.Scan_string();
					
					System.out.println("Selecciona el material:");
					ids=g_pist.listarMaterialesDisponibles();
					if(ids.size() == 0) {
						break;
					}
					do {
						id_material=Scanf.Scan_int();
						if(ids.contains(id_material) == false) {
							System.out.println("Error, el material seleccionado no esta disponible, seleccione otro material");
						}
					}while(ids.contains(id_material) == false);
					
					switch(g_pist.asignarMaterialAPista(nombre_pista, id_material)) {
						case 0:
							System.out.println("Material añadido a la pista con exito");
							break;
						case 1:
							System.out.println("Error, la pista introducida no existe");
							break;
						case 2:
							System.out.println("Error, no se pueden añadir mas materiales de ese tipo a la pista seleccionada");
							break;
						case 3:
							System.out.println("Error, el material seleccionado no se puede añadir a la pista seleccionada");
							break;
						case 4:
							System.out.println("Error, al asociar el material a la pista");
							break;
					}
					break;
				case 4:

					g_pist.listarPistasNoDisponibles();
					break;
				case 5:
					System.out.println("Introduce el numero de usuarios minimos de la pista:");
					num_usuarios=Scanf.Scan_int();
							
					System.out.println("Selecciona el tipo de pista:\n"
											+ "1.- Minibasket\n"
											+ "2.- Adultos\n"
											+ "3.- 3VS3\n");
					tamaño=Scanf.Scan_TipoPista();;
			
					g_pist.listarPistasJugadoresTamaño(num_usuarios, tamaño);
								
					break;
				case 6:
					System.out.println("Saliendo del gestor de pistas");
					break;
				default:
					System.out.println("Error, la opcion seleccionada no es valida");
					break;
			}

		}while(opc != 6);
	}
	
	public static void gestor_reservas() {
		System.out.println("Bienvenido al gestor de reservas");
		int opc, duracion, duracionNueva, nniños, nadultos, tipo_reserva, id_bono;
		Date fecha, nuevaFecha;
		String nombre_pista,correo;
		TipoPista tamaño;
		float descuento,precio;
		GestorPistas gestorPistas = GestorPistas.getInstance();
		GestorUsuarios gestorUsuarios = GestorUsuarios.getInstance();
		GestorReservas gestorReservas = GestorReservas.getInstance();
		do {
			System.out.println("\nSelecciona la opción que deseas:\n"
					+ "1.- Hacer reservas individuales\n"
					+ "2.- Crear un bono\n"
					+ "3.- Hacer reservas dentro de un bono\n"
					+ "4.- Modificar reserva\n"
					+ "5.- Cancelar reserva\n"
					+ "6.- Consultar número de reservas futuras\n"
					+ "7.- Consultar número de reservas para un día\n"
					+ "8.- Consultar bonos de un jugador\n"
					+ "9.- Salir\n");
			opc = Scanf.Scan_int();
			switch (opc) {
				case 1:
					// Hacer reservas individuales
					System.out.println("Selecciona el tipo de reserva:\n"
								+ "1.- Reserva adultos\n"
								+ "2.- Reserva infantil\n"
								+ "3.- Reserva familiar\n");
					tipo_reserva=Scanf.Scan_TipoReserva();
					
					
					System.out.println("Introduce el correo del usuario:");
					correo = Scanf.Scan_string();
					
					if (!gestorUsuarios.comprobarMayorEdad(correo)) {
				        System.out.println("El usuario introducido no es mayor de edad");
				        break;
				    }

					System.out.println("Introduzca el nombre de la pista");
					nombre_pista=Scanf.Scan_string();
					
					if(!gestorPistas.comprobarPistaReserva(nombre_pista, tipo_reserva)) {
						System.out.println("La pista seleccionada no es compatible con el tipo de reserva");
						break;
					}

					System.out.println("Introduce la fecha de la reserva (dd/MM/yyyy HH:mm):");
					fecha = Scanf.Scan_fecha_hora();
					
					if (!gestorReservas.comprobar24hantelacion(fecha)) {
				        System.out.println("No se puede hacer una reserva con menos de 24 horas de antelación.");
				        break;
				    }

					System.out.println("Introduce la duración de la reserva (60, 90, 120 minutos):");
					duracion = Scanf.Scan_Duracion();
					
					if(!gestorReservas.comprobarDisponibilidadFecha(nombre_pista, fecha, duracion)) {
						System.out.println("La pista seleccionada no esta disponible durante el tramo horario seleccionado");
						break;
					}
					
					precio=gestorReservas.AsignarPrecio(duracion);
					
					nniños=0;
					if(tipo_reserva != 1) {
						System.out.println("Introduce el numero de niños:");
						nniños = Scanf.Scan_int();
					}
					nadultos=0;
					if(tipo_reserva != 2) {
						System.out.println("Introduce el numero de adultos:");
						nadultos = Scanf.Scan_int();
					}
					
					if(!gestorPistas.comprobarMaxJugadores(nombre_pista, nadultos+nniños)) {
						System.out.println("La pista seleccionada no acepta tantos jugadores");
						break;
					}
					
					descuento=gestorUsuarios.calcularDescuento(correo, fecha);
					
					if(gestorReservas.añadirReservaIndividual(correo,nombre_pista,fecha,duracion,nniños,nadultos,descuento,precio)){
						System.out.println("Reserva individual creada con éxito.");
					}
					else{
						System.out.println("No se ha podido crear la reserva individual");
					}

					break;

				case 2:
					// Crear un bono
					System.out.println("Introduce el correo del usuario:");
					correo = Scanf.Scan_string();
					
					if (!gestorUsuarios.comprobarMayorEdad(correo)) {
				        System.out.println("El usuario introducido no es mayor de edad");
				        break;
				    }
					
					System.out.println("Selecciona el tipo de pista:\n"
											+ "1.- MINIBASKET\n"
											+ "2.- ADULTOS\n"
											+ "3.- TRESVSTRES\n");
					tamaño=Scanf.Scan_TipoPista();

					if(gestorReservas.crearBonoReservas(correo,tamaño)){
						System.out.println("Bono creado con éxito.");
					}
					else{
						System.out.println("No se ha podido crear el bono");
					}

					break;

				case 3:
					// Hacer reservas dentro de un bono
					System.out.println("Selecciona el tipo de reserva:\n"
							+ "1.- Reserva adultos\n"
							+ "2.- Reserva infantil\n"
							+ "3.- Reserva familiar\n");
					tipo_reserva=Scanf.Scan_TipoReserva();
					
					System.out.println("Introduce el correo del usuario:");
					correo = Scanf.Scan_string();
					
					System.out.println("Introduzca el nombre de la pista");
					nombre_pista=Scanf.Scan_string();
					
					if(!gestorPistas.comprobarPistaReserva(nombre_pista, tipo_reserva)) {
						System.out.println("La pista seleccionada no es compatible con el tipo de reserva");
						break;
					}
					
					id_bono=gestorReservas.comprobarBonoPista(correo, nombre_pista, new Date());
					if (id_bono == 0) {
				        System.out.println("El usuario introducido no tiene un bono compatible con la pista introducida");
				        break;
				    }

					System.out.println("Introduce la fecha de la reserva (dd/MM/yyyy HH:mm):");
					fecha = Scanf.Scan_fecha_hora();
					
					if (!gestorReservas.comprobar24hantelacion(fecha)) {
				        System.out.println("No se puede hacer una reserva con menos de 24 horas de antelación.");
				        break;
				    }

					System.out.println("Introduce la duración de la reserva (60, 90, 120 minutos):");
					duracion = Scanf.Scan_Duracion();
					
					if(!gestorReservas.comprobarDisponibilidadFecha(nombre_pista, fecha, duracion)) {
						System.out.println("La pista seleccionada no esta disponible durante el tramo horario seleccionado");
						break;
					}
					
					precio=gestorReservas.AsignarPrecio(duracion);
					
					nniños=0;
					if(tipo_reserva != 1) {
						System.out.println("Introduce el numero de niños:");
						nniños = Scanf.Scan_int();
					}
					nadultos=0;
					if(tipo_reserva != 2) {
						System.out.println("Introduce el numero de adultos:");
						nadultos = Scanf.Scan_int();
					}
					
					if(!gestorPistas.comprobarMaxJugadores(nombre_pista, nadultos+nniños)) {
						System.out.println("La pista seleccionada no acepta tantos jugadores");
						break;
					}
					
					//Dejamos la funcion porque si es la primera reserva del jugador actualiza el valor de fecha_inscripcion
					gestorUsuarios.calcularDescuento(correo, fecha);
					descuento=0.05f;
					
					gestorReservas.comprobarFechaCaducidadBono(id_bono, fecha);

					if(gestorReservas.añadirReservaABono(id_bono,correo,nombre_pista,fecha,duracion,nniños,nadultos,descuento,precio)){
						System.out.println("Reserva en bono creada con éxito.");
					}
					else{
						System.out.println("No se ha podido crear la reserva en el bono");
					}
					
					break;
				

				case 4:
					// Modificar reserva
					System.out.println("Introduzca el nombre de la pista");
					nombre_pista=Scanf.Scan_string();

					System.out.println("Introduce la fecha actual de la reserva (dd/MM/yyyy HH:mm):");
					fecha = Scanf.Scan_fecha_hora();
					
					if (!gestorReservas.comprobar24hantelacion(fecha)) {
				        System.out.println("La reserva no puede ser modificada con menos de 24 horas de antelación.");
				        break;
				    }
					
					System.out.println("Introduce la nueva fecha de la reserva (dd/MM/yyyy HH:mm):");
					nuevaFecha = Scanf.Scan_fecha_hora();
					
					if (!gestorReservas.comprobar24hantelacion(nuevaFecha)) {
				        System.out.println("La reserva no se puede modificar a una fecha con menos de 24h de antelación.");
				        break;
				    }
					
					System.out.println("Introduce la nueva duración de la reserva (60,90,120 minutos):");
					duracionNueva = Scanf.Scan_Duracion();
					
					if(!gestorReservas.comprobarDisponibilidadFecha(nombre_pista, nuevaFecha, duracionNueva)) {
						System.out.println("La pista seleccionada no esta disponible durante el tramo horario seleccionado");
						break;
					}
					
					if(gestorReservas.modificarReserva(nombre_pista, fecha, nuevaFecha, duracionNueva, gestorReservas.AsignarPrecio(duracionNueva)) == true) {
						System.out.println("Reserva modificada con éxito.");
					}
					else {
						System.out.println("Error, no se puede modificar la reserva.");
					}
	
					break;
				

				case 5:
					// Cancelar reserva
					System.out.println("Introduzca el nombre de la pista");
					nombre_pista=Scanf.Scan_string();

					System.out.println("Introduce la fecha de la reserva (dd/MM/yyyy HH:mm):");
					fecha = Scanf.Scan_fecha_hora();
					
					if (!gestorReservas.comprobar24hantelacion(fecha)) {
				        System.out.println("La reserva no puede ser cancelada con menos de 24 horas de antelación.");
				        break;
				    }
					
					if(gestorReservas.cancelarReserva(nombre_pista, fecha) == true) {
						System.out.println("Reserva cancelada con éxito.");
					}
					else {
						System.out.println("Error, no se puede cancelar la reserva.");
					}
					break;

				case 6:
					gestorReservas.mostrarReservasFuturas();
					break;

				case 7:
					// Consultar número de reservas para un día
					System.out.println("Introduzca el nombre de la pista");
					nombre_pista=Scanf.Scan_string();

					System.out.println("Introduce la fecha de consulta (dd/MM/yyyy):");
					fecha = Scanf.Scan_fecha();

					gestorReservas.mostrarReservas(fecha, nombre_pista);
					break;
					
				case 8:
					System.out.println("Introduce el correo del usuario:");
					correo = Scanf.Scan_string();
					gestorReservas.mostrarBonos(correo);
					break;

				case 9:
					System.out.println("Saliendo del gestor de reservas");
					break;

				default:
					System.out.println("Error, la opción seleccionada no es válida");
					break;
			}
		} while (opc != 9);

	}
}
