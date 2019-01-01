package eu.mapidev.pi.htclient.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClientPropertiesUtil {

    static final String PROPERTIES_FILENAME = "application.properties";
    static final Properties CLIENT_PROPERTIES = readPropertiesFromResources();

    private static Properties readPropertiesFromResources() {
	Properties properties = new Properties();
	try (InputStream io = ClassLoader.getSystemClassLoader().getResourceAsStream(PROPERTIES_FILENAME)) {
	    properties.load(io);
	    Properties systemProperties = System.getProperties();
	    for (Object object : properties.keySet()) {
		if (systemProperties.containsKey(object)) {
		    properties.setProperty((String) object, (String) systemProperties.get(object));
		}
	    }
	} catch (IOException ex) {
	    throw new IllegalStateException("Cannot read properties!");
	}
	return properties;
    }

    public static String getProperty(String key) {
	return CLIENT_PROPERTIES.getProperty(key);
    }

}
