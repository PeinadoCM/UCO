package main;

import java.util.ArrayList;

import org.checkerframework.checker.units.qual.A;

import dto.*;
import mgr.*;

/**
 * Clase principal de la aplicación que gestiona la interacción con el usuario y las operaciones
 * relacionadas con el registro, inicio de sesión y gestión de eventos.
 */
public class App {

    /**
     * Método principal que inicia la aplicación y muestra el menú de opciones al usuario.
     * Permite registrarse, iniciar sesión o salir de la aplicación.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        int opc;
        UsuarioMgr usuarioMgr = UsuarioMgr.getInstance();
        Usuario usuario = new Usuario();

        App.clearConsole();
        do {
            System.out.println("\nSelecciona la acción que desees realizar:" +
                    "\n1.- Registrarse" +
                    "\n2.- Iniciar Sesión" +
                    "\n0.- Salir");

            opc = aux.Scanf.scanInt();

            App.clearConsole();

            switch (opc) {
                case 0:
                    System.out.println("\nSaliendo de la aplicación");
                    break;
                case 1:
                    System.out.println("\nIntroduce tu correo:");
                    usuario.setCorreo(aux.Scanf.scanString());
                    System.out.println("\nIntroduce tu contraseña:");
                    usuario.setContrasena(aux.Scanf.scanString());
                    System.out.println("\nIntroduce tu nombre y apellidos:");
                    usuario.setNombreCompleto(aux.Scanf.scanString());
                    System.out.println("\nIntroduce tu numero de teléfono:");
                    usuario.setTelefono(aux.Scanf.scanIntPositivo());
                    System.out.println("\nIntroduce tu DNI:");
                    usuario.setDni(aux.Scanf.scanDni());

                    App.clearConsole();
                    if (usuarioMgr.registrarse(usuario) == true) {
                        System.out.println("\nUsuario registrado correctamente");
                    } else {
                        System.out.println("\nError al registrar el usuario");
                    }
                    break;

                case 2:
                    App.interfazIniciarSesion();
                    break;
                default:
                    System.out.println("\nLa opción seleccionada no existe");
                    break;
            }
        } while (opc != 0);

    }

    /**
     * Muestra un menú para iniciar sesión como organizador o usuario y gestiona las credenciales
     * de inicio de sesión. Si las credenciales son correctas, redirige al usuario a la interfaz
     * correspondiente.
     */
    public static void interfazIniciarSesion() {
        int opcIS;
        UsuarioMgr usuarioMgr = UsuarioMgr.getInstance();
        Usuario usuario = new Usuario();
        Organizador organizador = new Organizador();

        App.clearConsole();
        do {
            System.out.println("\nSelecciona como quieres Iniciar Sesión:" +
                    "\n1.- Iniciar Sesión como Organizador" +
                    "\n2.- Iniciar Sesión como Usuario" +
                    "\n0.- Volver");

            opcIS = aux.Scanf.scanInt();

            switch (opcIS) {
                case 0:
                    System.out.println("\nVolviendo al Menú Principal");
                    break;
                case 1:
                    System.out.println("\nIntroduce tu correo:");
                    organizador.setCorreo(aux.Scanf.scanString());
                    System.out.println("\nIntroduce tu contraseña:");
                    organizador.setContrasena(aux.Scanf.scanString());

                    App.clearConsole();
                    if (usuarioMgr.iniciarSesionOrganizador(organizador) == true) {
                        System.out.println("\nIniciando sesion como Organizador");
                        interfazOrganizador(organizador.getCorreo());
                    } else {
                        System.out.println("Credenciales incorrectas.");
                    }
                    break;
                case 2:
                    System.out.println("\nIntroduce tu correo:");
                    usuario.setCorreo(aux.Scanf.scanString());
                    System.out.println("\nIntroduce tu contraseña:");
                    usuario.setContrasena(aux.Scanf.scanString());

                    App.clearConsole();
                    if (usuarioMgr.iniciarSesionUsuario(usuario) == true) {
                        System.out.println("\nIniciando sesion como Usuario");
                        interfazUsuario(usuario.getCorreo());
                    } else {
                        System.out.println("\nError al iniciar sesion, el correo o la contraseña son incorrectos");
                    }
                    break;
                default:
                    System.out.println("\nLa opción seleccionada no existe");
                    break;
            }
        } while (opcIS != 0);
    }

