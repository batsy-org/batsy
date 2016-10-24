package org.batsy.core.service;

import org.batsy.core.exception.BatsyException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by ufuk on 21.10.2016.
 */
public class PropertyService implements IBatsyService {

    private static Logger logger = Logger.getLogger(PropertyService.class.getSimpleName());

    private static final String PROPERTY_FILE_NAME = "batsy.properties";
    private static Properties properties = null;

    @Override
    public void start() throws BatsyException {
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);
            if (stream != null) {
                properties = new Properties();
                properties.load(stream);
            } else {
                logger.warning(PROPERTY_FILE_NAME + " not found in the classpath");
            }
        } catch (IOException ex) {
            throw new BatsyException(ex.getMessage(), ex);
        }
    }

    public static String getProperty(String key, String defaultValue) {
        if (isPropertyFileLoaded()) {
            return properties.getProperty(key, defaultValue);
        }
        throw new IllegalStateException("Property file not loaded!");
    }

    public static String getProperty(String key) {
        return getProperty(key, null);
    }

    private static boolean isPropertyFileLoaded() {
        return properties != null;
    }
}
