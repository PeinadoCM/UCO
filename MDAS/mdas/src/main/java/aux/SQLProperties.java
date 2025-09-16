package aux;

import java.io.InputStream;	
import java.io.IOException;
import java.util.Properties;

/**
 * Clase que gestiona la carga de las consultas SQL desde un archivo de propiedades externo.
 */
public class SQLProperties {
    private Properties properties;

    /**
     * Constructor de la clase SQLProperties.
     */
    public SQLProperties() {

        properties = new Properties();
        
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("sql.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene una consulta SQL asociada a una clave específica desde el archivo de propiedades.
     * 
     * @param key La clave que identifica la consulta SQL en el archivo de propiedades.
     * @return La consulta SQL asociada a la clave proporcionada.
     */
    public String getSQLQuery(String key) {
        return properties.getProperty(key);
    }
}
