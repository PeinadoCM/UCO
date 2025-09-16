package dto;

/**
 * Clase factoría para crear objetos Entrada de forma simplificada.
 * Permite instanciar entradas de distintos tipos con distintos parámetros
 */
public class EntradaFactory {
    
    public static Entrada createEntrada(TipoEntrada tipoEntrada, float precio, String nombreEvento) {
        return new Entrada(tipoEntrada, precio, nombreEvento);
    }

    public static Entrada createEntradaConCorreoVendedor(TipoEntrada tipoEntrada, float precio, String nombreEvento,
            String correoVendedor) {
        return new Entrada(tipoEntrada, precio, nombreEvento, correoVendedor);
    }

    public static Entrada createEntradaConId(int id, TipoEntrada tipoEntrada, float precio, String nombreEvento,
            String correoVendedor) {
        return new Entrada(id, tipoEntrada, precio, nombreEvento, correoVendedor);
    }

    public static Entrada createEntradaGeneral(float precio, String nombreEvento, int cantidad, String correoVendedor) {
        return new Entrada(TipoEntrada.GENERAL, precio, nombreEvento, cantidad, correoVendedor);
    }

    public static Entrada createEntradaVIP(float precio, String nombreEvento, int cantidad, String correoVendedor) {
        return new Entrada(TipoEntrada.VIP, precio, nombreEvento, cantidad, correoVendedor);
    }

    public static Entrada createEntradaNumerada(float precio, String nombreEvento, int cantidad, String correoVendedor) {
        return new Entrada(TipoEntrada.NUMERADA, precio, nombreEvento, cantidad, correoVendedor);
    }
}