    /**
     * Muestra el menú de opciones disponibles para un usuario autenticado y gestiona
     * las acciones seleccionadas como comprar entradas, publicar reventas, recargar
     * el monedero, ver entradas, valorar usuarios y gestionar reclamaciones.
     *
     * @param correoUsuario El correo del usuario que ha iniciado sesión.
     **/
    public static void interfazUsuario(String correoUsuario) {
        int opc, contador = 0, entradaSeleccionada = 0;
        TipoTransaccion tipoTransaccion = null;

        UsuarioMgr usuarioMgr = UsuarioMgr.getInstance();
        Usuario usuario = new Usuario();

        TransaccionMgr transaccionMgr = TransaccionMgr.getInstance();
        EventoMgr eventoMgr = EventoMgr.getInstance();

        float saldoActual = 0, cantidad = 0, saldoNuevo = 0;
        Boolean recargaMonedero = false;
        ArrayList<Entrada> entradas = new ArrayList<Entrada>();
        Entrada entradaCompra = new Entrada();
        ArrayList<Evento> eventosDisponibles = new ArrayList<Evento>();
        ArrayList<String> nombreEventos = new ArrayList<String>();

        do {
            System.out.println("\nSelecciona la acción que desees realizar:" +
                    "\n1.- Comprar Entrada" +
                    "\n2.- Publicar Reventa" +
                    "\n3.- Recargar Monedero" +
                    "\n4.- Ver mis entradas" +
                    "\n5.- Valorar Usuario" +
                    "\n6.- Reclamar/Consultar" +
                    "\n0.- Cerrar Sesión");

            opc = aux.Scanf.scanInt();

            App.clearConsole();

            switch (opc) {
                case 0:
                    System.out.println("\nCerrando sesión");
                    break;
                case 1:

                    System.out.println("\nEventos disponibles:");
                    eventosDisponibles = eventoMgr.listarEventosDisponibles();

                    if (eventosDisponibles.isEmpty()) {
                        System.out.println("No hay eventos disponibles.");
                    } else {
                        for (Evento evento : eventosDisponibles) {
                            nombreEventos.add(evento.getNombre());
                            System.out.println("Nombre: " + evento.getNombre());
                            System.out.println("Descripción: " + evento.getDescripcion());
                            System.out.println("Lugar: " + evento.getLugar());
                            System.out.println("Fecha: " + evento.getFecha());
                            System.out.println("--------------------------");
                        }
                    }

                    System.out.println("\nIntroduce el nombre del evento para comprar entrada:");
                    String eventoCompra = aux.Scanf.scanString();

                    if(nombreEventos.contains(eventoCompra) == false) {
                        App.clearConsole();
                        System.out.println("\nEl evento introducido no existe");
                        break;
                    }

                    tipoTransaccion = interfazSeleccionarTipoCompra();

                    ArrayList<Entrada> entradasDisponibles = transaccionMgr.verEntradasPorEvento(eventoCompra,
                            tipoTransaccion);
                            
                    if (entradasDisponibles.isEmpty()) {
                        App.clearConsole();
                        System.out.println("No hay entradas disponibles para este evento.");
                        break;
                    }

                    System.out.println("\nEntradas disponibles para el evento: " + eventoCompra);
                    
                    contador = 1;

                    for (Entrada entrada : entradasDisponibles) {
                        System.out.println("\n" + contador + ".- Tipo: " + entrada.getTipo());
                        System.out.println("Precio: " + entrada.getPrecio() + "€");
                        System.out.println("--------------------------");
                        contador++;
                    }

                    System.out.println("Selecciona la entrada:");
                    entradaSeleccionada = aux.Scanf.scanIntPositivo() - 1;

                    if (entradaSeleccionada < 0 || entradaSeleccionada >= entradasDisponibles.size()) {
                        App.clearConsole();
                        System.out.println("\nSelección inválida.");
                        break;
                    }

                    entradaCompra = entradasDisponibles.get(entradaSeleccionada);

                    boolean exitoCompra = transaccionMgr.comprarEntrada(correoUsuario, entradaCompra, tipoTransaccion);

                    if (exitoCompra) {
                        System.out.println("\nCompra realizada con éxito.");
                    } else {
                        System.out.println("\nNo se pudo realizar la compra. Revisa saldo o disponibilidad.");
                    }

                    break;
                case 2:
                    entradas = transaccionMgr.verEntradasUsuarioSinReventa(correoUsuario);
                    contador = 1;
                    App.clearConsole();

                    if (entradas.isEmpty()) {
                        System.out.println("\nNo tienes entradas disponibles para reventa.");
                        break;
                    }

                    System.out.println("\nTienes las siguientes entradas disponibles para reventa:");
                    for (Entrada entrada : entradas) {

                        System.out.println("\n" + contador + ".- La entrada pertenece al evento: "
                                + entrada.getNombreEvento() +
                                "\n\t Entrada de tipo: " + entrada.getTipo() +
                                "\n\t Precio original: " + entrada.getPrecio() + "\n");
                        contador++;
                    }

                    System.out.println("\nSelecciona el número de la entrada que deseas revender:");
                    entradaSeleccionada = aux.Scanf.scanIntPositivo() - 1;

                    if (entradaSeleccionada < 0 || entradaSeleccionada >= entradas.size()) {
                        System.out.println("\nSelección inválida.");
                        break;
                    }

                    Entrada entradaReventa = entradas.get(entradaSeleccionada);

                    float limiteReventaEvento = eventoMgr.getLimiteReventaEvento(entradaReventa.getNombreEvento());

                    System.out.println("\nIntroduce el precio de reventa (máximo permitido: " +
                            (entradaReventa.getPrecio() * (1 + limiteReventaEvento)) + "):");
                    float precioReventa = aux.Scanf.scanFloatPositivo();

                    if (precioReventa <= 0
                            || precioReventa > (entradaReventa.getPrecio() * (1 + limiteReventaEvento))) {
                        System.out.println("\nPrecio inválido. Debe estar dentro del límite de reventa.");
                        break;
                    }

                    Entrada nuevaReventa = EntradaFactory.createEntradaConCorreoVendedor(entradaReventa.getTipo(),
                            precioReventa, entradaReventa.getNombreEvento(), correoUsuario);

                    boolean publicada = transaccionMgr.publicarReventa(nuevaReventa, entradaReventa.getId());

                    if (publicada) {
                        System.out.println("\nEntrada publicada para reventa con éxito.");
                    } else {
                        System.out.println("\nError al publicar la entrada para reventa.");
                    }

                    break;
                case 3:
                    saldoActual = usuarioMgr.consultarMonedero(correoUsuario);
                    System.out.println("\nSaldo actual: " + saldoActual);
                    System.out.println("Introduce la cantidad a recargar:");
                    cantidad = aux.Scanf.scanFloatPositivo();

                    if (cantidad > 0) {
                        recargaMonedero = usuarioMgr.recargarMonedero(correoUsuario, cantidad);
                        if (recargaMonedero) {
                            saldoNuevo = usuarioMgr.consultarMonedero(correoUsuario);
                            System.out.println("\nRecarga realizada con éxito. Nuevo saldo: " + saldoNuevo);
                        } else {
                            System.out.println("\nError al recargar el monedero.");
                        }
                    } else {
                        System.out.println("\nCantidad inválida.");
                    }
                    break;
                case 4:
                    entradas = transaccionMgr.verEntradasUsuario(correoUsuario);
                    contador = 1;
                    App.clearConsole();
                    if (entradas.isEmpty()) {
                        System.out.println("\nNo tienes entradas compradas");
                        break;
                    }
                    System.out.println("\nTienes las siguientes entradas:");
                    for (Entrada entrada : entradas) {
                        System.out.println(
                                "\n" + contador + ".- La entrada pertenece al evento:" + entrada.getNombreEvento() +
                                        "\n\t Entrada de tipo:" + entrada.getTipo() +
                                        "\n\t Precio de la entrada: " + entrada.getPrecio() + "\n");
                        contador++;
                    }

                    break;
                case 5: 
                    System.out.println("\nLo sentimos, esta función no se ha implementado todavia");
                    break;
                case 6:
                    System.out.println("\nLo sentimos, esta función no se ha implementado todavia");
                    break;
                default:
                    System.out.println("\nLa opción seleccionada no existe");
                    break;
            }
        } while (opc != 0);
    }

