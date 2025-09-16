package main;

import ejer1.Estado;		
import ejer1.Material;
import ejer1.Tipo;
import java.util.Scanner;
import ejer3.GestorPistas;
import ejer3.GestorReservas;
import ejer3.GestorUsuarios;
import java.util.Date;
import ejer1.TipoPista;
import ejer2.Reserva;
import ejer2.ReservaAdultos;
import ejer2.ReservaFamiliar;
import ejer2.ReservaInfantil;
import ejer2.ReservaIndividual;
import ejer2.ReservaBono;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import ejer1.Pista;

public class main{
	public static void main(String[] args) {
		//Variables para navegar por la interfaz
		int opc_gestor=0, opc=0;
		//Variables auxiliares
		int aux=0, pos=0, duracion=0;
		Boolean auxb=true, auxb2=true;
		String auxs="";
		//Variables para los usuarios/pistas/materiales/reservas
		int num_usuarios=0, tipo_reserva=0, num_niños=0, num_adultos=0;
		String nombre_usuario="", correo="", nombre_pista="";
		Date fecha= new Date(), fecha_nueva= new Date();
		Boolean estado_pista=true, tipo_pista=true, uso=true;
		Tipo tipo_material=Tipo.PELOTAS;
		TipoPista tamaño=TipoPista.ADULTOS;
		Estado estado_material=Estado.DISPONIBLE;
		Material material= new Material();
		ArrayList<Pista> pistas=new ArrayList<>();
		ReservaIndividual reserva_individual=new ReservaIndividual();
		ReservaBono reserva_bono=new ReservaBono();
		//Variables para leer por linea de comandos y para poner la fecha
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfh= new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Scanner sc= new Scanner(System.in);
		
		GestorUsuarios g_usus=GestorUsuarios.getInstance();
		GestorPistas g_pist=GestorPistas.getInstance();
		GestorReservas g_rese=GestorReservas.getInstance();
		
		do {
			System.out.println("\nSelecciona la opcion que deseas:\n"
								+ "1.- Gestor de Usuarios\n"
								+ "2.- Gestor de Pistas\n"
								+ "3.- Gestor de Reservas\n"
								+ "4.- Salir\n");
			try {
				opc_gestor=sc.nextInt();
				
			}catch(Exception e) {
				sc.next();
			}
			
			switch(opc_gestor) {
				//Gestor de usuarios
				case 1:
					do {
						System.out.println("\nSelecciona la opcion que deseas:\n"
								+ "1.- Dar de alta a un usuario\n"
								+ "2.- Modificar la informacion de un usuario\n"
								+ "3.- Listar usuarios registrados\n"
								+ "4.- Salir\n");
						try {
							opc=sc.nextInt();
							sc.nextLine();
							
						}catch(Exception e) {
							sc.next();
						}
						
						switch(opc) {
							//Dar de alta a un usuario
							case 1:
								System.out.println("Introduce el nombre del usuario\n");
								nombre_usuario=sc.nextLine();
								
								aux=-1;
								System.out.println("Introduce la fecha de nacimiento(dd/MM/yyyy):\n");
								
								do{
									try {
										fecha=sdf.parse(sc.nextLine());
										aux=0;
									}
									catch(Exception e) {
										System.out.println("Formato de fecha incorrecto\n");
									}
								}while(aux != 0);

								System.out.println("Introduce el correo del usuario\n");
								correo=sc.nextLine();
								g_usus.AltaUsuario(nombre_usuario, fecha, correo);
								break;
							//Modificar la informacion de un usuario
							case 2:
								System.out.println("Introduce el nuevo nombre:");
								nombre_usuario=sc.nextLine();
								
								aux=-1;
								System.out.println("Introduce la nueva fecha de nacimiento(dd/MM/yyyy):\n");
								do{
									try {
										fecha=sdf.parse(sc.nextLine());
										aux=0;
									}
									catch(Exception e) {
										System.out.println("Formato de fecha incorrecto\n");
									}
								}while(aux != 0);
								
								System.out.println("Introduce el correo del usuario\n");
								correo=sc.nextLine();
								g_usus.modificarUsuario(nombre_usuario,fecha, correo);
								break;
							//Listar usuarios registrados
							case 3:
								g_usus.listarUsuarios();
								break;
							case 4:
								System.out.println("Saliendo del gestor de usuarios\n");
								break;
							default:
								System.out.println("La opcion seleccionada no es valida\n");
								break;
						}
					}while(opc != 4);
					break;
				//Gestor de pistas
				case 2:
					do {
						System.out.println("\nSelecciona la opcion que deseas:\n"
								+ "1.- Crear pista\n"
								+ "2.- Crear materiales\n"
								+ "3.- Asociar material a pista\n"
								+ "4.- Listar pistas no disponibles\n"
								+ "5.- Buscar pista por numero de usuarios y tipo de pista\n"
								+ "6.- Salir\n");
						try {
							opc=sc.nextInt();
							sc.nextLine();
							
						}catch(Exception e) {
							sc.next();
						}
						
						switch(opc) {
							//Crear pista
							case 1:
								
								do{
									System.out.println("Introduzca el nombre de la pista\n");
									nombre_pista=sc.nextLine();
									auxb=g_pist.comprobarNombre(nombre_pista);
									if(auxb == false){
										System.out.println("Ya existe una pista con ese nombre\n");
									}
								}while(auxb == false);
								
								do{
									System.out.println("Introduzca si quiere que la pista sea de interior o exterior\n");
									auxs=sc.nextLine();
									aux=-1;
									if(auxs.equals("Exterior") || auxs.equals("exterior")){
										tipo_pista=true;
										aux=0;

									}
									else if(auxs.equals("Interior") || auxs.equals("interior")){
										tipo_pista=false;
										aux=0;

									}
									else{
										System.out.println("Error tipo no valido");
									}
								}while(aux != 0);
								
								estado_pista=true;
					
								do {
									System.out.println("Selecciona el tipo de pista:\n"
											+ "1.- Minibasket\n"
											+ "2.- Adultos\n"
											+ "3.- 3VS3\n");
									try {
										aux=sc.nextInt();
										
									}catch(Exception e) {
										sc.next();
									}
									
									switch(aux) {
										case 1:
											tamaño=TipoPista.MINIBASKET;
											break;
										case 2:
											tamaño=TipoPista.ADULTOS;
											break;
										case 3:
											tamaño=TipoPista.TRESVSTRES;
											break;
										default:
											System.out.println("Opcion introducida no valida:\n");
											break;
									
									}
								}while(aux < 1 || aux > 3);
								
								aux=-1;
								do {
									System.out.println("Introduzca el maximo de jugadores\n");
									try {
										num_usuarios=sc.nextInt();
										aux=0;
									}catch(Exception e) {
										System.out.println("Error, no se ha introducido un numero\n");
										sc.next();
									}
								}while(aux != 0);
								
								g_pist.crearPistas(nombre_pista,estado_pista,tipo_pista,tamaño,num_usuarios);
								break;
							//Crear materiales
							case 2:
								
								do{
									System.out.println("Introduzca el uso del material(Exterior o interior)");
									aux=-1;
									auxs=sc.nextLine();
									if(auxs.equals("Exterior") || auxs.equals("exterior")){
										uso=true;
										aux=0;
									}
									else if(auxs.equals("Interior") || auxs.equals("interior")){
										uso=false;
										aux=0;
									}
									else{
										System.out.println("Error uso no valido");
									}
								}while(aux != 0);
								
								do {
									System.out.println("Selecciona el tipo de material:\n"
											+ "1.- Pelota\n"
											+ "2.- Canasta\n"
											+ "3.- Cono\n");
									try {
										aux=sc.nextInt();
										
									}catch(Exception e) {
										sc.next();
									}
									
									switch(aux) {
										case 1:
											tipo_material=Tipo.PELOTAS;
											break;
										case 2:
											tipo_material=Tipo.CANASTAS;
											break;
										case 3:
											tipo_material=Tipo.CONOS;
											break;
										default:
											System.out.println("Opcion introducida no valida:\n");
											break;
									
									}
								}while(aux < 1 || aux > 3);
								
								estado_material=Estado.DISPONIBLE;
								g_pist.crearMaterial(g_pist.materialesSize(),uso,tipo_material,estado_material);
								break;
							//Asociar material a pista
							case 3:
								do{
									System.out.println("Introduzca el nombre de la pista\n");
									nombre_pista=sc.nextLine();
									auxb=g_pist.comprobarNombre(nombre_pista);
									if(auxb == true){
										System.out.println("No existe ninguna pista con ese nombre\n");
									}
								}while(auxb == true);
								
								System.out.println("Selecciona el material:");
								g_pist.listarMaterialesDisponibles();
								
								aux=-1;
								do {
									try {
										pos=sc.nextInt();
										material=g_pist.devolverMaterial(pos);
										aux=0;
									}catch(Exception e) {
										System.out.println("Error, el numero introducido no es valido\n");
										sc.nextLine();
									}
								}while(aux != 0);
								
								g_pist.asignarMaterialAPista(nombre_pista, material);
								break;
							//Listar pistas no disponibles
							case 4:
								g_pist.listarPistasNoDisponibles();
								break;
							//Buscar pista por numero de usuarios y tipo de pista
							case 5:
								aux=-1;
								do {
									System.out.println("Introduce el numero de usuarios minimos de la pista:\n");
									try {
										num_usuarios=sc.nextInt();
										aux=0;
									}catch(Exception e) {
										System.out.println("Error, no se ha introducido un numero\n");
										sc.next();
									}
								}while(aux != 0);
								
								do {
									System.out.println("Selecciona el tipo de pista:\n"
											+ "1.- Minibasket\n"
											+ "2.- Adultos\n"
											+ "3.- 3VS3\n");
									try {
										aux=sc.nextInt();
										
									}catch(Exception e) {
										sc.next();
									}
									
									switch(aux) {
										case 1:
											tamaño=TipoPista.MINIBASKET;
											break;
										case 2:
											tamaño=TipoPista.ADULTOS;
											break;
										case 3:
											tamaño=TipoPista.TRESVSTRES;
											break;
										default:
											System.out.println("Opcion introducida no valida:\n");
											break;
									
									}
								}while(aux < 1 || aux > 3);
								
								pistas=g_pist.pistasLibresConJugadores(num_usuarios, tamaño);
								for(Pista pista : pistas) {
									System.out.println(pista.toString());
								}
								break;
							//Salir
							case 6:
								System.out.println("Saliendo del gestor de pistas\n");
								break;
							default:
								System.out.println("La opcion seleccionada no es valida\n");
								break;
						}
						
					}while(opc != 6);
					break;
				//Gestor de reservas
				case 3:
					do {
						System.out.println("\nSelecciona la opcion que deseas:\n"
								+ "1.- Hacer reservas individuales\n"
								+ "2.- Crear un bono\n"
								+ "3.- Hacer reservas dentro de un bono\n"
								+ "4.- Modificar reserva\n"
								+ "5.- Cancelar reserva\n"
								+ "6.- Consultar numero de reservas futuras\n"
								+ "7.- Consultar numero de reservas para un dia\n"
								+ "8.- Salir\n");
						try {
							opc=sc.nextInt();
							sc.nextLine();
							
						}catch(Exception e) {
							sc.next();
						}
						
						switch(opc) {
							//Hacer reservas individuales
							case 1:
								do {
									System.out.println("Selecciona el tipo de reserva:\n"
											+ "1.- Reserva adultos\n"
											+ "2.- Reserva infantil\n"
											+ "3.- Reserva familiar\n");
									try {
										tipo_reserva=sc.nextInt();
										sc.nextLine();
										
									}catch(Exception e) {
										sc.nextLine();
									}
									
								}while(tipo_reserva < 1 || tipo_reserva > 3);
								
								do{
									System.out.println("Introduce el correo del usuario que va a hacer la reserva:");
									correo = sc.nextLine();
									auxb=g_usus.comprobarMayorEdad(correo);
									if(auxb != true) {
										System.out.println("Error, el usuario introducido es menor de edad");
									}
								}while(auxb != true);
								
								do {
									do{
										System.out.println("Introduzca el nombre de la pista");
										nombre_pista=sc.nextLine();
										auxb=g_pist.comprobarNombre(nombre_pista);
										if(auxb == true){
											System.out.println("Error, no existe una pista con ese nombre");
										}
										else{
											auxb2=g_pist.comprobarPistaReserva(nombre_pista, tipo_reserva);
											if(auxb2 == false){
												System.out.println("Error, la pista seleccionada no es compatible con tu reserva");
											}
										}
									}while(auxb == true || auxb2 == false);
								
									System.out.println("Introduce la fecha y hora de la reserva (dd/MM/yyyy HH:mm):");
									aux = -1;
									do {
										try {
											fecha = sdfh.parse(sc.nextLine());
											aux = 0;
											auxb=g_rese.comprobarFechaReserva(fecha);
											if(auxb == false){
												System.out.println("No se puede crear una reserva con menos de 24h antelacion, introduzca otra fecha");
											}
										} catch (Exception e) {
											System.out.println("Formato de fecha incorrecto\n");
										}
									} while (aux != 0 || auxb == false);
									auxb=g_rese.comprobarPistayFechaReserva(nombre_pista, fecha);
									if(auxb == false) {
										System.out.println("Ya hay una reserva en la pista "+nombre_pista+" en la fecha "+fecha);
									}
								} while(auxb == false);
								
								do {
									System.out.println("Introduce la duracion de la reserva (60, 90, 120 minutos):");
									try {
										duracion = sc.nextInt();
									}
									catch(Exception e){
										sc.nextLine();
									}
								}while(duracion != 60 && duracion != 90 && duracion != 120);
								aux=-1;
								switch(tipo_reserva) {
									//Reserva Adultos
									case 1:
										do {
											System.out.println("Introduce el numero de adultos que van a asistir:");
											try {
												num_adultos = sc.nextInt();
												aux=0;
												auxb=g_pist.comprobarMaxJugadores(nombre_pista, num_adultos);
												if(auxb == false){
													System.out.println("Error, el numero introducido es mayor que los jugadores qeu admite la pista");
												}
											}
											catch(Exception e){
												sc.nextLine();
											}
										}while(aux != 0 || auxb == false);
										
										g_rese.añadirReservaAdultos(reserva_individual.crearReservaAdultos(correo, fecha, duracion, nombre_pista, g_usus.asginarDescuento(correo), num_adultos));
										break;
									//Reserva Infantil
									case 2:
										do {
											System.out.println("Introduce el numero de niños que van a asistir:");
											try {
												num_niños= sc.nextInt();
												aux=0;
												auxb=g_pist.comprobarMaxJugadores(nombre_pista, num_niños);
												if(auxb == false){
													System.out.println("Error, el numero introducido es mayor que los jugadores que admite la pista");
												}
											}
											catch(Exception e){
												sc.nextLine();
											}
										}while(aux != 0 || auxb == false);
										
										g_rese.añadirReservaInfantil(reserva_individual.crearReservaInfantil(correo, fecha, duracion, nombre_pista, g_usus.asginarDescuento(correo), num_niños));
										break;
									//Reserva Familiar
									case 3:
										do{
											do {
												System.out.println("Introduce el numero de adultos que van a asistir:");
												try {
													num_adultos = sc.nextInt();
													aux=0;
												}
												catch(Exception e){
													sc.nextLine();
												}
											}while(aux != 0);
											aux=-1;
											do {
												System.out.println("Introduce el numero de niños que van a asistir:");
												try {
													num_niños = sc.nextInt();
													aux=0;
												}
												catch(Exception e){
													sc.nextLine();
												}
											}while(aux != 0);
											auxb=g_pist.comprobarMaxJugadores(nombre_pista, num_niños+num_adultos);
												if(auxb == false){
													System.out.println("Error, el numero de participantesintroducido es mayor que los jugadores que admite la pista");
												}
										}while(auxb == false);
										
										g_rese.añadirReservaFamiliar(reserva_individual.crearReservaFamiliar(correo, fecha, duracion, nombre_pista, g_usus.asginarDescuento(correo), num_niños, num_adultos));
										break;
								}
								
								System.out.println("Reserva creada con exito.");

							break;
							//Crear un bono
							case 2:
								do{
									System.out.println("Introduce el correo del usuario que va a hacer la reserva:");
									correo = sc.nextLine();
									auxb=g_usus.comprobarMayorEdad(correo);
									if(auxb != true) {
										System.out.println("Error, el usuario introducido es menor de edad");
									}
								}while(auxb != true);
								
								do {
									System.out.println("Selecciona el tipo de pista:\n"
											+ "1.- Minibasket\n"
											+ "2.- Adultos\n"
											+ "3.- 3VS3\n");
									try {
										aux=sc.nextInt();
										
									}catch(Exception e) {
										sc.next();
									}
									
									switch(aux) {
										case 1:
											tamaño=TipoPista.MINIBASKET;
											break;
										case 2:
											tamaño=TipoPista.ADULTOS;
											break;
										case 3:
											tamaño=TipoPista.TRESVSTRES;
											break;
										default:
											System.out.println("Opcion introducida no valida:\n");
											break;
									
									}
								}while(aux < 1 || aux > 3);
								
								g_rese.añadirBono(g_rese.crearBonoReservas(correo, tamaño));
								System.out.println("Bono creado con exito");
								
								break;
							//Hacer reservas dentro de un bono
							case 3:
								do{
									System.out.println("Introduce el correo del usuario que va a hacer la reserva:");
									correo = sc.nextLine();
									auxb=g_rese.comprobarBonoUsuario(correo);
									if(auxb != true) {
										System.out.println("Error, el usuario introducido no tiene ningun bono");
										break;
									}
								}while(auxb != true);
								
								if(auxb != true) {
									break;
								}
								
								do {
									System.out.println("Selecciona el tipo de reserva:\n"
											+ "1.- Reserva adultos\n"
											+ "2.- Reserva infantil\n"
											+ "3.- Reserva familiar\n");
									try {
										tipo_reserva=sc.nextInt();
										sc.nextLine();
										
									}catch(Exception e) {
										sc.nextLine();
									}
									
								}while(tipo_reserva < 1 || tipo_reserva > 3);
								
								if(g_rese.comprobarBonoTipoReserva(correo, tipo_reserva) == false) {
									System.out.println("Error, tu tipo de bono no permite el tipo de reserva seleccionado");
									break;
								}
								
								do {
									do{
										System.out.println("Introduzca el nombre de la pista");
										nombre_pista=sc.nextLine();
										auxb=g_pist.comprobarNombre(nombre_pista);
										if(auxb == true){
											System.out.println("Error, no existe una pista con ese nombre");
										}
										else{
											auxb2=g_pist.comprobarPistaReserva(nombre_pista, tipo_reserva);
											if(auxb2 == false){
												System.out.println("Error, la pista seleccionada no es compatible con tu reserva");
											}
											else {
												auxb2=g_rese.comprobarBonoPista(correo, g_pist.getTipoPista(nombre_pista));
												if(auxb2 == false){
													System.out.println("Error, la pista seleccionada no es compatible con tu bono");
												}
											}
										}
									}while(auxb == true || auxb2 == false);
								
									System.out.println("Introduce la fecha y hora de la reserva (dd/MM/yyyy HH:mm):");
									aux = -1;
									do {
										try {
											fecha = sdfh.parse(sc.nextLine());
											aux = 0;
											auxb=g_rese.comprobarFechaReserva(fecha);
											if(auxb == false){
												System.out.println("No se puede crear una reserva con menos de 24h antelacion, introduzca otra fecha");
											}
										} catch (Exception e) {
											System.out.println("Formato de fecha incorrecto\n");
										}
									} while (aux != 0 || auxb == false);
									auxb=g_rese.comprobarPistayFechaReserva(nombre_pista, fecha);
									if(auxb == false) {
										System.out.println("Ya hay una reserva en la pista "+nombre_pista+" en la fecha "+fecha);
									}
								} while(auxb == false);
								
								do {
									System.out.println("Introduce la duracion de la reserva (60, 90, 120 minutos):");
									try {
										duracion = sc.nextInt();
									}
									catch(Exception e){
										sc.nextLine();
									}
								}while(duracion != 60 && duracion != 90 && duracion != 120);
								
								aux=-1;
								switch(tipo_reserva) {
									//Reserva Adultos
									case 1:
										do {
											System.out.println("Introduce el numero de adultos que van a asistir:");
											try {
												num_adultos = sc.nextInt();
												aux=0;
												auxb=g_pist.comprobarMaxJugadores(nombre_pista, num_adultos);
												if(auxb == false){
													System.out.println("Error, el numero introducido es mayor que los jugadores qeu admite la pista");
												}
											}
											catch(Exception e){
												sc.nextLine();
											}
										}while(aux != 0 || auxb == false);
										
										g_rese.añadirReservaAdultos(reserva_bono.crearReservaAdultos(correo, fecha, duracion, nombre_pista, 0.05f, num_adultos));
										break;
									//Reserva Infantil
									case 2:
										do {
											System.out.println("Introduce el numero de niños que van a asistir:");
											try {
												num_niños= sc.nextInt();
												aux=0;
												auxb=g_pist.comprobarMaxJugadores(nombre_pista, num_niños);
												if(auxb == false){
													System.out.println("Error, el numero introducido es mayor que los jugadores que admite la pista");
												}
											}
											catch(Exception e){
												sc.nextLine();
											}
										}while(aux != 0 || auxb == false);
										
										g_rese.añadirReservaInfantil(reserva_bono.crearReservaInfantil(correo, fecha, duracion, nombre_pista, 0.05f, num_niños));
										break;
									//Reserva Familiar
									case 3:
										do{
											do {
												System.out.println("Introduce el numero de adultos que van a asistir:");
												try {
													num_adultos = sc.nextInt();
													aux=0;
												}
												catch(Exception e){
													sc.nextLine();
												}
											}while(aux != 0);
											aux=-1;
											do {
												System.out.println("Introduce el numero de niños que van a asistir:");
												try {
													num_niños = sc.nextInt();
													aux=0;
												}
												catch(Exception e){
													sc.nextLine();
												}
											}while(aux != 0);
											auxb=g_pist.comprobarMaxJugadores(nombre_pista, num_niños+num_adultos);
												if(auxb == false){
													System.out.println("Error, el numero de participantesintroducido es mayor que los jugadores que admite la pista");
												}
										}while(auxb == false);
										
										g_rese.añadirReservaFamiliar(reserva_bono.crearReservaFamiliar(correo, fecha, duracion, nombre_pista, 0.05f, num_niños, num_adultos));
										break;
								}
								
								g_rese.añadirReservaABono(correo, g_pist.getTipoPista(nombre_pista), nombre_pista, fecha);
								System.out.println("Reserva creada con exito.");
															
								break;
							//Modificar reserva
							case 4:
								do{
									System.out.println("Introduzca el nombre de la pista");
									nombre_pista=sc.nextLine();
									auxb=g_pist.comprobarNombre(nombre_pista);
									if(auxb == true){
										System.out.println("Error, no existe una pista con ese nombre");
									}
								}while(auxb == true);
								
								System.out.println("Introduce la fecha y hora de la reserva (dd/MM/yyyy HH:mm):");
								aux = -1;
								do {
									try {
										fecha = sdfh.parse(sc.nextLine());
										aux = 0;
										auxb=g_rese.comprobarFechaReserva(fecha);
										if(auxb == false){
											System.out.println("No se puede modificar una reserva con menos de 24h antelacion");
											break;
										}
									} catch (Exception e) {
										System.out.println("Formato de fecha incorrecto\n");
									}
								} while (aux != 0);
								
								if(auxb == false){
									break;
								}

								do {
									System.out.println("Introduce la nueva fecha y hora de la reserva (dd/MM/yyyy HH:mm):");
									aux = -1;								
									try {
										fecha_nueva = sdfh.parse(sc.nextLine());
										aux = 0;
										auxb=g_rese.comprobarPistayFechaReserva(nombre_pista, fecha_nueva);
										if(auxb == false) {
											System.out.println("Ya hay una reserva en la pista "+nombre_pista+" en la fecha "+fecha);
										}
										else{
											auxb=g_rese.comprobarFechaReserva(fecha);
											if(auxb == false){
												System.out.println("No se puede crear una reserva con menos de 24h antelacion, introduzca otra fecha");
											}
										}
									} catch (Exception e) {
										System.out.println("Formato de fecha incorrecto\n");
									}
								} while (aux != 0 || auxb == false);
								
								do {
									System.out.println("Introduce la duracion de la reserva (60, 90, 120 minutos):");
									try {
										duracion = sc.nextInt();
									}
									catch(Exception e){
										sc.nextLine();
									}
								}while(duracion != 60 && duracion != 90 && duracion != 120);
				
								if(g_rese.modificarReserva(nombre_pista, fecha, fecha_nueva, duracion) == true) {
									System.out.println("Reserva modificada con éxito.");
								}
								else {
									System.out.println("No se encontró la reserva");
								}

								break;
							//Cancelar reserva
							case 5:
								
								do{
									System.out.println("Introduzca el nombre de la pista");
									nombre_pista=sc.nextLine();
									auxb=g_pist.comprobarNombre(nombre_pista);
									if(auxb == true){
										System.out.println("Error, no existe una pista con ese nombre");
									}
								}while(auxb == true);
								
								System.out.println("Introduce la fecha y hora de la reserva (dd/MM/yyyy HH:mm):");
								aux = -1;
								do {
									try {
										fecha = sdfh.parse(sc.nextLine());
										aux = 0;
										auxb=g_rese.comprobarFechaReserva(fecha);
										if(auxb == false){
											System.out.println("No se puede cancelar una reserva con menos de 24h antelacion");
											break;
										}
									} catch (Exception e) {
										System.out.println("Formato de fecha incorrecto\n");
									}
								} while (aux != 0);
								
								if(auxb == false){
									break;
								}

								if(g_rese.cancelarReserva(nombre_pista, fecha) == true) {
									System.out.println("Reserva cancelada con éxito.");
								}
								else {
									System.out.println("No se encontró la reserva");
								}

								break;
							//Consultar numero de reservas futuras
							case 6:
								g_rese.mostrarReservasFuturas();
								break;
							//Consultar numero de reservas para un dia
							case 7:
								System.out.println("Introduce el nombre de la pista:\n");
								nombre_pista=sc.nextLine();
																
								aux=-1;
								do{
									System.out.println("Introduce la fecha de reserva(dd/MM/yyyy):\n");
									try {
										fecha=sdf.parse(sc.nextLine());
										aux=0;
									}
									catch(Exception e) {
										System.out.println("Formato de fecha incorrecto\n");
									}
								}while(aux != 0);
								
								g_rese.mostrarReservas(fecha, nombre_pista);
								break;
							//Salir
							case 8:
								System.out.println("Saliendo del gestor de reservas\n");
								break;
							default:
								System.out.println("La opcion seleccionada no es valida\n");
								break;
						}
						
					}while(opc != 8);
					break;
				//Salir
				case 4:
					System.out.println("Cerrando programa\n");
					break;
				default:
					System.out.println("La opcion seleccionada no es valida\n");
					break;
			}
			
			
		}while(opc_gestor != 4);
		sc.close();
		g_usus.guardarUsuariosEnArchivo();
		g_pist.guardarPistasEnArchivo();
		g_pist.guardarMaterialesEnArchivo();
		g_rese.guardarReservasAdultosEnArchivo();
		g_rese.guardarReservasInfantilesEnArchivo();
		g_rese.guardarReservasFamiliaresEnArchivo();
		g_rese.guardarBonosEnArchivo();
		//Guardar las variables en ficheros de texto
	}
}
