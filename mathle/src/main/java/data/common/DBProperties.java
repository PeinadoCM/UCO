package data.common;

import java.io.InputStream;	
import java.io.IOException;
import java.util.Properties;

/**
 * Clase que gestiona la carga de los datos de la base de datos
 */
public class DBProperties {
    private Properties properties;

    /**
     * Constructor de la clase DBProperties.
     */
    public DBProperties() {

        properties = new Properties();
        
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public String getDBString(String key) {
        return properties.getProperty(key);
    }
}
