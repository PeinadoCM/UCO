package aux;

import java.io.InputStream;	
import java.io.IOException;
import java.util.Properties;

/**
 * Clase SQLProperties.
 * 
 * Esta clase gestiona la carga de las consultas SQL desde un archivo de propiedades externo.
 * Las consultas SQL se almacenan en un objeto {@link Properties}, el cual es cargado al
 * instanciar la clase, leyendo el archivo `sql.properties` ubicado dentro del classpath del proyecto.
 * La clase proporciona un método para obtener consultas SQL a través de una clave específica.
 */
public class SQLProperties {
    private Properties properties;

    /**
     * Constructor de la clase SQLProperties.
     * 
     * Este constructor inicializa el objeto {@link Properties} que contiene las consultas SQL
     * almacenadas en un archivo de propiedades externo. El archivo de propiedades se carga
     * desde el recurso "resources/sql.properties" dentro del classpath del proyecto. 
     * Si ocurre un error durante la carga del archivo, se captura una excepción {@link IOException}
     * y se imprime el seguimiento de la pila del error.
     */
    public SQLProperties() {

        properties = new Properties();
        
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("resources/sql.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene una consulta SQL asociada a una clave específica desde el archivo de propiedades.
     * 
     * Este método recupera una consulta SQL previamente almacenada en el archivo de propiedades,
     * utilizando la clave proporcionada como argumento. El archivo de propiedades debe contener
     * las consultas SQL asociadas a claves específicas, y este método devuelve el valor (consulta SQL)
     * correspondiente a la clave solicitada.
     * 
     * @param key La clave que identifica la consulta SQL en el archivo de propiedades.
     * @return La consulta SQL asociada a la clave proporcionada, o {@code null} si no se encuentra
     *         la clave en el archivo de propiedades.
     */
    public String getSQLQuery(String key) {
        return properties.getProperty(key);
    }
}