    /**
     * Muestra un menú para seleccionar el tipo de transacción que el usuario desea realizar
     * al comprar una entrada. El usuario puede elegir entre compra primaria o secundaria.
     *
     * @return TipoTransaccion seleccionado por el usuario
     **/
    private static TipoTransaccion interfazSeleccionarTipoCompra() {
        int opc;
        TipoTransaccion tipoTransaccion = null;

        do {
            System.out.println("\nSelecciona el tipo de compra:" +
                    "\n1.- Compra Primaria" +
                    "\n2.- Compra Secundaria");

            opc = aux.Scanf.scanInt();

            switch (opc) {
                case 1:
                    tipoTransaccion = TipoTransaccion.VENTAPRIMARIA;
                    break;
                case 2:
                    tipoTransaccion = TipoTransaccion.VENTASECUNDARIA;
                    break;
                default:
                    System.out.println("\nLa opción seleccionada no existe");
                    break;
            }
        } while (opc != 1 && opc != 2);

        return tipoTransaccion;
    }
    
    /**
     * Muestra el menú de opciones disponibles para un organizador autenticado y gestiona
     * las acciones seleccionadas como publicar, modificar o cancelar eventos, y ver eventos.
     *
     * @param correoOrganizador El correo del organizador que ha iniciado sesión.
     **/
    public static void interfazOrganizador(String correoOrganizador) {
        int opc;
        int contador = 0;
        int eventoSeleccionado = 0;
        float precio = 0;
        int cantidad = 0;
        EventoMgr eventoMgr = EventoMgr.getInstance();
        TransaccionMgr transaccionMgr = TransaccionMgr.getInstance();
        Evento evento = new Evento();
        ArrayList<Entrada> entradas = new ArrayList<Entrada>();
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        do {
            System.out.println("\nSelecciona la acción que desees realizar:" +
                    "\n1.- Publicar Evento" +
                    "\n2.- Modificar Evento" +
                    "\n3.- Cancelar Evento" +
                    "\n4.- Ver mis eventos" +
                    "\n0.- Cerrar Sesión");

            opc = aux.Scanf.scanInt();


            App.clearConsole();

            switch (opc) {
                case 0:
                    System.out.println("\nCerrando sesión");
                    break;
                case 1:
                    Evento Evento = new Evento();

                    evento.setCorreoOrganizador(correoOrganizador);

                    System.out.println("\nIntroduce el nombre del evento:");
                    evento.setNombre(aux.Scanf.scanString());

                    System.out.println("\nIntroduce la descripción del evento:");
                    evento.setDescripcion(aux.Scanf.scanString());

                    System.out.println("\nIntroduce el lugar del evento:");
                    evento.setLugar(aux.Scanf.scanString());

                    System.out.println("\nIntroduce la fecha (formato yyyy-MM-dd):");
                    evento.setFecha(aux.Scanf.scanFechaFutura());

                    System.out.println("\nIntroduce el limite de reventa (0,%):");
                    evento.setLimiteReventa(aux.Scanf.scanFloatEntre0y1());

                    System.out.println("\nIntroduce la cantidad de entradas Generales:");
                    cantidad = aux.Scanf.scanIntPositivo();

                    System.out.println("\nIntroduce el precio de las entradas Generales:");
                    precio = aux.Scanf.scanFloatPositivo();

                    entradas.add(EntradaFactory.createEntradaGeneral(precio, evento.getNombre(), cantidad, correoOrganizador));

                    System.out.println("\nIntroduce la cantidad de entradas VIP:");
                    cantidad = aux.Scanf.scanIntPositivo();

                    System.out.println("\nIntroduce el precio de las entradas VIP:");
                    precio = aux.Scanf.scanFloatPositivo();

                    entradas.add(EntradaFactory.createEntradaVIP(precio, evento.getNombre(), cantidad, correoOrganizador));

                    System.out.println("\nIntroduce la cantidad de entradas Numeradas:");
                    cantidad = aux.Scanf.scanIntPositivo();

                    System.out.println("\nIntroduce el precio de las entradas Numeradas:");
                    precio = aux.Scanf.scanFloatPositivo();

                    entradas.add(EntradaFactory.createEntradaNumerada(precio, evento.getNombre(), cantidad, correoOrganizador));

                    boolean publicado = eventoMgr.publicarEvento(evento, entradas);

                    if (publicado) {
                        System.out.println("\nEvento publicado correctamente.");
                    } else {
                        System.out.println("\nError al publicar el evento.");
                    }
                    break;
                case 2:
                    System.out.println("Selecciona el evento que deseas modificar:");
                    eventos = eventoMgr.verEventosOrganizador(correoOrganizador);

                    evento = interfazSeleccionarEvento(eventos);

                    if(evento == null) {
                        break;
                    }

                    System.out.println("\nInformación del evento seleccionado:");
                    System.out.println("Nombre: " + evento.getNombre());
                    System.out.println("Descripción: " + evento.getDescripcion());
                    System.out.println("Lugar: " + evento.getLugar());
                    System.out.println("Fecha: " + evento.getFecha());
                    System.out.println("LimiteReventa: " + evento.getLimiteReventa());

                    System.out.println("\nIntroduce la nueva descripción del evento:");
                    evento.setDescripcion(aux.Scanf.scanString());

                    System.out.println("\nIntroduce el nuevo lugar del evento:");
                    evento.setLugar(aux.Scanf.scanString());

                    System.out.println("\nIntroduce la nueva fecha (formato yyyy-MM-dd):");
                    evento.setFecha(aux.Scanf.scanFechaFutura());

                    System.out.println("\nIntroduce el limite de reventa (0,%):");
                    evento.setLimiteReventa(aux.Scanf.scanFloatEntre0y1());

                    boolean modificado = eventoMgr.modificarEvento(evento);

                    if (modificado) {
                        System.out.println("\nEvento modificado correctamente.");
                    } else {
                        System.out.println(
                                "\nError al modificar el evento. Verifica que los datos introducidos sean correctos.");
                    }

                    break;
                case 3: 
                    System.out.println("\nSelecciona el evento que deseas cancelar:");
                    eventos = eventoMgr.verEventosOrganizador(correoOrganizador);

                    evento = interfazSeleccionarEvento(eventos);

                    if (evento == null) {
                        break;
                    }

                    transaccionMgr.devolverDinero(evento.getNombre());

                    transaccionMgr.eliminarTransacciones(evento.getNombre());

                    transaccionMgr.eliminarEntradas(evento.getNombre());

                    boolean cancelado = eventoMgr.cancelarEvento(evento.getNombre());

                    if (cancelado) {
                        System.out.println("\nEvento cancelado correctamente.");
                    } else {
                        System.out.println("\nNo se pudo cancelar el evento. ¿Existe con ese nombre?");
                    }

                    break;
                case 4:
                    System.out.println("\nEventos publicados por el organizador: " + correoOrganizador);
                    eventos = eventoMgr.verEventosOrganizador(correoOrganizador);

                    evento = interfazSeleccionarEvento(eventos);

                    if (evento == null) {
                        break;
                    }

                    System.out.println("\nInformación del evento seleccionado:");
                    System.out.println("Nombre: " + evento.getNombre());
                    System.out.println("Descripción: " + evento.getDescripcion());
                    System.out.println("Lugar: " + evento.getLugar());
                    System.out.println("Fecha: " + evento.getFecha());
                    System.out.println("LimiteReventa: " + evento.getLimiteReventa());

                    break;
                default:
                    System.out.println("\nLa opción seleccionada no existe");
                    break;
            }
        } while (opc != 0);
    }
    
     /**
     * Muestra una lista numerada de eventos y permite al usuario seleccionar uno de ellos por su posición.
     * @param eventos Lista de eventos disponibles para seleccionar.
     * @return El evento seleccionado por el usuario o null si la lista está vacía.
     */
    private static Evento interfazSeleccionarEvento(ArrayList<Evento> eventos) {
        int contador = 1, eventoSeleccionado = 0;
        Evento evento = new Evento();

        if (eventos.isEmpty()) {
            System.out.println("\nNo tienes eventos publicados");
            return null;
        }

        for (Evento eventoDisponible : eventos) {
            System.out.println("\n" + contador + ".- " + eventoDisponible.getNombre());
            contador++;
        }

        do {

            eventoSeleccionado = aux.Scanf.scanInt() - 1;

            if (eventoSeleccionado < 0 || eventoSeleccionado >= eventos.size()) {
                System.out.println("\nSelección inválida.");
            }

        } while (eventoSeleccionado < 0 || eventoSeleccionado >= eventos.size());

        evento = eventos.get(eventoSeleccionado);

        return evento;
    }

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
