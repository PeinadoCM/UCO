package mgr;

import dto.Organizador;
import dto.Usuario;
import dao.OrganizadorDAO;
import dao.UsuarioDAO;


/**
 * Clase que gestiona la lógica de negocio relacionada con usuarios y organizadores.
 */
public class UsuarioMgr {
    
    private static UsuarioMgr instance;


    private UsuarioMgr() {}

    /**
     * Método para obtener la instancia única de UsuarioMgr (Singleton).
     * @return Instancia única de UsuarioMgr.
     */
	public static UsuarioMgr getInstance() {
		if(instance == null) {
			instance = new UsuarioMgr();
		}
		return instance;
	}

    /**
    * Registra un nuevo usuario en el sistema.
    * @param usuario Objeto Usuario con los datos del nuevo registro.
    * @return true si se registró correctamente, false en caso contrario.
    */
    public Boolean registrarse(Usuario usuario) {
        Boolean registrado = false;
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        registrado = usuarioDAO.insertarUsuario(usuario);

        return registrado;
    }


    /**
     * Verifica las credenciales de un usuario al iniciar sesión.
     * @param usuario Objeto Usuario con correo y contraseña.
     * @return true si las credenciales son válidas, false si no coinciden.
     */
    public Boolean iniciarSesionUsuario(Usuario usuario) {
        Boolean permitirAcceso = false;
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        permitirAcceso = usuarioDAO.validarUsuario(usuario);

        return permitirAcceso;
    }

    /**
     * Verifica las credenciales de un organizador al iniciar sesión.
     * @param organizador Objeto Organizador con correo y contraseña.
     * @return true si las credenciales son válidas, false si no coinciden.
     */
    public Boolean iniciarSesionOrganizador(Organizador organizador) {
        Boolean permitirAcceso = false;
        OrganizadorDAO OrganizadorDAO = new OrganizadorDAO();

        permitirAcceso = OrganizadorDAO.validarOrganizador(organizador);

        return permitirAcceso;
    }
    

    /**
     * Consulta el saldo actual del monedero de un usuario.
     * @param correo Correo del usuario.
     * @return Saldo actual como float.
     */
    public float consultarMonedero(String correo) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.consultarMonedero(correo);
    }

    /**
     * Recarga el monedero de un usuario con una cantidad positiva.
     * @param correo Correo del usuario.
     * @param cantidad Cantidad a recargar.
     * @return true si la recarga se realizó correctamente, false si falló.
     */
    public boolean recargarMonedero(String correo, float cantidad){
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.recargarMonedero(correo, cantidad);
    }
}
