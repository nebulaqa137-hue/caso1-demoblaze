package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility - ConfigManager
 * Lee configuracion desde config.properties
 */
public class ConfigManager {

    private static final Logger log = LogManager.getLogger(ConfigManager.class);
    private static Properties properties = new Properties();
    private static ConfigManager instance;

    private ConfigManager() {
        loadProperties();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                log.error("No se encontro config.properties");
                return;
            }
            properties.load(input);
            log.info("config.properties cargado correctamente");
        } catch (IOException e) {
            log.error("Error al cargar config.properties: " + e.getMessage());
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    // Shortcuts utiles
    public String getUrl()          { return get("url"); }
    public String getBrowser()      { return get("browser"); }
    public int getImplicitWait()    { return getInt("implicit.wait"); }
    public int getExplicitWait()    { return getInt("explicit.wait"); }
    public int getPageLoadTimeout() { return getInt("page.load.timeout"); }
    public String getValidUser()    { return get("valid.user"); }
    public String getValidPass()    { return get("valid.pass"); }
    public String getInvalidUser()  { return get("invalid.user"); }
    public String getInvalidPass()  { return get("invalid.pass"); }
    public String getBuyerName()    { return get("buyer.name"); }
    public String getBuyerCountry() { return get("buyer.country"); }
    public String getBuyerCity()    { return get("buyer.city"); }
    public String getBuyerCard()    { return get("buyer.card"); }
    public String getBuyerMonth()   { return get("buyer.month"); }
    public String getBuyerYear()    { return get("buyer.year"); }
}
